# Overview

You can use the [Ktorceful](../api/ktorceful-annotation/dev.herrrta.ktorceful.annotation/-ktorceful/index.html) annotation to automatically create ktor routing for your classes.

!!! warning "To use the ktorceful annotation, you must install Ktor Resource plugin"

## Example class

```kotlin
@Ktorceful
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
All annotated classes will have their routing generated automatically.
You can call them all once using the `allKtorcefulRoutes()` extension.

```kotlin
fun Application.module() {
    install(Resources)

    allKtorcefulRoutes() // You can call this once for all routes!
    
    // Or if you need more control you can use the following format
    // ktorceful{ClassName}Route() 
    ktorcefulYourAPIRoute()
}
```

