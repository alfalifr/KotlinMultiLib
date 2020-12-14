package sidev.lib.reflex

import kotlin.reflect.KClass

//expect fun <T: Any> T.nativeShallowClone(): T
expect fun <T: Any> nativeSimpleNew(clazz: KClass<out T>, default: T?= null): T?

//expect fun <T: Any> nativeNew(clazz: KClass<T>, constructorParamValFunc: ((nativeParameter: Any) -> Any?)?= null): T?