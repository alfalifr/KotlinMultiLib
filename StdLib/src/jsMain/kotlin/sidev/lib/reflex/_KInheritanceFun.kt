package sidev.lib.reflex

import sidev.lib.reflex.js.getSupertypes
import kotlin.reflect.KClass

actual fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean = base in getSupertypes(this)