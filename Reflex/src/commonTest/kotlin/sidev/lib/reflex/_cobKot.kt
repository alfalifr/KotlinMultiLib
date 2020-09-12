package sidev.lib.reflex

import sidev.lib.collection.lazy_list.cachedSequenceOf
import sidev.lib.console.prin
import sidev.lib.reflex.full.primaryConstructor
import sidev.lib.reflex.native_.si
import kotlin.test.Test

fun afa(){
    val list= listOf(1,2,3,4)
    for(i in list){
        if(i %3 == 0) continue
        prin("halo i= $i")
    }
}

class CobKot{
    @Test
    fun constrParamTest(){
        val params= Poin::class.si.primaryConstructor.parameters.also { prin("size awal param= ${it.size}") }
//        params.last()
//        params.forEach { /*prin(it.toString() + " size= ${params.size}")*/ }
        prin("batas")
// /*
        val itr= params.iterator().also { prin("itr::class = ${it::class}") } //Poin::class.si.constructors[1].parameters.iterator()

        while(itr.hasNext().also { prin("itr.hasNext()= $it") }){
            prin("itr.next()= ${itr.next()} size= ${params.size}")
        }
// */
    }

    @Test
    fun cobTest(){
        val lazy= cachedSequenceOf(1,2,3,4,5).also { prin("size awal param= ${it.size}") }
        prin(lazy.first())

        for((i, constr) in Poin::class.si.constructors.withIndex()){
            prin("i= $i constr= $constr")
        }
/*
        val itr= lazy.iterator().also { prin("itr::class = ${it::class}") } //Poin::class.si.constructors[1].parameters.iterator()

        while(itr.hasNext().also { prin("itr.hasNext()= $it") }){
            prin("itr.next()= ${itr.next()} size= ${lazy.size}")
        }
 */
    }
}