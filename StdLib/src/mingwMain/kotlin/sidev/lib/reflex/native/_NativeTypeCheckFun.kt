package sidev.lib.reflex.native

import sidev.lib.reflex.mingw.MingwReflexConst
import kotlin.reflect.*


internal actual val Any.isNativeClassifier: Boolean
    get()= this is KClassifier
internal actual val Any.isNativeClass: Boolean
    get()= this is KClass<*>
internal actual val Any.isNativeType: Boolean
    get()= this is KType
internal actual val Any.isNativeCallable: Boolean
    get()= this is KCallable<*>
internal actual val Any.isNativeFunction: Boolean
    get()= this is Function<*>
internal actual val Any.isNativeProperty: Boolean
    get()= this is KProperty<*>
internal actual val Any.isNativeMutableProperty: Boolean
    get()= this is KMutableProperty<*>

@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG, ReplaceWith("false")
)
internal actual val Any.isNativeParameter: Boolean
    get()= false //Karena Kotlin/Native gak punya parameter. //this is KParameter