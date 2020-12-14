package sidev.lib.reflex.full.types

import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiTypeProjection
import sidev.lib.reflex.SiVariance
import sidev.lib.reflex.core.createType
import sidev.lib.reflex.full.isArray
import sidev.lib.reflex.full.isObjectArray
import sidev.lib.reflex.si
import kotlin.jvm.JvmName


@get:JvmName("arrayTypeArgument")
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

