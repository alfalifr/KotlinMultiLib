package sidev.lib.exception

import kotlin.reflect.KClass

open class DataIntegrityExc(relatedClass: KClass<*>?= DataIntegrityExc::class, msg: String= "")
    : Exc(relatedClass, "Integritas data bermasalah.", msg)