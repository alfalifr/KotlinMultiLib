@file:JvmName("_SiNativeReflexFunJvm")

package sidev.lib.reflex.native_

import sidev.lib.check.asNotNull
import sidev.lib.check.asNotNullTo
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.jvm.JvmReflexConst
import java.lang.reflect.Parameter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.TypeVariable
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

internal actual fun getSiNativeParameter(nativeParameter: Any): SiNativeParameter {
    var clazz: KClass<*> = Any::class
    var isGeneric= true
    val index: Int
    val name= when(nativeParameter){
        is KParameter -> {
            nativeParameter.type.classifier.asNotNull { cls: KClass<*> ->
                clazz= cls
                isGeneric= false
            }
            index= nativeParameter.index
            nativeParameter.name
        }
        is Parameter -> {
            clazz= nativeParameter.type.kotlin
            isGeneric= nativeParameter.parameterizedType is TypeVariable<*>
            index= nativeParameter.declaringExecutable.parameters.indexOf(nativeParameter)
            if(!JvmReflexConst.isParamDefault(nativeParameter)) nativeParameter.name
            else null
        }
        else -> throw ReflexComponentExc(currentReflexedUnit = nativeParameter, detMsg = """nativeParameter: "$nativeParameter" bkn merupakan native parameter pada Jvm.""")
    }
    return object : SiNativeParameter {
        override val implementation: Any = nativeParameter
        override val name: String? = name //?: NativeReflexConst.TEMPLATE_NATIVE_NAME
        override val index: Int= index
        override val type: KClass<*> = clazz
        override val isGeneric: Boolean = isGeneric
    }
}