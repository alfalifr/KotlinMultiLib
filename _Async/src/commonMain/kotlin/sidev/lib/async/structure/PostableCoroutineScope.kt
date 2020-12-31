package sidev.lib.async.structure

import kotlinx.coroutines.CoroutineScope
import sidev.lib.structure.data.Postable

interface PostableCoroutineScope<P>: CoroutineScope, Postable<P>

internal open class PostableCoroutineScopeImpl<P>(
    val origin: CoroutineScope,
    val postBlock: (P) -> Unit
): PostableCoroutineScope<P>, CoroutineScope by origin {
    override fun post(obj: P) = postBlock(obj)
}