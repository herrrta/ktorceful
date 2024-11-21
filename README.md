[![Maven metadata URL](https://img.shields.io/maven-central/v/dev.herrrta.ktorceful/core)](https://repo.maven.apache.org/maven2/dev/herrrta/ktorceful/)
![GitHub License](https://img.shields.io/github/license/herrrta/ktorceful)

# Ktorceful

Create class based routing for Ktor

Avoid writing out functions and declaring routing verbs in dsl!

See the [project website](https://herrrta.github.io/ktorceful/) for documentation and APIs


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