package sidev.lib.structure.data

interface RangeCopyable<out T: RangeCopyable<T>>: Copyable<T> {
    val maxRange: Int
    override fun copy(): T = copy(0)
    fun copy(from: Int= 0, until: Int= maxRange, reversed: Boolean= false): T
}