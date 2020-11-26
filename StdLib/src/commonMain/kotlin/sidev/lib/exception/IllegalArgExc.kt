package sidev.lib.exception

import kotlin.reflect.KClass

class IllegalArgExc(
    relatedClass: KClass<*>?= IllegalArgExc::class,
    vararg paramExcepted: Any = arrayOf("<param>"),
    detailMsg: String= ""
) : Exc(relatedClass, "Argumen yg di-pass salah, paramExcepted= \"${paramExcepted.joinToString()}\" ", detailMsg)