package sidev.lib.structure.data

interface Indexable<out T> {
    operator fun get(index: Int): T
}