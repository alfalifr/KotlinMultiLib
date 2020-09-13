package sidev.lib.reflex.annotation

import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiFunction
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.full.callBySafely
import sidev.lib.reflex.full.isSuperclassOf
import sidev.lib.reflex.si
import sidev.lib.reflex.realAnnotation

/** Implemtasi dari [AnnotatedFunctionClass], disertai cache. */
open class AnnotatedFunctionClassManager(override var annotatedFunctionClassOwner: Any): AnnotatedFunctionClass {
    private val annotatedFunCache: HashMap<Annotation, SiFunction<*>>
            by lazy { HashMap<Annotation, SiFunction<*>>() }

    override val annotatedFunctionClassManager: AnnotatedFunctionClassManager? = null

    final override fun <T : Annotation> callAnnotatedFunction(
        annotationClass: SiClass<T>,
        checkFun: (T) -> Boolean,
        paramArgFun: (SiParameter) -> Any?
    ): SiFunction<*>? {
        for(annotation in
            annotatedFunCache.keys.asSequence().filter { annotationClass.isSuperclassOf(it.realAnnotation::class.si) }){
            if(checkFun(annotation.realAnnotation as T)){
                val func= annotatedFunCache[annotation]!!
                val paramValMap= HashMap<SiParameter, Any?>()
                for(param in func.parameters)
                    paramValMap[param]= if(param.kind == SiParameter.Kind.VALUE) paramArgFun(param) else annotatedFunctionClassOwner
                func.callBySafely(paramValMap)
                return func
            }
        }
        val newlyFoundFunc= super.callAnnotatedFunction(annotationClass, checkFun, paramArgFun)
            ?: return null
        val newlyFoundAnnot= newlyFoundFunc.annotations.find { annotationClass.isSuperclassOf(it.realAnnotation::class.si) }!! //Jika fungsinya ketemu, gak mungkin anotasinya null
        annotatedFunCache[newlyFoundAnnot]= newlyFoundFunc
        return newlyFoundFunc
    }

    final override fun <T : Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: SiClass<T>,
        paramContainer: Any?,
        checkFun: (T) -> Boolean
    ): SiFunction<*>? {
        return super.callAnnotatedFunctionWithParamContainer(
            annotationClass,
            paramContainer,
            checkFun
        )
    }
}