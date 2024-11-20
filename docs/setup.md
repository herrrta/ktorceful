# Setup
Add ktorceful-core dependency to your application

=== "Dependencies"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation("dev.herrrta.ktorceful:core:x.x.x")
        
        // OPTIONAL! if using authentication in ktor
        implementation("dev.herrrta.ktorceful:auth:x.x.x")
    }
    ```

=== "Version Catalog"

    ```toml title="../gradle/libs.versions.toml"
    [versions]
    ktorceful = "x.x.x"
    ...
    
    [libraries]
    ktorceful-core = { module = "dev.herrrta.ktorceful:core", version.ref = "ktorceful" }
    
    # OPTIONAL! if using authentication in ktor
    ktorceful-auth = { module = "dev.herrrta.ktorceful:auth" }
    ...
    ```
    
    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.ktorceful.core)
        implementation(libs.ktorceful.auth)
    }
    ```
