@file:JvmName("_RunFunJvm")
@file:OptIn(ExperimentalContracts::class)
package sidev.lib.async

import kotlinx.coroutines.*
import kotlin.contracts.ExperimentalContracts
import kotlin.coroutines.CoroutineContext

import kotlinx.coroutines.runBlocking as ktRunBlocking

actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T = ktRunBlocking(context, block)
