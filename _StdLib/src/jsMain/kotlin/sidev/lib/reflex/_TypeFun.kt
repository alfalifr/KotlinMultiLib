package sidev.lib.reflex

import kotlin.reflect.KClass


actual val KClass<*>.isPrimitiveArray: Boolean
    get()= isPrimitiveArray_default

