package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.SiClass
import kotlin.reflect.KClass


actual object ReflexLoaderManager{
    private val loadedClass: MutableMap<String, SiClass<*>> by lazy {
        mutableMapOf<String, SiClass<*>>()
    }
    /** Mengecek apakah [nativeClass] memiliki loaded [SiClass] yg di cache. */
    actual fun checkCachedClass(nativeClass: Any): Boolean
            = (nativeClass as KClass<*>).qualifiedName in loadedClass.keys
    actual fun <T: Any> loadCachedClass(nativeClass: Any): SiClass<T>
            = loadedClass[(nativeClass as KClass<*>).qualifiedName ?: ""] as SiClass<T>
    actual fun saveLoadedClass(clazz: SiClass<*>){
        loadedClass[clazz.qualifiedName!!]= clazz
    }
}