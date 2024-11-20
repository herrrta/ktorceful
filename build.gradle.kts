import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaTask

plugins {
    alias(libs.plugins.gradle.maven.publish)
    alias(libs.plugins.kotlinJvm) apply false

    id("org.jetbrains.dokka") version "1.9.20"
}

val ktorcefulVersion: String by project

allprojects {
    group = "dev.herrrta.ktorceful"
    version = ktorcefulVersion
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:1.9.20")
    }
}

tasks.dokkaHtmlMultiModule {
    outputDirectory.set(rootProject.file("docs/api/"))
    suppressInheritedMembers.set(true)
}

applyDokkaHomePageLink(project)

subprojects {
    if (name != "sample") {
        apply(plugin = "org.jetbrains.dokka")
        apply(plugin = "com.vanniktech.maven.publish")

        applyDokkaHomePageLink(this)

        mavenPublishing {
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
            signAllPublications()

            coordinates("dev.herrrta.ktorceful", name, ktorcefulVersion)

            pom {
                name.set("Ktorceful")
                description.set("Ktorceful - Ktor class based resource routing")
                url.set("https://github.com/herrrta/ktorceful/")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    url.set("https://github.com/herrrta/ktorceful")
                    connection.set("scm:git:https://github.com/herrrta/ktorceful.git")
                }
                developers {
                    developer {
                        name.set("herrrta")
                        url.set("https://github.com/herrrta/")
                    }
                }
            }
        }
    }
}

fun applyDokkaHomePageLink(context: Project) {
    context.tasks.withType<AbstractDokkaTask>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            homepageLink = "https://github.com/herrrta/ktorceful/"
            separateInheritedMembers = true
        }
    }
}