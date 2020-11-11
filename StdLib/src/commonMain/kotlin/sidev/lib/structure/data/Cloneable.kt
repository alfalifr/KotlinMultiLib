package sidev.lib.structure.data

/**
 * Interface yg digunakan untuk menandakan bahwa turunan ini dapat di-clone,
 * yaitu membuat instance baru dg tipe [T] dg isi atau nilai dari property sama persis.
 */
interface Cloneable<T: Cloneable<T>> {
    /**
     * Membuat intance baru dg tipe [T] dg isi atau nilai dari property sama persis.
     * [isShallowClone] == true, maka clone hanya sekadar membuat instance baru dg tipe [T]
     * tanpa membuat instance baru untuk setiap nilai dari property yg ada di dalamnya.
     */
    fun clone_(isShallowClone: Boolean = true): T
}