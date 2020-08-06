---
code: true
type: page
title: MWrite
description: Creates or replaces multiple documents directly into the storage engine.
---

# MWrite

This is a low level route intended to bypass Kuzzle actions on document creation, notably:
  - check [document validity](/core/2/guides/essentials/data-validation),
  - add [kuzzle metadata](/core/2/guides/essentials/document-metadata),
  - trigger [realtime notifications](/core/2/guides/essentials/real-time) (unless asked otherwise)

:::: tabs
::: tab Java

## Arguments

```java
public CompletableFuture<ConcurrentHashMap<String, Any?>> importData (
  String index,
  String collection,
  ArrayList<ConcurrentHashMap<String, Object>> bulkData
) throws NotConnectedException, InternalException
```

| Argument     | Type              | Description                                                                                                                      |
|--------------|-------------------|----------------------------------------------------------------------------------------------------------------------------------|
| `index`      | <pre>String</pre> | Index name                                                                                                                       |
| `collection` | <pre>String</pre> | Collection name                                                                                                                  |
| `documents`  | <pre>ArrayList<ConcurrentHashMap<String, Object>></pre> | An array of JObject representing the documents|

### documents

An array of `ConcurrentHashMap<String, Object>`. Each object describes a document to create or replace, by exposing the following properties:
  - `_id`: document unique identifier (optional)
  - `body`: document content

### Options

| Property         | Type                         | Description                                                                              |
|------------------|------------------------------|------------------------------------------------------------------------------------------|
| `waitForRefresh` | <pre>Bool</pre><br>(`false`) | If set to true, Kuzzle will not respond until the created/replaced documents are indexed |
| `notify`         | <pre>Bool</pre><br>(`false`) | If set to true, Kuzzle will trigger realtime notifications                               |

## Return

Returns a `ConcurrentHashMap<String, Object>` containing 2 arrays: `successes` and `errors`

Each created or replaced document is an object of the `successes` array with the following properties:

| Name      | Type              | Description                                            |
| --------- | ----------------- | ------------------------------------------------------ |
| `_id`      | <pre>String</pre> | Document ID                     |
| `_version` | <pre>Integer</pre> | Version of the document in the persistent data storage |
| `_source`  | <pre>ConcurrentHashMap<String, Object></pre> | Document content                                       |
| `created`  | <pre>Bool</pre> | True if the document was created |

Each errored document is an object of the `errors` array with the following properties:

| Name      | Type              | Description                                            |
| --------- | ----------------- | ------------------------------------------------------ |
| `document`  | <pre>ConcurrentHashMap<String, Object></pre> | Document that cause the error                                       |
| `status` | <pre>Integer</pre> | HTTP error status |
| `reason`  | <pre>String</pre> | Human readable reason |

## Usage

<<< ./snippets/mwrite-java.java

:::
::: tab Kotlin

## Arguments

```kotlin
fun importData(
  index: String,
  collection: String,
  bulkData: ArrayList<ConcurrentHashMap<String, Any?>>
): CompletableFuture<ConcurrentHashMap<String, Any?>>
```

| Argument     | Type              | Description                                                                                                                      |
|--------------|-------------------|----------------------------------------------------------------------------------------------------------------------------------|
| `index`      | <pre>String</pre> | Index name                                                                                                                       |
| `collection` | <pre>String</pre> | Collection name                                                                                                                  |
| `documents`  | <pre>ArrayList<ConcurrentHashMap<String, Any?>></pre> | An array of JObject representing the documents|

### documents

An array of `ConcurrentHashMap<String, Any?>`. Each object describes a document to create or replace, by exposing the following properties:
  - `_id`: document unique identifier (optional)
  - `body`: document content

### Options

| Property         | Type                         | Description                                                                              |
|------------------|------------------------------|------------------------------------------------------------------------------------------|
| `waitForRefresh` | <pre>Bool</pre><br>(`false`) | If set to true, Kuzzle will not respond until the created/replaced documents are indexed |
| `notify`         | <pre>Bool</pre><br>(`false`) | If set to true, Kuzzle will trigger realtime notifications                               |

## Return

Returns a `ConcurrentHashMap<String, Object>` containing 2 arrays: `successes` and `errors`

Each created or replaced document is an object of the `successes` array with the following properties:

| Name      | Type              | Description                                            |
| --------- | ----------------- | ------------------------------------------------------ |
| `_id`      | <pre>String</pre> | Document ID                     |
| `_version` | <pre>Int</pre> | Version of the document in the persistent data storage |
| `_source`  | <pre><ConcurrentHashMap<String, Any?></pre> | Document content                                       |
| `created`  | <pre>Bool</pre> | True if the document was created |

Each errored document is an object of the `errors` array with the following properties:

| Name      | Type              | Description                                            |
| --------- | ----------------- | ------------------------------------------------------ |
| `document`  | <pre>ConcurrentHashMap<String, Any?></pre> | Document that cause the error                                       |
| `status` | <pre>Int</pre> | HTTP error status |
| `reason`  | <pre>String</pre> | Human readable reason |

## Usage

<<< ./snippets/mwrite-kotlin.kt

:::
::::