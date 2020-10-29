package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import kotlin.ranges.CharProgression as CharProgressionKt
import kotlin.ranges.until as untilKt


//fun <T> T.progressTo(other: T): NumberProgression<T> where T : Number, T: Comparable<T> = progressTo(other, 1 as T)

operator fun <T> T.rangeTo(other: Number): NumberProgression<T> where T : Number, T: Comparable<T> = progressTo(other)
operator fun Char.rangeTo(other: Char): CharProgression = progressTo(other)

infix fun <T> T.until(other: Number): NumberProgression<Int> where T : Number, T: Comparable<T> =
    this.toInt().progressTo(other.toInt(), endExclusiveness = Exclusiveness.EXCLUSIVE)
infix fun Char.until(other: Char): CharProgression = progressTo(other, endExclusiveness = Exclusiveness.EXCLUSIVE)

infix fun <T> T.downTo(other: Number): NumberProgression<Int> where T : Number, T: Comparable<T> =
    this.toInt().progressTo(other.toInt(), step = -1)
infix fun <T> T.downUntil(other: Number): NumberProgression<Int> where T : Number, T: Comparable<T> =
    this.toInt().progressTo(other.toInt(), step = -1, endExclusiveness = Exclusiveness.EXCLUSIVE)

infix fun Char.downTo(other: Char): CharProgression = progressTo(other, step = -1)
infix fun Char.downUntil(other: Char): CharProgression = progressTo(other, step = -1, endExclusiveness = Exclusiveness.EXCLUSIVE)

infix fun <T> T.percentileTo(other: Number): NumberProgression<Float> where T : Number, T: Comparable<T> =
    this.toFloat().progressTo(other.toFloat(), 0.01f)
infix fun <T> T.percentileUntil(other: Number): NumberProgression<Float> where T : Number, T: Comparable<T> =
    this.toFloat().progressTo(other.toFloat(), 0.01f, endExclusiveness = Exclusiveness.EXCLUSIVE)

infix fun <T> T.centileTo(other: Number): NumberProgression<Float> where T : Number, T: Comparable<T> =
    this.toFloat().progressTo(other.toFloat(), 0.1f)
infix fun <T> T.centileUntil(other: Number): NumberProgression<Float> where T : Number, T: Comparable<T> =
    this.toFloat().progressTo(other.toFloat(), 0.1f, endExclusiveness = Exclusiveness.EXCLUSIVE)

infix fun <T> T.quartileTo(other: Number): NumberProgression<Float> where T : Number, T: Comparable<T> =
    this.toFloat().progressTo(other.toFloat(), 0.25f)
infix fun <T> T.quartileUntil(other: Number): NumberProgression<Float> where T : Number, T: Comparable<T> =
    this.toFloat().progressTo(other.toFloat(), 0.25f, endExclusiveness = Exclusiveness.EXCLUSIVE)


fun <T> T.progressTo(
    other: Number,
    step: Number = 1,
    operationMode: NumberOperationMode= NumberOperationMode.INCREMENTAL,
    startExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE
): NumberProgression<T> where T : Number, T: Comparable<T>
        = NumberProgressionImpl(this, other as T, step as T, operationMode, startExclusiveness, endExclusiveness)
//infix fun <T> T.until(other: T): NumberProgression<T> where T : Number, T: Comparable<T> = progressTo(other, 1 as T)

fun Char.progressTo(
    other: Char,
    step: Int = 1,
    startExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE
): CharProgression = CharProgressionImpl(this, other, step, startExclusiveness, endExclusiveness)


fun <T> Progression<T>.toList(): List<T>{
    val list= ArrayList<T>()
    forEach {
        list += it
    }
    return list
}

fun <T> Progression<T>.slice(from: Int, to: Int): List<T>{
    val indexRange= from until to
    val list= ArrayList<T>()
    for((i, e) in this.withIndex()){
        if(i in indexRange){
            list += e
        } else if(i > to)
            break
    }
    return list
}
operator fun <T> Progression<T>.get(index: Int): T?{
    for((i, e) in this.withIndex())
        if(i == index)
            return e
    return null
}

