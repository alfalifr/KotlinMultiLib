package sidev.lib.number

import sidev.lib.universal.`val`.RoundMethod
import sidev.lib.universal.`val`.SuppressLiteral


infix fun Int.roundClosest(range: IntRange): Int{
    val diffToFirst= (this -range.first).asNumber().absoluteValue //Dijadikan number agar Int.MIN_VALUE dan Long.MIN_VLAUE dapat diubah jadi absolute value.
    val diffToLast= (this -range.last).asNumber().absoluteValue

    return if(diffToFirst < diffToLast) range.first
    else range.last
}
infix fun Number.roundClosest(range: IntRange): Int{
    val diffToFirst= (this -range.first).absoluteValue
    val diffToLast= (this -range.last).absoluteValue

    return if(diffToFirst < diffToLast) range.first
    else range.last
}

/**
 * Membulatkan angka pada [digitPlace] -1 dg menjadikan angka pada [digitPlace]-1 jadi 0
 * dan angka pada [digitPlace] ditambah 1 jika [digitPlace]-1 >= 5.
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T: Number> T.round(digitPlace: Int= 0, method: RoundMethod = RoundMethod.ROUND): T{
    if(digitPlace.isNegative()){
        if(!this.isDecimalType()) return this//Jika ternyata angka yg diambil adalah di belakang koma,
        // sedangkan tipe data angka kelas ini tidak memiliki koma, maka return angka ini.
        val digitTimer= (10 pow -digitPlace).toInt().toDouble() //Agar hasil koma bisa kelihatan dg pas.
        val newThis= this * digitTimer
        return (newThis.round(0, method) / digitTimer) as T
    }
    val numberInDigit= getNumberAtDigit(digitPlace-1)

    val digitPlaceDividerFactor= (digitPlace).notNegativeOr(0)
    val digitPlaceDivider= (10 pow digitPlaceDividerFactor).toInt()

    val increment= when(method){
        RoundMethod.ROUND -> if(numberInDigit < 5) 0 else 1
        RoundMethod.CEIL -> 1
        RoundMethod.FLOOR -> 0
    }
    return (((this / digitPlaceDivider).toInt() + increment) * digitPlaceDivider) as T
}
