plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinx.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    api(projects.ktorcefulCore)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.auth)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.resources)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.ktor.client.auth)
}