---
code: false
type: page
title: Kotlin
description: Kotlin Getting started
order: 99
---

# Getting Started

In this tutorial you will learn how to install the Kuzzle **Jvm SDK**.
This page shows examples of scripts that **store** documents in Kuzzle, and of scripts that subcribe to real-time **notifications** for each new document created.

::: success
Before proceeding, please make sure your system meets the following requirements:

- **Oracle JDK or OpenJDK** version 8 or higher ([OpenJDK installation instructions](https://openjdk.java.net/install/))
- A running Kuzzle Server ([Kuzzle installation guide](/core/2/guides/essentials/installing-kuzzle))

:::


::: info
Having trouble? Get in touch with us on [Discord](http://join.discord.kuzzle.io)!
:::

## Installation

You can find the SDK JARs directly on [bintray](https://bintray.com/kuzzle/maven/sdk-jvm). Download and add it to your classpath.

::: info
The following examples are made to be executed without any IDE.
If you're using Eclipse, IntelliJ or another Java IDE, you need to add the SDK as a project dependency in your classpath.
:::

### Installing on Android using gradle

In your app build.gradle add the following line and sync.

  `implementation "io.kuzzle:kuzzle-sdk-jvm:1.0.0"`

## First connection

Initialize a new Java project, create a `GettingStartedFirstConnection.kt` file and start by adding the code below:

<<< ./snippets/firstconnection.kt

This program initializes the Kuzzle Server storage by creating a index, and a collection inside it
Run the program with the following command:

```bash
$ javac -classpath ./path/to/the/sdk.jar GettingStartedFirstConnection.kt
$ java -classpath .:./path/to/the/sdk.jar GettingStartedFirstConnection
Connected!
Index nyc-open-data created!
Collection yellow-taxi created!
```

Congratulations, you performed your first connection to Kuzzle Server via a Kotlin program.
You now know how to:

- Instantiate Kuzzle SDK and connect to Kuzzle Server using a specific protocol (here `websocket`)
- Create a index
- Create a collection within an existing index

## Create your first document

Now that you successfully connected to your Kuzzle Server instance, and created an index and a collection, it's time to manipulate some data.

Here is how Kuzzle structures its storage space:

- indexes contain collections
- collections contain documents
  Create a `GettingStartedStorage.kt` file in the playground and add this code:

<<< ./snippets/document.kt

As you did before, build and run your program:

```bash
$ javac -classpath ./path/to/the/sdk.jar  GettingStartedStorage.kt
$ java -classpath .:./path/to/the/sdk.jar GettingStartedStorage
Connected!
New document added to yellow-taxi collection!
```

You can perform other actions such as [delete](/sdk/jvm/1/controllers/document/delete), [replace](/sdk/jvm/1/controllers/document/replace) or [search](/sdk/jvm/1/controllers/document/search) documents. There are also other ways to interact with Kuzzle like our [Admin Console](/core/2/guides/essentials/admin-console), the [Kuzzle HTTP API](/core/2/api/essentials/connecting-to-kuzzle) or by using your [own protocol](/core/2/protocols/essentials/getting-started).

Now you know how to:

- Store documents in a Kuzzle Server, and access those

## Subscribe to realtime document notifications (pub/sub)

Time to use realtime with Kuzzle. Create a new file `GettingStartedRealtime.kt` with the following code:

<<< ./snippets/realtime.kt

This program subscribes to changes made to documents with a `license` field set to `B`, within the `yellow-taxi` collection. Whenever a document matching the provided filters changes, a new notification is received from Kuzzle.

Build and run your program:

```bash
$ javac -classpath ./path/to/the/sdk.jar GettingStartedRealtime.kt
$ java -classpath .:./path/to/the/sdk.jar GettingStartedRealtime
Connected!
Successfully subscribing!
New document added to yellow-taxi collection!
New created document notification: [Document content as ConcurrentHashMap]
```

You should see document content as a `ConcurrentHashMap`.

Now, you know how to:

- Create realtime filters
- Subscribe to notifications

## Where do we go from here?

Now that you're more familiar with the Jvm SDK, you can dive even deeper to learn how to leverage its full capabilities:

- discover what this SDK has to offer by browsing other sections of this documentation
- learn how to use [Koncorde](/core/2/guides/cookbooks/realtime-api) to create incredibly fine-grained and blazing-fast subscriptions
- follow our guide to learn how to perform [basic authentication](/core/2/guides/essentials/user-authentication#local-strategy)
- follow our guide to learn how to [manage users and how to set up fine-grained access control](/core/2/guides/essentials/security)