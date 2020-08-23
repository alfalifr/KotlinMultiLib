package sidev.lib

import sidev.lib.console.prin
import kotlin.jvm.internal.CallableReference
import kotlin.math.ceil
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

object OP

fun main(){

    prin("OP::class.java.constructors= ${OP::class.java.constructors.isEmpty()}")

    val ao= AO(111)
    ao.defFun(b= true)
    ao.defFun(1, false, 19)
    prin("(ao::b as CallableReference)= ${(ao::b as CallableReference).boundReceiver}")

    val func= ao::class.memberFunctions.first()
    val param= func.parameters[1]
/*
    prin("func.call(10)= ${func.callBy(mapOf(param to 10))}")
    prin("ao::ada.call(10) ${ao::ada.call(10)}")

    prin("(ao::class.memberProperties.first() as CallableReference).boundReceiver= ${(ao::class.memberProperties.first() as CallableReference).boundReceiver}")

    prin("(ao::class as CallableReference).boundReceiver= ${(ao::class as CallableReference).boundReceiver}")
 */

    prin("\n================AO::class.java.methods=====================\n")
    for((i, method) in AO::class.java.methods.withIndex()){
        prin("i= $i method= $method synthetic= ${method.isSynthetic} genStr= ${method.toGenericString()}")
    }

    prin("\n================AO::class.java.constructors=====================\n")
    for((i, constr) in AO::class.java.constructors.withIndex()){
        prin("i= $i constr= $constr name= ${constr.name} synthetic= ${constr.isSynthetic} genStr= ${constr.toGenericString()}")
    }


    prin("\n================AO::class.members=====================\n")
    for((i, member) in AO::class.members.withIndex())
        prin("i= $i member= $member")

    prin("ceil(32.1001)= ${ceil(32.1001)}")
/*
    prin(
        by@ {
            listOf(1, 2, 4, 5, 11, 3).forEach { if(it > 10) return@by "ada yg lebih dari 10" }
            "gakda yg lebih dari 10"
        }())
 */
}
class AO(val b: Int= 10){
    constructor(a: Int, b: Boolean= true): this(a){
    }
    fun ada(a: Int){
        prin("Halo ada b= $b a= $a")
    }
    fun defFun(a: Int= 0, b: Boolean, c: Long= 10, d: Int= 10, e: Boolean= false, f: Double= 0.0, g: Boolean= false, h: Int= 10, i: Int= 10, j: Int= 10){

    }

    override fun toString(): String {
        return "AO b= $b"
    }
}