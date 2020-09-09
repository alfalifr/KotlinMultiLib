package sidev.lib.reflex.native_

import kotlin.reflect.KClass

/**
 * Representasi common dari nativeParam, baik itu [Parameter] pada Jvm amupun [JsParameter] pada Js.
 */
interface SiNativeParameter : SiNative{
    val name: String
    val type: KClass<*>
    val isGeneric: Boolean
}