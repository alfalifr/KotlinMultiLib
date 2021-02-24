package sidev.lib.exception

import sidev.lib.reflex.clazz
import sidev.lib.structure.prop.CodeProp
import kotlin.reflect.KClass

//import java.lang.Exception

open class Exc(relatedClass: KClass<*>?, private val commonMsg: String= "", private val detMsg: String= "",
               override var cause: Throwable?= null, override var code: Int= 1)
    : Exception("=================== \n" +
        "======================================================================= \n" +
        "Related Class  : ${(relatedClass ?: cause?.clazz ?: Exc::class.simpleName)} \n" +
        "Message        : ${if(commonMsg.isNotBlank()) commonMsg else "<empty>"} \n" +
        "Detail Message : ${if(detMsg.isNotBlank()) detMsg else "<empty>"} \n" +
        "Code           : $code \n" +
        "Cause          : $cause \n" +
        "======================================================================= "
    ), CodeProp {
//    override var cause: Throwable?= cause
    override val message: String?= null
//        get()= field ?: super.message
        get()= field ?: "=================== \n" +
                        "======================================================================= \n" +
                        "Related Class  : ${(relatedClass ?: cause?.clazz ?: Exc::class.simpleName)} \n" +
                        "Message        : ${if(commonMsg.isNotBlank()) commonMsg else "<empty>"} \n" +
                        "Detail Message : ${if(detMsg.isNotBlank()) detMsg else "<empty>"} \n" +
                        "Code           : $code \n" +
                        "Cause          : $cause \n" +
                        "======================================================================= "

    var relatedClass: KClass<*>? = relatedClass
        get()= field ?: this::class

    constructor(msg: String?= null, cause: Throwable?= null, code: Int= 1)
            : this(null, msg ?: "", cause = cause, code = code)
}