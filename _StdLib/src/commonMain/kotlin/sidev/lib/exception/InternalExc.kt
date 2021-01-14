package sidev.lib.exception

import kotlin.reflect.KClass

open class InternalExc(
    relatedClass: KClass<*>?= InternalExc::class,
    msg: String= ""
): Exc(
    relatedClass,
    "Terjadi kesalahan internal",
    msg
)