package sidev.lib

import sidev.lib.console.prin
import sidev.lib.reflex.native.si
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }

    @Test
    fun stdReflexTest(){
        prin("AC::class.si= ${AC::class.si}")
        for((i, member) in AC::class.si.members.withIndex()){
            prin("i= $i member= $member")
        }
    }
}