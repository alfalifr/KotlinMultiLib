package sidev.lib.reflex.common.full

import sidev.lib.check.notNull
import sidev.lib.check.notNullTo
import sidev.lib.console.prine
import sidev.lib.property.UNINITIALIZED_VALUE
import sidev.lib.reflex.common.SiCallable
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiParameter
import sidev.lib.reflex.common.SiProperty1
import sidev.lib.reflex.common.core.SiReflexConst
import sidev.lib.reflex.common.native.getIsAccessible
import sidev.lib.reflex.common.native.setIsAccessible
import kotlin.reflect.KCallable


var SiCallable<*>.isAccessible: Boolean
    get()= descriptor.native.notNullTo { getIsAccessible(it) } ?: false
    set(v){ descriptor.native.notNull { setIsAccessible(it, v) } }



/**
 * Cara aman untuk memanggil [KCallable.callBy].
 *
 * Fungsi ini melakukan pengecekan terhadap tipe argumen, nullabilitas, dan opsionalitasnya
 * sehingga meminimalisir terjadi error saat pemanggilan [KCallable].
 */
fun <R> SiCallable<R>.callBySafely(args: Map<SiParameter, Any?>): R{
    val newArgs= HashMap<SiParameter, Any?>()
    for(param in parameters){
        var value= args[param]
        if(value == null){
            if(param.isOptional) continue
            if(!param.type.isMarkedNullable)
                throw IllegalArgumentException("Nilai argumen param: \"$param\" tidak boleh null")
        } else if(param.type.classifier != value::class){
            if(param.isOptional) continue
            if(param.type.isMarkedNullable) value= null
            else throw IllegalArgumentException("Tipe param: \"$param\" adalah ${param.type.classifier}, namun argumen bertipe ${value::class}")
        }
        newArgs[param]= value
    }
    return callBy(newArgs)
}

fun <R> SiCallable<R>.forceCall(vararg args: Any?): R{
    val initAccessible= isAccessible
    isAccessible= true
    val vals= call(*args)
    isAccessible= initAccessible
    return vals
}

fun <R> SiCallable<R>.forceCallBy(args: Map<SiParameter, Any?>): R{
    val initAccessible= isAccessible
    isAccessible= true
    val vals= callBy(args)
    isAccessible= initAccessible
    return vals
}

val SiCallable<*>.isConstructor: Boolean
    get()= if((descriptor.host as? SiClass<*>)?.isArray == true) true
        else toString().matches(SiReflexConst.FUNCTION_CONSTRUCTOR_NAME_PATTERN.toRegex())
