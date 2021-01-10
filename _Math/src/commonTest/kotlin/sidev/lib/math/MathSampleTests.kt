package sidev.lib.math

import sidev.lib.collection.*
import sidev.lib.collection.countDuplication
import sidev.lib.console.prin
import sidev.lib.math.arithmetic.*
import sidev.lib.math.number.*
import sidev.lib.math.random.DistributedRandomImpl
import sidev.lib.math.random.distRandomOf
import sidev.lib.math.random.randomBoolean
import sidev.lib.math.stat.mean
import sidev.lib.math.stat.medianNode
import sidev.lib.math.stat.mode
import sidev.lib.number.getFloatingCommonScale
import sidev.lib.number.pow
import sidev.lib.number.root
import sidev.lib.number.toSameScaleWholeNumber
import sidev.lib.text.removeWhitespace
import kotlin.math.absoluteValue
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
        prin("blockStr => $blockStr")
        prin("blockStr3 => $blockStr3")
        prin("blockStr3 == blockStr => ${blockStr3 == blockStr}")
    }

    @Test
    fun blockEqualityTest(){
        // + 2x - 3
        // -3 + 2x
        val block1= blockOf(variableOf("x", 2).also { prin("variable 1= ${it.hashCode()}") })
        val block2= blockOf(constantOf(-3))

        block1.addOperation(constantOf(3), Operation.MINUS)
        block2.addOperation(variableOf("x", 2).also { prin("variable 2= ${it.hashCode()}") }, Operation.PLUS)

        val h1= block1.hashCode()
        val h2= block2.hashCode()

        prin("block1= $block1")
        prin("block2= $block2")

        prin("h1= $h1")
        prin("h2= $h2")

        prin("Operation.PLUS.hashCode()= ${Operation.PLUS.hashCode()}")
        prin("Operation.PLUS.hashCode()= ${Operation.PLUS.hashCode()}")
        prin("Operation.PLUS.hashCode()= ${Operation.PLUS.hashCode()}")

        prin("-3.absoluteValue.hashCode() = ${(-3).absoluteValue.hashCode()}")

        prin("12341231 == 12341231.hashCode() => ${12341231 == 12341231.hashCode()}")

        prin("Operation.PLUS.hashCode() + Operation.MINUS.hashCode() = ${Operation.PLUS.hashCode() + Operation.MINUS.hashCode()}")
        prin("Operation.MINUS.hashCode() + Operation.PLUS.hashCode()= ${Operation.MINUS.hashCode() + Operation.PLUS.hashCode()}")

        prin("block1 == block2 => ${block1 == block2}")

        block1.resultEquals(block2)
    }

    @Test
    fun blockTest_4(){
        // #4 - Ok
        //1*3*2/4
        //((1*3)-10)*2/4 ##1
        //(10-1)*3*2/4 ##2

        val block1= blockOf(constantOf(1))
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        val block2= blockOf(constantOf(1))
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        prin("block awal= $block1")

        block1.addOperation(constantOf(10), Operation.MINUS, 2, false).also { prin("block1= $it") }
        block2.addOperation(constantOf(10), Operation.MINUS, 0, false).also { prin("block1= $it") }

        prin("block1()= ${block1()}")
        prin("block2()= ${block2()}")
    }

    @Test
    fun blockTest_3(){
        // #3 - Ok
        //1*2*3/4
        //5-(1*2*3/4) ##1
        //1-(5*2*3/4) ##2
        //1-|17+(5*2*3/4)| ##2.1
        //1-|5+(17*2*3/4)| ##3 ###2.2

        val block1= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        val block2= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        val block3= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        block1.addOperation(constantOf(5), Operation.MINUS, 0).also { prin("block1 = $it") }
        block2.addOperation(constantOf(5), Operation.MINUS, 1).also { prin("block2 = $it") }
        block3.addOperation(constantOf(5), Operation.MINUS, 1)
        block2.addOperation(constantOf(17), Operation.PLUS, 0).also { prin("block2.1 = $it") }
        block3.addOperation(constantOf(17), Operation.PLUS, 1).also { prin("block3 2.2 = $it") }
    }

    @Test
    fun blockTest_2(){
        // #2 - Ok
        //1*2*3/4
        //0-(1*2*3/4)-5
        //(1*2*3/4)-5 ##1
        //(1*2*3)-(5/4) ##2
        //(1*2)-(5*3/4) ##3

        val block1= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        val block2= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        val block3= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.TIMES)
            .addOperation(constantOf(3), Operation.TIMES)
            .addOperation(constantOf(4), Operation.DIVIDES)

        block1.addOperation(constantOf(5), Operation.MINUS).also { prin("block1 = $it") }
        block2.addOperation(constantOf(5), Operation.MINUS, 3).also { prin("block2 = $it") }
        block3.addOperation(constantOf(5), Operation.MINUS, 2).also { prin("block3 = $it") }
    }

    @Test
    fun blockTest_1(){
        // #1 - Ok
        //1+2+3-4
        //1+2+3-(4*5) ##1
        //(5*1)+2+3-4 ##2
        //(1*5)+2+3-4 ##3
        //1+(2*5)+3-4 ##4

        val block1= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.PLUS)
            .addOperation(constantOf(3), Operation.PLUS)
            .addOperation(constantOf(4), Operation.MINUS)

        val block2= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.PLUS)
            .addOperation(constantOf(3), Operation.PLUS)
            .addOperation(constantOf(4), Operation.MINUS)

        val block3= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.PLUS)
            .addOperation(constantOf(3), Operation.PLUS)
            .addOperation(constantOf(4), Operation.MINUS)

        val block4= blockOf(constantOf(1))
            .addOperation(constantOf(2), Operation.PLUS)
            .addOperation(constantOf(3), Operation.PLUS)
            .addOperation(constantOf(4), Operation.MINUS)

        block1.addOperation(constantOf(5), Operation.TIMES).also { prin("block1 = $it") }
        block2.addOperation(constantOf(5), Operation.TIMES, 0).also { prin("block2 = $it") }
        block3.addOperation(constantOf(5), Operation.TIMES, 1).also { prin("block3 = $it") }
        block4.addOperation(constantOf(5), Operation.TIMES, 2).also { prin("block4 = $it") }

        prin("block1()= ${block1()}")
        prin("block2()= ${block2()}")
        prin("block3()= ${block3()}")
        prin("block4()= ${block4()}")
    }

    @Test
    fun blockParseTest(){

        val block1= Block.parse("(6x / (-2) * 3x) + (10 * 2) - (2x + 4 - 7x) - (4y * 2) ^ 2")
        prin("block1= $block1")
        prin("block1(\"x\" to 2, \"y\" to 3) 1= ${block1("x" to 2, "y" to 3)}")
        prin("block1.simply()= ${block1.simply()}")
        prin("block1(\"x\" to 2, \"y\" to 3) 2= ${block1("x" to 2, "y" to 3)}")
///*
        val block2= Block.parse("2 + x * y ^ 2")
        prin("block2= $block2")
///*
        prin("block2(\"x\" to 2, \"y\" to 3) 1= ${block2("x" to 2, "y" to 3)}")
        prin("block2.simply()= ${block2.simply()}")
        prin("block2(\"x\" to 2, \"y\" to 3) 2= ${block2("x" to 2, "y" to 3)}")
// */

        val block3= Block.parse("2 + x")
            .addOperation(variableOf("y", 1), Operation.TIMES)
            .addOperation(constantOf(2), Operation.POWER)
        prin("block3= $block3")
/*
        prin("block2(\"x\" to 2, \"y\" to 3) 1= ${block2("x" to 2, "y" to 3)}")
        prin("block2.simply()= ${block2.simply()}")
        prin("block2(\"x\" to 2, \"y\" to 3) 2= ${block2("x" to 2, "y" to 3)}")
 */
///*
        val block4= Block.parse("(2 + x * y) ^ 2")
        prin("block4= $block4")
///*
        prin("block4(\"x\" to 2, \"y\" to 3) 1= ${block4("x" to 2, "y" to 3)}")
        prin("block4.simply()= ${block4.simply()}")
        prin("block4(\"x\" to 2, \"y\" to 3) 2= ${block4("x" to 2, "y" to 3)}")
// */
    }

    @Test
    fun blockSimplyTest(){
        val block0= blockOf(variableOf("x", 2))
            .addOperation(constantOf(3), Operation.MINUS)
            .addOperation(variableOf("x", 3), Operation.MINUS)

        listOf(1,2,3,4,5).forEachIndexed(2) { i, it -> prin("it= $it") }

        prin("block0= $block0 level= ${block0.operationLevel}")
        prin("block0.simply()= ${block0.simply()}")

        val block1= Block.parse("(3x / (-2) / 3x) + (10 * 2) - (2x + 4 - 2x) - (4y * 2) ")
        prin("block1= $block1")
        prin("block1.simply() 1= ${block1.simply()}")
        prin("block1.simply() 2= ${block1.simply()}")

        val block2= Block.parse("(6x / 2y * 2y / 6)")
        prin("block2= $block2")
        prin("block2(\"x\" to 2, \"y\" to 3)= ${block2("x" to 2, "y" to 3)}")
        prin("block2.simply()= ${block2.simply()}")
        prin("block2(\"x\" to 2, \"y\" to 3)= ${block2("x" to 2, "y" to 3)}")

        val block3= Block.parse("(3x / (-2) * 3x / x /x/x) + (10 * 2) - (2x + 4 - 7x) - (4y * 2) ")
        prin("block3= $block3")
        prin("block3(\"x\" to 2, \"y\" to 3)= ${block3("x" to 2, "y" to 3)}")
        prin("block3.simply()= ${block3.simply()}")
        prin("block3(\"x\" to 2, \"y\" to 3)= ${block3("x" to 2, "y" to 3)}")

        val block4= Block.parse("(6x / (-2) * 3x) + (10 * 2) - (2x + 4 - 7x) - (4y * 2) ^ 2")
        prin("block4= $block4")
        prin("block4(\"x\" to 2, \"y\" to 3) 1= ${block4("x" to 2, "y" to 3)}")
        prin("block4.simply()= ${block4.simply()}")
        prin("block4(\"x\" to 2, \"y\" to 3) 2= ${block4("x" to 2, "y" to 3)}")

        val block5= Block.parse("(6x / (-2) * 3x) + (-5x + 4) - (10 * 2) - (2x + 4 - 7x) - (4y * 2) ^ 2")
        prin("block5= $block5")
        prin("block5(\"x\" to 2, \"y\" to 3) 1= ${block5("x" to 2, "y" to 3)}")
        prin("block5.simply()= ${block5.simply()}")
        prin("block5.simply()= ${block5}")
        prin("block5(\"x\" to 2, \"y\" to 3) 2= ${block5("x" to 2, "y" to 3)}")
        prin("block5.simply()= ${block5.simply()}")
        prin("block5(\"x\" to 2, \"y\" to 3) 3= ${block5("x" to 2, "y" to 3)}")
    }

    @Test
    fun blockSimplyTest_2(){
        val block1= Block.parse("2 ^ 4 ^ 3x ^ 2 ~ 6x")
        prin("block1= $block1")
        prin("block1.simply()= ${block1.simply()}")

        val block2= Block.parse("2 ^ 4 ^ 3x ^ 2 ~ 6x ~4")
        prin("block2= $block2")
        prin("block2.simply()= ${block2.simply()}")

        val block3= Block.parse("2 ^ 4 ^ 3x ^ (0) ~ 6x ~4")
        prin("block3= $block3")
        prin("block3.simply()= ${block3.simply()}")
    }

    @Test
    fun floatingPowTest(){
        prin(0.11.isPrime())
        prin(0.11.nextPrime())
        prin(0.012.primeFactors())

        prin(1.2 kpk 36)
        prin(1.2 kpk 3.6)
        prin(1.2 kpk 0.36)
        prin(120 kpk 36)
        prin(getFloatingCommonScale(1.2, 0.36))
        prin(toSameScaleWholeNumber(1.2, 0.36))
        prin(toSameScaleWholeNumber(0.5, 30))
        prin(0.5 kpk 30)
        prin(0.12 kpk 36)

        prin(kpk(360, 125))
        prin(kpk(0.1, 0.02, 3.6, 0.005, 1.25))
        prin(fpb(0.1, 0.02, 3.6, 0.005, 1.25))
    }

    @Test
    fun powTest(){
        prin(2 pow 4)
        prin(4 pow 2)
        prin(2 pow 3 pow 4)
        prin(2 pow (3 * 4))
        prin(2 pow 4 pow 3)
        prin(3 pow 4 pow 2)
        prin(4 pow 3 pow 2)
        prin(2 pow (3 pow 4))

        prin(4 pow 6 root 2)
        prin(4 pow (6 / 2))
        prin(4 pow 2 root 6)
        prin(4 pow (2 / 6.0))
    }

    @Test
    fun blockPowTest(){
///*
        val block1= Block.parse("4x ^ 2 ^ 3y")
        prin(block1)
        prin(block1("x" to 1, "y" to 2))
        prin(block1("x" to 9, "y" to 1 / 12.0))

        val block2= Block.parse("4x ^ 6y")
        prin(block2)
        prin(block2("x" to 1, "y" to 2))
        prin(block2("x" to 9, "y" to 1 / 12.0))
// */

        val block3= Block.parse("(2x + 1) + 4y ^ 2 - (2x+1)")
        prin(block3)
        prin(block3("x" to 3, "y" to 2))
        prin(block3.simply())
        prin(block3("x" to 3, "y" to 2))
        prin(block3("x" to 9, "y" to 1 / 12.0))

//        prin(block1.resultEquals(block2))
    }

    //2x-3 = 5
    @Test
    fun simpleEquationTest(){
        val block1= Block.parse("2x-3")
        val block2= constantOf(5)

        val simpleEq1= simpleEquationOf(block1, block2)

        prin(block1)
        prin(block2)
        prin(simpleEq1)

        prin(simpleEq1.solve())
        prin(simpleEq1.test("x" to 4))
        prin(simpleEq1.test("x" to 5))

        val block3= Block.parse("2x-3+y^2")
        val block4= constantOf(10)

        val simpleEq2= simpleEquationOf(block3, block4)

        prin(block3)
        prin(block4)
        prin(simpleEq2)

        prin(simpleEq2.solve())
        prin(simpleEq2)


        val block5= Block.parse("(13 - (y ^ 2)) / 2")
        prin(block5)
        prin(block3)
        prin(block3.replaceVars("x" to block5))
        prin((block3.replaceVars("x" to block5) as Block).simply())


        prin("============== block 6 =================")
        val block6= Block.parse("-2x-3")
        val block7= constantOf(5)

        val simpleEq3= simpleEquationOf(block6, block7, Equation.Sign.LESS_THAN_EQUAL)

        prin(block6)
        prin(block6.simply())
        prin(block7)
        prin(simpleEq3)
        prin(simpleEq3.solve())
    }

    @Test
    fun systemEqTest(){
        val eq1= simpleEquationOf("2x -3 = 5")
        val eq2= simpleEquationOf("2x - 4y = 0")
        val eq3= simpleEquationOf("x - (5y / 2) + 4z = 14")
//        val eq2= simpleEquationOf("2x - 4y = 5")
        prin("eq1= $eq1")
        prin("eq1= ${eq1.left.replaceCalcs()}")
        prin("eq2= $eq2")

        prin("========== eq1.left.varNames ===========")
        for((i, name) in eq1.left.varNames.withIndex()){
            prin("i= $i name= $name")
        }

        val sysEq1= systemEquationOf(eq1, eq2, eq3)
        prin("sysEq1= $sysEq1")
        prin("sysEq1.solve()= ${sysEq1.solve()}")
    }

    //TODO 2 Des 2020: Sempurnakan cara penyelesaian yg membutuhkan perulangan dalam penyelesaian variabel.
    @Test
    fun systemEqTest_2(){
        val eq1= simpleEquationOf("5x -3y +2z = 3")
        val eq2= simpleEquationOf("8x - 5y +6z= 7")
        val eq3= simpleEquationOf("3x + 4y - 3z = 15")
//        val eq2= simpleEquationOf("2x - 4y = 5")
        prin("eq1= $eq1")
        prin("eq1= ${eq1.left.replaceCalcs()}")
        prin("eq2= $eq2")

        prin("========== eq1.left.varNames ===========")
        for((i, name) in eq1.left.varNames.withIndex()){
            prin("i= $i name= $name")
        }

        val sysEq1= systemEquationOf(eq1, eq2, eq3)
        prin("sysEq1= $sysEq1")
        prin("sysEq1.solve()= ${sysEq1.solve()}")
    }

    @Test
    fun distRandomTest(){
        val distRand= distRandomOf<Int>()
        distRand[1]= 2
        distRand[2]= 6
        distRand[4]= 1
        distRand[6]= 1
///*
        distRand[7]= 3
///*
        distRand[8]= 9
        distRand[9]= 5
// */
        prin("(distRand as DistributedRandomImpl).distributions = ${(distRand as DistributedRandomImpl).distributions}")

        val occurences= mutableListOf<Int>()
        for(i in 0 until 100){
            occurences += distRand.next()
        }
        val unique= occurences.countUnique()
        val probs= occurences.countProbabilities()
        prin("occurences= $occurences")
        prin("occurences.countUnique()= $unique")
        prin("occurences.countProbabilities()= $probs")
///*
        val prob9= probs[9]!!
        val prob2= probs[2]!!
        val prob1= probs[1]!!
        val prob7= probs[7]!!
        val prob4= probs[4]

        prin("prob2 >= prob9 = ${prob2 >= prob9}")
        prin("prob7 >= prob1 = ${prob7 >= prob1}")
        prin("prob2 > prob7 = ${prob2 > prob7}")
        prin("prob1 >= prob4 = ${prob4?.compareTo(prob1)?.equals(-1)}")
        prin("prob7 > prob4 = ${prob4?.compareTo(prob7)?.equals(-1)}")
// */
    }

    @Test
    fun boolRandTest(){
        val prob= 0.6

        val occurences= mutableListOf<Boolean>()
        for(i in 0 until 10_000){
            occurences += randomBoolean(prob)
        }

        val unique= occurences.countUnique()
        val probs= occurences.countProbabilities()
        prin("occurences= $occurences")
        prin("occurences.countUnique()= $unique")
        prin("occurences.countProbabilities()= $probs")

        val probT= probs[true]!!
        val probF= probs[false]!!
        prin("probF > probT = ${probF > probT}")
    }
}