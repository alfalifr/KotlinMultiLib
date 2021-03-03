package sidev.lib.exception

import sidev.lib.reflex.clazz
import sidev.lib.structure.prop.CodeProp
import kotlin.reflect.KClass

//import java.lang.Exception

open class Exc(var relatedClass: KClass<*>?, private val commonMsg: String= "", private val detMsg: String= "",
               override var cause: Throwable?= null, override var code: Int= 1)
    : Exception("=================== \n" +
        "======================================================================= \n" +
        "Related Class  : ${(relatedClass ?: cause?.clazz ?: "<null>" /*?: Exc::class.simpleName*/)} \n" +
        "Message        : ${if(commonMsg.isNotBlank()) commonMsg else "<empty>"} \n" +
        "Detail Message : ${if(detMsg.isNotBlank()) detMsg else "<empty>"} \n" +
        "Code           : $code \n" +
        "Cause          : ${cause ?: "<null>"} \n" +
        "======================================================================= "
    ), CodeProp {

    /**
     * Jika `true`, maka [message] menghasilkan pesan dg titik dua (:) yg sejajar.
     * Jika `false`, maka [message] menghasilkan pesan dg titik dua (:) yg menempel pada kata sebelumnya.
     * Tujuan dari property ini adalah agar memudahkan pembacaan [message] yg diprint pada file yg memiliki
     * font non-monospace (ukuran tiap karakter berbeda).
     */
    var isMessageMonospace: Boolean = true

//    override var cause: Throwable?= cause
    override val message: String?= null
//        get()= field ?: super.message
        get()= field ?: "=================== \n" +
                        "======================================================================= \n" +
                        if(isMessageMonospace){
                            "Related Class  : ${(relatedClass ?: cause?.clazz ?: "<null>" /*?: Exc::class.simpleName*/)} \n" +
                            "Message        : ${if(commonMsg.isNotBlank()) commonMsg else "<empty>"} \n" +
                            "Detail Message : ${if(detMsg.isNotBlank()) detMsg else "<empty>"} \n" +
                            "Code           : $code \n" +
                            "Cause          : ${cause ?: "<null>"} \n"
                        } else {
                            "Related Class: ${(relatedClass ?: cause?.clazz ?: "<null>" /*?: Exc::class.simpleName*/)} \n" +
                            "Message: ${if(commonMsg.isNotBlank()) commonMsg else "<empty>"} \n" +
                            "Detail Message: ${if(detMsg.isNotBlank()) detMsg else "<empty>"} \n" +
                            "Code: $code \n" +
                            "Cause: ${cause ?: "<null>"} \n"
                        } +
                        "======================================================================= "
/*
    var relatedClass: KClass<*>? = relatedClass
        get()= field ?: this::class
 */

    constructor(msg: String?= null, cause: Throwable?= null, code: Int= 1)
            : this(null, msg ?: "", cause = cause, code = code)
}