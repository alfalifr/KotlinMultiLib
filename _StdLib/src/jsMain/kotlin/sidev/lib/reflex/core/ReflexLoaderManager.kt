package sidev.lib.reflex.core

import sidev.lib.reflex.SiClass
import sidev.lib.reflex.js.getSiMetadata
import sidev.lib.reflex.js.jsPureFunction
import sidev.lib.reflex.js.putSiMetadata

//*
actual object ReflexLoaderManager{
    /** Mengecek apakah [nativeClass] memiliki loaded [SiClass] yg di cache. */
    actual fun checkCachedClass(nativeClass: Any): Boolean{
        return getSiMetadata<Any?>(nativeClass, SiReflexConst.SI_REFLEX_METADATA_KEY) != undefined //jsPureFunction(nativeClass)[SiReflexConst.SI_REFLEX_METADATA_KEY] != undefined
//        println("checkCache= $checkCache")
    }
    actual fun <T: Any> loadCachedClass(nativeClass: Any): SiClass<T>
            = getSiMetadata(nativeClass, SiReflexConst.SI_REFLEX_METADATA_KEY)!! //jsPureFunction(nativeClass)[SiReflexConst.SI_REFLEX_METADATA_KEY] as SiClass<T>
    actual fun saveLoadedClass(clazz: SiClass<*>){
        clazz.putSiMetadata(SiReflexConst.SI_REFLEX_METADATA_KEY, clazz)
//        jsPureFunction(clazz)[SiReflexConst.SI_REFLEX_METADATA_KEY]= clazz
    }
}

// */