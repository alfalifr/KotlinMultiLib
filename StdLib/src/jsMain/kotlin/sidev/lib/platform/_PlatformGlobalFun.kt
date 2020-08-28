package sidev.lib.platform

import sidev.lib.console.prine
import sidev.lib.reflex.core.SiReflexConst
import sidev.lib.reflex.js.asJson
import kotlin.js.Json
import kotlin.js.json

/**
 * Menaruh objek dg nama [name] dg nilai [value] pada global.
 * @return -> `true` jika berhasil menempatkan [name] pada global,
 *   -> `false` jika sudah ada objek dg nama [name] sebelumnya pada global dan [forceReplace] == false.
 */
actual fun setGlobalObject(name: String, value: Any, forceReplace: Boolean): Boolean{
//    prine("putGlobalObject() name= $name value= $value")
//    println("global= ${globalRef == undefined}")
    if(globalRef[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME] == undefined)
        globalRef[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME]= json()

    if(!forceReplace){
        if(globalRef[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME][name] != undefined)
            return false
    }
    globalRef[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME][name]= value
/*
    if(name == "AC"){
        val acRef= globalRef[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME]["AC"]
//        prine("global= $globalRef")
        log(globalRef)
//        prine("putGlobal() = name= $name value= $value acRef= $acRef")
//        prine("eval pas AC")
//        log(eval(SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME))
    }
 */
    return true
}

/**
 * @return `null` jika tidak ada objek global dg nama [name].
 */
actual fun getGlobalObject(name: String): Any?{
    return try {
        val v= globalRef[SiReflexConst.SI_JS_GLOBAL_OBJECT_NAME][name] as? Any
        if(v == undefined) null else v
    } catch (e: Throwable){ null }
}

val globalRef= try{ eval("global") } catch (e: Throwable){ eval("window") }

internal actual fun putInternalObjectOnGlobal(obj: Any){
    prine("putInternalObjectOnGlobal() obj= $obj")
    val name= obj::class.simpleName!!
    prine("putInternalObjectOnGlobal() obj= $obj name= $name")
    val internalObj= (getGlobalObject(SiReflexConst.SI_JS_GLOBAL_INTERNAL_OBJECT_NAME) ?: json()) as Json
    internalObj[name]= obj
    setGlobalObject(SiReflexConst.SI_JS_GLOBAL_INTERNAL_OBJECT_NAME, internalObj)
}

internal actual fun getInternalObject(name: String): Any{
    return getGlobalObject(SiReflexConst.SI_JS_GLOBAL_INTERNAL_OBJECT_NAME)?.let { it.asJson()[name] as Any }
        ?: throw NoSuchElementException("Tidak ada internal object dg nama $name")
}

/*
val getFunctionOnGlobal: (funcName: String) -> JsCallable<*> = {
    JsCallableImpl<Any>(eval(it) as Any)
}
 */