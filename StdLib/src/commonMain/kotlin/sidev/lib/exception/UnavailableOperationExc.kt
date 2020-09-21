package sidev.lib.exception

import kotlin.reflect.KClass


open class UnavailableOperationExc(
    relatedClass: KClass<*>?= UnavailableOperationExc::class,
    accessedElement: Any = "<accessed-element>",
    detailMsg: String= ""
) : Exc(relatedClass, """Sebuah operasi tidak didukung, accessedElement= "$accessedElement".""", detailMsg)