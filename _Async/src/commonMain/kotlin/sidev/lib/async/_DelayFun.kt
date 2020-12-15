package sidev.lib.async

import kotlinx.coroutines.*
import sidev.lib.async.`val`.AsyncConst
import sidev.lib.console.prine
import sidev.lib.exception.IllegalStateExc
import sidev.lib.structure.data.iteration.MutableIteration
import sidev.lib.structure.data.iteration.mutableIterationOf
import sidev.lib.structure.data.value.*
import kotlin.coroutines.CoroutineContext

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
    maxRep: Int= 20,
    stateContainer: MutableMap<String, Any>? = null,
    delayMsg: String = "Menunggu",
    block: (condition: Var<Boolean>, itr: MutableIteration) -> Unit
) {
    val conditionCheckVal= true.asBoxed()
    whileAndWait({ conditionCheckVal.value }, delayMillis, exceptionWaitCheck, maxRep, stateContainer, delayMsg) {
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
    conditionCheck: (itr: MutableIteration) -> Boolean,
    delayMillis: Long = 5000,
    exceptionWaitCheck: (Exception) -> Boolean = { true },
    maxRep: Int= 20,
    stateContainer: MutableMap<String, Any>? = null,
    delayMsg: String = "Menunggu",
    block: (itr: MutableIteration) -> Unit
){
    val i= (stateContainer?.get(AsyncConst.Config.KEY_LAST_ITERATION) as? Int) ?: 0
    val rep= (stateContainer?.get(AsyncConst.Config.KEY_LAST_REPETITION) as? Int) ?: 0
    val state= mutableIterationOf(i, rep, values = stateContainer) //i refIndexes stateContainer
    var loop= true
    var currRep= 0
    var currExc: Exception?= null
    while(loop){
        if(currRep >= maxRep){
            throw IllegalStateExc(
                currentState = "repetisi ($currRep) >= maxRep ($maxRep)",
                expectedState = "repetisi ($currRep) < maxRep ($maxRep)",
                detMsg = "repetisi='$currRep' melebihi batas='$maxRep'"
            ).apply { cause= currExc!! }
        }
        try {
//            delay()
            while(conditionCheck(state)){
                prine("whileAndWait() i= $i")
                block(state)
//            state= refIterationOf(++i, rep) //++i refIndexes stateContainer
                state.index += 1
                currRep= 0
                state.repetition = currRep
            }
            loop= false
        } catch (e: Exception){
            prine("whileAndWait() i= $i e= $e")
            if(exceptionWaitCheck(e)){
                runBlocking { printDelay(delayMillis, delayMsg) }
                state.repetition = ++currRep
                currExc= e
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
//    val loop= true.asBoxed()
    val job= launch {
        var dotLen= -1
        var dot: String
        while(true){
//    for(i in 0 until 10){
//            for(i in 0 until dotLen) {
            print("\r")
//            }

            if(++dotLen >= 5)
                dotLen= 0

            dot= ""
            for(i in 0 until dotLen)
                dot += "."
            print("$msg$dot")
            delay(40)
        }
    }
    delay(delay)
    println()
    job.cancelAndJoin()
}