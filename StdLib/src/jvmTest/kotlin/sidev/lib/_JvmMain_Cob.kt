package sidev.lib

class ACD{
    val a = 1
    val b = 2
    val c = "3A"
    val d = '4'
    val e = ACD2(10)
}

class ACD2(val a: Int)
/*
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.property.MutableLazy
import sidev.lib.property.mutableLazy
import sidev.lib.reflex.full.declaredFieldsTree
import sidev.lib.reflex.full.forceGet
import sidev.lib.reflex.full.fieldValuesTree
import sidev.lib.reflex.comp.native.si
import sidev.lib.reflex.native.si

object OP

fun main(){
    prin("\n================= MutableLazy::class.java.methods ================\n")
    for((i, method) in MutableLazy::class.java.methods.withIndex()){
        prin("i= $i method= $method")
    }

    val ao= AO()

    prin("\n================= AO::class.si.declaredFieldsTree ================\n")
    for((i, field) in AO::class.si.declaredFieldsTree.withIndex()){
        val value= field.forceGet(ao). also { value ->
            if(value is Lazy<*>)
                value.getValue(null, AO::b)
        }
        val valIsStr= value is String
        prin("i= $i field= $field value= $value valIsStr= $valIsStr")
    }

    prin("\n================= ao.implementedFieldValuesTree ================\n")
    for((i, field) in ao.fieldValuesTree.withIndex()){
        prin("i= $i field= $field isStr= ${field.second is String} native= ${field.first.descriptor.native}")
    }
/*
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

    val str by lazy { "afaf" }
    var strMutab by mutableLazy {
        prine("strMutab initialized")
        "afaf"
    }

    override fun toString(): String {
        return "AO b= $b"
    }
}

 */