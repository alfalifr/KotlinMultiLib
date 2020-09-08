package sidev.lib.reflex

import kotlin.reflect.KClass

actual val KClass<*>.nativeFullName: String get()= js.name
actual val KClass<*>.nativeSimpleName: String get()= js.name

/** Kelas native dari paltform-specific. */
actual val KClass<*>.native: Any get()= js