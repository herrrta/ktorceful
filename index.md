# Ktorceful
A simple solution to create reusable class based routing for Ktor

Avoid needing to type out repetitive routing dsl!

```kotlin
// Create your class based view to handle all your required HTTP methods
@Resource("user")
class UserRoute: Get, Post, Delete {
    override suspend fun get(call: RoutingCall) {
        // ...
    }

    override suspend fun post(call: RoutingCall) {
        // ...
    }

    override suspend fun delete(call: RoutingCall) {
        // ...
    }
}


fun Application.module() {
    install(Resources)
    // ...
    
    // Create routing using the included functions
    createBasicRoute<UserRoute>()
}
```

Any future changes to your route can all be done within your class!
