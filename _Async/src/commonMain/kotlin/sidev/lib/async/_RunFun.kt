@file:OptIn(ExperimentalContracts::class)
package sidev.lib.async

import kotlinx.coroutines.*
import sidev.lib.async.structure.PostableCoroutineScope
import sidev.lib.async.structure.asPostable
import sidev.lib.structure.data.Postable
import kotlin.contracts.ExperimentalContracts
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

expect fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T

inline fun <P, R> async(
    crossinline asyncBlock: suspend PostableCoroutineScope<P>.() -> R,
    crossinline onProgress: (P) -> Unit,
    crossinline onResult: (R) -> Unit
): Job = GlobalScope.launch {
    val postable= asPostable<P> { onProgress(it) } //Postable<P> { onProgress(it) }
    onResult(withContext(Dispatchers.Default) { asyncBlock(postable) })
}

inline fun <R> async(
    crossinline asyncBlock: suspend () -> R,
    crossinline onResult: (R) -> Unit
): Job = GlobalScope.launch {
    onResult(withContext(Dispatchers.Default) { asyncBlock() })
}