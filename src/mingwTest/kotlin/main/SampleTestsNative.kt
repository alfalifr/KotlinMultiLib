package main

import platform.windows.navOpenInNewWindow
import kotlin.reflect.AssociatedObjectKey
import kotlin.reflect.ExperimentalAssociatedObjects
import kotlin.reflect.KProperty
import kotlin.reflect.findAssociatedObject
import kotlin.test.Test
import kotlin.test.assertTrue

//header fun adadd()
//impl fun adadd(){}

class SampleTestsNative {
    @Test
    fun testHello() {
        assertTrue("Native" in hello())
    }

    @ExperimentalAssociatedObjects
    @Test
    fun nativeTest(){
        println("AMingw::a = ${AMingw::a}")
        println("AMingw::a.returnType = ${AMingw::a.returnType}")
        println("AMingw::b.returnType = ${AMingw::b.returnType}")
        println("AMingw::class = ${AMingw::class}")
        println("AMingw::ada::class = ${AMingw::ada::class}")
        println("AMingw::ada::class.qualifiedName = ${AMingw::ada::class.qualifiedName}")
        println("AMingw::a is KProperty<*> = ${AMingw::a is KProperty<*>}")

        val anon1 = object : iAMingW{}
        val anon2 = object : iAMingW{}
        println("anon1::class.qualifiedName= ${anon1::class.qualifiedName}")
        println("anon1 = $anon1")
        println("anon1.hashCode()= ${anon1.hashCode()}")
        println("anon1::class.hashCode()= ${anon1::class.hashCode()}")
        println("anon2::class.hashCode()= ${anon2::class.hashCode()}")

        val int: Int? = 1

        println("(int as Int)::class= ${(int as Int)::class}")
        println("1::class= ${1::class}")
//        println(AMingw::class.findAssociatedObject<Anot>())
    }
}
/*
@ExperimentalAssociatedObjects
@AssociatedObjectKey
@Retention(AnnotationRetention.RUNTIME)
annotation class Anot

@ExperimentalAssociatedObjects
@Anot
 */
class AMingw{
    val a = 1
    val b = "Str"
    val c = 'c'
    fun ada() = "ok"
}

interface iAMingW