package sidev.lib.reflex.common.full

import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiFunction
import sidev.lib.reflex.common.SiProperty1
import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.KProperty1

/*
val SiClass<*>.memberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

val <T: Any> SiClass<T>.memberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>
 */

val SiClass<*>.declaredMemberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

val <T: Any> SiClass<T>.declaredMemberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>


val <T: Any> SiClass<T>.declaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>> get()= object : NestedSequence<SiProperty1<T, *>>{
    override fun iterator(): NestedIterator<*, SiProperty1<T, *>> = object : NestedIteratorImpl<SiClass<*>, SiProperty1<T, *>>(
        this@declaredMemberPropertiesTree.classesTree.iterator()
    ){
        override fun getOutputIterator(nowInput: SiClass<*>): Iterator<SiProperty1<T, *>>?
                = nowInput.declaredMemberProperties.iterator() as Iterator<SiProperty1<T, *>>
        override fun getInputIterator(nowOutput: SiProperty1<T, *>): Iterator<SiClass<*>>? = null
    }
}

/*
val <T: Any> SiClass<T>.nestedDeclaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>> get()= object : NestedSequence<SiProperty1<T, *>>{
    override fun iterator(): NestedIterator<*, SiProperty1<T, *>> = object : NestedIteratorImpl<SiClass<*>, SiProperty1<T, *>>(
        this@nestedDeclaredMemberPropertiesTree.classesTree.iterator()
    ){
        override fun getOutputIterator(nowInput: SiClass<*>): Iterator<SiProperty1<T, *>>?
                = nowInput.declaredMemberProperties.iterator() as Iterator<SiProperty1<T, *>>
        override fun getInputIterator(nowOutput: SiProperty1<T, *>): Iterator<SiClass<*>>?
                = nowOutput.returnType
    }
}
 */

//val <T: Any> SiClass<T>.memberProperties

