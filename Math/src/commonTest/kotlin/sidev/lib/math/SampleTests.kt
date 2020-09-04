package main

import sidev.lib.collection.duplicatUnion
import sidev.lib.console.prin
import sidev.lib.math.Sample
import sidev.lib.math.fpb
import sidev.lib.math.kpk
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
}