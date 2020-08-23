package sidev.lib.reflex.common.full.types

import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiTypeProjection
import sidev.lib.reflex.common.SiVariance
import sidev.lib.reflex.common.core.createType
import sidev.lib.reflex.common.full.isArray
import sidev.lib.reflex.common.full.isObjectArray
import sidev.lib.reflex.common.native.si
import kotlin.reflect.KClass
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance


val <T: Any> SiClass<T>.arrayTypeArgument: SiTypeProjection?
    get(){
        if(!isArray) return null

        var variance= typeParameters.firstOrNull()?.variance
        val classifier= if(isObjectArray) try{ typeParameters.first() } catch (e: Throwable){ Any::class.si } //block catch jaga" kalo array gak punya typeParam, sprti di Js.
        else {
            variance= SiVariance.INVARIANT
            when(this.descriptor.native){
                IntArray::class -> Int::class
                LongArray::class -> Long::class
                FloatArray::class -> Float::class
                DoubleArray::class -> Double::class
                CharArray::class -> Char::class
                ShortArray::class -> Short::class
                BooleanArray::class -> Boolean::class
                ByteArray::class -> Byte::class
                else -> return null
            }.si
        }
        return SiTypeProjection(variance, classifier.createType())
    }

