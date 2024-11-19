import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.gradle.maven.publish)
    alias(libs.plugins.kotlinJvm) apply false
}

val ktorcefulVersion: String by project

allprojects {
    group = "dev.herrrta.ktorceful"
    version = ktorcefulVersion
}

subprojects {
    apply(plugin = "com.vanniktech.maven.publish")
    if (name != "sample") {
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