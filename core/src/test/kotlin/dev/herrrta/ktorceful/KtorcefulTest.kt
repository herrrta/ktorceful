package dev.herrrta.ktorceful

import dev.herrrta.ktorceful.core.extensions.createRoutes
import dev.herrrta.ktorceful.core.interfaces.HTTPMethod
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import io.ktor.server.testing.testApplication
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KtorcefulTest {
    @Test
    fun `creating basic routes`() {
        ktorBasicRouteApplication<BasicRoute> {
            val getResponse = it.get("basic")

            assertEquals(HttpStatusCode.OK, getResponse.status)
            assertTrue { getResponse.bodyAsText().contains(BasicRoute.GET_RESPONSE_TEST) }

            val postResponse = it.post("basic")

            assertEquals(HttpStatusCode.OK, postResponse.status)
            assertTrue { postResponse.bodyAsText().contains(BasicRoute.POST_RESPONSE_TEST) }
        }
    }

}

private inline fun <reified RouteClass: HTTPMethod> ktorBasicRouteApplication(
    crossinline test: suspend (client: HttpClient) -> Unit
) = testApplication {
    application {
        install(Resources)
        createRoutes<RouteClass>()
    }

    test.invoke(client)
}