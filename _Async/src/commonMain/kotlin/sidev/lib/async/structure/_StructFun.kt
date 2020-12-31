package sidev.lib.async.structure

import kotlinx.coroutines.CoroutineScope
import sidev.lib.structure.data.iteration.Iteration

fun <P> CoroutineScope.asPostable(postBlock: (P) -> Unit): PostableCoroutineScope<P> = PostableCoroutineScopeImpl(this, postBlock)
fun <P> CoroutineScope.asPostableIteration(itr: Iteration, postBlock: (P) -> Unit): PostableCoroutineScopeIteration<P> =
    PostableCoroutineScopeIterationImpl(this, itr, postBlock)
fun <P> PostableCoroutineScope<P>.asPostableIteration(itr: Iteration): PostableCoroutineScopeIteration<P> {
    return if(this is PostableCoroutineScopeImpl) PostableCoroutineScopeIterationImpl(origin, itr, postBlock)
    else PostableCoroutineScopeIterationImpl(this, itr, ::post)
}