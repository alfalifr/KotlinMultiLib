package sidev.lib.exception

import kotlin.reflect.KClass

open class ParameterExc(
    relatedClass: KClass<*>?= ParameterExc::class,
    paramName: String= "<parameter>",
    currentParamValue: Any?= null,
    detMsg: String= "")
    : Exc(relatedClass, "Parameter yg diinputkan: \"$paramName\" salah, nilai: \"$currentParamValue\"", detMsg)