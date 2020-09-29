package sidev.lib.reflex

import com.sigudang.android._Dummy.inboundList_created
import sidev.lib._config_.SidevLibConfig
import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.reflex.annotation.callAnnotatedFunction
import sidev.lib.reflex.annotation.nativeCallAnnotatedFunction
import sidev.lib.reflex.full.fieldValuesTree
import sidev.lib.reflex.full.isCollection
import sidev.lib.reflex.full.nativeClone
import sidev.lib.reflex.jvm.javaFieldValuesTree
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

    @Test
    fun jvmBoundClassesTree(){
        val boundProd= inboundList_created[0].productList ?: ArrayList()

        for((i, prop) in boundProd.first().javaFieldValuesTree.withIndex()){
            prin("i= $i prop= $prop")
        }
    }

    @Test
    fun jvmBoundProperty(){
        SidevLibConfig.java7SupportEnabled= false
        val boundProd= inboundList_created[0].productList ?: ArrayList()
        prin("boundProd::class.isCollection= ${boundProd::class.isCollection}")
        for((i, prop) in boundProd.javaFieldValuesTree.withIndex()){
            prin("i= $i prop= $prop")
        }
        prin(boundProd.nativeClone())
//        prin(boundProd.first().nativeClone())
    }
}