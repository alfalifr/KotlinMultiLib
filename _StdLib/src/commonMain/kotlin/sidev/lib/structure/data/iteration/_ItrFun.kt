package sidev.lib.structure.data.iteration

fun iterationOf(i: Int, repetition: Int = 0, no: Int = 0, values: Map<String, Any>?= null): Iteration = IterationImpl(i, repetition, no, values)
fun refIterationOf(i: Int, repetition: Int = 0, no: Int = 0, values: MutableMap<String, Any>?= null): RefIteration = RefIterationImpl(i, repetition, no, values)

fun Iteration.toRefIteration(): RefIteration = RefIterationImpl(index, repetition, no, value?.toMutableMap())

fun Int.asIteration(): Iteration = iterationOf(this)
fun Int.asRefIteration(): RefIteration = refIterationOf(this)