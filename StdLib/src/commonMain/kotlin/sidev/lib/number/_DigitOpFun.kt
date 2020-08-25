package sidev.lib.number

import sidev.lib.console.prine

/**
 * Mengambil angka pada digit [digitPlace]. Fungsi ini tidak mengambil angka di belakang koma.
 * [digitPlace] dihitung dari belakang, bkn dari depan. [digitPlace] dimulai dari 0.
 * Jika [digitPlace] negatif, brarti angka yg diambil berada di belakang koma.
 */
fun Number.getNumberAtDigit(digitPlace: Int): Int{
//    if(digitPlace.isNegative()) throw ParameterExc(paramName = "digitPlace", detMsg = "Tidak boleh negatif.")
    if(digitPlace.isNegative()){
        if(!this.isDecimalType()) return 0 //Jika ternyata angka yg diambil adalah di belakang koma,
        // sedangkan tipe data angka kelas ini tidak memiliki koma, maka return 0.
        val newThis= this * (10 pow -digitPlace).toInt()
        return newThis.getNumberAtDigit(0)
    }
    val digitPlaceDividerFactor= (digitPlace).notNegativeOr(0)
    val digitPlaceModderFactor= (digitPlace+1).notNegativeOr(0)

    val digitPlaceDivider= (10 pow digitPlaceDividerFactor).toInt()
    val digitPlaceModder= (10 pow digitPlaceModderFactor).toInt()

    return ((this % digitPlaceModder) / digitPlaceDivider).toInt() //as T
}

operator fun Number.get(digitPlace: Int): Int = getNumberAtDigit(digitPlace)

/**
 * Mengambil angka desimal saja. Kemungkinan @return 0 jika `this.extension` adalah angka bulat.
 * Fungsi ini tidak menjamin angka desimal yg diambil bulat dan sesuai input.
 */
fun Number.getDecimal(): Number = this -(this.toInt())
