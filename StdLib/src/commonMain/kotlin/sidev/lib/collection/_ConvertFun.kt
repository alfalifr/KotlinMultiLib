package sidev.lib.collection

import sidev.lib.check.notNull

/*
===============
Convert
===============
 */
//<4 Juli 2020> => Definisi baru
fun <T> Array<T>.toArrayList(): ArrayList<T>{
    return this.toMutableList() as ArrayList<T>
}


inline fun <I, reified O> Array<I>.toArrayOf(func: (I) -> O?): Array<O> {
    val newList= mutableListOf<O>()
    for(inn in this){
        func(inn).notNull { newList.add(it) }
    }
    return newList.toTypedArray()
}
inline fun <I, reified O> Iterable<I>.toArrayOf(func: (I) -> O?): Array<O> {
    val newList= mutableListOf<O>()
    for(inn in this){
        func(inn).notNull { newList.add(it) }
    }
    return newList.toTypedArray()
}