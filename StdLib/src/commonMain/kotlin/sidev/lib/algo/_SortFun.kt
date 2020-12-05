package sidev.lib.algo

import sidev.lib.`val`.Order
import sidev.lib.collection.copy
import sidev.lib.number.univLessThan
import sidev.lib.number.univMoreThan

fun <T: Comparable<T>> Array<T>.swap(i: Int, u: Int) {
    val tmp= this[i]
    this[i]= this[u]
    this[u]= tmp
}

fun <T: Comparable<T>> Array<T>.selectionSort(order: Order= Order.ASC){
    val replaceFun= if(order == Order.ASC) ::univMoreThan else ::univLessThan
    for(i in indices){
        val iVal= this[i]
        for(u in i+1 until size){
            val uVal= this[u]
            if(replaceFun(iVal, uVal)){
                this[i]= uVal
                this[u]= iVal
            }
        }
    }
}

fun <T: Comparable<T>> Array<T>.insertionSort(order: Order= Order.ASC){
    val replaceFun= if(order == Order.ASC) ::univMoreThan else ::univLessThan
    for(i in 0 until size-1){
        val iVal= this[i]
        if(replaceFun(iVal, this[i+1])){
            var head= 0
            for(u in i downTo 0){
                val uVal= this[u]
                if(replaceFun(iVal, uVal)){
                    head= u
                    break
                }
                this[u+1]= uVal
            }
            this[head]= iVal
        }
    }
}