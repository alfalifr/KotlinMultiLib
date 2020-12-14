package sidev.lib.reflex.native


internal actual fun getIsAccessible(nativeReflexUnit: Any): Boolean
        = unavailableReflexAcces_bool_reflex("getIsAccessible(nativeReflexUnit: Any)", "nativeReflexUnit: $nativeReflexUnit")
internal actual fun setIsAccessible(nativeReflexUnit: Any, isAccessible: Boolean){
    printUnavailableReflexWarning_reflex("setIsAccessible(nativeReflexUnit: Any, isAccessible: Boolean)", "nativeReflexUnit: $nativeReflexUnit", "<kotlin.Unit>", "<kotlin.Unit>")
}