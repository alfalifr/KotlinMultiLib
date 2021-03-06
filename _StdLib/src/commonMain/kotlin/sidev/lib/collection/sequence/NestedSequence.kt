package sidev.lib.collection.sequence

import sidev.lib.annotation.Interface
import sidev.lib.collection.iterator.NestedIterator

/**
 * Struktur data yg sama seperti [Sequence] namun juga menyertakan
 * sequence turunan (nested sequence).
 */
@Interface
interface NestedSequence<T>: Sequence<T>{
    override fun iterator(): NestedIterator<*, T>
}