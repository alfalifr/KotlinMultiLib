package sidev.lib.number

import sidev.lib.`val`.NumberOperationMode
import sidev.lib.`val`.Order
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.console.prine
import sidev.lib.progression.progressTo
import sidev.lib.structure.data.value.Var
import kotlin.math.absoluteValue
import kotlin.reflect.KClass


fun Number.isZero(): Boolean = this.compareTo(0) == 0
fun Number.isNegative(): Boolean = this < 0
fun Number.isPositive(): Boolean = this > 0

fun Number.isNotZero(): Boolean = !isZero()
fun Number.isNotNegative(): Boolean = !isNegative()
fun Number.isNotPositive(): Boolean = !isPositive()

/**
 * Mengambil tanda dari `this.extension` `Number` yang relatif terhadap 0
 * Return:
 *  - 0 jika `this.extension` == 0
 *  - 1 jika `this.extension` positif
 *  - -1 jika `this.extension` negatif
 */
val Number.signum: Int
    get()= compareTo(0)

/**
 * Menentukan apakah angka `this.extension` merupakan faktor yang menyebabkan angka lain
 * yang dioperasikan dengan [operationMode] menjadi lebih besar nilainya secara [absoluteValue].
 */
fun Number.isIncreasingFactor(operationMode: NumberOperationMode, absoluteValue: Boolean = true): Boolean = when(operationMode){
    NumberOperationMode.INCREMENTAL -> isPositive()
    NumberOperationMode.MULTIPLICATIONAL -> this > 1 || absoluteValue && this < -1
    NumberOperationMode.EXPONENTIAL -> this > 1
}
/**
 * Menentukan apakah angka `this.extension` merupakan faktor yang tidak menyebabkan angka lain
 * yang dioperasikan dengan [operationMode] mengalami perubahan nilai secara [absoluteValue].
 */
fun Number.isNeutralFactor(operationMode: NumberOperationMode, absoluteValue: Boolean = true): Boolean = when(operationMode){
    NumberOperationMode.INCREMENTAL -> isZero()
    NumberOperationMode.MULTIPLICATIONAL -> this == 1 || absoluteValue && this == -1
    NumberOperationMode.EXPONENTIAL -> this == 1
}
/**
 * Menentukan apakah angka `this.extension` merupakan faktor yang menyebabkan angka lain
 * yang dioperasikan dengan [operationMode] menjadi lebih kecil nilainya secara [absoluteValue].
 */
fun Number.isDecreasingFactor(operationMode: NumberOperationMode, absoluteValue: Boolean = true): Boolean =
    !isIncreasingFactor(operationMode, absoluteValue) && !isNeutralFactor(operationMode, absoluteValue)
/**
 * Menentukan apakah angka `this.extension` merupakan faktor yang menyebabkan angka lain
 * yang dioperasikan dengan [operationMode] mengalami perubahan ke nilai yang sama untuk semua angka.
 */
fun Number.isNeutralizingFactor(operationMode: NumberOperationMode): Boolean = when(operationMode){
    NumberOperationMode.INCREMENTAL -> false //Tidak ada angka yang bisa menetralkan angka lain pada mode penjumlahan.
    else -> this == 0
}
/**
 * Menentukan apakah angka `this.extension` merupakan faktor yang menyebabkan angka lain
 * yang dioperasikan dengan [operationMode] mengalami perubahan nilai ke arah sesuai [order] secara [absoluteValue].
 */
fun Number.isProgressingFactor(operationMode: NumberOperationMode, order: Order, absoluteValue: Boolean = true): Boolean = when(operationMode){
    NumberOperationMode.INCREMENTAL -> order == Order.ASC && this > 0 || this < 0
    NumberOperationMode.MULTIPLICATIONAL -> !isNeutralizingFactor(operationMode)
            && (if(absoluteValue) this.absoluteValue else this).run { order == Order.ASC && this > 1 || this < 1 }
    NumberOperationMode.EXPONENTIAL -> !isNeutralizingFactor(operationMode)
            && order == Order.ASC && this > 1 || this < 1
}


