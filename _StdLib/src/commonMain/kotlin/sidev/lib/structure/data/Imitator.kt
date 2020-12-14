package sidev.lib.structure.data

/**
 * Interface yang dapat meniru semua internal state (field) pada objek yang di-pass ke [imitate].
 */
fun interface Imitator<T: Imitator<T>> {
    /**
     * Men-copy semua field pada [other] ke `this`.
     */
    fun imitate(other: T)
}