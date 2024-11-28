package dev.herrrta.sample

import dev.herrrta.ktorceful.dao.interfaces.CreateEntity
import dev.herrrta.ktorceful.dao.interfaces.DeleteEntity
import dev.herrrta.ktorceful.dao.interfaces.GetEntity
import dev.herrrta.ktorceful.dao.interfaces.UpdateEntity
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.routing.RoutingCall
import io.ktor.util.reflect.TypeInfo
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Resource("api")
class APIPrefix

@Serializable
abstract class BaseAPI<E : Any, PK : Any>(
    val parent: APIPrefix = APIPrefix()
): CreateEntity<E>, GetEntity<E, PK>, UpdateEntity<E, PK>, DeleteEntity<E, PK> {
    abstract val repo: BaseRepository<E, PK>

    override suspend fun post(call: RoutingCall, klass: KClass<E>) {
        val entity: E = call.receive(klass)
        repo.insert(entity)
    }

    override suspend fun get(call: RoutingCall) {
        call.respond(repo.all(), TypeInfo(List::class))
    }

    override suspend fun getInstance(pk: PK): E? {
        return repo.get(pk)
    }

    override suspend fun put(call: RoutingCall, pk: PK, klass: KClass<E>) {
        val entity: E = call.receive(klass)
        repo.update(entity)
    }
}