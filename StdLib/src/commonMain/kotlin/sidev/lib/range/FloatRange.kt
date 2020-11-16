package sidev.lib.range

/**
 * Versi [ClosedDoubleRange] yang pengecekannya dapat diubah antara eksklusif atau inklusif dari [_start] dan [_end]-nya.
 */
class FloatRange(
    start: Float,
    end: Float,
    val emptyFun: (start: Float, end: Float) -> Boolean,
    val containsFun: (start: Float, end: Float, value: Number) -> Boolean
) : ClosedFloatingPointRange<Float> {
    private val _start = start
    private val _end = end
    override val start: Float get() = _start
    override val endInclusive: Float get() = _end

    override fun lessThanOrEquals(a: Float, b: Float): Boolean = a <= b

    operator fun contains(value: Number): Boolean = containsFun(_start, _end, value)
    override fun contains(value: Float): Boolean = containsFun(_start, _end, value)
    override fun isEmpty(): Boolean = emptyFun(_start, _end)

    override fun equals(other: Any?): Boolean {
        return other is FloatRange && (isEmpty() && other.isEmpty() ||
                _start == other._start && _end == other._end)
    }

    override fun hashCode(): Int {
        return if (isEmpty()) -1 else 31 * _start.hashCode() + _end.hashCode()
    }

    override fun toString(): String = "$_start..$_end"
}