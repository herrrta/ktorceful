package dev.herrrta.sample

import dev.herrrta.ktorceful.core.interfaces.Post
import dev.herrrta.ktorceful.dao.interfaces.CreateEntity
import dev.herrrta.ktorceful.dao.interfaces.DeleteEntity
import dev.herrrta.ktorceful.dao.interfaces.GetEntity
import dev.herrrta.ktorceful.dao.interfaces.UpdateEntity
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.instanceOf
import kotlinx.serialization.Serializable

@Resource("api")
class APIPrefix

@Serializable
abstract class BaseAPI<E : Any, PK : Any>(
    val parent: APIPrefix = APIPrefix()
): CreateEntity<E>, GetEntity<E>, UpdateEntity<E>, DeleteEntity<E> {
    abstract val repo: BaseRepository<E, PK>

    override suspend fun post(call: RoutingCall) {
        val entity: E = call.receive(klass)
        repo.insert(entity)
    }

    override suspend fun get(call: RoutingCall) {
        if ("pk" !in call.parameters) {
            return call.respond(repo.all(), TypeInfo(List::class))
        }
        super.get(call)
    }

    override suspend fun getInstance(pk: String): E? {
        return (pk as? PK)?.let { repo.get(it) }
    }

    override suspend fun put(call: RoutingCall) {
        val entity: E = call.receive(klass)
        repo.update(entity)
    }
}