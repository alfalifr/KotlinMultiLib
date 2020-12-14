@file:JvmName("_NewInstanceFunJvm_Native")
package sidev.lib.reflex

import sidev.lib.console.prine
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance


actual fun <T: Any> nativeSimpleNew(clazz: KClass<out T>, default: T?): T?{
    if(clazz.javaPrimitiveType != null)
        return defaultPrimitiveValue(clazz)

    return try{ clazz.createInstance() }
    catch (e: Exception){
        prine("""nativeNew(): clazz: "$clazz" tidak punya konstruktor dg parameter kosong, return `default`.""")
        default
    }
}