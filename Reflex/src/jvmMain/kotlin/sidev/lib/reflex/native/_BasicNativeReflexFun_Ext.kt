@file:JvmName("_BasicNativeReflexFunJvm_Ext")

package sidev.lib.reflex.native

import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.isAccessible


internal actual fun getIsAccessible(nativeReflexUnit: Any): Boolean = when(nativeReflexUnit){
    is KCallable<*> -> nativeReflexUnit.isAccessible
    is AccessibleObject -> nativeReflexUnit.isAccessible
    else -> false
}
internal actual fun setIsAccessible(nativeReflexUnit: Any, isAccessible: Boolean){
    when(nativeReflexUnit){
        is KCallable<*> -> nativeReflexUnit.isAccessible= isAccessible
        is AccessibleObject -> nativeReflexUnit.isAccessible= isAccessible
    }
}