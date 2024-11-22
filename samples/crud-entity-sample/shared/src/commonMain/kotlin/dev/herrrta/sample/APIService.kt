package dev.herrrta.sample

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

object APIService {
    private val client = HttpClient {
        install(Resources)
        install(ContentNegotiation) { json() }
        defaultRequest {
            host = "10.0.2.2"
            port = 8080
        }
    }

    suspend fun getUsers(): List<User> = client.get("/api/user").body()
    suspend fun deleteUser(pk: Long) {
        client.delete("/api/user/${pk}")
    }

    suspend fun updateUser(user: User) {
        client.put("/api/user/${user.id}") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    suspend fun addUser(user: User) {
        client.post("/api/user") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }
}