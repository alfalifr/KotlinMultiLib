package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.js.JsReflexConst
import sidev.lib.reflex.js.asJson
import sidev.lib.reflex.js.jsPureFunction
import kotlin.reflect.KClass

//*
actual object ReflexLoaderManager{
    /** Mengecek apakah [nativeClass] memiliki loaded [SiClass] yg di cache. */
    actual fun checkCachedClass(nativeClass: Any): Boolean{
        val checkCache= jsPureFunction(nativeClass)[JsReflexConst.SI_REFLEX_METADATA_KEY] != undefined
        println("checkCache= $checkCache")
        return checkCache
    }
    actual fun <T: Any> loadCachedClass(nativeClass: Any): SiClass<T>
            = jsPureFunction(nativeClass)[JsReflexConst.SI_REFLEX_METADATA_KEY] as SiClass<T>
    actual fun saveLoadedClass(clazz: SiClass<*>){
        jsPureFunction(clazz)[JsReflexConst.SI_REFLEX_METADATA_KEY]= clazz
    }
}

// */