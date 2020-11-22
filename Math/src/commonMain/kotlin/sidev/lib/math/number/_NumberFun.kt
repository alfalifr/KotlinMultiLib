package sidev.lib.math.number

import sidev.lib.number.*
import sidev.lib.number.compareTo
import sidev.lib.range.DoubleRange
import sidev.lib.range.FloatRange
import sidev.lib.range.rangeTo_

val byteValueRange: LongRange get()= Byte.MIN_VALUE .. Byte.MAX_VALUE.toLong()
val shortValueRange: LongRange get()= Short.MIN_VALUE .. Short.MAX_VALUE.toLong()
val intValueRange: LongRange get()= Int.MIN_VALUE .. Int.MAX_VALUE.toLong()
val longValueRange: LongRange get()= Long.MIN_VALUE .. Long.MAX_VALUE

val floatValueRange: FloatRange get()= Float.MIN_VALUE.rangeTo_(Float.MAX_VALUE)
val doubleValueRange: DoubleRange get()= Double.MIN_VALUE.rangeTo_(Double.MAX_VALUE)


fun Byte.isSameAs(short: Short): Boolean = isSameAs_internal(short, byteValueRange)
fun Byte.isSameAs(int: Int): Boolean = isSameAs_internal(int, byteValueRange)
fun Byte.isSameAs(long: Long): Boolean = isSameAs_internal(long, byteValueRange)

fun Short.isSameAs(int: Int): Boolean = isSameAs_internal(int, shortValueRange)
fun Short.isSameAs(long: Long): Boolean = isSameAs_internal(long, shortValueRange)
fun Short.isSameAs(byte: Byte): Boolean = byte.isSameAs(this)

fun Int.isSameAs(long: Long): Boolean = isSameAs_internal(long, intValueRange)
fun Int.isSameAs(short: Short): Boolean = short.isSameAs(this)
fun Int.isSameAs(byte: Byte): Boolean = byte.isSameAs(this)

fun Long.isSameAs(byte: Byte): Boolean = byte.isSameAs(this)
fun Long.isSameAs(short: Short): Boolean = short.isSameAs(this)
fun Long.isSameAs(int: Int): Boolean = int.isSameAs(this)


internal fun Number.isSameAs_internal(broader: Number, minimValueRange: LongRange): Boolean {
    if(broader in minimValueRange)
        return this.compareTo(broader) == 0

    var narrowerItr= this
    var broaderItr= broader

    if(broaderItr.isPositive()){
        val maxVal= minimValueRange.last
        while(broaderItr > maxVal){
            broaderItr -= maxVal
            narrowerItr -= maxVal
        }
    } else if(broaderItr.isNegative()){
        val minVal= minimValueRange.first
        while(broaderItr < minVal){
            broaderItr -= minVal
            narrowerItr -= minVal
        }
    }

    return broaderItr.compareTo(narrowerItr) == 0
}


fun Double.isSameAs(float: Float): Boolean = float.isSameAs(this)
fun Float.isSameAs(double: Double): Boolean {
    if(double in floatValueRange)
        return double.compareTo(this) == 0

    var floatItr= this
    var doubleItr= double

    if(doubleItr.isPositive()){
        while(doubleItr > Float.MAX_VALUE){
            doubleItr -= Float.MAX_VALUE
            floatItr -= Float.MAX_VALUE
        }
    } else if(doubleItr.isNegative()){
        while(doubleItr < Float.MIN_VALUE){
            doubleItr -= Float.MIN_VALUE
            floatItr -= Float.MIN_VALUE
        }
    }

    return doubleItr.compareTo(floatItr) == 0
}


fun wholeNumber(number: Number): WholeNumber = when(number){
    is WholeNumber -> number
    is BroaderNumber -> WholeNumber.fromAny(number.primitiveValue)
    else -> WholeNumber.fromAny(number)
}
fun floatingNumber(number: Number): FloatingNumber = when(number){
    is FloatingNumber -> number
    is BroaderNumber -> FloatingNumber.fromAny(number.primitiveValue)
    else -> FloatingNumber.fromAny(number)
}

fun Number.asWholeNumber(): WholeNumber = wholeNumber(this)
fun Number.asFloatingNumber(): FloatingNumber = floatingNumber(this)


fun FloatingNumber.getDigitBehindDecimal(): Int = when(val vals= primitiveValue){
    is Double -> vals.getDigitBehindDecimal()
    is Float -> vals.getDigitBehindDecimal()
    else -> throw UnsupportedOperationException("`primitiveValue`:\"$vals\" dari `FloatingNumber`:\"$this\" memiliki tipe data yg tidak didukung.")
}