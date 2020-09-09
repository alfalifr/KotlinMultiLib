package sidev.lib.reflex.native_

import sidev.lib.console.prine
import sidev.lib.platform.platform
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiVisibility
import sidev.lib.reflex.core.ReflexTemplate


internal fun printUnavailableReflexWarning_reflex(callerFunName: String, fromUnit: Any, getUnit: String, returnStr: String){
    prine("WARNING: Reflex belum tersedia pada platform: \"${platform.name}\"")
    prine("WARNING: Current called function: $callerFunName")
    prine("WARNING: , from unit: \"$fromUnit\" get unit: \"$getUnit\", return `$returnStr`")
}
internal fun <T> unavailableReflexAcces_sequence_reflex(callerFunName: String, fromUnit: Any, getUnit: String): Sequence<T>{
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, getUnit, "emptySequence()")
    return emptySequence()
}
internal fun <T> unavailableReflexAcces_list_reflex(callerFunName: String, fromUnit: Any, getUnit: String): List<T>{
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, getUnit, "emptyList()")
    return emptyList()
}
internal fun unavailableReflexAcces_any_reflex(callerFunName: String, fromUnit: Any, getUnit: String): Any?{
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, getUnit, "null")
    return null
}
internal fun unavailableReflexAcces_siType_reflex(callerFunName: String, fromUnit: Any): SiType {
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`SiType`", ReflexTemplate.typeDynamic.toString())
    return ReflexTemplate.typeDynamic
}
internal fun unavailableReflexAcces_siParamterKind_reflex(callerFunName: String, fromUnit: Any): SiParameter.Kind {
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`SiType`", SiParameter.Kind.VALUE.toString())
    return SiParameter.Kind.VALUE
}
internal fun unavailableReflexAcces_siVisibility_reflex(callerFunName: String, fromUnit: Any): SiVisibility {
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`SiVisibility`", SiVisibility.PUBLIC.toString())
    return SiVisibility.PUBLIC
}
internal fun unavailableReflexAcces_bool_reflex(callerFunName: String, fromUnit: Any, defaultBool: Boolean = false): Boolean{
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`boolean`", defaultBool.toString())
    return defaultBool
}
internal fun unavailableReflexAcces_int_reflex(callerFunName: String, fromUnit: Any, defaultInt: Int = 0): Int{
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`boolean`", defaultInt.toString())
    return defaultInt
}
internal fun <T> unavailableReflexAcces_callBlock_reflex(callerFunName: String, fromUnit: Any): (Array<out Any?>) -> T {
    val callBlock = { _: Array<out Any?> -> }
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`SiType`", callBlock.toString())
    return callBlock as ((Array<out Any?>) -> T)
}
internal fun <T> unavailableReflexAcces_getBlock_reflex(callerFunName: String, fromUnit: Any): (Array<out Any>) -> T {
    val callBlock = { _: Array<out Any> -> }
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`SiType`", callBlock.toString())
    return callBlock as ((Array<out Any>) -> T)
}

internal fun <T> unavailableReflexAcces_setBlock_reflex(callerFunName: String, fromUnit: Any): (Array<out Any>, T) -> Unit {
    val setBlock = { _: Array<out Any>, _: T -> }
    printUnavailableReflexWarning_reflex(callerFunName, fromUnit, "`SiType`", setBlock.toString())
    return setBlock
}