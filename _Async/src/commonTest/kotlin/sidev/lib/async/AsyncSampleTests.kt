package sidev.lib.async

import sidev.lib.console.prin
import sidev.lib.structure.data.value.inc
import sidev.lib.structure.data.value.plusAssign
import kotlin.test.Test


class AsyncSampleTests {
    @Test
    fun whileAndWaitTest(){
        val limit= 20
        whileAndWait { condition, indexedState ->
            val i= indexedState.index
            condition.value= i < limit
            if(i % 6 == 0){
                indexedState.indexBox += 1
                throw Exception()
            }
            prin("i= $i condition.value= $condition")
        }

        prin("\n\n ============= ganti ================ \n\n")

        whileAndWait({ it.index < limit }) {
            val i= it.index
            if(i % 6 == 0){
                it.indexBox += 1
                throw Exception()
            }
            prin("i= $i condition.value= $it rep= ${it.repetition}")
        }
    }
}