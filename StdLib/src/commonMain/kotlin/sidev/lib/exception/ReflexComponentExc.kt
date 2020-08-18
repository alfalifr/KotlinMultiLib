package sidev.lib.exception

import sidev.lib.reflex.common.SiReflex
import kotlin.reflect.KClass

open class ReflexComponentExc(
    relatedClass: KClass<*> = ReflexComponentExc::class,
    currentReflexedUnit: Any = SiReflex::class,
    detMsg: String = ""
) : Exc(
    relatedClass,
    """"Terjadi kesalahan saat melakukan operasi Reflex, currentReflexedUnit: "$currentReflexedUnit".""",
    detMsg
)