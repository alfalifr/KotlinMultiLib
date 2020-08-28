package sidev.lib.reflex.core

import sidev.lib.reflex.comp.SiClass


expect object ReflexLoaderManager{
    /** Mengecek apakah [nativeClass] memiliki loaded [SiClass] yg di cache. */
    fun checkCachedClass(nativeClass: Any): Boolean
    fun <T: Any> loadCachedClass(nativeClass: Any): SiClass<T>
    fun saveLoadedClass(clazz: SiClass<*>)
}