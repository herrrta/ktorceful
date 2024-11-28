plugins {
    alias(libs.plugins.kotlinJvm)
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
    implementation(projects.core)
    implementation(projects.dao)
    implementation(projects.ktorcefulAnnotation)
    implementation(libs.ksp.symbol.processing.api)
    implementation(libs.ktor.server.core)

    testImplementation(projects.dao)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.ksp.symbol.processing)
    testImplementation(libs.ksp.test)
}