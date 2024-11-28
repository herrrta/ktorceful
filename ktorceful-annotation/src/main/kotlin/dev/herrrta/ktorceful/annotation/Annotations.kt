package dev.herrrta.ktorceful.annotation


/**
 * This annotation is used to declare what class requires compile time processing
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@SuppressWarnings("unused")
annotation class Ktorceful


/**
 * This annotation is used on a companion object that gets assigned ktorceful generated resources.
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@SuppressWarnings("unused")
annotation class ResourceCompanion
