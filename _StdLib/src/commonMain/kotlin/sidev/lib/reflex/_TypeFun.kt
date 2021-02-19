package sidev.lib.reflex

import kotlin.reflect.KClass


expect val KClass<*>.isPrimitiveArray: Boolean

internal val KClass<*>.isPrimitiveArray_default: Boolean get()= when(this){
    ByteArray::class -> true
    ShortArray::class -> true
    IntArray::class -> true
    LongArray::class -> true
    FloatArray::class -> true
    DoubleArray::class -> true
    BooleanArray::class -> true
    CharArray::class -> true
    else -> false
}

val KClass<*>.isPrimitiveIntNumberArray: Boolean get()= when(this){
    ByteArray::class -> true
    ShortArray::class -> true
    IntArray::class -> true
    LongArray::class -> true
    else -> false
}

val KClass<*>.isPrimitiveFloatingNumberArray: Boolean get()= when(this){
    FloatArray::class -> true
    DoubleArray::class -> true
    else -> false
}

val KClass<*>.isPrimitiveNumberArray: Boolean
    get()= isPrimitiveIntNumberArray || isPrimitiveFloatingNumberArray


inline val <reified T: Any> KClass<T>.isPrimitiveIntNumberArrayLike: Boolean
    get()= isPrimitiveIntNumberArray || when(T::class){
        Byte::class -> true
        Short::class -> true
        Int::class -> true
        Long::class -> true
        else -> false
    }
inline val <reified T: Any> KClass<T>.isPrimitiveFloatingNumberArrayLike: Boolean
    get()= isPrimitiveFloatingNumberArray || when(T::class){
        Float::class -> true
        Double::class -> true
        else -> false
    }
inline val <reified T: Any> KClass<T>.isPrimitiveNumberArrayLike: Boolean
    get()= isPrimitiveIntNumberArrayLike || isPrimitiveFloatingNumberArrayLike