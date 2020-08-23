package sidev.lib.reflex

import sidev.lib.universal.`val`.StringLiteral
import sidev.lib.universal.`val`.SuppressLiteral
import kotlin.reflect.KClass


inline fun <reified T: Any> defaultPrimitiveValue(): T?= defaultPrimitiveValue(T::class)
/**
 * Digunakan untuk mendapatkan nilai default dari suatu tipe data yg ada pada [clazz].
 * Nilai default dapat diperoleh jika tipe data pada [clazz] merupakan tipe primitif
 * sesuai definisi yg ada.
 *
 * Fungsi ini tidak dapat menghasilkan nilai default dari Array<*>.
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST, SuppressLiteral.IMPLICIT_CAST_TO_ANY)
fun <T: Any> defaultPrimitiveValue(clazz: KClass<T>): T?{

//    Log.e("defaultPrimitiveValue()", "clazz.simpleName= ${clazz.simpleName} String.classSimpleName_k()= ${String::class.classSimpleName()}")
    val res= when (clazz) {
//        null -> intent.putExtra(it.first, null as Serializable?)
//        CharSequence.classSimpleName() -> ""
        Int::class -> 0
        Long::class -> 0L
        String::class -> ""
        Float::class -> 0f
        Double::class -> 0.0
        Char::class -> '_'
        Short::class -> 0.toShort()
        Boolean::class -> true
        Byte::class -> 0.toByte()

//        Serializable::class -> 0

        IntArray::class -> IntArray(0)
        LongArray::class -> LongArray(0)
        FloatArray::class -> FloatArray(0)
        DoubleArray::class -> DoubleArray(0)
        CharArray::class -> CharArray(0)
        ShortArray::class -> ShortArray(0)
        BooleanArray::class -> BooleanArray(0)
        ByteArray::class -> ByteArray(0)

//        is Serializable -> intent.putExtra(it.first, value)
//        is Bundle -> intent.putExtra(it.first, value)
//        is Parcelable -> intent.putExtra(it.first, value)
//        Array(0){it as T}.classSimpleName_k() -> Array(0){ it as T }

        else -> {
            println("${StringLiteral.ANSI_RED}defaultPrimitiveValue(): Kelas: $clazz bkn merupakan primitif. Hasil akhir == NULL ${StringLiteral.ANSI_RESET}")
//            Log.e("defaultPrimitiveValue()", "Kelas: ${clazz.simpleName} bkn merupakan primitif. Hasil akhir == NULL")
            null
        } //throw Exception("Tipe data \"${clazz.simpleName}\" bukan nilai primitif.")
    }
    return res as? T
}
/*
fun <T: Any> nativeDefaultPrimitiveValue(clazz: KClass<T>): T?{

}
 */


/*
fun <T: Any> checkTypeSafety(any: T): Boolean{

}
 */