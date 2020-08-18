@file:JvmName("KTypesKtJvm")

package sidev.lib.reflex.inner

import kotlin.reflect.KType
import kotlin.reflect.full.withNullability as _withNullability
import kotlin.reflect.full.isSubtypeOf as _isSubtypeOf


actual fun KType.withNullability(nullable: Boolean): KType = _withNullability(nullable)

actual fun KType.isSubtypeOf(other: KType): Boolean = _isSubtypeOf(other)
