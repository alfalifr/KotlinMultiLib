package sidev.lib.math.number

/**
 * [Number_] yg memiliki tipe lebih luas yaitu [WholeNumber] dan [FloatingNumber].
 */
interface BroaderNumber: Number_ {
//    val isStable: Boolean
    val isInGoodIntegrity: Boolean
    val bitSize: Int
    val minValue: Number
    val maxValue: Number
    val primitiveValue: Number

    /**
     * Menentukan apakah [other] memiliki nilai [primitiveValue] yang msh dalam jangkauan `this` [primitiveValue],
     * yaitu [other] >= [minValue] && [other] <= [maxValue].
     */
    fun isStableWith(other: BroaderNumber): Boolean = other.primitiveValue.run {
        compareTo(minValue) >= 0 && compareTo(maxValue) <= 0
    }

    /**
     * Mengecilkan ukuran space pada memory yg digunakan untuk menyimpan [primitiveValue].
     */
    fun trim(): BroaderNumber
}