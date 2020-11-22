package sidev.lib.reflex

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf as kIsSubclassOf

actual fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean = kIsSubclassOf(base)