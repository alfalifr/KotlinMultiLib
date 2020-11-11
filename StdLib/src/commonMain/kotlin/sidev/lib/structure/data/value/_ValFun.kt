package sidev.lib.structure.data.value

fun <T> T.asBoxed(): Val<T> = Val(this)

fun <T> T.withLevel(level: Int= 0): LeveledValue<T> = LeveledValue(level, this)
fun <T> T.withIndex(index: Int= 0): IndexedValue<T> = IndexedValue(index, this)

infix fun <T> Int.levels(any: T): LeveledValue<T> = LeveledValue(this, any)
infix fun <T> Int.indexes(any: T): IndexedValue<T> = IndexedValue(this, any)
