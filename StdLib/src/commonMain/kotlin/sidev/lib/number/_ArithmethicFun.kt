package sidev.lib.number

import sidev.lib.console.prine
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Bbrp bagian kode diambil dari
 * StackOverFlow: https://stackoverflow.com/questions/101439/the-most-efficient-way-to-implement-an-integer-based-power-function-powint-int#answer-34660211
 */
fun Int.pow(exp: Int): Int{
    val exp= exp.also { if(it.isNegative()){
        prine("""Int.pow(): exp negatif ($exp), karena fungsi ini mengembalikan Int, return 0.""")
            //Karena pangkat negatif adalah koma atau mendekati 0, tapi karena return brp Int, maka return 0
            // Ditambah, operasi dg exp negatif tidak aman untuk operasi bit shift.
        return 0
    } }

    return when{
        this == 1 -> 1
        exp == 0 -> 1
        this == 2 -> 1 shl exp
        else -> {
            var base = this
            var exp = exp

            var result = 1
            while (exp != 0) {
                if (exp and 1 == 1) result *= base
                exp = exp shr 1
                base *= base
            }
            result
        }
    }
}


/**
 * Kode diambil dari
 * StackOverFlow: https://stackoverflow.com/questions/101439/the-most-efficient-way-to-implement-an-integer-based-power-function-powint-int#answer-34660211
 */
fun Float.pow(exp: Int): Float {
    if (exp == 0) return 1f
    val temp = pow(exp / 2)
    return when{
        exp % 2 == 0 -> temp *temp
        exp > 0 -> this * temp * temp
        else -> temp * temp / this //negative exponent computation
    }
}


/** Fungsi untuk mengambil `this.extension` pangkat [exp] */
infix fun <T: Number> T.pow(exp: Number): T{
    val base= toDecimalType()
    val exp= exp.toDecimalType()

    return (when(base){
        is Float -> when(exp){
            is Float -> base.pow(exp)
            is Double -> base.toDouble().pow(exp)
            else -> {
                prine("""Number.pow(): Tidak dapat mengambil hasil dari "$this pow $exp", return `this` ($this) """)
                this
            }
        }
        is Double -> when(exp){
            is Float -> base.pow(exp.toDouble())
            is Double -> base.pow(exp)
            else -> {
                prine("""Number.pow(): Tidak dapat mengambil hasil dari "$this pow $exp", return `this` ($this) """)
                this
            }
        }
        else -> {
            prine("""Number.pow(): Tidak dapat mengambil hasil dari "$this pow $exp", return `this` ($this) """)
            this
        }
    }) as T
}

infix fun <T: Number> T.root(root: Number): T = this pow (1.0/root)

fun <T: Number> T.sqrt(): T {
    val base= toDecimalType()

    return (when(base){
        is Float -> sqrt(base)
        is Double -> sqrt(base)
        else -> {
            prine("""Number.pow(): Tidak dapat mengambil hasil dari "$this.sqrt()", return `this` ($this) """)
            this
        }
    }) as T
}

infix fun <T: Number> T.log(x: Number): T {
    val base= toDecimalType()
    val x= x.toDecimalType()

    return (when(base){
        is Float -> when(x){
            is Float -> log(x, base)
            is Double -> log(x, base.toDouble())
            else -> {
                prine("""Number.pow(): Tidak dapat mengambil hasil dari "$this log $x", return `this` ($this) """)
                this
            }
        }
        is Double -> when(x){
            is Float -> log(x.toDouble(), base)
            is Double -> log(x, base)
            else -> {
                prine("""Number.pow(): Tidak dapat mengambil hasil dari "$this log $x", return `this` ($this) """)
                this
            }
        }
        else -> {
            prine("""Number.pow(): Tidak dapat mengambil hasil dari "$this log $x", return `this` ($this) """)
            this
        }
    }) as T
}