package sidev.lib.structure.util

/**
 * Interface yang dapat memberikan `comparison` [e] terhadap
 * elemen yang diharapkan.
 */
fun interface Comparison<T> {
    /**
     * [e] adalah elemen yang diiterasi.
     * Semisal `a` adalah elemen yang diharapkan, maka fungsi ini me-return perbandingan:
     *   -0 berarti [e] merupakan atau sebanding dg `a`.
     *   -Negatif berarti [e] berada pada posisi sebelum `a`.
     *   -Positif berarti [e] berada pada posisi sesudah `a`.
     */
    fun comparison(e: T): Int
}