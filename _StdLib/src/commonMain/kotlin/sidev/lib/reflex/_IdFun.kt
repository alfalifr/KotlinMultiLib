package sidev.lib.reflex

import sidev.lib.collection.maxSize
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

fun getHashCode(vararg fromObj: Any?, calculateOrder: Boolean= true): Int{
    if(fromObj.isEmpty())
        return 0
    var result = 0 //fromObj.first().hashCode()
    val factor= if(calculateOrder) 31 else 1
    for(obj in fromObj)
        result = (factor * result) + when(obj){
            is Iterable<*> -> getContentHashCode(obj, calculateOrder)
            is Array<*> -> getHashCode(*obj, calculateOrder = calculateOrder)
            is Map<*, *> -> getContentHashCode(obj, calculateOrder)
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