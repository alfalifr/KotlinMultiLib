package sidev.lib.structure.data

/**
 * Interface turunan ini dapat di-copy scr shallow.
 */
interface Copyable<T: Copyable<T>> {
    fun copy(): T
}