package sidev.lib.structure.data.iteration

fun iterationOf(i: Int, repetition: Int = 0, no: Int = 0, values: Map<String, Any>?= null): Iteration = IterationImpl(i, repetition, no, values)
fun mutableIterationOf(i: Int, repetition: Int = 0, no: Int = 0, values: MutableMap<String, Any>?= null): MutableIteration = MutableIterationImpl(i, repetition, no, values)

fun Iteration.asMutableIteration(): MutableIteration =
    if(this is MutableIteration) this else MutableIterationImpl(index, repetition, no, value?.toMutableMap())
fun MutableIteration.toIteration(): Iteration = IterationImpl(index, repetition, no, value)

fun Int.asIteration(): Iteration = iterationOf(this)
fun Int.asMutableIteration(): MutableIteration = mutableIterationOf(this)