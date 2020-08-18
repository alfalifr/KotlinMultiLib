package sidev.lib

import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsNative {
    @Test
    fun testHello() {
        assertTrue("Native" in hello())
    }
}