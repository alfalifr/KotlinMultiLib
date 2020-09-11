package sidev.lib.reflex

import sidev.lib.collection.sequence.nestedSequenceSimple
import kotlin.test.Test
import kotlin.test.assertTrue

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
}