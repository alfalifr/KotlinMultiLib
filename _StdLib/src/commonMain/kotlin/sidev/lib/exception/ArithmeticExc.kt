package sidev.lib.exception

import kotlin.reflect.KClass


open class ArithmeticExc (
    relatedClass: KClass<*>?= ArithmeticExc::class,
    operation: Any= "<operation>",
    detailMsg: String= ""
) : Exc(relatedClass, """Terjadi kesalahan saat perhitungan aritmatika, operation= "$operation".""", detailMsg)