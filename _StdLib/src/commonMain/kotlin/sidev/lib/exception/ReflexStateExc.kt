package sidev.lib.exception

import sidev.lib.reflex.SiDescriptorContainer
import kotlin.reflect.KClass

open class ReflexStateExc(
    relatedClass: KClass<*> = ReflexStateExc::class,
    relatedReflexUnit: Any = SiDescriptorContainer::class,
    currentState: Any = "<Illegal State>",
    detMsg: String = ""
) : Exc(
    relatedClass,
    """"Terjadi kesalahan saat melakukan operasi Reflex, relatedReflexUnit: "$relatedReflexUnit", currentState: "$currentState".""",
    detMsg
)