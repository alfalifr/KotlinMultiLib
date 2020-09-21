package sidev.lib.reflex

import sidev.lib.console.log
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.reflex.full.enumValues
import sidev.lib.reflex.js.jsClass
import sidev.lib.reflex.js.jsConstructor
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


    @Test
    fun jsConstrTest(){
        prin(jsConstructor(Poin::class.jsClass))
        prin(jsConstructor(Poin::class.jsClass))
        prin(jsConstructor(Poin::class.jsClass))
    }

    @Test
    fun cobTest(){
        log(AC::class.si)
    }

    @Test
    fun intTest(){
        prin(BlaBlaInt::class)
        log(BlaBlaInt::class)
        log(BlaBlaInt::class.asDynamic().`jClass_1ppatx$_0`)
    }

    @Test
    fun jsEnumTest(){
        prin("\n================ En.values() ==================\n")
        for((i, en) in En.values().withIndex()){
            prin("i= $i en= $en")
        }
        En.values()
        En.A.enumValues
        log(eval("En\$values()"))
//        js("class;A")
    }
}