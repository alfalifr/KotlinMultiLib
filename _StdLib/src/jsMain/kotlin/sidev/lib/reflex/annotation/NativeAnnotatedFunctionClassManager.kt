package sidev.lib.reflex.annotation

import sidev.lib.reflex.js.kotlin.KotlinJsConst
import sidev.lib.reflex.native_.SiNativeParameter
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/** Implemtasi dari [NativeAnnotatedFunctionClass], disertai cache. */
@Deprecated("${KotlinJsConst.KOTLIN_JS_NOT_SUPPORT_ANNOTATION_YET_MSG}. Gunakan AnnotatedFunctionClassManager sbg gantinya.")
actual open class NativeAnnotatedFunctionClassManager(override val nativeAnnotatedFunctionCLassOwner: Any)
    : NativeAnnotatedFunctionClass {
    internal actual val annotatedFunCache: HashMap<Annotation, KFunction<*>>
            by lazy { HashMap<Annotation, KFunction<*>>() }

    @Deprecated(
        "${KotlinJsConst.KOTLIN_JS_NOT_SUPPORT_ANNOTATION_YET_MSG}. Gunakan AnnotatedFunctionClassManager sbg gantinya.",
        ReplaceWith("null")
    )
    override val nativeAnnotatedFunctionCLassManager: NativeAnnotatedFunctionClassManager?= null

    @Deprecated(
        "${KotlinJsConst.KOTLIN_JS_NOT_SUPPORT_ANNOTATION_YET_MSG}. Gunakan AnnotatedFunctionClassManager sbg gantinya.",
        ReplaceWith("null")
    )
    override fun <T : Annotation> callAnnotatedFunction(
        annotationClass: KClass<T>,
        checkFun: (T) -> Boolean,
        paramArgFun: (SiNativeParameter) -> Any?
    ): KFunction<*>? = null

    @Deprecated(
        "${KotlinJsConst.KOTLIN_JS_NOT_SUPPORT_ANNOTATION_YET_MSG}. Gunakan AnnotatedFunctionClassManager sbg gantinya.",
        ReplaceWith("null")
    )
    override fun <T : Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: KClass<T>,
        paramContainer: Any?,
        checkFun: (T) -> Boolean
    ): KFunction<*>? = null
}