package dev.herrrta.sample

interface BaseRepository<E, PK> {
    val items: MutableList<E>

    fun all(): List<E> = items

    fun insert(entity: E): PK
    fun get(pk: PK): E
    fun update(entity: E)
    fun delete(pk: PK)
}