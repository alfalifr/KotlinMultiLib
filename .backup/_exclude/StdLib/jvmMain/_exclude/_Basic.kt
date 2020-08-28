@file:JvmName("BasicKtJvm")

package sidev.lib.reflex

import kotlin.reflect.KClass

actual val KClass<*>.nativeFullName: String get()= java.name
actual val KClass<*>.nativeSimpleName: String get()= java.simpleName

/** Kelas native dari paltform-specific. */
actual val KClass<*>.native: Any get()= java