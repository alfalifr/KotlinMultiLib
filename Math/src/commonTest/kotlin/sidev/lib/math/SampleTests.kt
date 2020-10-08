package main

import sidev.lib.collection.duplicatUnion
import sidev.lib.console.prin
import sidev.lib.math.*
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }

    @Test
    fun mathTest(){
        val numbers= intArrayOf(20, 30, 15, 120, 10)
        val kpk= kpk(*numbers)
        val fpb= fpb(*numbers)

        val list1= listOf(1,3 ,4)
        val list2= listOf(1,3 ,4)

        list1 duplicatUnion list2

        prin("numbers= ${numbers.joinToString()} kpk= $kpk fpb= $fpb")
    }

    @Test
    fun possibilytTest(){
        println("combination(3,2)= ${combination(3,2)}")
        println("permutation(3,2)= ${permutation(3,2)}")
        println("listOf(1,3,4,2,4,3).mode() = ${listOf(1,3,4,2,4,3).mode()}")
        println("listOf(1,3,-10,4,1).medianNode()= ${listOf(1,3,-10,4,1).medianNode()}")
        println("arrayOf(\"a\", 1,4,1f,'z', \"b\", 'c').medianNode()= ${arrayOf("a", 1,4,1f, 'z',"b", 'c').medianNode()}")
        println("arrayOf(1,2,3,4,1,1,4,8,9,8).average()= ${arrayOf(1,2,3,4,1,1,4,8,9,8).average()}")
    }
}