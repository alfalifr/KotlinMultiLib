package sidev.lib.reflex

import sidev.lib.reflex.mingw.MingwReflexConst
import kotlin.reflect.KClass

//expect fun <T: Any> T.nativeShallowClone(): T
@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG, ReplaceWith("default")
)
actual fun <T: Any> nativeSimpleNew(clazz: KClass<out T>, default: T?): T? = default //Karena Kotlin/Native gak punya refleksi.

//expect fun <T: Any> nativeNew(clazz: KClass<T>, constructorParamValFunc: ((nativeParameter: Any) -> Any?)?= null): T?