package sidev.lib.structure.data.value

import sidev.lib.number.dec
import sidev.lib.number.inc
import sidev.lib.number.plus
import sidev.lib.number.plusCast
import kotlin.collections.IndexedValue as KIndexedValue

operator fun <T: Number> Var<T>.inc(): Var<T> = apply { value++ }
operator fun <T: Number> Var<T>.dec(): Var<T> = apply { value-- }

operator fun <T: Number> Val<T>.plus(other: Val<T>): Val<T> = (value.plusCast(other.value)).asBoxedVal()
operator fun <T: Number> Val<T>.plus(other: T): Val<T> = (value.plusCast(other)).asBoxedVal()
operator fun <T: Number> Var<T>.plusAssign(other: Val<T>){
    value= value.plusCast(other.value)
}
operator fun <T: Number> Var<T>.plusAssign(other: T){
    value= value.plusCast(other)
}

fun <T> T.asBoxed(): Var<T> = VarImpl(this)
fun <T> T.asBoxedVal(): Val<T> = ValImpl(this)

fun <T> T?.asNullableBoxed(): NullableVar<T> = NullableVarImpl(this)
fun <T> T?.asNullableBoxedVal(): NullableVal<T> = NullableValImpl(this)

fun <T> nullableVarOf(value: T?= null): NullableVar<T> = value.asNullableBoxed()
fun <T> nullableValOf(value: T?= null): NullableVal<T> = value.asNullableBoxedVal()

fun <T> T.asLeveled(level: Int= 0): LeveledValue<T> = LeveledValue(level, this)
fun <T> T.asIndexed(index: Int= 0): IndexedValue<T> = IndexedValueImpl(index, this)
fun <T> T.asRefIndexed(index: Int= 0): RefIndexedValue<T> = RefIndexedValueImpl(index, this)
fun <T> KIndexedValue<T>.toSiIndexed(): IndexedValue<T> = IndexedValueImpl(index, value)
fun <T> T.asKIndexed(index: Int= 0): KIndexedValue<T> = KIndexedValue(index, this)

infix fun <T> Int.levels(any: T): LeveledValue<T> = LeveledValue(this, any)
infix fun <T> Int.indexes(any: T): IndexedValue<T> = IndexedValueImpl(this, any)
infix fun <T> Int.refIndexes(any: T): RefIndexedValue<T> = RefIndexedValueImpl(this, any)
infix fun <T> Int.kIndexes(any: T): KIndexedValue<T> = KIndexedValue(this, any)
