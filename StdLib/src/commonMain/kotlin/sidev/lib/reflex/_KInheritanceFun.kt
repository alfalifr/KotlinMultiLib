package sidev.lib.reflex

import kotlin.reflect.KClass

expect fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean //= base in classesTree
fun KClass<*>.isSuperclassOf(derived: KClass<*>): Boolean = derived.isSubclassOf(this)