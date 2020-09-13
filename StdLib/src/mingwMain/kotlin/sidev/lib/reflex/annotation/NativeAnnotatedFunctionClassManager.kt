package sidev.lib.reflex.annotation

import sidev.lib.reflex.mingw.MingwReflexConst
import sidev.lib.reflex.native_.SiNativeParameter
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/** Implemtasi dari [NativeAnnotatedFunctionClass], disertai cache. */
@Deprecated(MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG)
actual open class NativeAnnotatedFunctionClassManager(override val nativeAnnotatedFunctionCLassOwner: Any)
    : NativeAnnotatedFunctionClass {
    internal actual val annotatedFunCache: HashMap<Annotation, KFunction<*>>
            by lazy { HashMap<Annotation, KFunction<*>>() }

    @Deprecated(MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG, ReplaceWith("null"))
    override val nativeAnnotatedFunctionCLassManager: NativeAnnotatedFunctionClassManager?= null

    @Deprecated(MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG, ReplaceWith("null"))
    override fun <T : Annotation> callAnnotatedFunction(
        annotationClass: KClass<T>,
        checkFun: (T) -> Boolean,
        paramArgFun: (SiNativeParameter) -> Any?
    ): KFunction<*>? = null

    @Deprecated(MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG, ReplaceWith("null"))
    override fun <T : Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: KClass<T>,
        paramContainer: Any?,
        checkFun: (T) -> Boolean
    ): KFunction<*>? = null
}