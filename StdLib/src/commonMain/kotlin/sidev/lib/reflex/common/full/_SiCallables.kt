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
