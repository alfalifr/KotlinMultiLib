package sidev.lib.range

import sidev.lib.`val`.Exclusiveness
import sidev.lib.number.compareTo

fun Double.rangeTo_(
    other: Double,
    startExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE
): DoubleRange = DoubleRange(
    this, other,
    { start, end ->
        if(endExclusiveness == Exclusiveness.EXCLUSIVE || startExclusiveness == Exclusiveness.EXCLUSIVE) start < end
        else start <= end
    },
    { start, end, value ->
        (if(startExclusiveness == Exclusiveness.INCLUSIVE) value >= start else value > start)
                && (if(endExclusiveness == Exclusiveness.INCLUSIVE) value <= end else value < end)
    }
)

fun Float.rangeTo_(
    other: Float,
    startExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE
): FloatRange = FloatRange(
    this, other,
    { start, end ->
        if(endExclusiveness == Exclusiveness.EXCLUSIVE || startExclusiveness == Exclusiveness.EXCLUSIVE) start < end
        else start <= end
    },
    { start, end, value ->
        (if(startExclusiveness == Exclusiveness.INCLUSIVE) value >= start else value > start)
                && (if(endExclusiveness == Exclusiveness.INCLUSIVE) value <= end else value < end)
    }
)


operator fun <R, T> R.contains(number: Number): Boolean
        where T: Number, T: Comparable<T>, R: ClosedRange<T>, R: LongProgression =
    number >= first && number <= last

operator fun <R, T> R.contains(number: Number): Boolean
        where T: Number, T: Comparable<T>, R: ClosedRange<T>, R: IntProgression =
    number >= first && number <= last