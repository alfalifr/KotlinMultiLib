package sidev.lib.number

import sidev.lib.`val`.NumberOperationMode


fun getArithmeticElement(a: Int, b: Int, n: Int): Int = a + b * (n-1)
fun getGeometricElement(a: Int, r: Int, n: Int): Int = a * (r pow n-1).toInt()

fun getArithmeticSum(a: Int, b: Int, n: Int): Int = n * (a + getArithmeticElement(a, b, n)) / 2
fun getGeometricSum(a: Int, r: Int, n: Int): Int =
    (a * if(r.isDecreasingFactor(NumberOperationMode.MULTIPLICATIONAL, true)) (1 - (r pow n)) / (1 - r)
    else ((r pow n) - 1) / (r - 1)).toInt()


fun getArithmeticElement(a: Number, b: Number, n: Number): Number = a + b * (n-1)
fun getGeometricElement(a: Number, r: Number, n: Number): Number = a * (r pow n-1)

fun getArithmeticSum(a: Number, b: Number, n: Number): Number = n * (a + getArithmeticElement(a, b, n)) / 2
fun getGeometricSum(a: Number, r: Number, n: Number): Number =
    a * if(r.isDecreasingFactor(NumberOperationMode.MULTIPLICATIONAL, true)) (1 - (r pow n)) / (1 - r)
    else ((r pow n) - 1) / (r - 1)


fun getArithmeticSequence(a: Int, b: Int, n: Int): Sequence<Int> = Sequence {
    object : Iterator<Int> {
        var currN= 0
        var curr: Int= a
        override fun hasNext(): Boolean = currN < n
        override fun next(): Int {
            val next= curr
            curr += b
            return next
        }
    }
}
fun getGeometricSequence(a: Int, r: Int, n: Int): Sequence<Int> = Sequence {
    object : Iterator<Int> {
        var currN= 0
        var curr: Int= a
        override fun hasNext(): Boolean = currN < n
        override fun next(): Int {
            val next= curr
            curr *= r
            return next
        }
    }
}

fun getArithmeticSequence(a: Number, b: Number, n: Number): Sequence<Number> = Sequence {
    object : Iterator<Number> {
        var currN= 0
        var curr: Number= a
        override fun hasNext(): Boolean = currN < n
        override fun next(): Number {
            val next= curr
            curr += b
            return next
        }
    }
}
fun getGeometricSequence(a: Number, r: Number, n: Number): Sequence<Number> = Sequence {
    object : Iterator<Number> {
        var currN= 0
        var curr: Number= a
        override fun hasNext(): Boolean = currN < n
        override fun next(): Number {
            val next= curr
            curr *= r
            return next
        }
    }
}