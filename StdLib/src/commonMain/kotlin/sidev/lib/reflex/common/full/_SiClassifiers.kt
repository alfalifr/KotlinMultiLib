package sidev.lib.reflex.common.full

import sidev.lib.check.asNotNullTo
import sidev.lib.collection.iterator.nestedSequenceSimple
import sidev.lib.console.prine
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.native.si
import sidev.lib.universal.structure.collection.sequence.NestedSequence


/**
 * Mengambil supertype yg merupakan kelas namun bkn interface.
 * @return `null` jika `this.extension` merupakan interface atau [Array].
 */
val SiClass<*>.superclass: SiClass<*>?
    get()= supertypes.find { it.classifier.asNotNullTo { cls: SiClass<*> -> !cls.isInterface } == true }
        ?.classifier as? SiClass<*>

/**
 * Mengubah [SiClass.supertypes] menjadi superclasses dg cara mengambil supertype yg
 * memiliki classifier brp [SiClass].
 */
val SiClass<*>.superclasses: Sequence<SiClass<*>>
    get()= supertypes.asSequence().filter { it.classifier is SiClass<*> }.map { it.classifier as SiClass<*> }

val SiClass<*>.classesTree: NestedSequence<SiClass<*>>
    get()= nestedSequenceSimple(this){ it.superclasses.iterator() }

val SiClass<*>.superclassesTree: NestedSequence<SiClass<*>>
    get()= nestedSequenceSimple(superclasses.iterator()){ input: SiClass<*> -> input.superclasses.iterator() }

val SiType.typesTree: NestedSequence<SiType>
    get()= nestedSequenceSimple(this){ it.classifier.asNotNullTo { cls: SiClass<*> -> cls.supertypes.iterator() } }

val SiClass<*>.supertypesTree: NestedSequence<SiType>
    get()= nestedSequenceSimple<SiType>(supertypes.iterator()){ it.classifier.asNotNullTo { cls: SiClass<*> -> cls.supertypes.iterator() } }

/*
/**
 * Mengambil [SiClass] yg sesuai dg [T].
 * @return [SiClass] dari [T], atau `this.extension` jika merupakan [SiClass].
 */
internal fun <T: Any> getQualifiedClass(any: T): SiClass<T> = (if(any is SiClass<*>) any else any::class.si) as SiClass<T>
 */