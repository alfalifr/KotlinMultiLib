package sidev.lib.structure.data.value

import sidev.lib.number.*
import kotlin.collections.IndexedValue as KIndexedValue

operator fun <T: Number> Var<T>.inc(): Var<T> = apply { value++ }
operator fun <T: Number> Var<T>.dec(): Var<T> = apply { value-- }
operator fun <T: Number> Var<T>.unaryMinus(): Var<T> = apply { value= -value }

operator fun <T: Number> Val<T>.inc(): Val<T> = valOf(value plusCast 1)
operator fun <T: Number> Val<T>.dec(): Val<T> = valOf(value minusCast 1)
operator fun <T: Number> Val<T>.unaryMinus(): Val<T> = valOf(-value)

operator fun <T: Number> Val<T>.plus(other: Val<T>): Val<T> = (value plusCast other.value).asBoxedVal()
operator fun <T: Number> Val<T>.plus(other: T): Val<T> = (value plusCast other).asBoxedVal()
operator fun <T: Number> Var<T>.plusAssign(other: Val<T>){
    value= value.plusCast(other.value)
}
operator fun <T: Number> Var<T>.plusAssign(other: T){
    value= value.plusCast(other)
}

fun <T> T.asBoxed(): Var<T> = VarImpl(this)
fun <T> T.asBoxedVal(): Val<T> = ValImpl(this)
fun <T> varOf(value: T): Var<T> = VarImpl(value)
fun <T> valOf(value: T): Val<T> = ValImpl(value)

fun <T> T.asLive(): LiveVar<T> = LiveVarImpl(this)
fun <T> T.asLiveVal(): LiveVal<T> = LiveValImpl(this)
fun <T> liveVarOf(value: T): LiveVar<T> = LiveVarImpl(value)
fun <T> liveValOf(value: T): LiveVal<T> = LiveValImpl(value)


fun <T> T?.asNullableBoxed(): NullableVar<T> = NullableVarImpl(this)
//fun <T> T?.asNullableBoxedVal(): NullableVal<T> = NullableValImpl(this)

fun <T> nullableVarOf(value: T?= null): NullableVar<T> = NullableVarImpl(value)
//fun <T> nullableValOf(value: T?= null): NullableVal<T> = value.asNullableBoxedVal()

fun <T> T?.asLiveNullable(): LiveNullableVar<T> = LiveNullableVarImpl(this)
//fun <T> T?.asNullableBoxedVal(): NullableVal<T> = NullableValImpl(this)

fun <T> liveNullableVarOf(value: T?= null): LiveNullableVar<T> = LiveNullableVarImpl(value)
//fun <T> nullableValOf(value: T?= null): NullableVal<T> = value.asNullableBoxedVal()

fun <T> T.asLeveled(level: Int= 0): LeveledValue<T> = LeveledValue(level, this)
fun <T> T.asIndexed(index: Int= 0): IndexedValue<T> = IndexedValueImpl(index, this)
fun <T> T.asRefIndexed(index: Int= 0): RefIndexedValue<T> = RefIndexedValueImpl(index, this)
fun <T> KIndexedValue<T>.toSiIndexed(): IndexedValue<T> = IndexedValueImpl(index, value)
fun <T> T.asKIndexed(index: Int= 0): KIndexedValue<T> = KIndexedValue(index, this)

fun <T> Val<T>.asVar(): Var<T> = if(this is Var) this else VarImpl(value)
fun <T> Var<T>.toVal(): Val<T> = ValImpl(value)

infix fun <T> Int.levels(any: T): LeveledValue<T> = LeveledValue(this, any)
infix fun <T> Int.indexes(any: T): IndexedValue<T> = IndexedValueImpl(this, any)
infix fun <T> Int.refIndexes(any: T): RefIndexedValue<T> = RefIndexedValueImpl(this, any)
infix fun <T> Int.kIndexes(any: T): KIndexedValue<T> = KIndexedValue(this, any)
infix fun <T, V> V.withTag(tag: T): TaggedVal<T, V> = TaggedValImpl(tag, this)
infix fun <T, V> V.withTagVar(tag: T): TaggedVar<T, V> = TaggedVarImpl(tag, this)

fun <T, V> Pair<T, V>.toTaggedVal(): TaggedVal<T, V> = TaggedValImpl(first, second)
fun <T, V> Pair<T, V>.toTaggedVar(): TaggedVar<T, V> = TaggedVarImpl(first, second)

fun <V> Pair<Int, V>.toIndexedVal(): IndexedValue<V> = first indexes second
fun <V> Pair<Int, V>.toRefIndexedVal(): RefIndexedValue<V> = first refIndexes second
fun <V> Pair<Int, V>.toLeveledValue(): LeveledValue<V> = first levels second

//fun <E, P> E.asPostable(postBlock: (P) -> Unit): PostableWrapper<E, P> = PostableWrapperImpl(this, postBlock)

operator fun <T> Val<T>.component1(): T = value

operator fun <T> TaggedVal<T, *>.component1(): T? = tag
operator fun <V> TaggedVal<*, V>.component2(): V = value

operator fun IndexedValue<*>.component1(): Int = index
operator fun <T> IndexedValue<T>.component2(): T = value

//operator fun LeveledValue<*>.component1(): Int = level
//operator fun <T> LeveledValue<T>.component2(): T = value