package dev.herrrta.ktorceful

import dev.herrrta.ktorceful.core.interfaces.HTTPMethod
import dev.herrrta.ktorceful.dao.createRoutes
import dev.herrrta.ktorceful.dao.interfaces.EntityRoute
import dev.herrrta.ktorceful.dao.resources.EntityResource
import dev.herrrta.ktorceful.data.entity.User
import dev.herrrta.ktorceful.data.repository.UserRepository
import dev.herrrta.ktorceful.routes.UserRoute
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.testing.testApplication
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.client.plugins.resources.Resources as ClientResources

class KtorcefulDaoTest {
    @Before
    fun before() {
        UserRepository.users = mutableListOf(
            User(1, "First"),
            User(2, "Second"),
            User(3, "Third"),
        )
    }

    @Test
    fun `post new entity instance`() {
        ktorBasicRouteApplication<UserRoute, User, Int> {
            val response = it.post(UserRoute()) {
                contentType(ContentType.Application.Json)
                setBody(User(10, "NEW"))
            }
            assertEquals(HttpStatusCode.OK, response.status)

            val user: User = it.get(EntityResource.Pk(UserRoute(), "10")).body()
            assertEquals(10, user.id)
        }
    }

    @Test
    fun `get single entity instance`() {
        ktorBasicRouteApplication<UserRoute, User, Int> {
            val response = it.get("/api/user/1")
            val user: User = response.body()

            assertEquals(1, user.id)
        }
    }

    @Test
    fun `get all entity instances`() {
        ktorBasicRouteApplication<UserRoute, User, Int> {
            val response = it.get(UserRoute())
            val users: List<User> = response.body()

            users.withIndex().forEach { (index, user) ->
                assertEquals(index + 1, user.id)
            }
        }
    }

    @Test
    fun `delete single entity instance`() {
        ktorBasicRouteApplication<UserRoute, User, Int> {
            val pk = 2

            // Get entity
            var response = it.get(EntityResource.Pk(UserRoute(), pk.toString()))
            val user: User = response.body()

            assertEquals(pk, user.id)

            // Delete entity
            response = it.delete(EntityResource.Pk(UserRoute(), pk.toString()))
            assertEquals(HttpStatusCode.OK, response.status)

            // Check if it still exists
            response = it.get(EntityResource.Pk(UserRoute(), pk.toString()))
            assertEquals(HttpStatusCode.BadRequest, response.status)
        }
    }

    @Test
    fun `update single entity instance`() {
        ktorBasicRouteApplication<UserRoute, User, Int> {
            val pk = 3

            // Get entity
            var response = it.get(EntityResource.Pk(UserRoute(), pk.toString()))
            var user: User = response.body()

            assertEquals("Third", user.firstName)
            assertTrue { user.active }

            // Update entity
            response = it.put(EntityResource.Pk(UserRoute(), pk.toString())) {
                contentType(ContentType.Application.Json)
                setBody(User(pk, "changed", active = false))
            }

            assertEquals(HttpStatusCode.OK, response.status)

            user = it.get(EntityResource.Pk(UserRoute(), pk.toString())).body()
            assertEquals("changed", user.firstName)
            assertTrue { !user.active }
        }
    }

    @Test
    fun `run action against entity instances`() {
        ktorBasicRouteApplication<UserRoute, User, Int> { client ->
            val response = client.post(EntityResource.Action(UserRoute(), "deactivate")) {
                contentType(ContentType.Application.Json)
                setBody(listOf(
                    User(1),
                    User(2),
                    User(3),
                ))
            }

            assertEquals(HttpStatusCode.OK, response.status)

            (1..3).forEach { pk ->
                val user: User = client.get(EntityResource.Pk(UserRoute(), pk.toString())).body()
                assertTrue { !user.active }
            }
        }
    }

    @Test
    fun `run action with different name`() {
        ktorBasicRouteApplication<UserRoute, User, Int> { client ->
            val response = client.post(EntityResource.Action(UserRoute(), "DEACTIVATE_USERS")) {
                contentType(ContentType.Application.Json)
                setBody(listOf(
                    User(1),
                    User(2),
                    User(3),
                ))
            }

            assertEquals(HttpStatusCode.OK, response.status)

            (1..3).forEach { pk ->
                val user: User = client.get(EntityResource.Pk(UserRoute(), pk.toString())).body()
                assertTrue { !user.active }
            }
        }
    }

    @Test
    fun `run action using empty list`() {
        ktorBasicRouteApplication<UserRoute, User, Int> {
            val response = it.post(EntityResource.Action(UserRoute(), "deactivate")) {
                contentType(ContentType.Application.Json)
                setBody(emptyList<User>())
            }

            assertEquals(HttpStatusCode.BadRequest, response.status)
        }
    }

    @Test
    fun `run nonexistent action`() {
        ktorBasicRouteApplication<UserRoute, User, Int> {
            assertFailsWith<NotImplementedError> {
                it.post(EntityResource.Action(UserRoute(), "nonexistent")) {
                    contentType(ContentType.Application.Json)
                    setBody(listOf(User(1)))
                }
            }
        }
    }
}

private inline fun <reified EntityRouteClass, reified EntityClass : Any, reified PK : Any> ktorBasicRouteApplication(
    crossinline test: suspend (client: HttpClient) -> Unit
) where EntityRouteClass : EntityRoute<*>, EntityRouteClass : HTTPMethod = testApplication {
    application {
        install(Resources)
        install(ContentNegotiation) { json() }

        createRoutes<EntityRouteClass, EntityClass, PK>()
    }

    test.invoke(
        createClient {
            install(ClientResources)
            install(ClientContentNegotiation) { json() }
        }
    )
}