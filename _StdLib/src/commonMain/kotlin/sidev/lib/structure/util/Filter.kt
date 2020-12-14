package sidev.lib.structure.util

fun interface Filter<in T> {
    /**
     * Return `true` jika [e] tidak dihapus dari daftar.
     */
    fun filter(e: T): Boolean
}