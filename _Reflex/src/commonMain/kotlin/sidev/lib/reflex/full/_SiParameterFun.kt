package sidev.lib.reflex.full

import sidev.lib.console.prine
import sidev.lib.reflex.SiCallable
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.SiProperty
import kotlin.jvm.JvmName

@get:JvmName("isInConstructor")
val SiParameter.isInConstructor: Boolean
    get()= (descriptor.host as? SiCallable<*>)?.isConstructor == true //.contains(K_FUNCTION_CONSTRUCTOR_NAME_PREFIX)
//Karena tidak tersedia informasi mengenai fungsi pada interface KParameter,
// info yg bisa diambil berasal dari fungsi toString().

fun SiParameter.isPropertyLike(prop: SiProperty<*>, isInConstructorKnown: Boolean= false): Boolean{
//    prine("SiParameter.isPropertyLike isInConstructor= $isInConstructor")
    return if(isInConstructorKnown || isInConstructor){
//        prine("SiParameter.isPropertyLike name= $name name == prop.name => ${name == prop.name} type == prop.returnType => ${type == prop.returnType}")
//        prine("SiParameter.isPropertyLike type= $type prop.returnType= ${prop.returnType}")
        name == prop.name && type == prop.returnType
    } else false
}