operator fun IntProgression.get(range: IntRange): IntProgression = slice(range)
operator fun IntProgression.get(from: Int, to: Int): IntProgression = slice(from, to)
operator fun IntProgression.get(index: Int): Int{
    val e= first +step *index
    if(step > 0 && e > last || step < 0 && e < last)
        throw IndexOutOfBoundsException("last= $last namun int pada index $index sama dg (int= $e) di luar range.")
    return e
}
val IntProgression.size: Int
    get()= (range / step) +1
val IntProgression.range: Int
    get()= last -first

// 1,3,5,7
// f= 1 s= 2 l= 7/8
// 1,5,9,13
// f= 1 s= 4 l= 13-16

fun IntProgression.slice(range: IntRange): IntProgression = slice(range.first, range.last)
/**
 * Mengambil potongan dari `this.extension` [IntProgression] dari index [from] (inklusif) sampai iindex [to] (eksklusif).
 */
fun IntProgression.slice(from: Int, to: Int): IntProgression {
    val first= first
    val firstInt= first +step *from
    val finalInt= first +step *(to -1)
    return IntProgression.fromClosedRange(firstInt, finalInt, step)
}

fun IntProgression.asEndExclusive(): IntProgression = IntProgression.fromClosedRange(
    first,
    if(step > 0) last -1 else last +1,
    step
)
fun IntProgression.asEndInclusive(): IntProgression = IntProgression.fromClosedRange(
    first,
    if(step > 0) last +1 else last -1,
    step
)
fun IntRange.asEndExclusive(): IntRange = first untilKt last
fun IntRange.asEndInclusive(): IntRange = first .. last

operator fun LongProgression.get(range: IntRange): LongProgression = slice(range)
operator fun LongProgression.get(from: Int, to: Int): LongProgression = slice(from, to)
operator fun LongProgression.get(index: Int): Long {
    val e= first +step *index
    if(step > 0 && e > last || step < 0 && e < last)
        throw IndexOutOfBoundsException("last= $last namun int pada index $index sama dg (int= $e) di luar range.")
    return e
}

fun LongProgression.slice(range: IntRange): LongProgression = slice(range.first, range.last)
/**
 * Mengambil potongan dari `this.extension` [LongProgression] dari index [from] (inklusif) sampai iindex [to] (eksklusif).
 */
fun LongProgression.slice(from: Int, to: Int): LongProgression {
    val first= first
    val firstInt= first +step *from
    val finalInt= first +step *(to -1)
    return LongProgression.fromClosedRange(firstInt, finalInt, step)
}

operator fun CharProgressionKt.get(range: IntRange): CharProgressionKt = slice(range)
operator fun CharProgressionKt.get(from: Int, to: Int): CharProgressionKt = slice(from, to)
operator fun CharProgressionKt.get(index: Int): Char {
    val e= first +step *index
    if(step > 0 && e > last || step < 0 && e < last)
        throw IndexOutOfBoundsException("last= $last namun int pada index $index sama dg (int= $e) di luar range.")
    return e
}

fun CharProgressionKt.slice(range: IntRange): CharProgressionKt = slice(range.first, range.last)
/**
 * Mengambil potongan dari `this.extension` [CharProgression] dari index [from] (inklusif) sampai iindex [to] (eksklusif).
 */
fun CharProgressionKt.slice(from: Int, to: Int): CharProgressionKt {
    val first= first
    val firstInt= first +step *from
    val finalInt= first +step *(to -1)
    return CharProgressionKt.fromClosedRange(firstInt, finalInt, step)
}


//operator fun <T> ClosedRange<T>.contains(value: T): Boolean where T: Number, T: Comparable<T> = value >= start && value <= endInclusive