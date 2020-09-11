@file:JvmName("_KClassFunJvm")

package sidev.lib.reflex.full

import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.collection.sequence.NestedSequence
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.superclasses
import java.util.List as javaList

@get:JvmName("isArray")
actual val KClass<*>.isArray: Boolean
    get()= java.isArray

@get:JvmName("isPrimitive")
actual val KClass<*>.isPrimitive: Boolean
    get()= javaPrimitiveType != null //java.isPrimitive

@get:JvmName("isCopySafe")
actual val KClass<*>.isCopySafe: Boolean
    get()= isPrimitive || this == String::class
            || isSubclassOf(Enum::class)

@get:JvmName("isCollection")
actual val KClass<*>.isCollection: Boolean
    get()= isSubclassOf(Collection::class)
            || isSubclassOf(javaList::class)

@get:JvmName("classesTree")
actual val KClass<*>.classesTree: NestedSequence<KClass<*>>
    get()= nestedSequenceSimple(this){ it.superclasses.iterator() }