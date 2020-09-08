@file:JvmName("_NativeTypeCheckFunJvm")

package sidev.lib.reflex.native

import java.lang.reflect.*
import kotlin.reflect.*


internal actual val Any.isNativeClassifier: Boolean
    get()= this is KClassifier || this is Class<*>
internal actual val Any.isNativeClass: Boolean
    get()= this is KClass<*> || this is Class<*>
internal actual val Any.isNativeType: Boolean
    get()= this is KType || this is Type
internal actual val Any.isNativeCallable: Boolean
    get()= this is KCallable<*> || this is AccessibleObject
internal actual val Any.isNativeFunction: Boolean
    get()= this is Function<*> || this is Executable
internal actual val Any.isNativeProperty: Boolean
    get()= this is KProperty<*> || this is Field
internal actual val Any.isNativeMutableProperty: Boolean
    get()= this is KMutableProperty<*> || this is Field
internal actual val Any.isNativeParameter: Boolean
    get()= this is KParameter || this is Parameter