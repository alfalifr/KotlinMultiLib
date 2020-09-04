package sidev.lib

import sidev.lib.`val`.RoundingMode
import sidev.lib.collection.common.arrayWrapperOf
import sidev.lib.collection.common.getValue
import sidev.lib.collection.lazy_list.CachedSequence
import sidev.lib.collection.lazy_list.LazyHashMap
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.number.*
import sidev.lib.reflex.native.si
import kotlin.math.exp
import kotlin.math.log
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }


/*
    @Test
    fun stdReflexTest(){
        prin("AC::class.si= ${AC::class.si}")
        for((i, member) in AC::class.si.members.withIndex()){
            prin("i= $i member= $member")
        }
    }
 */


    @Test
    fun mathTest(){
        val int= 2.3 as Number
        val int2= (2 as Number / int)
        prin("int= $int2")

        prin(ipow(2, 3))
        prin(ipow(2, -2))
        prin("2.pow(-2)= ${2 pow -2}")
        prin("125 root 3= ${125 root 3}")
        prin("81 root 4= ${81 root 4}")
        prin("81 root 2= ${81 root 2}")
        prin("81.sqrt()= ${81.sqrt()}")
        prin("exp(2.0)= ${exp(2.0)}")
        prin(ipow(3, 4))
        prin(ipow(5, 5))
        log(2f, 3f)
        prin("10 log 1000= ${10 log 1000}")
        prin("3 log 81= ${3 log 81}")

        prin("223.1352= ${223.1352}")
        prin("223.1352.round()= ${223.1352.round()}")
        prin("-223.1352.round()= ${(-223.1352).round()}")
        prin("223.1352.round(-1)= ${223.1352.round(-1)}")
        prin("-223.1352.round(-1)= ${(-223.1352).round(-1)}")
        prin("223.1352.round(-2)= ${223.1352.round(-2)}")

        prin("-223.1352.round(-2, RoundingMode.UP)= ${(-223.1352).round(-2, RoundingMode.UP)}")
        prin("223.1352.round(-2, RoundingMode.UP)= ${(223.1352).round(-2, RoundingMode.UP)}")
        prin("-223.1352.round(-2, RoundingMode.CEIL)= ${(-223.1352).round(-2, RoundingMode.CEIL)}")
        prin("223.1352.round(-2, RoundingMode.CEIL)= ${(223.1352).round(-2, RoundingMode.CEIL)}")
        prin("-223.1352.round(-2, RoundingMode.DOWN)= ${(-223.1352).round(-2, RoundingMode.DOWN)}")
        prin("223.1352.round(-2, RoundingMode.DOWN)= ${(223.1352).round(-2, RoundingMode.DOWN)}")
        prin("-223.1352.round(-2, RoundingMode.FLOOR)= ${(-223.1352).round(-2, RoundingMode.FLOOR)}")
        prin("223.1352.round(-2, RoundingMode.FLOOR)= ${(223.1352).round(-2, RoundingMode.FLOOR)}")
        prin("223.1352.round(-2, RoundingMode.HALF_UP)= ${(223.1352).round(-2, RoundingMode.HALF_UP)}")
        prin("223.1352.round(-2, RoundingMode.HALF_DOWN)= ${(223.1352).round(-2, RoundingMode.HALF_DOWN)}")
        prin("223.1352[-2]= ${223.1352[-2]}")
    }


    fun ipow(base: Int, exp: Int): Int {
        prine("base= $base exp= $exp")
        if (exp == 0) return 1
        val temp = ipow(base, (exp / 2))
        return if (exp % 2 == 0) temp * temp else base * temp * temp
    }

    @Test
    fun commonListTest1(){
        val arrayWrap= arrayWrapperOf(1,3,5,6.778,"hkj")
        val array by arrayWrap
        array.forEach {
            prin("array foreeach it= $it")
        }
    }

    @Test
    fun commonListTest2(){
        println("\n============= BATAS CachedSequence ==============\n")
        val strSeq= sequenceOf("Aku", "Mau", "Makan")
        val strSeq2= sequenceOf("Kamu" , "Dan", "Dia")
        val lazySeq= CachedSequence<String>()
        lazySeq += "Halo"
        lazySeq += "Bro"
        lazySeq += strSeq
        lazySeq += strSeq2
///*
        val containsAku= "Aku" in lazySeq //.contains("Aku")
        val containsKamu= "Kamu" in lazySeq //.contains("Kamu")
        val containsDiaJelek= "Dia Jelek" in lazySeq //.contains("Kamu")
        val indexMau= lazySeq.indexOf("Mau")
        val indexKamu= lazySeq.indexOf("Kamu")
        val ke4= lazySeq[4]
        prin("indexMau= $indexMau ke4= $ke4 containsAku= $containsAku containsKamu= $containsKamu containsDiaJelek= $containsDiaJelek indexKamu= $indexKamu")
// * /

        println("\n============= BATAS CachedSequence.iterator() ==============\n")
        for((i, data) in lazySeq.withIndex()){
            prin("i= $i data= $data")
        }

        println("\n============= BATAS LazyHashMap ==============\n")

        val pairSeq= sequenceOf("Aku" to 3, "Dia" to 10, "Kamu" to 1)
        val pairSeq2= sequenceOf("Makan" to 2, "Belajar" to 5, "Tidur" to 0)
        val lazyMap= LazyHashMap<String, Int>()
        lazyMap["Mau"]= 7
        lazyMap["Iya"]= 6

        lazyMap += pairSeq
        lazyMap += pairSeq2

        println("\n============= BATAS LazyHashMap.iterator() ==============\n")
        for((i, data) in lazyMap.withIndex()){
            prin("i= $i data= $data")
        }

        val valueMau= lazyMap["Mau"]
        val valueIya= lazyMap["Iya"]
        val valueKamu= lazyMap["Kamu"]
        val valueTidur= lazyMap["Tidur"]
        val contains5= lazyMap.containsValue(5)
        val contains0= lazyMap.containsValue(0)
        val contains4= lazyMap.containsValue(4)

        prin("valueMau= $valueMau valueIya= $valueIya valueKamu= $valueKamu contains5= $contains5 contains0= $contains0 contains4= $contains4 valueTidur= $valueTidur")
    }
}