package sidev.lib.reflex.common.core

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.common.SiCallable
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.SiTypeParameter
import sidev.lib.reflex.common.native.SiNativeCallable
import sidev.lib.reflex.common.native.SiNativeClassifier
import sidev.lib.reflex.common.native.si
import java.lang.reflect.Executable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass


internal actual object ReflexFactoryHelper{
    /**
     * [native] dapat berupa [KClass]
     * atau [JsClass]/function dg tipe [dynamic] pada konteks Js.
     */
    actual fun getSupertypes(
        classifier: SiClass<*>,
        native: Any,
        name: String?
    ): List<SiType> = when(native){
        is KClass<*> -> native.supertypes.map { it.si }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn classifier di jvm.")
    }

    actual fun getTypeParameter(
        classifier: SiClass<*>,
        native: Any,
        name: String?
    ): List<SiTypeParameter> = when(native){
        is KClass<*> -> native.typeParameters.map { it.si.apply { mutableHost= classifier } }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn classifier di jvm.")
    }

    actual fun getTypeParameter(
        callable: SiCallable<*>,
        native: Any,
        name: String?
    ): List<SiTypeParameter> = when(native){
        is KCallable<*> -> native.typeParameters.map { it.si.apply { mutableHost= callable } }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "classifier bkn fungsi di jvm.")
    }
//    fun getSimpleName(classifier: SiNativeClassifier, qualifiedName: String?): String?
}