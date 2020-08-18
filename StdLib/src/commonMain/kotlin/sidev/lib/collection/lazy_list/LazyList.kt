package sidev.lib.collection.lazy_list

/**
 * List yg bersifat lazy, yaitu berukuran kecil di awal dan akan berkembang
 * seiring penggunaannya yg melibatkan pemanggilan [builderIterator].
 */
interface LazyList<T>{
    /** Digunakan untuk mengambil iterator yg berfungsi sbg pengisi [LazyList] ini. */
    val builderIterator: Iterator<T>
    fun iteratorHasNext(): Boolean = builderIterator.hasNext()
}