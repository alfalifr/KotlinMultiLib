package sidev.lib.reflex.core

import sidev.lib.check.isNull
import sidev.lib.check.notNull
import sidev.lib.reflex.comp.SiClass
import kotlin.reflect.KClass


actual object ReflexLoaderManager{
    private const val MAX_ANONYMOUS_CLASS_AMOUNT = 10
    private val anonClsList = arrayOfNulls<SiClass<*>>(MAX_ANONYMOUS_CLASS_AMOUNT) //ArrayList<SiClass<*>>(MAX_ANONYMOUS_CLASS_AMOUNT)
    private var anonClsIndex= 0
    private val loadedClass: MutableMap<String, SiClass<*>> by lazy {
        mutableMapOf<String, SiClass<*>>()
    }
    /** Mengecek apakah [nativeClass] memiliki loaded [SiClass] yg di cache. */
    actual fun checkCachedClass(nativeClass: Any): Boolean{
        val javaName= (nativeClass as KClass<*>).java.name
        return nativeClass.qualifiedName in loadedClass.keys
                || anonClsList.find { it?.descriptor?.innerName == javaName } != null
    }

    actual fun <T: Any> loadCachedClass(nativeClass: Any): SiClass<T>{
        val name= (nativeClass as KClass<*>).qualifiedName
        return (if(name != null) loadedClass[name]
        else {
            val javaName= nativeClass.java.name
            try{
                anonClsList.find { it?.descriptor?.innerName == javaName }!!
            } catch (e: KotlinNullPointerException){
                throw NoSuchElementException("""ReflexLoaderManager.loadCachedClass(): SiClass dg nama "$javaName" belum diload.""")
            }
        }) as SiClass<T>
    }

    actual fun saveLoadedClass(clazz: SiClass<*>){
        clazz.qualifiedName.notNull {
            loadedClass[it]= clazz
        }.isNull {
            anonClsList[anonClsIndex++]= clazz
        }
    }
}

/*
====== Gak jadi ======
Karena mungkin dapat membebani memori.
======================
/**
 * [isAnonymous] digunakan saat operasi penghapusan data anonmous class yg tidak dipakai lagi.
 * [age] berisi info ttg sbrp lama [clazz] sudah disimpan sejak pertama kali diload.
 *   Data ini berguna untuk perhitungan penghapusan data anonymous clazz.
 */
internal data class SiClassContainer(val clazz: SiClass<*>, val isAnonymous: Boolean, var age: Int= 0)
 */