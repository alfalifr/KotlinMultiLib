package sidev.lib.reflex.core

import sidev.lib.reflex.SiCallable
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiProperty1
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiTypeParameter
import sidev.lib.reflex.native_.unavailableReflexAcces_bool
import sidev.lib.reflex.native_.unavailableReflexAcces_list
import kotlin.reflect.KClass


internal actual object ReflexFactoryHelper{
    /**
     * [native] dapat berupa [KClass]
     * atau [JsClass]/function dg tipe [dynamic] pada konteks Js.
     */
    actual fun getSupertypes(classifier: SiClass<*>, native: Any): List<SiType>
            = unavailableReflexAcces_list("getSupertypes(classifier: SiClass<*>, native: Any)", "classifier: $classifier", "supertype")
    actual fun getTypeParameter(classifier: SiClass<*>, native: Any): List<SiTypeParameter>
            = unavailableReflexAcces_list("getTypeParameter(classifier: SiClass<*>, native: Any)", "classifier: $classifier", "typeParameter")
    actual fun getTypeParameter(hostClass: SiClass<*>?, callable: SiCallable<*>, native: Any): List<SiTypeParameter>
            = unavailableReflexAcces_list("getTypeParameter(hostClass: SiClass<*>, callable: SiCallable<*> native: Any)", "callable: $callable", "typeParameter")
    actual fun hasBackingField(property: SiProperty1<*, *>, native: Any): Boolean
            = unavailableReflexAcces_bool("hasBackingField(property: SiProperty1<*, *>, native: Any)", "property: $property", true)
//    fun <R, T> getBackingField(property: SiProperty1<R, T>, native: Any): SiField<R, T>?
//    fun <R, T> getMutableBackingField(property: SiMutableProperty1<R, T>, native: Any): SiMutableField<R, T>?
//    fun getSimpleName(classifier: SiNativeClassifier, qualifiedName: String?): String?
}