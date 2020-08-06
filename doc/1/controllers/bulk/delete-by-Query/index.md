---
code: true
type: page
title: DeleteByQuery
description: Deletes documents matching query.
---

# DeleteByQueryAsync

Deletes documents matching the provided search query.

Kuzzle uses the [ElasticSearch Query DSL](https://www.elastic.co/guide/en/elasticsearch/reference/5.6/query-dsl.html) syntax.

:::: tabs
::: tab Java

## Arguments

```java
public CompletableFuture<Int> deleteByQuery(
  String index,
  String collection,
  ConcurrentHashMap<String, Object> searchQuery,
  Boolean waitForRefresh
) throws NotConnectedException, InternalException

public CompletableFuture<Int> deleteByQuery(
  String index,
  String collection,
  ConcurrentHashMap<String, Object> searchQuery
) throws NotConnectedException, InternalException
```

<br/>

| Argument     | Type                                 | Description                             |
| ------------ | ------------------------------------ | --------------------------------------- |
| `index`      | <pre>String</pre>        | Index name                              |
| `collection` | <pre>String</pre>        | Collection name                         |
| `searchQuery`      | <pre>ConcurrentHashMap<String, Any?></pre>        | JSON representing the query to match |
| `waitForRefresh` | <pre>Bool</pre><br>(`false`)  | If set to true, Kuzzle will not respond until the delete documents are indexed |


## Return

An Int containing the number of deleted documents.

## Exceptions

Throws a `KuzzleException` if there is an error. See how to [handle errors](/sdk/csharp/2/essentials/error-handling).

## Usage

<<< ./snippets/delete-by-query-java.java

:::
::: tab Kotlin

## Arguments

```kotlin
fun deleteByQuery(
  index: String,
  collection: String,
  searchQuery: ConcurrentHashMap<String, Any?>,
  waitForRefresh: Boolean? = null
): CompletableFuture<Int>
```

<br/>

| Argument     | Type                                 | Description                             |
| ------------ | ------------------------------------ | --------------------------------------- |
| `index`      | <pre>String</pre>        | Index name                              |
| `collection` | <pre>String</pre>        | Collection name                         |
| `searchQuery`      | <pre>ConcurrentHashMap<String, Any?></pre>        | JSON representing the query to match |
| `waitForRefresh` | <pre>Boolean</pre><br>(`false`)  | If set to true, Kuzzle will not respond until the delete documents are indexed |


## Return

An Int containing the number of deleted documents.

## Exceptions

Throws a `KuzzleException` if there is an error. See how to [handle errors](/sdk/csharp/2/essentials/error-handling).

## Usage

<<< ./snippets/delete-by-query-kotlin.kt

:::
::::