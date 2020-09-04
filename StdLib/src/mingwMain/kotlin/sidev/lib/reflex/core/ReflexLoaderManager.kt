package sidev.lib.reflex.core

import sidev.lib.check.isNull
import sidev.lib.check.notNull
import sidev.lib.check.notNullTo
import sidev.lib.exception.ReflexStateExc
import sidev.lib.reflex.SiClass
import kotlin.reflect.KClass

@ThreadLocal
actual object ReflexLoaderManager{
    private const val MAX_ANONYMOUS_CLASS_AMOUNT = 10
    private val anonClsList = arrayOfNulls<SiClass<*>>(MAX_ANONYMOUS_CLASS_AMOUNT) //ArrayList<SiClass<*>>(MAX_ANONYMOUS_CLASS_AMOUNT)
    private var anonClsIndex= -1
    private val loadedClass: MutableMap<String, SiClass<*>> by lazy {
        mutableMapOf<String, SiClass<*>>()
    }

    /** Mengecek apakah [nativeClass] memiliki loaded [SiClass] yg di cache. */
    actual fun checkCachedClass(nativeClass: Any): Boolean{
        return (nativeClass as KClass<*>).qualifiedName in loadedClass.keys
            //Tidak melakukan pengecekan terhadap innerName karena Kotlin/Native gak punya refleksi.
    }

    actual fun <T: Any> loadCachedClass(nativeClass: Any): SiClass<T>{
        return ((nativeClass as KClass<*>).qualifiedName.notNullTo {
            loadedClass[it]
        } as? SiClass<T>) ?: throw ReflexStateExc(
            relatedReflexUnit = nativeClass, currentState = "Tidak ada pada cache",
            detMsg = "Kelas: \"$nativeClass\" tidak ada pada cache definisi kelas."
        )
    }

    actual fun saveLoadedClass(clazz: SiClass<*>){
        clazz.qualifiedName.notNull {
            loadedClass[it]= clazz
        }.isNull {
            anonClsIndex++
            anonClsIndex %= MAX_ANONYMOUS_CLASS_AMOUNT
            anonClsList[anonClsIndex]= clazz
        }
    }
}