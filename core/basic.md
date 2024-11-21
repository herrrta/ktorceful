[![Maven metadata URL](https://img.shields.io/maven-central/v/dev.herrrta.ktorceful/core)](https://repo.maven.apache.org/maven2/dev/herrrta/ktorceful/)
![GitHub License](https://img.shields.io/github/license/herrrta/ktorceful)

# Ktorceful
A simple solution to easily create reusable class based routing.

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



## Setup
Add ktorceful-core dependency to your application

```kotlin
dependencies {
    implementation("dev.herrrta.ktorceful:core:x.x.x")
    
    // OPTIONAL! if using authentication in ktor
    implementation("dev.herrrta.ktorceful:auth:x.x.x")
}
```


### If you are using version catalogs:

```toml
[versions]
ktorceful = "x.x.x"
...

[libraries]
ktorceful-core = { module = "dev.herrrta.ktorceful:core", version.ref = "ktorceful" }

# OPTIONAL! if using authentication in ktor
ktorceful-auth = { module = "dev.herrrta.ktorceful:auth" }
...
```

```kotlin
dependencies {
    implementation(libs.ktorceful.core)
    implementation(libs.ktorceful.auth)
}
```
