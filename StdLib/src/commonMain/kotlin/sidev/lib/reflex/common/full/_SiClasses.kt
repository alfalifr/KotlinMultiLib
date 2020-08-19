package sidev.lib.reflex.common.full

import sidev.lib.collection.iterator.NestedIteratorSimpleImpl
import sidev.lib.console.prine
import sidev.lib.reflex.common.SiClass
import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.KClass

val SiClass<*>.superclasses: Sequence<SiClass<*>>
    get()= supertypes.asSequence().filter { it.classifier is SiClass<*> }.map { it.classifier as SiClass<*> }

val SiClass<*>.classesTree: NestedSequence<SiClass<*>> get()= object : NestedSequence<SiClass<*>>{
    override fun iterator(): NestedIterator<*, SiClass<*>> = object : NestedIteratorSimpleImpl<SiClass<*>>(
        this@classesTree
    ){
        override fun getOutputIterator(nowInput: SiClass<*>): Iterator<SiClass<*>>? = nowInput.superclasses.iterator()
    }
}

val SiClass<*>.superclassesTree: NestedSequence<SiClass<*>> get()= object : NestedSequence<SiClass<*>>{
    override fun iterator(): NestedIterator<*, SiClass<*>> = object : NestedIteratorSimpleImpl<SiClass<*>>(
        this@superclassesTree.superclasses.iterator()
    ){
        override fun getOutputIterator(nowInput: SiClass<*>): Iterator<SiClass<*>>? = nowInput.superclasses.iterator()
    }
}