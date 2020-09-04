package sidev.lib.math

import sidev.lib.collection.duplicatUnion
import sidev.lib.console.prin
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsJVM {
    @Test
    fun testHello() {
        assertTrue("JVM" in hello())
    }
}