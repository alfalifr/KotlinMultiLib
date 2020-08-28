package sidev.lib.reflex.core

import sidev.lib.reflex.SiClass
import sidev.lib.reflex.js.jsPureFunction

//*
actual object ReflexLoaderManager{
    /** Mengecek apakah [nativeClass] memiliki loaded [SiClass] yg di cache. */
    actual fun checkCachedClass(nativeClass: Any): Boolean{
        val checkCache= jsPureFunction(nativeClass)[SiReflexConst.SI_REFLEX_METADATA_KEY] != undefined
//        println("checkCache= $checkCache")
        return checkCache
    }
    actual fun <T: Any> loadCachedClass(nativeClass: Any): SiClass<T>
            = jsPureFunction(nativeClass)[SiReflexConst.SI_REFLEX_METADATA_KEY] as SiClass<T>
    actual fun saveLoadedClass(clazz: SiClass<*>){
        jsPureFunction(clazz)[SiReflexConst.SI_REFLEX_METADATA_KEY]= clazz
    }
}

// */