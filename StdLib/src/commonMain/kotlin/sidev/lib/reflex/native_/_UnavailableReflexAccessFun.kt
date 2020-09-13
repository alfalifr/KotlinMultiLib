package sidev.lib.reflex.native_

import sidev.lib.console.prine
import sidev.lib.exception.ReflexStateExc
import sidev.lib.platform.platform
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiVisibility
import sidev.lib.reflex.core.ReflexTemplate


internal fun printUnavailableReflexWarning(callerFunName: String, fromUnit: Any, getUnit: String, returnStr: String){
    prine("WARNING: Reflex belum tersedia pada platform: \"${platform.name}\"")
    prine("WARNING: Current called function: $callerFunName")
    prine("WARNING: , from unit: \"$fromUnit\" get unit: \"$getUnit\", return `$returnStr`")
}
internal fun <T> unavailableReflexAcces_sequence(callerFunName: String, fromUnit: Any, getUnit: String): Sequence<T>{
    printUnavailableReflexWarning(callerFunName, fromUnit, getUnit, "emptySequence()")
    return emptySequence()
}
internal fun <T> unavailableReflexAcces_list(callerFunName: String, fromUnit: Any, getUnit: String): List<T>{
    printUnavailableReflexWarning(callerFunName, fromUnit, getUnit, "emptyList()")
    return emptyList()
}
internal fun unavailableReflexAcces_any(callerFunName: String, fromUnit: Any, getUnit: String): Any?{
    printUnavailableReflexWarning(callerFunName, fromUnit, getUnit, "null")
    return null
}
internal fun unavailableReflexAcces_throw(callerFunName: String, fromUnit: Any, getUnit: String): Nothing{
    printUnavailableReflexWarning(callerFunName, fromUnit, getUnit, "throw")
    throw ReflexStateExc(relatedReflexUnit = fromUnit, currentState = "Platform == \"${platform.name}\"",
    detMsg = "Reflex belum tersedia pada platform: \"${platform.name}\"")
}
internal fun unavailableReflexAcces_siType(callerFunName: String, fromUnit: Any): SiType {
    printUnavailableReflexWarning(callerFunName, fromUnit, "`SiType`", ReflexTemplate.typeDynamic.toString())
    return ReflexTemplate.typeDynamic
}
internal fun unavailableReflexAcces_siParamterKind(callerFunName: String, fromUnit: Any): SiParameter.Kind {
    printUnavailableReflexWarning(callerFunName, fromUnit, "`SiType`", SiParameter.Kind.VALUE.toString())
    return SiParameter.Kind.VALUE
}
internal fun unavailableReflexAcces_siVisibility(callerFunName: String, fromUnit: Any): SiVisibility {
    printUnavailableReflexWarning(callerFunName, fromUnit, "`SiVisibility`", SiVisibility.PUBLIC.toString())
    return SiVisibility.PUBLIC
}
internal fun unavailableReflexAcces_bool(callerFunName: String, fromUnit: Any, defaultBool: Boolean = false): Boolean{
    printUnavailableReflexWarning(callerFunName, fromUnit, "`boolean`", defaultBool.toString())
    return defaultBool
}
internal fun unavailableReflexAcces_int(callerFunName: String, fromUnit: Any, defaultInt: Int = 0): Int{
    printUnavailableReflexWarning(callerFunName, fromUnit, "`boolean`", defaultInt.toString())
    return defaultInt
}
internal fun <T> unavailableReflexAcces_callBlock(callerFunName: String, fromUnit: Any): (Array<out Any?>) -> T {
    val callBlock = { _: Array<out Any?> -> }
    printUnavailableReflexWarning(callerFunName, fromUnit, "`SiType`", callBlock.toString())
    return callBlock as ((Array<out Any?>) -> T)
}
internal fun <T> unavailableReflexAcces_getBlock(callerFunName: String, fromUnit: Any): (Array<out Any>) -> T {
    val callBlock = { _: Array<out Any> -> }
    printUnavailableReflexWarning(callerFunName, fromUnit, "`SiType`", callBlock.toString())
    return callBlock as ((Array<out Any>) -> T)
}

internal fun <T> unavailableReflexAcces_setBlock(callerFunName: String, fromUnit: Any): (Array<out Any>, T) -> Unit {
    val setBlock = { _: Array<out Any>, _: T -> }
    printUnavailableReflexWarning(callerFunName, fromUnit, "`SiType`", setBlock.toString())
    return setBlock
}