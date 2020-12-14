package sidev.lib.reflex

import sidev.lib.reflex.mingw.MingwReflexConst
import kotlin.reflect.KClass


@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG, ReplaceWith("false")
)
actual fun <T: Annotation> SiAnnotatedElement.setAnnotation(vararg annotations: KClass<T>): Boolean = false