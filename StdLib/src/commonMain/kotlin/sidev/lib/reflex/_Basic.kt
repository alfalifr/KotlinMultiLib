package sidev.lib.reflex

import kotlin.reflect.KClass

expect val KClass<*>.nativeFullName: String
expect val KClass<*>.nativeSimpleName: String

/** Kelas native dari paltform-specific. */
expect val KClass<*>.native: Any