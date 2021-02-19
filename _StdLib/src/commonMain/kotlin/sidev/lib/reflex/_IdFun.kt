package sidev.lib.reflex

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.common.ArrayWrapper
import sidev.lib.collection.common.asWrapped
import sidev.lib.collection.toTypedArray

/*
//TODO 2 Des 2020: Dikembangkan di masa mendatang.
fun getPairHashCode(vararg fromObjList: List<Any?>, calculateOrder: Boolean= true): Int {
    if(fromObjList.isEmpty())
        return 0

    if(!calculateOrder)
        return getHashCode(*fromObjList)

    var result = 0 //fromObj.first().hashCode()
    val factor= 31 //if(calculateOrder) 31 else 1

    val maxSize= fromObjList.fold(0) { acc, list ->
        val size= list.size
        if(acc < size) size else acc
    }

    val a= fromObjList

    for(i in 0 until maxSize){

    }
    for(obj in fromObj)
        result = (factor * result) + when(obj){
            is Iterable<*> -> getContentHashCode(obj, calculateOrder)
            is Array<*> -> getHashCode(*obj, calculateOrder = calculateOrder)
            else -> obj.hashCode()
        }
    return result
}
 */

fun getHashCode(
    vararg fromObj: Any?,
    calculateOrder: Boolean= true,
    nestedCalculation: Boolean = true,
    ignoreLevel: Boolean= true,
    level: Int= 0
): Int{
    if(fromObj.isEmpty())
        return 0
    var result = 0 //fromObj.first().hashCode()
    val factor= if(calculateOrder) 31 else 1
    if(nestedCalculation) {
        val levelFactor= if(ignoreLevel) 0 else 31
        for(obj in fromObj){
            result = (factor * result) + when(obj){
                is Iterable<*> -> getContentHashCode(obj, calculateOrder)
                is Array<*> -> getHashCode(
                    *obj, calculateOrder = calculateOrder,
                    nestedCalculation = nestedCalculation,
                    ignoreLevel = ignoreLevel,
                    level = level+1
                )
                //is ArrayWrapper<*> -> getContentHashCode(obj, calculateOrder)
                is Map<*, *> -> getContentHashCode(obj, calculateOrder)
                is ByteArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                is ShortArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                is IntArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                is LongArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                is FloatArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                is DoubleArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                is BooleanArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                is CharArray -> getContentHashCode(obj.asWrapped(false), calculateOrder, nestedCalculation, ignoreLevel, level+1)
                else -> when {
                    obj == null -> 0
                    obj::class.simpleName == Array::class.simpleName ->
                        @Suppress(SuppressLiteral.UNCHECKED_CAST)
                        getHashCode(
                            *(obj as Array<Any?>),
                            calculateOrder = calculateOrder,
                            nestedCalculation = nestedCalculation,
                            ignoreLevel = ignoreLevel,
                            level = level+1
                        )
                    else -> obj.hashCode()
                }
            }
            result += levelFactor * level
        }
    } else {
        for(obj in fromObj)
            result = (factor * result) + obj.hashCode()
    }
    return result
}

fun getContentHashCode(
    iterable: Iterable<Any?>,
    calculateOrder: Boolean= true,
    nestedCalculation: Boolean = true,
    ignoreLevel: Boolean= true,
    level: Int= 0
): Int =
    getHashCode(
        *iterable.toTypedArray(),
        calculateOrder = calculateOrder,
        nestedCalculation = nestedCalculation,
        ignoreLevel = ignoreLevel,
        level = level
    )

fun getContentHashCode(
    map: Map<out Any?, Any?>,
    calculateOrder: Boolean= true,
    nestedCalculation: Boolean = true,
    ignoreLevel: Boolean= true,
    level: Int= 0
): Int =
    getHashCode(
        *map.iterator().run { Array(map.size){ next().getContentHashCode(calculateOrder) } },
        calculateOrder = calculateOrder,
        nestedCalculation = nestedCalculation,
        ignoreLevel = ignoreLevel,
        level = level
    )

fun Map.Entry<*, *>.getContentHashCode(
    calculateOrder: Boolean= true,
    nestedCalculation: Boolean = true,
    ignoreLevel: Boolean= true,
    level: Int= 0
): Int =
    getHashCode(
        key, value,
        calculateOrder = calculateOrder,
        nestedCalculation = nestedCalculation,
        ignoreLevel = ignoreLevel,
        level = level
    )