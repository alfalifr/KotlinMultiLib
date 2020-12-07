package sidev.lib

import sidev.lib.`val`.Order
import sidev.lib.algo.selectionSort

fun main(){
    val arr= arrayOf(1,3,1,41)
    arr.selectionSort(0, arr.size, Order.DESC)
}