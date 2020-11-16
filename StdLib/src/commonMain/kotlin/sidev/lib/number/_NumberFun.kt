package sidev.lib.number

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.console.prine
import sidev.lib.structure.data.value.Val
import kotlin.math.absoluteValue
import kotlin.reflect.KClass


fun Number.isZero(): Boolean = this.compareTo(0) == 0
fun Number.isNegative(): Boolean = this < 0
fun Number.isPositive(): Boolean = this > 0

fun Number.isNotZero(): Boolean = !isZero()
fun Number.isNotNegative(): Boolean = !isNegative()
fun Number.isNotPositive(): Boolean = !isPositive()

/** @return true jika `this.extension` merupakan angka dg tipe data yg memiliki angka di belakang koma. */
fun Number.isDecimalType(): Boolean = this is Double || this is Float

fun Number.toDecimalType(): Number =
    try{ toFloat() }
    catch (e: NumberFormatException){
        try{ toDouble() }
        catch (e: NumberFormatException){
            prine("""Tidak dapat mengubah `this` ($this) menjadi tipe angka dg koma di belakangnya, return `this`.""")
            this
        }
    }

/*
==== GAK BERHASIL =========
/**
 * Fungsi yg digunakan untuk melakukan operasi pada
 */
internal fun <T: Number, R: Number> T.decimalTypeOp(
    failMsg: String = """Tidak dapat melakukan operasi numberOp() pada `this` ($this) """,
    operation: (T) -> R
): R = when(this){
    is Float -> operation(this)
    is Double -> operation(this)
    else -> {
        prine("Number.decimalTypeOp(): `this` bkn merupakan `Float` atau `Double`, return `this` ($this).")
        this as R
    }
}
 */


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

        @Suppress(SuppressLiteral.UNCHECKED_CAST) //Kotlin dapat meng-cast sendiri tipe data number.
        return if(res.isNotNegative()) res as T
        else (res +1).absoluteValue as T
    }

infix fun Val<Int>.or(num: Int): Val<Int> = apply { value= (value ?: 0) or num }

/*
======================
Math Check Fun
======================
 */

fun <T: Number> T.isIndexNumberOr(default: T): T = notNegativeOr(default)
fun <T: Number> T.notNegativeOr(default: T): T = if(!isNegative()) this else default
fun <T: Number> T.notPositiveOr(default: T): T = if(!isPositive()) this else default
fun <T: Number> T.notZeroOr(default: T): T = if(!isZero()) this else default


fun <T: Number> Number.toFormatLike(other: T): T = toFormat(other::class)

inline fun <reified T: Number> Number.toFormat(): T = toFormat(T::class)
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T: Number> Number.toFormat(cls: KClass<*>): T = when(cls){
    this::class -> this
    Int::class -> toInt()
    Long::class -> toLong()
    Float::class -> toFloat()
    Double::class -> toDouble()
    Byte::class -> toByte()
    Short::class -> toShort()
    else -> throw UnsupportedOperationException("Format angka \"$cls\" tidak diketahui")
} as T
