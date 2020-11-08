package sidev.lib.collection.iterator

interface WrapperIterator<P, C>: Iterator<P> {
    val childIterator: Iterator<C>
}