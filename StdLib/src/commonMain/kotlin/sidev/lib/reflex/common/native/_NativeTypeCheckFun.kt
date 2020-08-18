package sidev.lib.reflex.common.native

import kotlin.reflect.KClassifier


internal expect val Any.isClassifier: Boolean
/** Sub dari [isClassifier]. Jika [isClass] == true, maka [isClassifier] harus true, namun tidak berlaku kebalikan. */
internal expect val Any.isClass: Boolean
internal expect val Any.isType: Boolean
internal expect val Any.isCallable: Boolean
internal expect val Any.isFunction: Boolean
internal expect val Any.isProperty: Boolean
internal expect val Any.isMutableProperty: Boolean
internal expect val Any.isParameter: Boolean

internal val Any.isReflexUnit: Boolean
    get()= isClassifier || isCallable || isFunction || isProperty || isMutableProperty || isParameter
            || isClass || isType