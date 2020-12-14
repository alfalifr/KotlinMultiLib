package sidev.lib.reflex.core

import sidev.lib.reflex.*
import sidev.lib.reflex.native_.*
import kotlin.reflect.KClass


internal actual val SiNative.nativeInnerName: String?
    get()= (getNativeClass(implementation) as KClass<*>).fullName
internal actual val SiNative.nativeFullName: String?
    get()= (getNativeClass(implementation) as KClass<*>).fullName
internal actual val SiNative.nativeSimpleName: String?
    get()= (getNativeClass(implementation) as KClass<*>).nativeSimpleName