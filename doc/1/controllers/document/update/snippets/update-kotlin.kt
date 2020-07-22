val content: ConcurrentHashMap<String, Any?> =
  ConcurrentHashMap<String, Any?>().apply {
    put("name", "Johny")
  }


val result: ConcurrentHashMap<String, ArrayList<Any>> =
  kuzzle
  .documentController
  .update("nyc-open-data", "yellow-taxi", "some-id", content)
  .get()
