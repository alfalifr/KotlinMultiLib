package sidev.lib.reflex.core

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.*
import sidev.lib.reflex.js.JsCallableImpl
import sidev.lib.reflex.js.JsClassImpl_
import sidev.lib.reflex.js.JsProperty
import sidev.lib.reflex.native_.siClass
import kotlin.reflect.KClass


internal actual object ReflexFactoryHelper{
    /**
     * [native] dapat berupa [KClass]
     * atau [JsClass]/function dg tipe [dynamic] pada konteks Js.
     */
    actual fun getSupertypes(classifier: SiClass<*>, native: Any): List<SiType>{
        return sidev.lib.reflex.js.getSupertypes(native).map {
            JsClassImpl_<Any>((it as JsCallableImpl<*>).func).siClass as SiClass<Any>
        }.map {
            it.createType()
        }
    }
    actual fun getTypeParameter(classifier: SiClass<*>, native: Any): List<SiTypeParameter>
            = emptyList()
    actual fun getTypeParameter(
        hostClass: SiClass<*>?,
        callable: SiCallable<*>,
        native: Any
    ): List<SiTypeParameter>
            = emptyList()


    actual fun hasBackingField(property: SiProperty1<*, *>, native: Any): Boolean = when(native){
        is JsProperty<*, *> -> true
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn property di js.")
    }
/*
    actual fun <R, T> getBackingField(property: SiProperty1<R, T>, native: Any): SiField<R, T>? = when(native){
        is JsProperty<*, *> -> ReflexFactory.createField(
            createNativeWrapper(native), property, native.innerName, property.returnType
        )
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn property di js.")
    }

    actual fun <R, T> getMutableBackingField(property: SiMutableProperty1<R, T>, native: Any): SiMutableField<R, T>? = when(native){
        is JsMutableProperty<*, *> -> ReflexFactory.createMutableField(
            createNativeWrapper(native), property, native.innerName, property.returnType
        )
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn mutable property di js.")
    }
 */
}