inline fun <reified T: Number> getMinProgressingFactor(
    operationMode: NumberOperationMode,
    order: Order = Order.ASC,
    toNegative: Boolean = false
): T = getMinProgressingFactor(T::class, operationMode, order)

/**
 * [toNegative] berguna pada operasi [NumberOperationMode.MULTIPLICATIONAL] yang melibatkan angka negatif.
 */
fun <T: Number> getMinProgressingFactor(
    cls: KClass<T>,
    operationMode: NumberOperationMode,
    order: Order = Order.ASC,
    returnInt: Boolean = false,
    toNegative: Boolean = false
): T = when(operationMode){
    NumberOperationMode.INCREMENTAL -> if(order == Order.ASC) 1 else -1
    NumberOperationMode.MULTIPLICATIONAL -> (if(order == Order.ASC || returnInt) 2 else 0.5).run { if(toNegative) -this else this }
    NumberOperationMode.EXPONENTIAL -> if(order == Order.ASC || returnInt) 2 else 0.5
}.toFormat(cls)


fun getProgressingOrder(operationMode: NumberOperationMode, n1: Number, n2: Number): Order = if(
    if(operationMode == NumberOperationMode.INCREMENTAL) n1 <= n2
    else n1.absoluteValue <= n2.absoluteValue
) Order.ASC else Order.DESC


/** @return true jika `this.extension` merupakan angka dg tipe data yg memiliki angka di belakang koma. */
fun Number.isFloatingType(): Boolean = this is Double || this is Float

fun Number.toFloatingType(): Number =
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
 *   - `Int.MIN_VALUE` dan `Long.MIN_VALUE` akan menghasilkan MIN_VALUE karena overflow.
 */
val <T: Number> T.absoluteValueCast: T
    get(){
        val res= when(this){
            is Int -> absoluteValue
            is Long -> absoluteValue
            is Float -> absoluteValue
            is Double -> absoluteValue
            is Short -> toInt().absoluteValue.toShort()
            is Byte -> toInt().absoluteValue.toByte()
            else -> if(!isNegative()) this else -this
        }

        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        return res as T
/*
        return if(res.isNotNegative()) res as T
        else (res +1).absoluteValue as T
 */
    }
/**
 * Mengambil nilai absolut dari `this.extension` [Number] apapun formatnya.
 * Special Case:
 *   - `Int.MIN_VALUE` dan `Long.MIN_VALUE` akan menghasilkan MIN_VALUE karena overflow.
 */
val Number.absoluteValue: Number get()= when(this){
    is Int -> absoluteValue
    is Long -> absoluteValue
    is Float -> absoluteValue
    is Double -> absoluteValue
    else -> if(!isNegative()) this else -this
}

infix fun Var<Int>.or(num: Int): Var<Int> = apply { value= value or num }

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


/**
 * Menghilangkan tanda desimal (titik) pada angka dg format floating
 * dg cara mengalikan 10 ^ jml digit di belakang tanda desimal.
 */
fun Number.getRidOfDecimal(): Long {
    val factor= 10 powCast getDigitBehindDecimal()
    return (this * factor).toLong()
}

/**
 * Menghilangkan tanda desimal (titik) pada angka dg format floating
 * dg cara mengalikan 10 ^ jml digit di belakang tanda desimal.
 *
 * Properti ini sama dg [getRidOfDecimal], namun lebih praktis karena singkat.
 */
val Number.noDecimalValue: Long
    get()= getRidOfDecimal()

fun max(n1: Number, n2: Number): Number = if(n1 > n2) n1 else n2
fun min(n1: Number, n2: Number): Number = if(n1 < n2) n1 else n2

fun toSameScaleWholeNumber(vararg nums: Number): List<Long> {
    val usedFactor = getCommonScale(*nums)
    return nums.map { (it * usedFactor).toLong() }
}

fun getCommonScale(vararg nums: Number): Long =
    10L powCast nums.map { it.getDigitBehindDecimal() }.reduce { acc, int -> max(acc, int) as Int }

fun getFloatingCommonScale(vararg nums: Number): Double =
    10.0 powCast nums.map { it.getDigitBehindDecimal() }.reduce { acc, int -> max(acc, int) as Int }