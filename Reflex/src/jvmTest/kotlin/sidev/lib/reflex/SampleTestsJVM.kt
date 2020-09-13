package sidev.lib.reflex

import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.reflex.annotation.callAnnotatedFunction
import sidev.lib.reflex.annotation.nativeCallAnnotatedFunction
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class SampleTestsJVM {
    @Test
    fun testHello() {
        assertTrue("JVM" in hello())
    }

    @Test
    fun cob(){
        nestedSequenceSimple(ArrayList::class.java as Class<*>){
            it.classes.iterator()
        }
    }

    @ExperimentalTime
    @Test
    fun nativeAnnotationCallTest(){
        val jvmAc= JvmAc()

        prin("\n=============== callAnnotatedFunction ==============\n")
        measureTime { jvmAc.callAnnotatedFunction<JvmAnot>{ 14 } }.also{ prine("timeTaken= $it") }
        measureTime { jvmAc.callAnnotatedFunction<JvmAnot>({it.x == 2}){
            when(it.name){
                "x" -> 15
                "y" -> 25
                "z" -> 35
                else -> null
            }
        } }.also{ prine("timeTaken= $it") }

        prin("\n=============== nativeCallAnnotatedFunction ==============\n")
        measureTime { jvmAc.nativeCallAnnotatedFunction<JvmAnot>{ 14 } }.also{ prine("timeTaken= $it") }
        measureTime { jvmAc.nativeCallAnnotatedFunction<JvmAnot>({it.x == 2}){
            when(it.name){
                "x" -> 15
                "y" -> 25
                "z" -> 35
                else -> null
            }
        } }.also{ prine("timeTaken= $it") }
    }
}