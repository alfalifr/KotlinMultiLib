package sidev.lib.reflex.core

import sidev.lib.reflex.comp.*
import kotlin.reflect.KClass


internal expect object ReflexFactoryHelper{
    /**
     * [native] dapat berupa [KClass]
     * atau [JsClass]/function dg tipe [dynamic] pada konteks Js.
     */
    fun getSupertypes(classifier: SiClass<*>, native: Any): List<SiType>
    fun getTypeParameter(classifier: SiClass<*>, native: Any): List<SiTypeParameter>
    fun getTypeParameter(hostClass: SiClass<*>?, callable: SiCallable<*>, native: Any): List<SiTypeParameter>
    fun hasBackingField(property: SiProperty1<*, *>, native: Any): Boolean
//    fun <R, T> getBackingField(property: SiProperty1<R, T>, native: Any): SiField<R, T>?
//    fun <R, T> getMutableBackingField(property: SiMutableProperty1<R, T>, native: Any): SiMutableField<R, T>?
//    fun getSimpleName(classifier: SiNativeClassifier, qualifiedName: String?): String?
}