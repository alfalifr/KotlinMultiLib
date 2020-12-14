package sidev.lib.collection.sequence

interface SkippingSequence<T>: Sequence<T>{
    /** @return true maka [now] akan dilewati. */
    fun skip(now: T): Boolean
}