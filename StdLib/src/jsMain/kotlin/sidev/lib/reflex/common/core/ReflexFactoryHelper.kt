package sidev.lib.reflex.common.core

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.common.SiCallable
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.SiTypeParameter
import sidev.lib.reflex.common.native.SiNativeCallable
import sidev.lib.reflex.common.native.SiNativeClassifier
import sidev.lib.reflex.common.native.si
import sidev.lib.reflex.common.native.siClass
import sidev.lib.reflex.js.JsCallableImpl
import sidev.lib.reflex.js.JsClassImpl_
import kotlin.reflect.KCallable
import kotlin.reflect.KClass


internal actual object ReflexFactoryHelper{
    /**
     * [native] dapat berupa [KClass]
     * atau [JsClass]/function dg tipe [dynamic] pada konteks Js.
     */
    actual fun getSupertypes(classifier: SiClass<*>, native: Any, name: String?): List<SiType>{
        return sidev.lib.reflex.js.getSupertypes(native).map {
            JsClassImpl_<Any>((it as JsCallableImpl<*>).func).siClass as SiClass<Any>
        }.map {
            it.createType()
        }
    }
    actual fun getTypeParameter(classifier: SiClass<*>, native: Any, name: String?): List<SiTypeParameter>
            = emptyList()
    actual fun getTypeParameter(callable: SiCallable<*>, native: Any, name: String?): List<SiTypeParameter>
            = emptyList()
}