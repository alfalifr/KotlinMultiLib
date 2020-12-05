package sidev.lib.algo

import sidev.lib.`val`.Order
import sidev.lib.number.univLessThan
import sidev.lib.number.univMoreThan

fun <T: Comparable<T>> Array<T>.swap(i: Int, u: Int) {
    val tmp= this[i]
    this[i]= this[u]
    this[u]= tmp
}

fun <T: Comparable<T>> Array<T>.selectionSort(left: Int = 0, right: Int = size, order: Order= Order.ASC){
    val replaceFun: (T, T) -> Boolean = if(order == Order.ASC) ::univMoreThan else ::univLessThan
    for(i in left until right){
        val iVal= this[i]
        for(u in i+1 until right){
            val uVal= this[u]
            if(replaceFun(iVal, uVal)){
                this[i]= uVal
                this[u]= iVal
            }
        }
    }
}

fun <T: Comparable<T>> Array<T>.insertionSort(left: Int = 0, right: Int = size, order: Order= Order.ASC){
    val replaceFun: (T, T) -> Boolean = if(order == Order.ASC) ::univMoreThan else ::univLessThan
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
//0 1 2 3 4 5 6 7 8 9  //10
//l         r
//
fun <T: Comparable<T>> Array<T>.mergeSort(left: Int = 0, right: Int = size, order: Order= Order.ASC){
    if(left >= right) return
    val checkFun: (T, T) -> Boolean = if(order == Order.ASC) ::univLessThan else ::univMoreThan

    fun Array<T>.merge(left: Int = 0, right: Int = size){
        var all= left -1
        var l= left
        var r= (right - left) ushr 1

        val lArr= copyOfRange(l, r)
        val rArr= copyOfRange(r, size)

        while(++all < right){
            val lVal= lArr[l]
            val rVal= rArr[r]
            this[all]= if(checkFun(lVal, rVal)){
                l++
                lVal
            } else {
                r++
                rVal
            }
        }
    }

    val mid= (right - left) ushr 1
    //Left
    mergeSort(left, mid, order)
    mergeSort(mid+1, right, order)
    merge(left, right)
}

//TODO 5 Des 2020: Quick Sort