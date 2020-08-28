package sidev.lib.reflex.comp.native

import sidev.lib.reflex.comp.SiParameter
import sidev.lib.reflex.comp.SiType
import sidev.lib.reflex.comp.SiVisibility


internal expect val isDynamicEnabled: Boolean


/** `this.extension` dapat berupa apa saja. */
internal expect fun getNativeClass(any: Any): Any

/** Fungsi ini hanya mengambil declared member functions saja. */
internal expect fun getNativeFunctions(nativeClass: Any): Sequence<Any>
//internal fun SiNativeClassifier.getNativeFunctions(): Sequence<Any> = implementation.getFunctions()

/** Fungsi ini hanya mengambil declared member properties saja. */
internal expect fun getNativeProperties(nativeClass: Any): Sequence<Any>
//internal fun SiNativeClassifier.getNativeProperties(): Sequence<Any> = implementation.getProperties()

/** Fungsi ini hanya mengambil declared member mutable properties saja. */
internal expect fun getNativeMutableProperties(nativeClass: Any): Sequence<Any>
//internal fun SiNativeClassifier.getNativeMutableProperties(): Sequence<Any> = implementation.getMutableProperties()

/** Fungsi ini hanya mengambil declared field saja. */
internal expect fun getNativeFields(nativeClass: Any, nativeProperties: Sequence<Any>): Sequence<Any?>

/** Sama dg [getNativeFields], namun hanya mengambil field dari satu [nativeProperty]. */
internal expect fun getNativeField(nativeProperty: Any): Any?

/**
 * Mengambil member yg dapat dipanggil dan dijadikan sbg [SiNativeCallable].
 * Member yg diambil termasuk fungsi dan semua properti.
 */
internal expect fun getNativeMembers(nativeClass: Any): Sequence<Any>
//internal fun SiNativeClassifier.getNativeMembers(): Sequence<Any> = implementation.getMembers()

internal expect fun getNativeConstructors(nativeClass: Any): Sequence<Any>
//internal fun SiNativeClassifier.getNativeConstructors(): Sequence<Any> = implementation.getNativeConstructors()

/** Biasanya `this` merupakan fungsi yg punya paramter. */
internal expect fun getNativeParameters(nativeFunc: Any): Sequence<Any>
//internal fun SiNativeCallable<*>.getNativeParameters(): Sequence<Any> = implementation.getParameters()

/** Mengambil immediate supertype dari `this`. */
internal expect fun getNativeSupertypes(nativeClass: Any): Sequence<Any>
//internal fun SiNativeClassifier.getNativeSupertypes(): Sequence<Any> = implementation.getSupertypes()


internal expect fun getParamIsOptional(nativeParam: Any): Boolean
internal expect fun getParamIsVararg(nativeParam: Any): Boolean
internal expect fun getParamType(nativeParam: Any): SiType
internal expect fun getParamKind(nativeParam: Any): SiParameter.Kind
internal expect fun getParamDefaultValue(nativeParam: Any): Any?
internal expect fun <T> getFuncCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T
internal expect fun <T> getConstrCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T
//untuk receiver2, sbenarnya gak tau guna yg sebenarnya.
internal expect fun <T> getFuncDefaultCallBlock(nativeFuncHost: Any, nativeFunc: Any): ((args: Array<out Any?>) -> T)?

internal expect fun <T> getPropGetValueBlock(nativeProp: Any): (receivers: Array<out Any>) -> T
internal expect fun <T> getPropSetValueBlock(nativeProp: Any): (receivers: Array<out Any>, value: T) -> Unit

internal expect fun getReturnType(nativeCallable: Any): SiType
/** @return `true` jika [nativeType] sudah final dan gak perlu dievaluasi lagi. */
internal expect fun isTypeFinal(nativeType: Any): Boolean

internal expect fun getVisibility(nativeReflexUnit: Any): SiVisibility

internal expect fun getIsAccessible(nativeReflexUnit: Any): Boolean
internal expect fun setIsAccessible(nativeReflexUnit: Any, isAccessible: Boolean)

internal expect fun getModifiers(nativeReflexUnit: Any): Int