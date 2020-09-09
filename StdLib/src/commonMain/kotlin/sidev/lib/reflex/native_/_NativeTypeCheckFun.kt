package sidev.lib.reflex.native_


internal expect val Any.isNativeClassifier: Boolean
/** Sub dari [isNativeClassifier]. Jika [isNativeClass] == true, maka [isNativeClassifier] harus true, namun tidak berlaku kebalikan. */
internal expect val Any.isNativeClass: Boolean
internal expect val Any.isNativeType: Boolean
internal expect val Any.isNativeCallable: Boolean
internal expect val Any.isNativeFunction: Boolean
internal expect val Any.isNativeProperty: Boolean
internal expect val Any.isNativeMutableProperty: Boolean
internal expect val Any.isNativeParameter: Boolean

internal val Any.isReflexUnit: Boolean
    get()= isNativeClassifier || isNativeCallable || isNativeFunction || isNativeProperty || isNativeMutableProperty || isNativeParameter
            || isNativeClass || isNativeType