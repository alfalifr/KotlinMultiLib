package sidev.lib.reflex.full

import sidev.lib.reflex.*
import sidev.lib.reflex.mingw.MingwReflexConst
import sidev.lib.exception.ReflexStateExc
import sidev.lib.console.prine
import kotlin.reflect.*


actual val SiClass<*>.isPrimitive: Boolean
    get()= (descriptor.native as KClass<*>).isPrimitive

actual val SiClass<*>.isObjectArray: Boolean
    get()= (descriptor.native as KClass<*>) == Array::class

actual val SiClass<*>.isPrimitiveArray: Boolean get()= when(this.descriptor.native){
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

@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG,
    ReplaceWith("false")
)
internal actual val SiClass<*>.isNativeInterface: Boolean get()= false


@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG,
    ReplaceWith(
        "throw ReflexStateExc(relatedReflexUnit = \"constructor\", detMsg = MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG)",
        "sidev.lib.exception.ReflexStateExc"
    )
)
actual val <T: Any> SiClass<T>.primaryConstructor: SiFunction<T>
    get()= throw ReflexStateExc(relatedReflexUnit = "constructor", detMsg = MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG)

actual val SiClass<*>.sealedSubclasses: Sequence<SiClass<*>>
    get(){
        prine("WARNING: Kotlin/Native msh blum ada refleksi, `SiClass<*>.sealedSubclasses` return `emptySequence()`")
        return emptySequence()
    }
actual val SiClass<*>.isAnonymous: Boolean
    get()= qualifiedName == null

internal actual val Any.isNativeReflexUnit: Boolean get()= when(this){
    is KClassifier -> true
    is KCallable<*> -> true
//        is KParameter -> SiDescriptor.ElementType.PARAMETER
    is KType -> true
    is KVariance -> true
//    is KVisibility -> true
    else -> false
}

internal actual val Any.isNativeDelegate: Boolean get()= when(this){
    is Lazy<*> -> true
    else -> false
}