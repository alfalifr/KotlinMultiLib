package sidev.lib.reflex.core

import sidev.lib.console.prine
import sidev.lib.platform.Platform
import sidev.lib.platform.platform
import sidev.lib.property.reevaluateLazy
import sidev.lib.reflex.*
import sidev.lib.reflex.native.*
import sidev.lib.reflex.native.getReturnType
import sidev.lib.reflex.native.getVisibility
import kotlin.reflect.KClass


internal actual val SiNative.nativeInnerName: String?
    get()= (getNativeClass(implementation) as KClass<*>).fullName
internal actual val SiNative.nativeFullName: String?
    get()= (getNativeClass(implementation) as KClass<*>).fullName
internal actual val SiNative.nativeSimpleName: String?
    get()= (getNativeClass(implementation) as KClass<*>).nativeSimpleName