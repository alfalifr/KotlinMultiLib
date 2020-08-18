package sidev.lib.collection.iterator

import sidev.lib.annotation.Interface
import sidev.lib.universal.structure.collection.iterator.NestedIterator

/**
 * Digunakan untuk melakukan iterasi terhadap data yg memiliki banyak keturunan.
 * Sebagai contoh adalah [ViewGroup] pada Android yg punya banyak child view.
 *
 * Iterasi dilakukan menggunakan metode DEPTH-FIRST PRE-ORDER.
 */
@Interface
interface NestedIteratorSimple<T>: NestedIterator<T, T>, Iterator<T>{
    override fun getOutputIterator(nowInput: T): Iterator<T>?
    override fun getInputIterator(nowOutput: T): Iterator<T>?
}