package sidev.lib.structure.data

/**
 * Interface turunan ini dapat di-copy scr shallow.
 */
interface Copyable<out T: Copyable<T>> {
    fun copy(): T
}