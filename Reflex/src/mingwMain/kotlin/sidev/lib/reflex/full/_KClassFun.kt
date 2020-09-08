package sidev.lib.reflex.full

import sidev.lib.reflex.mingw.MingwReflexConst
import sidev.lib.collection.sequence.emptyNestedSequence
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.KClass


actual val KClass<*>.isPrimitive: Boolean get() = when(this){
    Int::class -> true
    Long::class -> true
    String::class -> true
    Float::class -> true
    Double::class -> true
    Char::class -> true
    Short::class -> true
    Boolean::class -> true
    Byte::class -> true
    Unit::class -> true
    else -> false
}
actual val KClass<*>.isArray: Boolean get() = when(this){
    Array::class -> true
    IntArray::class -> true
    LongArray::class -> true
    FloatArray::class -> true
    DoubleArray::class -> true
    CharArray::class -> true
    ShortArray::class -> true
    BooleanArray::class -> true
    ByteArray::class -> true
    else -> false
}

actual val KClass<*>.isCopySafe: Boolean
    get()= isPrimitive || this == String::class

actual val KClass<*>.isCollection: Boolean
    get()= try{ (this as Collection<*>); true }
            catch (e: ClassCastException){ false }

@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG,
    ReplaceWith("emptyNestedSequence()", "sidev.lib.collection.sequence.emptyNestedSequence")
)
actual val KClass<*>.classesTree: NestedSequence<KClass<*>> get()= emptyNestedSequence()