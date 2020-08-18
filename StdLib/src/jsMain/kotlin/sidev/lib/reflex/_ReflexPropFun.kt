package sidev.lib.reflex

import sidev.lib.reflex._ReflexConst.K_ARRAY_CLASS_STRING
import sidev.lib.reflex.inner.isSubclassOf
import sidev.lib.reflex.js.JsType
import kotlin.reflect.*


actual val <T: Any> KClass<T>.isPrimitive: Boolean
    get()= js.let { it.name == JsType.STRING.jsName || it.name == JsType.NUMBER.jsName }