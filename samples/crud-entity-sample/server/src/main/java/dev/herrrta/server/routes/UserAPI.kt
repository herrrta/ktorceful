package dev.herrrta.server.routes

import dev.herrrta.sample.BaseAPI
import dev.herrrta.sample.BaseRepository
import dev.herrrta.sample.User
import dev.herrrta.server.data.UserRepository
import io.ktor.resources.Resource
import io.ktor.server.routing.RoutingCall
import kotlin.reflect.KClass

@Resource("user")
class UserAPI: BaseAPI<User, Long>() {
    override val repo: BaseRepository<User, Long> get() = UserRepository
    override val klass: KClass<User> by lazy { User::class }

    override suspend fun delete(call: RoutingCall, pk: Long) {
        repo.delete(pk)
    }
}