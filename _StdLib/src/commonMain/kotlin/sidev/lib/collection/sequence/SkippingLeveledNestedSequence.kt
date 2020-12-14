package sidev.lib.collection.sequence

import sidev.lib.structure.data.value.LeveledValue

interface SkippingLeveledNestedSequence<T>: LeveledNestedSequence<T>, SkippingSequence<LeveledValue<T>>