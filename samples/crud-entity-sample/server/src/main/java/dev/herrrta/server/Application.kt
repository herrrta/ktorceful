package dev.herrrta.server

import dev.herrrta.ktorceful.dao.createRoutes
import dev.herrrta.sample.User
import dev.herrrta.server.routes.UserAPI
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources

fun main() {
    embeddedServer(
        factory = Netty,
        port = SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(Resources)
    install(ContentNegotiation) { json() }

    createRoutes<UserAPI, User, Long>()
}