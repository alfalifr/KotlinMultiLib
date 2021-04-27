package sidev.lib.exception

import kotlin.reflect.KClass

open class IllegalInheritanceExc(
//    relatedClass: KClass<*>?= ArithmeticExc::class,
    superClass: KClass<*>,
    subClass: KClass<*>,
    detailMsg: String= ""
) : Exc(
    subClass,
    "Terjadi pelanggaran aturan inheritance, super= $superClass, sub= $subClass",
    detailMsg
)