package main

import sidev.lib.collection.duplicatUnion
import sidev.lib.console.prin
import sidev.lib.math.*
import sidev.lib.math.arithmetic.*
import sidev.lib.math.number.*
import sidev.lib.math.stat.mean
import sidev.lib.math.stat.medianNode
import sidev.lib.math.stat.mode
import sidev.lib.text.removeWhitespace
import kotlin.test.Test
import kotlin.test.assertTrue

class MathSampleTests {
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
    fun fpbTest(){
        prin("25.fpb(100)= ${25.fpb(100)}")
        prin("25.fpb(105)= ${25.fpb(105)}")
    }

    @Test
    fun possibilytTest(){
        class A
        println("combination(3,2)= ${combination(3,2)}")
        println("permutation(3,2)= ${permutation(3,2)}")
        println("listOf(1,3,4,2,4,3).mode() = ${listOf(1,3,4,2,4).mode()}")
        println("arrayOf(1,3,-10,4,1).medianNode()= ${arrayOf(1,3,-10,4,1).medianNode(false)}")
        println("arrayOf(1,2,3,4,1,1,4,8,9,8).average()= ${arrayOf(1,2,3,4,1,1,4,8,9,8).mean()}")
        println("arrayOf(\"a\", 1,4,1f,'z', \"b\", 'c').medianNode()= ${arrayOf<Comparable<*>>("a", 1,4,1f, 'z',"b", 'c').medianNode()}")
        val a= arrayOf("a", 1,4,1f, 'z',"b", 'c')
    }

    @Test
    fun broaderNumberTest(){
        prin("Short.MAX_VALUE in shortValueRange = ${Short.MAX_VALUE in shortValueRange}")
        prin("Short.MAX_VALUE in byteValueRange = ${Short.MAX_VALUE in byteValueRange}")
        prin("Short.MAX_VALUE in intValueRange = ${Short.MAX_VALUE in intValueRange}")

        val num1= wholeNumber(123L)
        prin("num1 = $num1 size= ${num1.bitSize} num1::class = ${num1::class}")

        val num2= wholeNumber(Short.MAX_VALUE)
        prin("num2 = $num2 size= ${num2.bitSize} num2::class = ${num2::class}")

        val num3= num1 + num2
        prin("num3 = $num3 size= ${num3.bitSize} num3::class = ${num3::class}")

        val num4 = floatingNumber(1.2)
        prin("num4 = $num4 size= ${num4.bitSize} num4::class = ${num4::class}")

        val num5 = num3 + num4
        prin("num5 = $num5 size= ${num5.bitSize} num5::class = ${num5::class}")
    }

    @Test
    fun fractionTest(){
        prin("0.25.toFraction()= ${0.25.toFraction()}")
        prin("0.75.toFraction()= ${0.75.toFraction()}")
        prin("0.50.toFraction()= ${0.50.toFraction()}")
        prin("0.125.toFraction()= ${0.125.toFraction()}")

        val fr1= 0.25.toFraction()
        val fr2= 0.75.toFraction()
        val fr3= 0.50.toFraction()
        val fr4= 0.125.toFraction()
        val fr5= fr2.makeCommonFractions(fr4)
        val fr6= commonFractionsOf(fr1, fr2, fr3, fr4)
        val fr7= fr2 + fr4

        prin("fr5= $fr5")
        prin("fr6= $fr6")
        prin("fr7= $fr7 realNumber= ${fr7.realNumber}")

        val fr8= 0.144.toFraction()
        val fr9= 0.121.toFraction()
        val fr10= (fr8 + fr9).simply()
        val fr11= fr8.makeCommonFractions(fr9)

        prin("fr8= $fr8")
        prin("fr9= $fr9")
        prin("fr11= $fr11")
        prin("fr10= $fr10 realNumber= ${fr10.realNumber}")
///*
        val fr12= fr8.numerator per 10
        prin("fr12= $fr12 realNumber= ${fr12.realNumber}")
// */
        val fr13= 36 per 20
        prin("fr13= $fr13")
        prin("fr12 == fr13= ${fr12 == fr13}")

        val fr14= fr13 * 3
        prin("fr14= $fr14")
        prin("fr14 == fr13= ${fr14 == fr13}")

        val fr15= fr14 / 3
        prin("fr15= $fr15")
        prin("fr15 == fr13= ${fr15 == fr13}")
        prin("fr15.simply()= ${fr15.simply()} fr13.simply()= ${fr13.simply()}")

        val fr16= fr15 + 2
        prin("fr16= $fr16")
    }

    @Test
    fun arithmeticTest(){
        //(3x + 10 - (2x + 4) - 4y) * 2
        //((3x + 10) * 2 - (2x + 4) - 4y) * 2
        //(((3x + 10) * 2 - (2x + 4) - 4y) / 2) * 2

        val firstElement= variableOf("x", 3)
        val block= blockOf(firstElement)

        block.addOperation(constantOf(10), Operation.PLUS)
        block.addOperation(variableOf("y", 4), Operation.MINUS)
        block.addOperation(constantOf(2), Operation.TIMES, prioritizePrecedence = true)

        val block2= blockOf(variableOf("x", 2))
        block2.addOperation(constantOf(4), Operation.PLUS)

        block.addOperation(block2, Operation.MINUS, 2)
        block.addOperation(constantOf(2), Operation.TIMES, 2)
        block.addOperation(constantOf(-2), Operation.DIVIDES, 1, false)
//        block.addOperation(constantOf(2), Operation.POWER, prioritizePrecedence = false)

//        (block.elements[1] as Block).addOperation(variableOf("y", 2), Operation.MINUS)

        prin("======== block.elements.forEach(::println) =========")
        block.elements.forEach {
            println("it= $it class= ${it::class}")
        }
        prin("=================")

        prin("======== block.operations.forEach(::println) =========")
        block.operations.forEach(::println)
        prin("=================")

        val x= 5
        val y= 2
        val res= block("x" to x, "y" to y)
        val res2= block2("x" to x)
        prin("res= $res res2= $res2")

        prin("block= $block")

        val blockStr= block.toString()
        prin("blockStr.length= ${blockStr.length}")

        val block3= Block.parse(blockStr.removeWhitespace().also { prin("blockStr.removeWhitespace() = $it") })
        val res3= block3("x" to x, "y" to y)
        prin("res3= $res3")
        prin("block3= $block3")

        prin("======== block3.elements.forEach(::println) =========")
        block3.elements.forEach {
            println("it= $it class= ${it::class} elements= ${if(it is Block) it.elements else "<null>"}")
        }
        prin("=================")

        prin("======== block3.operations.forEach(::println) =========")
        block3.operations.forEach(::println)
        prin("=================")

        val blockStr3= block3.toString()
        prin("blockStr3 == blockStr => ${blockStr3 == blockStr}")
    }
}