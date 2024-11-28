# Overview

To create a basic rest api you can implement any HTTP Method interfaces required for your api

All ktor restful methods are [available as interfaces](../api/ktorceful-core/dev.herrrta.ktorceful.core.interfaces/index.html).

## Example class

```kotlin
@Resource("/your-api")
class YourAPI: Get, Post {
    override fun get(call: RoutingCall) {
        //...
    }

    override fun post(call: RoutingCall) {
        //...
    }
}
```

## Define route

```kotlin
fun Application.module() {
    install(Resources)

    createRoutes<YourAPI>() // (1)!
}
```

1.  Automatically handles defining your route