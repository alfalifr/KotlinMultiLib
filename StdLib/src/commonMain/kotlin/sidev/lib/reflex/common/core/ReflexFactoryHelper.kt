package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.SiCallable
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.SiTypeParameter
import sidev.lib.reflex.common.native.SiNativeCallable
import sidev.lib.reflex.common.native.SiNativeClassifier
import kotlin.reflect.KClass


internal expect object ReflexFactoryHelper{
    /**
     * [native] dapat berupa [KClass]
     * atau [JsClass]/function dg tipe [dynamic] pada konteks Js.
     */
    fun getSupertypes(classifier: SiClass<*>, native: Any, name: String?): List<SiType>
    fun getTypeParameter(classifier: SiClass<*>, native: Any, name: String?): List<SiTypeParameter>
    fun getTypeParameter(callable: SiCallable<*>, native: Any, name: String?): List<SiTypeParameter>
//    fun getSimpleName(classifier: SiNativeClassifier, qualifiedName: String?): String?
}