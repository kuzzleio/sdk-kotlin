---
code: true
type: page
title: Connect
description: Connects the SDK to Kuzzle
---

# Connect

Connects to Kuzzle using the underlying protocol `connect` method. 

Subsequent calls have no effect if the SDK is already connected.

:::: tabs
::: tab Java
## Arguments

```java
public void connect() throws Exception;
```

## Usage

<<< ./snippets/connect-java.java
:::
::: tab Kotlin
## Arguments

```kotlin
fun connect()
```

## Usage

<<< ./snippets/connect-kotlin.kt
:::
::::
