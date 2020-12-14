@file:OptIn(ExperimentalContracts::class)
package sidev.lib.async

import kotlinx.coroutines.*
import sidev.lib.structure.data.value.VarImpl
import sidev.lib.structure.data.value.asBoxed
import kotlin.contracts.ExperimentalContracts
import kotlin.coroutines.CoroutineContext

actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T {
    val res= VarImpl<T>()
    val loop= true.asBoxed()
    GlobalScope.launch(context) {
        res.value= block()
        loop.value= false
    }
    while(loop.value!!);
    return res.value!!
}
