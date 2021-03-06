---
code: true
type: page
title: write
description: Creates or replaces a document directly into the storage engine.
---

# write

Create or replace a document directly into the storage engine.

:::: tabs
::: tab Java

## Arguments

```java
public CompletableFuture<Map<String, Object>> write(
  String index,
  String collection,
  Map<String, Object> content,
  String id,
  Bool notify,
  Bool waitForRefresh
) throws NotConnectedException, InternalException

public CompletableFuture<Map<String, Object>> write(
  String index,
  String collection,
  Map<String, Object> content,
  String id,
  Bool notify
) throws NotConnectedException, InternalException

public CompletableFuture<Map<String, Object>> write(
  String index,
  String collection,
  Map<String, Object> content,
  String id
) throws NotConnectedException, InternalException

public CompletableFuture<Map<String, Object>> write(
  String index,
  String collection,
  Map<String, Object> content
) throws NotConnectedException, InternalException
```

| Argument     | Type               | Description                 |
|--------------|--------------------|-----------------------------|
| `index`      | <pre>String</pre>  | Index name                  |
| `collection` | <pre>String</pre>  | Collection name             |
| `content`    | <pre>Map<String, Object></pre> | Document content to create. |
| `id`         | <pre>String</pre><br>(`null`) | set the document unique ID to the provided value, instead of auto-generating a random ID |
| `waitForRefresh` | <pre>Bool</pre><br>(`false`)  | If set to true, Kuzzle will not respond until the created/replaced documents are indexed |
| `notify`         | <pre>Bool</pre><br>(`false`)  | If set to true, Kuzzle will trigger realtime notifications                               |

## Return

Return a Map<String, Object> with the following properties:

| Property   | Type               | Description                                     |
|------------|--------------------|-------------------------------------------------|
| `_id`      | <pre>String</pre>  | Created document unique identifier.             |
| `_source`  | <pre>Map<String, Object></pre> | Document content.                               |
| `_version` | <pre>Int</pre>     | Version number of the document                  |

## Usage

<<< ./snippets/write-java.java

:::
::: tab Kotlin

## Arguments

```kotlin
fun write(
  index: String,
  collection: String,
  content: Map<String, Any?>,
  id: String? = null,
  notify: Boolean? = null,
  waitForRefresh: Boolean? = null):
  CompletableFuture<Map<String, Any?>>
```

| Argument     | Type               | Description                 |
|--------------|--------------------|-----------------------------|
| `index`      | <pre>String</pre>  | Index name                  |
| `collection` | <pre>String</pre>  | Collection name             |
| `content`    | <pre>Map<String, Any?></pre> | Document content to create. |


### Options

| Property         | Type                          | Description                                                                              |
|------------------|-------------------------------|------------------------------------------------------------------------------------------|
| `id`     | <pre>String</pre><br>(`null`) | set the document unique ID to the provided value, instead of auto-generating a random ID |
| `waitForRefresh` | <pre>Boolean</pre><br>(`false`)  | If set to true, Kuzzle will not respond until the created/replaced documents are indexed |
| `notify`         | <pre>Boolean</pre><br>(`false`)  | If set to true, Kuzzle will trigger realtime notifications                               |

## Return

Return a Map<Strin, Any?> with the following properties:

| Property   | Type               | Description                                     |
|------------|--------------------|-------------------------------------------------|
| `_id`      | <pre>String</pre>  | Created document unique identifier.             |
| `_source`  | <pre>Map<String, Any?></pre> | Document content.                               |
| `_version` | <pre>Int</pre>     | Version number of the document                  |

## Usage

<<< ./snippets/write-kotlin.kt

:::
::::
