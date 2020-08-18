package main

import main.Sample
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }
}