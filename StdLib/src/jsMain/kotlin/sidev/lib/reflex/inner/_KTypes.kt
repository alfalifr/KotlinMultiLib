package sidev.lib.reflex.inner

import kotlin.reflect.KType


/**
 * Returns a new type with the same classifier, arguments and annotations as the given type, and with the given nullability.
 */
@SinceKotlin("1.1")
expect fun KType.withNullability(nullable: Boolean): KType


/**
 * Returns `true` if `this` type is the same or is a subtype of [other], `false` otherwise.
 */
@SinceKotlin("1.1")
expect fun KType.isSubtypeOf(other: KType): Boolean

/**
 * Returns `true` if `this` type is the same or is a supertype of [other], `false` otherwise.
 */
@SinceKotlin("1.1")
fun KType.isSupertypeOf(other: KType): Boolean = other.isSubtypeOf(this)
