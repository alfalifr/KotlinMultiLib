package sidev.lib.async.structure

import kotlinx.coroutines.CoroutineScope
import sidev.lib.structure.data.iteration.Iteration

interface PostableCoroutineScopeIteration<P>: PostableCoroutineScope<P>, CoroutineScopeIteration

internal open class PostableCoroutineScopeIterationImpl<P>(
    origin: CoroutineScope,
    override val iteration: Iteration,
    postBlock: (P) -> Unit
): PostableCoroutineScopeImpl<P>(origin, postBlock), PostableCoroutineScopeIteration<P> //, CoroutineScope by origin