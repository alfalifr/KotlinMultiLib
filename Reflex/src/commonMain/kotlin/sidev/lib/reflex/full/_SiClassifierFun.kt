package sidev.lib.reflex.full

import sidev.lib.check.asNotNullTo
import sidev.lib.check.notNullTo
import sidev.lib.collection.iterator.iteratorSimple
import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.collection.sequence.emptyNestedSequence
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiType
import sidev.lib.collection.sequence.NestedSequence
import kotlin.jvm.JvmName


/**
 * Mengambil supertype yg merupakan kelas namun bkn interface.
 * @return `null` jika `this.extension` merupakan interface atau [Array].
 */
@get:JvmName("superclass")
val SiClass<*>.superclass: SiClass<*>?
    get()= supertypes.find { it.classifier.asNotNullTo { cls: SiClass<*> -> !cls.isInterface } == true }
        ?.classifier as? SiClass<*>

/**
 * Mengubah [SiClass.supertypes] menjadi superclasses dg cara mengambil supertype yg
 * memiliki classifier brp [SiClass].
 */
@get:JvmName("superclasses")
val SiClass<*>.superclasses: Sequence<SiClass<*>>
    get()= supertypes.asSequence().filter { it.classifier is SiClass<*> }.map { it.classifier as SiClass<*> }

/**
 * Mengambil semua kelas yg masuk dalam pohon keturunan dari kelas `this.extension`, termasuk yg berupa kelas maupun interface.
 * Properti ini juga menyertakan `this.extension` dalam sequence.
 */
@get:JvmName("classesTree")
val SiClass<*>.classesTree: NestedSequence<SiClass<*>>
    get()= nestedSequenceSimple(this){ it.superclasses.iterator() }

/**
 * Mirip dg [classesTree], namun hanya superclass yg bkn brp interface.
 * Khusus untuk `this.extension`, walaupun merupakan anonymous class yg berasal dari instansisasi interface,
 * tetap masuk dalam sequence.
 */
@get:JvmName("extendingClassesTree")
val SiClass<*>.extendingClassesTree: NestedSequence<SiClass<*>>
    get()= nestedSequenceSimple(this){ it.superclass.notNullTo { iteratorSimple(it) } }

/** Mirip dg [classesTree], namun tidak menyertakan `this.extension` dalam sequence. */
@get:JvmName("superclassesTree")
val SiClass<*>.superclassesTree: NestedSequence<SiClass<*>>
    get()= nestedSequenceSimple(superclasses){ input: SiClass<*> -> input.superclasses.iterator() }

/** Mirip dg [extendingClassesTree], namun tidak menyertakan `this.extension` dalam sequence. */
@get:JvmName("extendingSuperclassesTree")
val SiClass<*>.extendingSuperclassesTree: NestedSequence<SiClass<*>>
    get()= superclass.notNullTo { supr ->
        nestedSequenceSimple(supr){ input: SiClass<*> -> input.superclasses.iterator() }
    } ?: emptyNestedSequence()


/**
 * Mengambil semua tipe yg masuk dalam pohon keturunan dari tipe `this.extension`, termasuk yg berupa kelas maupun interface.
 * Properti ini juga menyertakan `this.extension` dalam sequence.
 */
@get:JvmName("typesTree")
val SiType.typesTree: NestedSequence<SiType>
    get()= nestedSequenceSimple(this){ it.classifier.asNotNullTo { cls: SiClass<*> -> cls.supertypes.iterator() } }

/** Mirip dg [typesTree], namun tidak menyertakan `this.extension` dalam sequence. */
@get:JvmName("supertypesTree")
val SiClass<*>.supertypesTree: NestedSequence<SiType>
    get()= nestedSequenceSimple<SiType>(supertypes.asSequence()){ it.classifier.asNotNullTo { cls: SiClass<*> -> cls.supertypes.iterator() } }

/*
/**
 * Mengambil [SiClass] yg sesuai dg [T].
 * @return [SiClass] dari [T], atau `this.extension` jika merupakan [SiClass].
 */
internal fun <T: Any> getQualifiedClass(any: T): SiClass<T> = (if(any is SiClass<*>) any else any::class.si) as SiClass<T>
 */