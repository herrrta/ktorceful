package dev.herrrta.server.data

import dev.herrrta.sample.BaseRepository
import dev.herrrta.sample.User

object UserRepository: BaseRepository<User, Long> {
    override val items: MutableList<User> = mutableListOf(
        User(1, "Test", "First"),
        User(2, "Test", "Second", false),
        User(3, "Test", "Third"),
        User(4, "Test", "Fourth", false),
    )

    override fun insert(entity: User): Long {
        val lastUser = items.lastOrNull()
        val newId = (lastUser?.id ?: 0) + 1
        items.add(entity.copy(id = newId))
        return newId
    }

    override fun get(pk: Long): User {
        return items.first { it.id == pk }
    }

    override fun update(entity: User) {
        val index = items.indexOfFirst { it.id == entity.id }
        items[index] = entity
    }

    override fun delete(pk: Long) {
        items.removeIf { it.id == pk }
    }
}