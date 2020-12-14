package sidev.lib.reflex.native_

import kotlin.reflect.KClass

/**
 * Representasi common dari nativeParam, baik itu [Parameter] pada Jvm amupun [JsParameter] pada Js.
 */
interface SiNativeParameter : SiNative{
    /** Jika null, maka parameter tidak punya nama sebenarnya. */
    val name: String?
    val index: Int
    val type: KClass<*>
    val isGeneric: Boolean
}