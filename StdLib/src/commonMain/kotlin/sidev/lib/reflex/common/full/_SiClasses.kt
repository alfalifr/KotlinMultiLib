package sidev.lib.reflex.common.full

import sidev.lib.reflex.common.SiClass
import kotlin.reflect.KClass

val SiClass<*>.superclasses: Sequence<SiClass<*>>
    get()= supertypes.filter { it is SiClass<*> } as Sequence<SiClass<*>>