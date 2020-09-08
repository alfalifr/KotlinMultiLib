package sidev.lib.reflex

import kotlin.reflect.KClass

actual val KClass<*>.nativeFullName: String get()= qualifiedName ?: "<nativeFullName>"
actual val KClass<*>.nativeSimpleName: String get()= simpleName ?: "<nativeSimpleName>"

/** Kelas native dari paltform-specific. */
actual val KClass<*>.native: Any get()= this