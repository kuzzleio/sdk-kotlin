package io.kuzzle.sdk.controllers

import io.kuzzle.sdk.Kuzzle
import io.kuzzle.sdk.coreClasses.maps.KuzzleMap
import io.kuzzle.sdk.coreClasses.responses.Response
import java.util.*
import java.util.concurrent.CompletableFuture

class AuthController(kuzzle: Kuzzle) : BaseController(kuzzle) {

    fun checkToken(
        token: String
    ): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "checkToken")
            put(
                "body",
                KuzzleMap().apply {
                    put("token", token)
                }
            )
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response -> response.result as Map<String, Any?> }
    }

    fun createMyCredentials(
        strategy: String,
        credentials: Map<String, Any?>
    ): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "createMyCredentials")
            put("body", credentials)
            put("strategy", strategy)
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response -> response.result as Map<String, Any?> }
    }

    fun credentialsExist(strategy: String): CompletableFuture<Boolean> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "credentialsExist")
            put("strategy", strategy)
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response -> response.result as Boolean? }
    }

    fun deleteMyCredentials(strategy: String): CompletableFuture<Void> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "deleteMyCredentials")
            put("strategy", strategy)
        }
        return kuzzle.query(query).thenRunAsync {}
    }

    fun getCurrentUser(): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "getCurrentUser")
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response ->
                response.result as Map<String, Any?>
            }
    }

    fun getMyCredentials(
        strategy: String
    ): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "getMyCredentials")
            put("strategy", strategy)
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response ->
                response.result as Map<String, Any?>
            }
    }

    fun getMyRights(): CompletableFuture<ArrayList<Any>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "getMyRights")
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response ->
                KuzzleMap
                    .from(response.result as Map<String?, Any?>)
                    .getArrayList("hits") as ArrayList<Any>
            }
    }

    fun getStrategies(): CompletableFuture<ArrayList<String>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "getStrategies")
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response -> response.result as ArrayList<String> }
    }

    @JvmOverloads
    fun login(
        strategy: String,
        credentials: Map<String, Any?>?,
        expiresIn: String? = null
    ): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "login")
            put("strategy", strategy)
            put("body", credentials)
        }
        if (expiresIn != null) {
            query["expiresIn"] = expiresIn
        }
        return kuzzle.query(query).thenApplyAsync { response ->
            val map: KuzzleMap = KuzzleMap
                .from(response.result as Map<String?, Any?>)
            kuzzle.authenticationToken = map.getString("jwt")
            if (map.getString("_id") != null) {
                kuzzle.protocol.trigger("loginAttempt", "true")
            } else {
                kuzzle.protocol.trigger("loginAttempt", "false")
            }
            map as Map<String, Any?>
        }
    }

    fun logout(): CompletableFuture<Response> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "logout")
        }
        return kuzzle.query(query)
    }

    @JvmOverloads
    fun refreshToken(
        expiresIn: String? = null
    ): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "refreshToken")
            put("expiresIn", expiresIn)
        }
        return kuzzle.query(query).thenApplyAsync { response ->
            val map: KuzzleMap = KuzzleMap
                .from(response.result as Map<String?, Any?>)
            kuzzle.authenticationToken = map.getString("jwt")
            map as Map<String, Any?>
        }
    }

    fun updateMyCredentials(
        strategy: String,
        credentials: Map<String, Any?>
    ): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "updateMyCredentials")
            put("strategy", strategy)
            put("body", credentials)
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response ->
                response.result as Map<String, Any?>
            }
    }

    fun updateSelf(
        content: Map<String, Any?>
    ): CompletableFuture<Map<String, Any?>> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "updateSelf")
            put("body", content)
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response ->
                response.result as Map<String, Any?>
            }
    }

    fun validateMyCredentials(
        strategy: String,
        credentials: Map<String, Any?>
    ): CompletableFuture<Boolean> {
        val query = KuzzleMap().apply {
            put("controller", "auth")
            put("action", "validateMyCredentials")
            put("strategy", strategy)
            put("body", credentials)
        }
        return kuzzle
            .query(query)
            .thenApplyAsync { response -> response.result as Boolean }
    }
}
