package sidev.lib.exception

import kotlin.reflect.KClass

open class IllegalAccessExc(relatedClass: KClass<*>?= IllegalAccessExc::class, msg: String= "")
    : Exc(relatedClass, "Terjadi kesalahan saat mengakses fungsi atau properti.", msg)