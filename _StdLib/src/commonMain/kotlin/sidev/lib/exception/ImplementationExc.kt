package sidev.lib.exception

import kotlin.reflect.KClass

open class ImplementationExc(
    relatedClass: KClass<*>?= ImplementationExc::class,
    implementedClass: KClass<*>?= null,
    msg: String= "Kesalahan implementasi kelas terletak di luar library (oleh programmer).")
    : Exc(relatedClass, "Terjadi kesalahan dalam implementasi kelas: \"$implementedClass\"", msg)