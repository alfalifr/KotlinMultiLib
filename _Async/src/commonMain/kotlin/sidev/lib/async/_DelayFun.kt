package sidev.lib.async

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sidev.lib.async.`val`.AsyncConst
import sidev.lib.console.prine
import sidev.lib.structure.data.value.*

//import kotlinx.coroutines.run


/**
 * Fungsi yang melakukan while dan dapat melakukan delay jika terjadi `Exception` di tengah iterasi.
 * [exceptionWaitCheck] digunakan untuk memfilter exception yang terjadi.
 *  -[exceptionWaitCheck] return `true` maka fungsi akan melakukan delay.
 *  -[exceptionWaitCheck] return `false` maka fungsi akan men-throw exception yang terjadi.
 */
fun whileAndWait(
//    conditionCheckVal: Var<Boolean> = true.asBoxed(),
    delay: Long = 5000,
    exceptionWaitCheck: (Exception) -> Boolean = { true },
    stateContainer: MutableMap<String, Any?>? = null,
    delayMsg: String = "Menunggu",
    block: (condition: Var<Boolean>, indexedState: RefIndexedValue<MutableMap<String, Any?>?>) -> Unit
) {
    val conditionCheckVal= true.asBoxed()
    whileAndWait({ conditionCheckVal.value }, delay, exceptionWaitCheck, stateContainer, delayMsg) {
        block(conditionCheckVal, it)
    }
}

/**
 * Fungsi yang melakukan while dan dapat melakukan delay jika terjadi `Exception` di tengah iterasi.
 * [exceptionWaitCheck] digunakan untuk memfilter exception yang terjadi.
 *  -[exceptionWaitCheck] return `true` maka fungsi akan melakukan delay.
 *  -[exceptionWaitCheck] return `false` maka fungsi akan men-throw exception yang terjadi.
 */
fun whileAndWait(
    conditionCheck: (indexedState: RefIndexedValue<MutableMap<String, Any?>?>) -> Boolean,
    delay: Long = 5000,
    exceptionWaitCheck: (Exception) -> Boolean = { true },
    stateContainer: MutableMap<String, Any?>? = null,
    delayMsg: String = "Menunggu",
    block: (indexedState: RefIndexedValue<MutableMap<String, Any?>?>) -> Unit
){
    var i= (stateContainer?.get(AsyncConst.Config.KEY_LAST_ITERATION) as? Int) ?: 0
    var state= i refIndexes stateContainer
    try {
        while(conditionCheck(state)){
            block(state)
//            prine("whileAndWait() i= $i")
            state= ++i refIndexes stateContainer
        }
    } catch (e: Exception){
        if(exceptionWaitCheck(e)){
            runBlocking { printDelay(delay, delayMsg) }
            whileAndWait(
                conditionCheck, delay, exceptionWaitCheck,
                (stateContainer ?: mutableMapOf()).apply { this[AsyncConst.Config.KEY_LAST_ITERATION]= state.index },
                delayMsg, block
            )
        } else throw e
    }
}

suspend fun printDelay(delay: Long = 5000, msg: String= "Menunggu") = coroutineScope {
    val job= launch {
        var dotLen= -1
        var dot: String
        while(true){
//            for(i in 0 until dotLen) {
            print("\r")
//            }

            if(++dotLen >= 5)
                dotLen= 0

            dot= ""
            for(i in 0 until dotLen)
                dot += "."
            print("$msg$dot")
            delay(500)
        }
    }
    delay(delay)
    println()
    job.cancelAndJoin()
}