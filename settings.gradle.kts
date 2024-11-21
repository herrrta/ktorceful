rootProject.name = "ktorceful"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":core",
    ":auth",
    ":dao",
    "samples:basic-sample",
    ":samples:crud-entity-sample"
)