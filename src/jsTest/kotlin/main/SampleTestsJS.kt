package main

import kotlin.reflect.AssociatedObjectKey
import kotlin.reflect.ExperimentalAssociatedObjects
import kotlin.reflect.findAssociatedObject
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsJS {
    @Test
    fun testHello() {
        assertTrue("JS" in hello())
    }

    @ExperimentalAssociatedObjects
    @Test
    fun jsTest(){
        println(AJs::class.findAssociatedObject<Anot>())
        println(AJs::class.findAssociatedObject<Anot2>())
    }
}

@ExperimentalAssociatedObjects
@AssociatedObjectKey
@Retention(AnnotationRetention.RUNTIME)
annotation class Anot

@Retention(AnnotationRetention.RUNTIME)
annotation class Anot2

@ExperimentalAssociatedObjects
@Anot
@Anot2
class AJs