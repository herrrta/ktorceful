package dev.herrrta.ktorceful

import com.sun.security.auth.UserPrincipal
import dev.herrrta.ktorceful.auth.createBasicRoute
import dev.herrrta.ktorceful.core.interfaces.HTTPMethod
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserPasswordCredential
import io.ktor.server.auth.basic
import io.ktor.server.resources.Resources
import io.ktor.server.testing.testApplication
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KtorcefulAuthTest {
    @Test
    fun `optional auth route`() {
        ktorAuthRouteApplication<TestAuthRoute>(authOptional = true) {
            val response = it.get("test-auth")

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue { response.bodyAsText().contains(TestAuthRoute.GET_RESPONSE_TEST) }
        }
    }

    @Test
    fun `required auth route`() {
        ktorAuthRouteApplication<TestAuthRoute>(
            authOptional = false,
            onValidate = { UserPrincipal(it.name) }
        ) {
            val response = it.get("test-auth")

            assertEquals(HttpStatusCode.OK, response.status)
            assertTrue { response.bodyAsText().contains(TestAuthRoute.GET_RESPONSE_TEST) }
        }
    }

    @Test
    fun `unauthorized access route`() {
        ktorAuthRouteApplication<TestAuthRoute>(authOptional = false) {
            val response = it.get("test-auth")

            assertEquals(HttpStatusCode.Unauthorized, response.status)
        }
    }
}

private inline fun <reified RouteClass: HTTPMethod> ktorAuthRouteApplication(
    authOptional: Boolean,
    noinline onValidate: suspend ApplicationCall.(UserPasswordCredential) -> Any? = { null },
    crossinline test: suspend (client: HttpClient) -> Unit
) = testApplication {
    application {
        install(Resources)
        install(Authentication) {
            basic("basic-auth") { validate(onValidate) }
        }

        createBasicRoute<RouteClass>(
            configurations = arrayOf("basic-auth"),
            authOptional = authOptional
        )
    }

    val authClient = createClient {
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials("test", "pass")
                }
            }
        }
    }

    test.invoke(authClient)
}