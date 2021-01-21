package io.kuzzle.sdk.controllers

import io.kuzzle.sdk.Kuzzle
import io.kuzzle.sdk.coreClasses.json.JsonSerializer
import io.kuzzle.sdk.coreClasses.maps.KuzzleMap
import io.kuzzle.sdk.coreClasses.responses.Response
import io.kuzzle.sdk.handlers.NotificationHandler
import io.kuzzle.sdk.protocol.ProtocolState
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

class RealtimeController(kuzzle: Kuzzle) : BaseController(kuzzle) {
    private inner class Subscription(
        val index: String,
        val collection: String,
        val filter: ConcurrentHashMap<String, Any>,
        val handler: (Response) -> Unit,
        val scope: String,
        val users: String,
        val subscribeToSelf: Boolean,
        val volatile: ConcurrentHashMap<String?, Any?>
    )

    init {
        kuzzle.protocol.addListener("unhandledResponse") {
            val response = Response().apply {
                fromMap(JsonSerializer.deserialize(it) as ConcurrentHashMap<String?, Any?>)
            }

            if (response.error != null && response.error!!.id.equals("security.token.expired")) {
                kuzzle.protocol.trigger("tokenExpired")
            } else {
                var sdkInstanceId = ""
                if (response.Volatile != null) {
                    sdkInstanceId = response.Volatile!!["sdkInstanceId"].toString()
                }

                val subs: ArrayList<Subscription>? = currentSubscriptions[response.room]
                if (subs != null) {
                    val instanceId = sdkInstanceId
                    subs.forEach {
                        if (instanceId == kuzzle.instanceId && it.subscribeToSelf || instanceId != kuzzle.instanceId) {
                            it.handler(response)
                        }
                    }
                }
            }
        }

        kuzzle.protocol.addListener("networkStateChange") {
            if (it == ProtocolState.CLOSE.toString()) {
                currentSubscriptions.clear()
            }
        }
    }

    fun count(roomId: String): CompletableFuture<Int> {
        return kuzzle
            .query(
                KuzzleMap().apply {
                    put("controller", "realtime")
                    put("action", "count")
                    put("body", KuzzleMap().put("roomId", roomId))
                }
            )
            .thenApplyAsync { response -> (response.result as KuzzleMap?)!!.getNumber("count")?.toInt() }
    }

    fun publish(index: String, collection: String, message: ConcurrentHashMap<String?, Any?>): CompletableFuture<Void> {
        return kuzzle
            .query(
                KuzzleMap().apply {
                    put("controller", "realtime")
                    put("action", "publish")
                    put("index", index)
                    put("collection", collection)
                    put("body", KuzzleMap().put("message", message))
                }
            )
            .thenApplyAsync { null }
    }

    fun renewSubscriptions() {
        for ((key, value) in subscriptionsCache) {
            (value).forEach(
                Consumer { subscription: Subscription ->
                    subscribe(
                        subscription.index,
                        subscription.collection,
                        subscription.filter,
                        subscription.scope,
                        subscription.users,
                        subscription.subscribeToSelf,
                        subscription.volatile,
                        subscription.handler
                    )
                }
            )
            subscriptionsCache[key]!!.clear()
        }
    }

    fun subscribe(
        index: String?,
        collection: String?,
        filters: ConcurrentHashMap<String, Any>,
        scope: String = "all",
        users: String = "all",
        subscribeToSelf: Boolean = true,
        volatiles: ConcurrentHashMap<String?, Any?> = ConcurrentHashMap(),
        handler: (Response) -> Unit
    ): CompletableFuture<String> {
        val query: KuzzleMap = KuzzleMap().apply {
            put("controller", "realtime")
            put("action", "subscribe")
            put("index", index)
            put("collection", collection)
            put("body", filters)
            put("volatile", volatiles)
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response ->
                val channel = (response.result as ConcurrentHashMap<*, *>)["channel"].toString()
                val subscription = Subscription(
                    index!!,
                    collection!!,
                    filters,
                    handler,
                    scope,
                    users,
                    subscribeToSelf,
                    volatiles
                )
                if (currentSubscriptions[channel] == null) {
                    val item = ArrayList<Subscription>()
                    item.add(subscription)
                    currentSubscriptions[channel] = item
                    subscriptionsCache[channel] = item
                } else {
                    currentSubscriptions[channel]!!.add(subscription)
                    subscriptionsCache[channel]!!.add(subscription)
                }
                (response.result as ConcurrentHashMap<*, *>)["roomId"].toString()
            }
    }

    /**
     For JAVA
     */
    @JvmOverloads
    fun subscribe(
        index: String?,
        collection: String?,
        filters: ConcurrentHashMap<String, Any>,
        scope: String = "all",
        users: String = "all",
        subscribeToSelf: Boolean = true,
        volatiles: ConcurrentHashMap<String?, Any?> = ConcurrentHashMap(),
        handler: NotificationHandler
    ): CompletableFuture<String> {
        return subscribe(
            index,
            collection,
            filters,
            scope,
            users,
            subscribeToSelf,
            volatiles
        ) {
            handler.run(it)
        }
    }

    fun unsubscribe(roomId: String): CompletableFuture<Void> {
        return kuzzle
            .query(
                KuzzleMap().apply {
                    put("controller", "realtime")
                    put("action", "unsubscribe")
                    put("body", KuzzleMap().put("roomId", roomId))
                }
            )
            .thenApplyAsync { _ ->
                var subs: ArrayList<Subscription>? = currentSubscriptions[roomId]
                if (subs != null) {
                    currentSubscriptions[roomId]!!.clear()
                }
                subs = subscriptionsCache[roomId]
                if (subs != null) {
                    subscriptionsCache[roomId]!!.clear()
                }
                null
            }
    }

    private val currentSubscriptions = ConcurrentHashMap<String, ArrayList<Subscription>>()
    private val subscriptionsCache = ConcurrentHashMap<String, ArrayList<Subscription>>()
}
