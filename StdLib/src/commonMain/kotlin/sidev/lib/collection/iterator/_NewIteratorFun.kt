package sidev.lib.collection.iterator


fun <I, O> newIterator(vararg element: I, mapping: ((I) -> O)?= null): Iterator<O>{
    return if(mapping != null) element.iterator().toOtherIterator(mapping)
    else element.iterator() as Iterator<O>
} //= element.iterator()


fun <T> newIteratorSimple(vararg element: T): Iterator<T> = element.iterator()