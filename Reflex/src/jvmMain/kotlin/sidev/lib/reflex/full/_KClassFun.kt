@file:JvmName("_KClassFunJvm")

package sidev.lib.reflex.full

import sidev.lib.check.asNotNullTo
import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.collection.sequence.nestedSequence
import sidev.lib.reflex.SiProperty1
import sidev.lib.reflex.jvm.classesTree
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
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

@get:JvmName("isInterface")
actual val KClass<*>.isInterface: Boolean
    get()= java.isInterface

@get:JvmName("isCommonSealed")
actual val KClass<*>.isCommonSealed: Boolean
    get()= isSealed



@get:JvmName("declaredPropertiesTree")
val <T: Any> KClass<T>.declaredMemberPropertiesTree: NestedSequence<KProperty1<T, *>>
    get()= nestedSequence(classesTree){ cls: KClass<*> -> cls.declaredMemberProperties.iterator() as Iterator<KProperty1<T, *>> }

@get:JvmName("declaredFunctionsTree")
val <T: Any> KClass<T>.declaredMemberFunctionsTree: NestedSequence<KFunction<*>>
    get()= nestedSequence(classesTree){ cls: KClass<*> -> cls.declaredFunctions.iterator() }


inline fun <T: Any, reified R: Any> KClass<T>.getProperty(name: String= ""): KProperty1<T, R>? = getProperty(R::class, name)
fun <T: Any, R: Any> KClass<T>.getProperty(clazz: KClass<R>, name: String= ""): KProperty1<T, R>?
        = clazz.declaredMemberPropertiesTree.find {
    (it.returnType.classifier.asNotNullTo { cls: KClass<*> ->
        cls.isSubclassOf(clazz)
    } ?: false)
            && (name.isBlank() || it.name == name)
} as? KProperty1<T, R>