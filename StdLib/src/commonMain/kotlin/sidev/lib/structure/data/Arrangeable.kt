package sidev.lib.structure.data

import sidev.lib.collection.common.ArrayWrapper
import sidev.lib.collection.common.CommonList
import sidev.lib.collection.common.CommonMutableList
import sidev.lib.collection.common.MutableArrayWrapper
import sidev.lib.structure.prop.SizeProp


/**
 * Interface yang menjadi wadah umum bagi struktur data yang memiliki
 * banyak element yang dapat diakses melalui index, seperti [Array], [MutableList], [MutableArrayWrapper], dan [CommonMutableList].
 * [List], [CommonList], dan [ArrayWrapper] tidak termasuk interface ini karena tidak memiliki fungsi `set`.
 */
interface Arrangeable<T>: FiniteIndexable<T> /*Indexable<T>, SizeProp*/ /*: RangeCopyable<Arrangeable<*>>*/ {
    override val size: Int
    //    val origin: Any
    override operator fun get(index: Int): T
    operator fun set(index: Int, element: T): T
    /**
     * Fungsi `set` yang tidak mengembalikan nilai.
     * Fungsi bertujuan untuk efisiensi proses `set`.
     */
    fun set_(index: Int, element: T)
//    override fun copy(): Arrangeable<T> = copy(0)
//    override fun copy(from: Int, until: Int, reversed: Boolean): Arrangeable<T>
}


internal class ListArrangeable<T>(val list: MutableList<T>): Arrangeable<T> {
    constructor(origin: Array<T>): this(origin.toMutableList())
    constructor(initSize: Int): this(ArrayList(initSize))
    constructor(): this(mutableListOf())

    override val size: Int
        get() = list.size
//    override val origin: Any get() = list
    override fun get(index: Int): T = list[index]
    override fun set(index: Int, element: T): T {
        val old= list[index]
        list[index]= element
        return old
    }
    override fun set_(index: Int, element: T) {
        list[index]= element
    }

    override fun equals(other: Any?): Boolean = list.equals(other)
    override fun hashCode(): Int = list.hashCode()
    override fun toString(): String = list.toString()
}


