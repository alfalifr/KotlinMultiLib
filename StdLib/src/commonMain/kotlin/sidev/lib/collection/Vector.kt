package sidev.lib.collection

import sidev.lib.`val`.QueueMode

/**
 * MutableList yang memiliki arah, yaitu yang memiliki index ujung yang merupakan index untuk elemen
 * selanjutnya yang akan dikeluarkan sesuai dg [queueMode].
 */
interface Vector<T>: MutableList<T>, Trimmable {
    val queueMode: QueueMode

    /**
     * Mengambil sekaligus menghapus item [T] pada index ujung dari daftar.
     */
    fun pop(): T

    /**
     * Mengambil item [T] pada index ujung dari daftar, namun tidak menghapusnya.
     */
    fun peek(): T

    /**
     * Menambah item [T] pada index ujung ke daftar.
     * Fungsi ini mengembalikan [item].
     */
    fun push(item: T): T

    /**
     * Fungsi yg digunakan untuk mengambil ujung index sebelum [currentIndex]
     * tepat sebelum sebuah item pada index [removedIndex] dihapus dari daftar.
     */
    fun popIndex(currentIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int= 1): Int

    /**
     * Fungsi yg digunakan untuk mengambil ujung index setelah [currentIndex]
     * tepat sebelum sebuah item dimasukan pada index [addedIndex] di daftar.
     */
    fun pushIndex(currentIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int= 1): Int
}