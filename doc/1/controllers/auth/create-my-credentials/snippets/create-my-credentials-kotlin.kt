kuzzle.authController.login("local", HashMap<String, Any?>().apply {
  put("username", "foo")
  put("password", "bar")
}).get()

kuzzle.authController.createMyCredentials("other", HashMap<String, Any?>().apply {
  put("username", "foo2")
  put("password", "bar2")
}).get()
