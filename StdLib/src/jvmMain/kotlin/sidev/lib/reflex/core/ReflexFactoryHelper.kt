package sidev.lib.reflex.core

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.*
import sidev.lib.reflex.native.si
import java.lang.reflect.Field
import kotlin.reflect.*
import kotlin.reflect.jvm.javaField


internal actual object ReflexFactoryHelper{
    /**
     * [native] dapat berupa [KClass]
     * atau [JsClass]/function dg tipe [dynamic] pada konteks Js.
     */
    actual fun getSupertypes(
        classifier: SiClass<*>,
        native: Any
    ): List<SiType> = when(native){
        is KClass<*> -> native.supertypes.map { it.si }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn classifier di jvm.")
    }

    actual fun getTypeParameter(
        classifier: SiClass<*>,
        native: Any
    ): List<SiTypeParameter> = when(native){
        is KClass<*> -> {
            val siTypeParams= ArrayList<SiTypeParameter>()
            val pendingTypeParamIndex= ArrayList<Int>() //Index untuk type-param yg upperboundnya pending (karena blum semua upperbound di-resolved).
//            val pendingResolvedUpperBound= ArrayList<ArrayList<SiType>>() //Upper bound yg udah di-resolved milik type param yg msh pending.
//            val pendingUnresolvedUpperBound= ArrayList<ArrayList<KType>>() //Upper bound yg blum di-resolved milik type param yg msh pending.
            val pendingUpperBoundIndex= ArrayList<ArrayList<Int>>() //Index dari [pendingUnresolvedUpperBound] pada satu type param yg index-nya ditunjuk oleh [pendingUpperBoundTypeParamIndex].

//            prine("getTypeParameter() native= $native")

            native.typeParameters.forEachIndexed { i, typeParam ->
                val siUpperBounds= ArrayList<SiType>()
//                val pendingUpperBounds= ArrayList<KType>()
                val pendingUpperBoundIndexInner= ArrayList<Int>()

                typeParam.upperBounds.forEachIndexed { i, type ->
//                    prine("type= $type classifier native= $native")
//                    prine("type.classifier != native && type.classifier is KClass<*>= ${type.classifier != native && type.classifier is KClass<*>}")
                    if(type.classifier != native && type.classifier is KClass<*>)
                        siUpperBounds.add(type.si)
                    else
                        pendingUpperBoundIndexInner.add(i)
                }

                if(pendingUpperBoundIndexInner.isNotEmpty()){
                    pendingUpperBoundIndex.add(pendingUpperBoundIndexInner)
                    pendingTypeParamIndex.add(i)
//                    pendingResolvedUpperBound.add(siUpperBounds)
                }

                siTypeParams.add( ReflexFactory.createTypeParameter(
                    createNativeWrapper(typeParam), classifier, siUpperBounds, typeParam.variance.si
                ))
            }

            pendingTypeParamIndex.forEachIndexed { i, typeParamIndex ->
                val typeParam= native.typeParameters[typeParamIndex]
                val siTypeParam= siTypeParams[typeParamIndex]

                pendingUpperBoundIndex[i].forEach { upperBoundIndex ->
                    val upperBound= typeParam.upperBounds[upperBoundIndex]
                    var typeArgs= emptyList<SiTypeProjection>()
                    val upperBoundClassifier= when{
                        upperBound.classifier == native -> {
                            typeArgs= siTypeParams
                                .map { ReflexFactory.createType(createNativeWrapper(it), it) }
                                .mapIndexed { u, siType -> SiTypeProjection(siTypeParams[u].variance, siType) }

                            classifier
                        }
                        //brarti classifier-nya brp type param lain.
                        else -> siTypeParams.find { it.name == (upperBound.classifier as KTypeParameter).name }!!
                    }
                    val resolvedPendingUpperBound= ReflexFactory._createType(
                        createNativeWrapper(upperBound), upperBoundClassifier, typeArgs
                    )
                    (siTypeParam.upperBounds as MutableList).add(upperBoundIndex, resolvedPendingUpperBound)
                }
            }
            siTypeParams
        }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn classifier di jvm.")
    }

    actual fun getTypeParameter(
        hostClass: SiClass<*>?,
        callable: SiCallable<*>,
        native: Any
    ): List<SiTypeParameter> = when(native){
        is KCallable<*> -> native.typeParameters.map { it.si.apply { mutableHost= callable } }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn fungsi di jvm.")
    }

    actual fun hasBackingField(property: SiProperty1<*, *>, native: Any): Boolean = when(native){
        is KProperty<*> -> native.javaField != null
        is Field -> true
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn property di jvm.")
    }
/*
    actual fun <R, T> getBackingField(property: SiProperty1<R, T>, native: Any): SiField<R, T>? = when(native){
        is KProperty<*> -> native.javaField.notNullTo {
            ReflexFactory.createField<R, T>(
                createNativeWrapper(it), property, it.name, property.returnType
            )
        }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn property di jvm.")
    }
    actual fun <R, T> getMutableBackingField(property: SiMutableProperty1<R, T>, native: Any): SiMutableField<R, T>? = when(native){
        is KMutableProperty<*> -> native.javaField.notNullTo {
            ReflexFactory.createMutableField<R, T>(
                createNativeWrapper(it), property, it.name, property.returnType
            )
        }
        else -> throw ReflexComponentExc(currentReflexedUnit = native::class, detMsg = "native bkn mutable property di jvm.")
    }
 */
//    fun getSimpleName(classifier: SiNativeClassifier, qualifiedName: String?): String?
}