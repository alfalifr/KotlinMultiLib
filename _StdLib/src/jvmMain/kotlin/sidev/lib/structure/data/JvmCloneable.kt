package sidev.lib.structure.data

/**
 * [Cloneable] yg disesuaikan untuk bahasa Java.
 * Interface ini berguna untuk memanggil `clone_(Boolean)` di mana overriding
 * tidak dapat dilakukan pada interface.
 */
interface JvmCloneable<T: JvmCloneable<T>>: Cloneable<T>, kotlin.Cloneable {
    fun clone_(): T = clone_(true)
}