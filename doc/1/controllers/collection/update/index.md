---
code: true
type: page
title: update
description: Update the collection mapping
---

# update

<SinceBadge version="Kuzzle 2.1.0" />

You can define the collection [dynamic mappings policy](/core/2/guides/essentials/database-mappings#dynamic-mapping-policy) by setting the `dynamic` field to the desired value.

You can define [collection additional metadata](/core/2/guides/essentials/database-mappings#collection-metadata) within the `_meta` root field.

<SinceBadge version="Kuzzle 2.2.0" />

You can also provide Elasticsearch [index settings](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/index-modules.html#index-modules-settings) when creating a new collection.

<br/>
:::: tabs
::: tab Java

## Arguments

```java
public CompletableFuture<ConcurrentHashMap<String, Object>> update(
      final String index,
      final String collection,
      final ConcurrentHashMap<String, Object> definition)

```

<br/>

| Arguments    | Type              | Description                                                 |
|--------------|-------------------|-------------------------------------------------------------|
| `index`      | <pre>String</pre> | Index name                                                  |
| `collection` | <pre>String</pre> | Collection name                                             |
| `definition` | <pre>ConcurrentHashMap<String, Object></pre> | Describes the collection mappings and the ES index settings |

### definition

An object containing:
 - [collection mappings](/core/2/guides/essentials/database-mappings).
 - Elasticsearch [index settings](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/index-modules.html#index-modules-settings)


```java
{
  "mappings"={
    "properties"={
      "field1"={ "type"="text" },
      "field2"={
        "properties"={
          "nestedField"={ "type": "keyword" }
        }
      }
    }    
  },
  "settings"={
    // index settings (e.g. analyzers)
  }
};
```

## Usage

<<< ./snippets/update-java.java

:::
::: tab Kotlin

## Arguments

```kotlin
fun update(
    index: String,
    collection: String,
    definition: ConcurrentHashMap<String, Any?>
  ): CompletableFuture<Void>
```

<br/>

| Arguments    | Type              | Description                                                 |
|--------------|-------------------|-------------------------------------------------------------|
| `index`      | <pre>String</pre> | Index name                                                  |
| `collection` | <pre>String</pre> | Collection name                                             |
| `definition` | <pre>ConcurrentHashMap<String, Any?></pre> | Describes the collection mappings and the ES index settings |

### definition

An object containing:
 - [collection mappings](/core/2/guides/essentials/database-mappings).
 - Elasticsearch [index settings](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/index-modules.html#index-modules-settings)


```kotlin
{
  "mappings"={
    "properties"={
      "field1"={ "type"="text" },
      "field2"={
        "properties"={
          "nestedField"={ "type": "keyword" }
        }
      }
    }    
  },
  "settings"={
    // index settings (e.g. analyzers)
  }
};
```

## Usage

<<< ./snippets/update-kotlin.kt

:::
::::