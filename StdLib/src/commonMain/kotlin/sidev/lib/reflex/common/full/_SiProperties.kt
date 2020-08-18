package sidev.lib.reflex.common.full

import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiFunction
import sidev.lib.reflex.common.SiProperty1


val SiClass<*>.memberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

val <T: Any> SiClass<T>.memberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>

val <T: Any> SiClass<T>.declaredMemberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>


//val <T: Any> SiClass<T>.memberProperties

