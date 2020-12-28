package sidev.lib.structure.data

fun interface Postable<T> {
    fun post(obj: T)
}