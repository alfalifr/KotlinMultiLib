package sidev.lib.reflex

import sidev.lib.`val`.SuppressLiteral
import kotlin.reflect.KClass

expect val KClass<*>.nativeFullName: String
expect val KClass<*>.nativeSimpleName: String

/** Kelas native dari paltform-specific. */
expect val KClass<*>.native: Any

val KClass<*>.fullName: String
    get()= nativeFullName //qualifiedName ?: nativeFullName
@Suppress(SuppressLiteral.EXTENSION_SHADOWED_BY_MEMBER) //Untuk pemakaian oleh Java.
val KClass<*>.simpleName: String
    get()= this.simpleName ?: nativeSimpleName

/** Berguna untuk mengambil kelas dari instance dg tipe generic yg tidak punya batas atas Any. */
val Any.clazz: KClass<*>
    get()= this::class