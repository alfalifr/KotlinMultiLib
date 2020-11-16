package sidev.lib.range

/**
 * Versi [ClosedDoubleRange] yang pengecekannya dapat diubah antara eksklusif atau inklusif dari [_start] dan [_end]-nya.
 */
class DoubleRange(
    start: Double,
    end: Double,
    val emptyFun: (start: Double, end: Double) -> Boolean,
    val containsFun: (start: Double, end: Double, value: Number) -> Boolean
) : ClosedFloatingPointRange<Double> {
    private val _start = start
    private val _end = end
    override val start: Double get() = _start
    override val endInclusive: Double get() = _end

    override fun lessThanOrEquals(a: Double, b: Double): Boolean = a <= b

    operator fun contains(value: Number): Boolean = containsFun(_start, _end, value)
    override fun contains(value: Double): Boolean = containsFun(_start, _end, value)
    override fun isEmpty(): Boolean = emptyFun(_start, _end)

    override fun equals(other: Any?): Boolean {
        return other is DoubleRange && (isEmpty() && other.isEmpty() ||
                _start == other._start && _end == other._end)
    }

    override fun hashCode(): Int {
        return if (isEmpty()) -1 else 31 * _start.hashCode() + _end.hashCode()
    }

    override fun toString(): String = "$_start..$_end"
}