package sidev.lib.structure.data

import sidev.lib.structure.util.Comparison
import sidev.lib.structure.util.Filter

/**
 * Interface yang dapat melakukan fungsi `sort`, `filter`, dan `search`.
 */
interface Arranger<T> {
    /**
     * Mengurutkan elemen sesuai nilai komparasi yang dihasilkan oleh [comparator].
     */
    fun sort(comparator: Comparator<T>)

    /**
     * Memfilter atau membuang elemen yang mengembalikan nilai `false` dari [filter].
     */
    fun filter(filter: Filter<T>)

    /**
     * Mencari elemen tertentu yang mengembalikan nilai komparasi 0 dari [comparison].
     * Return index dari elemen yang mengembalikan nilai komparasi 0 atau -1
     * jika tidak ada elemen yang mengembalikan 0.
     */
    fun search(comparison: Comparison<T>): Int
}