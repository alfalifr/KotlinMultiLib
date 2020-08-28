package sidev.lib.reflex.full

import sidev.lib.reflex.comp.SiCallable
import sidev.lib.reflex.comp.SiParameter
import sidev.lib.reflex.comp.SiProperty


val SiParameter.isInConstructor: Boolean
    get()= (descriptor.host as? SiCallable<*>)?.isConstructor == true //.contains(K_FUNCTION_CONSTRUCTOR_NAME_PREFIX)
//Karena tidak tersedia informasi mengenai fungsi pada interface KParameter,
// info yg bisa diambil berasal dari fungsi toString().

fun SiParameter.isPropertyLike(prop: SiProperty<*>, isInConstructorKnown: Boolean= false): Boolean{
    return if(isInConstructorKnown || isInConstructor){
//        prine("SiParameter.isPropertyLike name= $name name == prop.name => ${name == prop.name} type == prop.returnType => ${type == prop.returnType}")
//        prine("SiParameter.isPropertyLike type= $type prop.returnType= ${prop.returnType}")
        name == prop.name && type == prop.returnType
    } else false
}