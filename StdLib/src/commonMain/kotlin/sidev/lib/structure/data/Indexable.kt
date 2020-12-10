package sidev.lib.structure.data

fun interface Indexable<out T> {
    operator fun get(index: Int): T
}