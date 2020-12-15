@file:OptIn(ExperimentalContracts::class)
package sidev.lib.async

import kotlinx.coroutines.*
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.structure.data.value.asBoxed
import sidev.lib.structure.data.value.nullableVarOf
import kotlin.contracts.ExperimentalContracts
import kotlin.coroutines.CoroutineContext

actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T {
    val res= nullableVarOf<T>()
    val loop= true.asBoxed()
    GlobalScope.launch(context) {
        res.value= block()
        loop.value= false
    }
    @Suppress(SuppressLiteral.EMPTY_BODY_CONTROL_FLOW)
    while(loop.value);
    return res.value!!
}
