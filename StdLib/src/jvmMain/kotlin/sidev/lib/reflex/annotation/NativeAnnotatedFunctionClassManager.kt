package sidev.lib.reflex.annotation

import sidev.lib.reflex.native_.NativeReflexFactory
import sidev.lib.reflex.native_.SiNativeParameter
import sidev.lib.reflex.realAnnotation
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.isSuperclassOf

/** Implemtasi dari [NativeAnnotatedFunctionClass], disertai cache. */
actual open class NativeAnnotatedFunctionClassManager(override val nativeAnnotatedFunctionCLassOwner: Any)
    : NativeAnnotatedFunctionClass, NativeAnnotatedFunctionClassDef {
    internal actual val annotatedFunCache: HashMap<Annotation, KFunction<*>>
            by lazy { HashMap<Annotation, KFunction<*>>() }

    override val nativeAnnotatedFunctionCLassManager: NativeAnnotatedFunctionClassManager?= null

    override fun <T : Annotation> callAnnotatedFunction(
        annotationClass: KClass<T>,
        checkFun: (T) -> Boolean,
        paramArgFun: (SiNativeParameter) -> Any?
    ): KFunction<*>? {
        for(annotation in
        annotatedFunCache.keys.asSequence().filter { annotationClass.isSuperclassOf(it.realAnnotation::class) }){
            if(checkFun(annotation.realAnnotation as T)){
                val func= annotatedFunCache[annotation]!!
                val paramValMap= HashMap<KParameter, Any?>()
                for(param in func.parameters)
                    paramValMap[param]= if(param.kind == KParameter.Kind.VALUE) paramArgFun(NativeReflexFactory._createNativeParameter(param)) else nativeAnnotatedFunctionCLassOwner
                func.callBy(paramValMap)
                return func
            }
        }
        val newlyFoundFunc= super.callAnnotatedFunction(annotationClass, checkFun, paramArgFun)
            ?: return null
        val newlyFoundAnnot= newlyFoundFunc.annotations.find { annotationClass.isSuperclassOf(it.realAnnotation::class) }!! //Jika fungsinya ketemu, gak mungkin anotasinya null
        annotatedFunCache[newlyFoundAnnot]= newlyFoundFunc
        return newlyFoundFunc
    }

    override fun <T : Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: KClass<T>,
        paramContainer: Any?,
        checkFun: (T) -> Boolean
    ): KFunction<*>? {
        return super.callAnnotatedFunctionWithParamContainer(
            annotationClass,
            paramContainer,
            checkFun
        )
    }
}