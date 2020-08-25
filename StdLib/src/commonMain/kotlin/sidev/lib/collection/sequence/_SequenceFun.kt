package sidev.lib.collection.sequence

inline fun <reified T> Sequence<T>.toTypedArray(): Array<T> = toList().toTypedArray()