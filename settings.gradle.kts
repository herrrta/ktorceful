rootProject.name = "ktorceful"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://oss.sonatype.org/content/repositories/snapshots")

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()

        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

include(
    ":core",
    ":auth",
    ":dao",
    ":samples:basic-sample",
    ":samples:crud-entity-sample:composeApp",
    ":samples:crud-entity-sample:server",
    ":samples:crud-entity-sample:shared"
)
