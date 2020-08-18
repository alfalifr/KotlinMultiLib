package sidev.lib.number

import kotlin.math.absoluteValue


fun Number.isZero(): Boolean = this.compareTo(0) == 0
fun Number.isNegative(): Boolean = this < 0
fun Number.isPositive(): Boolean = this > 0

fun Number.isNotZero(): Boolean = !isZero()
fun Number.isNotNegative(): Boolean = !isNegative()
fun Number.isNotPositive(): Boolean = !isPositive()

/** @return true jika `this.extension` merupakan angka dg tipe data yg memiliki angka di belakang koma. */
fun Number.isDecimalType(): Boolean = this is Double || this is Float


/** Fungsi yg mengubah [String] menjadi [Number] apapun. */
fun String.toNumber(): Number{
    return try{ toInt() }
    catch (e: NumberFormatException){
        try{ toFloat() }
        catch (e: NumberFormatException){
            try{ toLong() }
            catch (e: NumberFormatException){ toDouble() }
        }
    }
}

fun Number.asNumber(): Number = this
/**
 * Mengambil nilai absolut dari `this.extension` [Number] apapun formatnya.
 * Special Case:
 *   - `Int.MIN_VALUE` dan `Long.MIN_VALUE` akan menghasilkan MIN_VALUE +1 agar bisa jadi positif.
 */
val <T: Number> T.absoluteValue: T
    get(){
        val res= when(this){
            is Int -> absoluteValue
            is Long -> absoluteValue
            is Float -> absoluteValue
            is Double -> absoluteValue
            else -> if(!isNegative()) this else -this
        }
        return if(res.isNotNegative()) res as T
        else (res +1).absoluteValue as T
    }


/*
======================
Math Check Fun
======================
 */

fun <T: Number> T.isIndexNumberOr(default: T): T = notNegativeOr(default)
fun <T: Number> T.notNegativeOr(default: T): T = if(!isNegative()) this else default
fun <T: Number> T.notPositiveOr(default: T): T = if(!isPositive()) this else default
fun <T: Number> T.notZeroOr(default: T): T = if(!isZero()) this else default

