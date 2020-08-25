package sidev.lib.reflex.common

/**
 * Field tempat menyimpan nilai dari property.
 * Semua field dapat dilakukan operasi read-write.
 * Disaranakan untuk pengubahan nilai menggunakan turunan [SiProperty]
 * agar lebih aman. Penggunaan [SiField] dilakukan untuk
 * kepentingan refleksi yg mendesak.
 */
interface SiField<in R, out T>: SiDescriptorContainer{
    val name: String
    val type: SiType
    fun get(receiver: R): T
//    fun set(receiver: R, value: T)
}

interface SiMutableField<in R, T>: SiField<R, T>{
    fun set(receiver: R, value: T)
}