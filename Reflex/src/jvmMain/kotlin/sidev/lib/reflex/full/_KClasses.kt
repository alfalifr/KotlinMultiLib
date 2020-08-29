@file:JvmName("KClassesKtJvm")

package sidev.lib.reflex.full

import sidev.lib.collection.iterator.nestedSequenceSimple
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.superclasses
import java.util.List as javaList


actual val KClass<*>.isArray: Boolean
    get()= java.isArray

actual val KClass<*>.isPrimitive: Boolean
    get()= javaPrimitiveType != null //java.isPrimitive

actual val KClass<*>.isCopySafe: Boolean
    get()= isPrimitive || this == String::class
            || isSubclassOf(Enum::class)

actual val KClass<*>.isCollection: Boolean
    get()= isSubclassOf(Collection::class)
            || isSubclassOf(javaList::class)

actual val KClass<*>.classesTree: NestedSequence<KClass<*>>
    get()= nestedSequenceSimple(this){ it.superclasses.iterator() }