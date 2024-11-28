package dev.herrrta.ktorceful.data.repository

import dev.herrrta.ktorceful.data.entity.User

object UserRepository {
    lateinit var users: MutableList<User>

    fun get(pk: Int) = users.firstOrNull { it.id == pk }

    fun insert(user: User) = users.add(user)

    fun delete(pk: Int) = users.removeIf { it.id == pk }

    fun update(user: User) {
        val index = users.indexOfFirst { user.id == it.id }
        users[index] = user
    }
}