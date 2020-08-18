package sidev.lib.collection.iterator

import sidev.lib.annotation.Interface

@Interface
interface SkippableIterator<T>: Iterator<T>{
    /** @return true maka [now] akan dilewati. */
    fun skip(now: T): Boolean
}