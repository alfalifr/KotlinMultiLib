package sidev.lib.reflex.native

import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiVisibility


actual val isDynamicEnabled: Boolean = false

/** `this.extension` dapat berupa apa saja. */
internal actual fun getNativeClass(any: Any): Any = any::class //.qualifiedName ?: ""

/** Fungsi ini hanya mengambil declared member functions saja. */
internal actual fun getNativeFunctions(nativeClass: Any): Sequence<Any> = unavailableReflexAcces_sequence("getNativeFunctions(nativeClass: Any)", "nativeClass: $nativeClass", "function")
//internal fun SiNativeClassifier.getNativeFunctions(): Sequence<Any> = implementation.getFunctions()

/** Fungsi ini hanya mengambil declared member properties saja. */
internal actual fun getNativeProperties(nativeClass: Any): Sequence<Any> = unavailableReflexAcces_sequence("getNativeProperties(nativeClass: Any)", "nativeClass: $nativeClass", "property")
//internal fun SiNativeClassifier.getNativeProperties(): Sequence<Any> = implementation.getProperties()

/** Fungsi ini hanya mengambil declared member mutable properties saja. */
internal actual fun getNativeMutableProperties(nativeClass: Any): Sequence<Any> = unavailableReflexAcces_sequence("getNativeMutableProperties(nativeClass: Any)", "nativeClass: $nativeClass", "mutableProperty")
//internal fun SiNativeClassifier.getNativeMutableProperties(): Sequence<Any> = implementation.getMutableProperties()

/** Fungsi ini hanya mengambil declared field saja. */
internal actual fun getNativeFields(nativeClass: Any, nativeProperties: Sequence<Any>): Sequence<Any?> = unavailableReflexAcces_sequence("getNativeFields(nativeClass: Any, nativeProperties: Sequence<Any>)", "nativeClass: $nativeClass", "field")

/** Sama dg [getNativeFields], namun hanya mengambil field dari satu [nativeProperty]. */
internal actual fun getNativeField(nativeProperty: Any): Any? = unavailableReflexAcces_any("getNativeField(nativeProperty: Any)", "nativeProperty: $nativeProperty", "field")

/**
 * Mengambil member yg dapat dipanggil dan dijadikan sbg [SiNativeCallable].
 * Member yg diambil termasuk fungsi dan semua properti.
 */
internal actual fun getNativeMembers(nativeClass: Any): Sequence<Any> = unavailableReflexAcces_sequence("getNativeMembers(nativeClass: Any)", "nativeClass: $nativeClass", "member")
//internal fun SiNativeClassifier.getNativeMembers(): Sequence<Any> = implementation.getMembers()

internal actual fun getNativeConstructors(nativeClass: Any): Sequence<Any> = unavailableReflexAcces_sequence("getNativeConstructors(nativeClass: Any)", "nativeClass: $nativeClass", "constructor")
//internal fun SiNativeClassifier.getNativeConstructors(): Sequence<Any> = implementation.getNativeConstructors()

/** Biasanya `this` merupakan fungsi yg punya paramter. */
internal actual fun getNativeParameters(nativeFunc: Any): Sequence<Any> = unavailableReflexAcces_sequence("getNativeParameters(nativeFunc: Any)", "nativeFunc: $nativeFunc", "parameter")
//internal fun SiNativeCallable<*>.getNativeParameters(): Sequence<Any> = implementation.getParameters()

/** Mengambil immediate supertype dari `this`. */
internal actual fun getNativeSupertypes(nativeClass: Any): Sequence<Any> = unavailableReflexAcces_sequence("getNativeSupertypes(nativeClass: Any)", "nativeClass: $nativeClass", "supertype")
//internal fun SiNativeClassifier.getNativeSupertypes(): Sequence<Any> = implementation.getSupertypes()


internal actual fun getParamIsOptional(nativeParam: Any): Boolean = unavailableReflexAcces_bool("getParamIsOptional(nativeParam: Any)", nativeParam, false)
internal actual fun getParamIsVararg(nativeParam: Any): Boolean = unavailableReflexAcces_bool("getParamIsVararg(nativeParam: Any)", nativeParam, false)
internal actual fun getParamType(nativeParam: Any): SiType = unavailableReflexAcces_siType("getParamType(nativeParam: Any)", "nativeParam: $nativeParam")
internal actual fun getParamKind(nativeParam: Any): SiParameter.Kind = unavailableReflexAcces_siParamterKind("getParamKind(nativeParam: Any)", "nativeParam: $nativeParam")
internal actual fun getParamDefaultValue(nativeParam: Any): Any? = unavailableReflexAcces_any("getParamDefaultValue(nativeParam: Any)", "nativeParam: $nativeParam", "parameterDefaultValue")
internal actual fun <T> getFuncCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T = unavailableReflexAcces_callBlock("getFuncCallBlock(nativeFuncHost: Any, nativeFunc: Any)", "nativeFunc: $nativeFunc")
internal actual fun <T> getConstrCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T = unavailableReflexAcces_callBlock("getConstrCallBlock(nativeFuncHost: Any, nativeFunc: Any)", "nativeFunc: $nativeFunc")
//untuk receiver2, sbenarnya gak tau guna yg sebenarnya.
internal actual fun <T> getFuncDefaultCallBlock(nativeFuncHost: Any, nativeFunc: Any): ((args: Array<out Any?>) -> T)?
        = unavailableReflexAcces_callBlock("getFuncDefaultCallBlock(nativeFuncHost: Any, nativeFunc: Any)", "nativeFunc: $nativeFunc")
internal actual fun <T> getPropGetValueBlock(nativeProp: Any): (receivers: Array<out Any>) -> T
        = unavailableReflexAcces_getBlock("getPropGetValueBlock(nativeProp: Any)", "nativeProp: $nativeProp")
internal actual fun <T> getPropSetValueBlock(nativeProp: Any): (receivers: Array<out Any>, value: T) -> Unit
        = unavailableReflexAcces_setBlock("getPropSetValueBlock(nativeProp: Any)", "nativeProp: $nativeProp")

internal actual fun getReturnType(nativeCallable: Any): SiType = unavailableReflexAcces_siType("getReturnType(nativeCallable: Any)", "nativeCallable: $nativeCallable")
/** @return `true` jika [nativeType] sudah final dan gak perlu dievaluasi lagi. */
internal actual fun isTypeFinal(nativeType: Any): Boolean = unavailableReflexAcces_bool("isTypeFinal(nativeType: Any)", "nativeType: $nativeType", true)
internal actual fun getVisibility(nativeReflexUnit: Any): SiVisibility = unavailableReflexAcces_siVisibility("getVisibility(nativeReflexUnit: Any)", "nativeReflexUnit: $nativeReflexUnit")

//internal expect fun getIsAccessible(nativeReflexUnit: Any): Boolean
//internal expect fun setIsAccessible(nativeReflexUnit: Any, isAccessible: Boolean)

internal actual fun getModifiers(nativeReflexUnit: Any): Int = unavailableReflexAcces_int("getModifiers(nativeReflexUnit: Any)", "nativeReflexUnit: $nativeReflexUnit")