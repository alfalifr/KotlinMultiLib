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
     * Fungsi yg digunakan untuk mengambil index ujung yg digunakan untuk mengeluarkan item ujung
     * jika index ujung sebelum pop adalah [cursorIndex] dan ukuran koleksi adalah [currentSize]
     * dengan ukuran yg dikeluarkan sebanyak [removedCount] dimulai dari [removedIndex].
     *
     * Fungsi ini dipanggil tepat sebelum operasi [pop].
     */
    fun popIndex(cursorIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int= 1): Int

    /**
     * Fungsi yg digunakan untuk mengambil index ujung yg digunakan untuk memasukan item ujung
     * jika index ujung sebelum push adalah [cursorIndex] dan ukuran koleksi adalah [currentSize]
     * dengan ukuran yg dimasukan sebanyak [addedCount] dimulai dari [addedCount].
     *
     * Fungsi ini dipanggil tepat sebelum operasi [push].
     */
    fun pushIndex(cursorIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int= 1): Int
}