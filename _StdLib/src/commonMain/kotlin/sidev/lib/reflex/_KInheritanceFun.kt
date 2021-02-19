package sidev.lib.reflex

import kotlin.reflect.KClass

expect fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean //= base in classesTree
fun KClass<*>.isSuperclassOf(derived: KClass<*>): Boolean = derived.isSubclassOf(this)

internal fun Any.isSubclassInstanceOf(base: Any): Boolean = base::class.isInstance(this)
internal fun Any.isSuperclassInstanceOf(derived: Any): Boolean = derived.isSubclassInstanceOf(this)