@file:JvmName("NativeTypeCheckFunKtJvm")

package sidev.lib.reflex.common.native

import java.lang.reflect.*
import kotlin.reflect.*


internal actual val Any.isClassifier: Boolean
    get()= this is KClassifier || this is Class<*>
internal actual val Any.isClass: Boolean
    get()= this is KClass<*> || this is Class<*>
internal actual val Any.isType: Boolean
    get()= this is KType || this is Type
internal actual val Any.isCallable: Boolean
    get()= this is KCallable<*> || this is AccessibleObject
internal actual val Any.isFunction: Boolean
    get()= this is Function<*> || this is Executable
internal actual val Any.isProperty: Boolean
    get()= this is KProperty<*> || this is Field
internal actual val Any.isMutableProperty: Boolean
    get()= this is KMutableProperty<*> || this is Field
internal actual val Any.isParameter: Boolean
    get()= this is KParameter || this is Parameter