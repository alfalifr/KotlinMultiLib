package sidev.lib.reflex

import sidev.lib.collection.toTypedArray

fun getHashCode(vararg fromObj: Any?, calculateOrder: Boolean= true): Int{
    if(fromObj.isEmpty())
        return 0
    var result = 0 //fromObj.first().hashCode()
    val factor= if(calculateOrder) 31 else 1
    for(obj in fromObj)
        result = (factor * result) + when(obj){
            is Iterable<*> -> getContentHashCode(obj, calculateOrder)
            is Array<*> -> getHashCode(*obj, calculateOrder = calculateOrder)
            else -> obj.hashCode()
        }
    return result
}

fun getContentHashCode(iterable: Iterable<Any?>, calculateOrder: Boolean= true): Int =
    getHashCode(*iterable.toTypedArray(), calculateOrder = calculateOrder)

fun getContentHashCode(map: Map<out Any?, Any?>, calculateOrder: Boolean= true): Int =
    getHashCode(
        *map.iterator().run { Array(map.size){ next().getContentHashCode(calculateOrder) } },
        calculateOrder = calculateOrder
    )

fun Map.Entry<*, *>.getContentHashCode(calculateOrder: Boolean= true): Int =
    getHashCode(key, value, calculateOrder = calculateOrder)