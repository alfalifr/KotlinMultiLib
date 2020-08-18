package sidev.lib.reflex.type

import sidev.lib.reflex.inner.createType
import sidev.lib.reflex.isArray
import kotlin.reflect.KClass
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance


val <T: Any> KClass<T>.arrayTypeArgument: KTypeProjection?
    get(){
        if(!isArray) return null

        var variance= typeParameters.firstOrNull()?.variance
        val classifier= if(toString() == Array<Any>::class.toString()) typeParameters.first()
        else {
            variance= KVariance.INVARIANT
            when(this){
                IntArray::class -> Int::class
                LongArray::class -> Long::class
                FloatArray::class -> Float::class
                DoubleArray::class -> Double::class
                CharArray::class -> Char::class
                ShortArray::class -> Short::class
                BooleanArray::class -> Boolean::class
                ByteArray::class -> Byte::class
                else -> return null
            }
        }
        return KTypeProjection(variance, classifier.createType())
    }