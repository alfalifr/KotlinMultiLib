package sidev.lib.reflex

import kotlin.reflect.KClass


val KClass<*>.isPrimitiveArray: Boolean get()= when(this){
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

val KClass<*>.isPrimitiveNumberArray: Boolean get()= when(this){
    ByteArray::class -> true
    ShortArray::class -> true
    IntArray::class -> true
    LongArray::class -> true
    else -> false
}

inline val <reified T: Any> KClass<T>.isNumberArray: Boolean
    get()= isPrimitiveNumberArray || when(T::class){
        Byte::class -> true
        Short::class -> true
        Int::class -> true
        Long::class -> true
        else -> false
    }