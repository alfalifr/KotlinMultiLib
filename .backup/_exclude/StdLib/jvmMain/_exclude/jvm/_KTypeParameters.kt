package sidev.lib.reflex.jvm

import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.iteratorSimple
import sidev.lib.structure.data.type.LinkedTypeParameter
import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KTypeProjection


/** Mengambil hubungan nested antar [KTypeParameter] dalam satu [KClass]. */
fun KTypeParameter.getTypeParameterLink(clazz: KClass<*>): LinkedTypeParameter? {
    val targetTypeParams= clazz.typeParameters
    if(this !in targetTypeParams) return null

    val linkedTypeParamList= ArrayList<KTypeParameter>()
    for(typeArg in nestedUpperBoundTypeArguments){
        val typeParamItr= typeArg.type?.classifier
        if(typeParamItr in targetTypeParams && typeParamItr !in linkedTypeParamList)
            linkedTypeParamList += typeParamItr as KTypeParameter
    }
    return LinkedTypeParameter(this, linkedTypeParamList)
}

/** Sequence semua nested [KTypeProjection] dari `upperBounds`. `this.extension` upperBounds juga disertakan. */
val KTypeParameter.nestedUpperBoundTypeArguments: NestedSequence<KTypeProjection>
    get()= object : NestedSequence<KTypeProjection> {
        private val initUpperBounds= this@nestedUpperBoundTypeArguments.upperBounds
        override fun iterator(): NestedIterator<KType, KTypeProjection>
                = object : NestedIteratorImpl<KType, KTypeProjection>(initUpperBounds.iterator()){
            private var initOutputEmitionLimit= initUpperBounds.size
            private var emissionNumber= 0
            override fun getOutputIterator(nowInput: KType): Iterator<KTypeProjection>?
                    = if(emissionNumber++ < initOutputEmitionLimit) {
                iteratorSimple(KTypeProjection(this@nestedUpperBoundTypeArguments.variance, nowInput))
            } else nowInput.arguments.iterator()
            override fun getInputIterator(nowOutput: KTypeProjection): Iterator<KType>?
                    = if(nowOutput.type != null) iteratorSimple(nowOutput.type!!) else null
        }
    }