package sidev.lib.reflex.inner
/*
import kotlin.reflect.KAnnotatedElement

/**
 * Returns an annotation of the given type on this element.
 */
@SinceKotlin("1.1")
inline fun <reified T : Annotation> KAnnotatedElement.findAnnotation(): T? =
    @Suppress("UNCHECKED_CAST")
    annotations.firstOrNull { it is T } as T?

/**
 * Returns true if this element is annotated with an annotation of type [T].
 */
@ExperimentalStdlibApi
@SinceKotlin("1.3")
inline fun <reified T : Annotation> KAnnotatedElement.hasAnnotation(): Boolean =
    findAnnotation<T>() != null


 */