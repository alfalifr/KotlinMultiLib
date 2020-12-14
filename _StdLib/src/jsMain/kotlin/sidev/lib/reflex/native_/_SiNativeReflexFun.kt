package sidev.lib.reflex.native_

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.property.reevaluateLazy
import sidev.lib.reflex.js.JsParameter
import sidev.lib.reflex.js.jsPureFunction
import kotlin.reflect.KClass

internal actual fun getSiNativeParameter(nativeParameter: Any): SiNativeParameter {
    if(nativeParameter !is JsParameter)
        throw ReflexComponentExc(currentReflexedUnit = nativeParameter, detMsg = """nativeParameter: "$nativeParameter" bkn merupakan native parameter pada Js.""")

    return object : SiNativeParameter {
        override val implementation: Any = nativeParameter
        override val name: String? = nativeParameter.name //?: NativeReflexConst.TEMPLATE_NATIVE_NAME
        override val index: Int= nativeParameter.index
        override val type: KClass<*> by reevaluateLazy {
            try{
                val cls=
                    if(nativeParameter.type.classifier != null)
                        @Suppress(SuppressLiteral.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE)
                        (jsPureFunction(nativeParameter.type.classifier!!) as JsClass<*>).kotlin ?: Any::class
                    else Any::class
                it.value= true
                cls
            } catch (e: Throwable){ Any::class }
        }
        override val isGeneric: Boolean = false
    }
}