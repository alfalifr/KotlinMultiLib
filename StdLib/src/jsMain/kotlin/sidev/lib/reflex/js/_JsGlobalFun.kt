package sidev.lib.reflex.js

import sidev.lib.reflex.InnerReflexConst
import sidev.lib.reflex.common.core.SiReflexConst
import kotlin.js.json
/*
/**
 * Menaruh objek dg nama [name] dg nilai [value] pada global.
 * @return -> `true` jika berhasil menempatkan [name] pada global,
 *   -> `false` jika sudah ada objek dg nama [name] sebelumnya pada global dan [forceReplace] == false.
 */
fun putGlobalObject(name: String, value: Any, forceReplace: Boolean = false): Boolean {
    val global= try{
        val v= eval("global")
        println("eval(\"global\") berhasil")
        v
    } catch (e: Throwable){ eval("window") }
    println("global= ${global == undefined}")
    if(global[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME] == undefined)
        global[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME]= json()

    if(!forceReplace){
        if(global[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME][name] != undefined)
            return false
    }
    global[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME][name]= value
    return true
}
 */

val getFunctionOnGlobal: (funcName: String) -> JsCallable<*> = {
    JsCallableImpl<Any>(eval(it) as Any)
}