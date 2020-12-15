package sidev.lib.async

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sidev.lib.async.`val`.AsyncConst
import sidev.lib.structure.data.iteration.RefIteration
import sidev.lib.structure.data.iteration.refIterationOf
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
    delayMillis: Long = 5000,
    exceptionWaitCheck: (Exception) -> Boolean = { true },
    stateContainer: MutableMap<String, Any>? = null,
    delayMsg: String = "Menunggu",
    block: (condition: Var<Boolean>, itr: RefIteration) -> Unit
) {
    val conditionCheckVal= true.asBoxed()
    whileAndWait({ conditionCheckVal.value }, delayMillis, exceptionWaitCheck, stateContainer, delayMsg) {
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
    conditionCheck: (itr: RefIteration) -> Boolean,
    delayMillis: Long = 5000,
    exceptionWaitCheck: (Exception) -> Boolean = { true },
    stateContainer: MutableMap<String, Any>? = null,
    delayMsg: String = "Menunggu",
    block: (itr: RefIteration) -> Unit
){
    val i= (stateContainer?.get(AsyncConst.Config.KEY_LAST_ITERATION) as? Int) ?: 0
    val rep= (stateContainer?.get(AsyncConst.Config.KEY_LAST_REPETITION) as? Int) ?: 0
    val state= refIterationOf(i, rep, values = stateContainer) //i refIndexes stateContainer
    var loop= true
    while(loop){
        try {
            while(conditionCheck(state)){
                block(state)
//            prine("whileAndWait() i= $i")
//            state= refIterationOf(++i, rep) //++i refIndexes stateContainer
                state.indexBox += 1
                state.repetitionBox.value = 0
            }
            loop= false
        } catch (e: Exception){
            if(exceptionWaitCheck(e)){
                runBlocking { printDelay(delayMillis, delayMsg) }
                state.repetitionBox += 1
/*
                whileAndWait(
                    conditionCheck, delayMillis, exceptionWaitCheck,
                    (stateContainer ?: mutableMapOf()).apply {
                        this[AsyncConst.Config.KEY_LAST_ITERATION]= state.index
                        this[AsyncConst.Config.KEY_LAST_REPETITION]= state.repetition +1
                    },
                    delayMsg, block
                )
 */
            } else throw e
        }
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