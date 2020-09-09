package sidev.lib.reflex

import sidev.lib.console.log
import sidev.lib.console.prin
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsJS {
    @Test
    fun testHello() {
        assertTrue("JS" in hello())
    }

    @Test
    fun jsAnotTest(){
        log(FunAnot::class.js)
        prin(FunAnot::class.js)
        val ac = AC<BlaBla2>()
        log(ac::someFun)
        prin(ac::someFun)
    }
}