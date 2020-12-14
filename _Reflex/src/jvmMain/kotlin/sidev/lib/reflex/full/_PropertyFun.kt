@file:JvmName("_PropertyFunJvm")
package sidev.lib.reflex.full

import sidev.lib.reflex.jvm.JvmReflexConst
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Type
import kotlin.reflect.*


@get:JvmName("isNativeReflexUnit")
actual val Any.isNativeReflexUnit: Boolean get()= when(this){
    is KParameter -> true
    is KCallable<*> -> true
    is KClass<*> -> true
    is KType -> true
    is KTypeParameter -> true
    is KClassifier -> true

    is AccessibleObject -> true
    is Type -> true

    else -> false
}

@get:JvmName("isNativeDelegate")
internal actual val Any.isNativeDelegate: Boolean get(){
    return this::class.java.methods.find { JvmReflexConst.isDelegateGetValueMethod(it, this::class.java) } != null
            || this::class.java.methods.find { JvmReflexConst.isDelegateSetValueMethod(it, this::class.java) } != null
}