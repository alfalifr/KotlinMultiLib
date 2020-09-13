package sidev.lib.reflex.annotation

import kotlin.reflect.KFunction

/** Implemtasi dari [AnnotatedFunctionClass], disertai cache. */
expect open class NativeAnnotatedFunctionClassManager: NativeAnnotatedFunctionClass {
    internal val annotatedFunCache: HashMap<Annotation, KFunction<*>>
//            by lazy { HashMap<Annotation, KFunction<*>>() }
}