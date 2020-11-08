package sidev.lib.reflex

import com.sigudang.android._Dummy.inboundList_created
import sidev.lib._config_.SidevLibConfig
import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.collection.sequence.withLevel
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.reflex.annotation.callAnnotatedFunction
import sidev.lib.reflex.annotation.nativeCallAnnotatedFunction
import sidev.lib.reflex.full.isCollection
import sidev.lib.reflex.full.nativeClone
import sidev.lib.reflex.jvm.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

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

    @ExperimentalTime
    @Test
    fun nativeAnnotationCallTest(){
        val jvmAc= JvmAc()

        prin("\n=============== callAnnotatedFunction ==============\n")
        measureTime { jvmAc.callAnnotatedFunction<JvmAnot>{ 14 } }.also{ prine("timeTaken= $it") }
        measureTime { jvmAc.callAnnotatedFunction<JvmAnot>({it.x == 2}){
            when(it.name){
                "x" -> 15
                "y" -> 25
                "z" -> 35
                else -> null
            }
        } }.also{ prine("timeTaken= $it") }

        prin("\n=============== nativeCallAnnotatedFunction ==============\n")
        measureTime { jvmAc.nativeCallAnnotatedFunction<JvmAnot>{ 14 } }.also{ prine("timeTaken= $it") }
        measureTime { jvmAc.nativeCallAnnotatedFunction<JvmAnot>({it.x == 2}){
            when(it.name){
                "x" -> 15
                "y" -> 25
                "z" -> 35
                else -> null
            }
        } }.also{ prine("timeTaken= $it") }
    }

    @Test
    fun jvmBoundClassesTree(){
        val boundProd= inboundList_created[0].productList ?: ArrayList()

        for((i, prop) in boundProd.first().javaFieldValuesTree.withIndex()){
            prin("i= $i prop= $prop")
        }
    }

    @Test
    fun jvmBoundProperty(){
        SidevLibConfig.java7SupportEnabled= false
        val boundProd= inboundList_created[0].productList ?: ArrayList()
        prin("boundProd::class.isCollection= ${boundProd::class.isCollection}")
        for((i, prop) in boundProd.javaFieldValuesTree.withIndex()){
            prin("i= $i prop= $prop")
        }
        prin(boundProd.nativeClone())
//        prin(boundProd.first().nativeClone())
    }

    @Test
    fun nativeParamTest(){
        val a= ParamTest(2, "aaf")
        for((i, constr) in a::class.java.constructors.withIndex()){
            prin("i= $i constr= $constr")
        }
        for((i, param) in a::class.java.constructors[0].parameters.withIndex()){
            prin("i= $i param= $param")
        }
        for((i, field) in a::class.java.declaredFields.withIndex()){
            prin("i= $i field= $field")
        }
        for((i, prop) in a::class.declaredMemberProperties.withIndex()){
            prin("i= $i prop= $prop")
        }
    }

    @Test
    fun jvmNamedValuesMap(){
        class A(val a: Int= 1, val b: Int= 3)
        class B(val str: String= "Halo bro", val aa: A= A(), val bInt: Int= 3)
        class C(val str2: String= "Ok bro", val bb: B= B(), val cInt: Int= 5)
///*
        val c= C()
        val fields= c::class.java.fields
        prin("b::class.java= ${c::class.java} fields= ${fields.joinToString()} isEmpty= ${fields.isEmpty()}")

        prin("================== javaPrimitiveFieldValuesTreeNamedMap ===============")
        c.javaNestedPrimitiveFieldValuesTreeNamedMap.forEach { (t, u) ->
            prin("name= $t value= $u")
        }

        prin("================== javaNonExhaustiveNestedFieldValuesTree ===============")
        c.javaNonExhaustiveNestedFieldValuesTree.forEach { (t, u) ->
            prin("field= $t name= ${t.name} field type= ${t.type} value= $u")
        }

        prin("================== javaFieldValuesTree ===============")
        c.javaFieldValuesTree.forEach { (t, u) ->
            prin("field= $t name= ${t.name} field type= ${t.type} value= $u")
        }

        prin("Int::class.java.fields.joinToString()= ${Int::class.java.fields.joinToString()}")
    }

    @Test
    fun primitiveWrapperTest(){
        prin(Integer::class.java)
        prin(Int::class.javaObjectType)
        prin(Int::class.java)
        prin(Integer::class.java.isPrimitiveWrapper)
        prin(Int::class.javaObjectType.isPrimitiveWrapper)
    }

    @Test
    fun javaNestedFieldTest(){
        val ac3= AC<BlaBla2>()
        prin("\n============= ac3.nestedFieldValuesTree.withLevel() ===============\n")
        for((i, field) in ac3.javaNestedFieldValuesTree.withLevel().withIndex())
            prin("i= $i field= $field")
    }
}