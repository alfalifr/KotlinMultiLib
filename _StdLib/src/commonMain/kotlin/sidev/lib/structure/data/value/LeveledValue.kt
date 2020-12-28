package sidev.lib.structure.data.value

import sidev.lib.collection.sequence.NestedSequence

/** Struktur data yg disertai dg level scr hirarki pada [NestedSequence]. */
data class LeveledValue<T>(val level: Int, override val value: T): Val<T>