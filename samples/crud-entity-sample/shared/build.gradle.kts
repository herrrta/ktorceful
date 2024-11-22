import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm()
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs()
    sourceSets {
        commonMain.dependencies {
            api(projects.dao)
            implementation(libs.ktor.server.resources)
            implementation(libs.ktor.server.serialization.kotlinx.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.resources)
            implementation(libs.ktor.client.content.negotiation)

        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }
}



android {
    namespace = "dev.herrrta.sample"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}