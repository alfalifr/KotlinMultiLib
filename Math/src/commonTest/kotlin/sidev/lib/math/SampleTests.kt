package main

import sidev.lib.collection.duplicatUnion
import sidev.lib.console.prin
import sidev.lib.math.Sample
import sidev.lib.math.fpb
import sidev.lib.math.kpk
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }
}