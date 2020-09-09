package sidev.lib.reflex.annotation

import sidev.lib.reflex.SiFunction
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.full.callBySafely
import sidev.lib.reflex.full.isSuperclassOf
import sidev.lib.reflex.native_.si
import sidev.lib.reflex.realAnnotation
import kotlin.reflect.KClass

/** Implemtasi dari [AnnotatedFunctionClass], disertai cache. */
open class AnnotatedFunctionClassManager(override var owner: Any): AnnotatedFunctionClass {
    private val annotatedFunCache: HashMap<Annotation, SiFunction<*>>
            by lazy { HashMap<Annotation, SiFunction<*>>() }

    override val manager: AnnotatedFunctionClassManager? = null

    final override fun <T : Annotation> callAnnotatedFunction(
        annotationClass: KClass<T>,
        checkFun: (T) -> Boolean,
        callFun: (SiParameter) -> Any?
    ): SiFunction<*>? {
        val usedAnnotationClass= annotationClass.si
        for(annotation in
            annotatedFunCache.keys.asSequence().filter { usedAnnotationClass.isSuperclassOf(it.realAnnotation::class.si) }){
            if(checkFun(annotation.realAnnotation as T)){
                val func= annotatedFunCache[annotation]!!
                val paramValMap= HashMap<SiParameter, Any?>()
                for(param in func.parameters)
                    paramValMap[param]= if(param.kind == SiParameter.Kind.VALUE) callFun(param) else owner
                func.callBySafely(paramValMap)
                return func
            }
        }
        val newlyFoundFunc= super.callAnnotatedFunction(annotationClass, checkFun, callFun)
            ?: return null
        val newlyFoundAnnot= newlyFoundFunc.annotations.find { usedAnnotationClass.isSuperclassOf(it.realAnnotation::class.si) }!! //Jika fungsinya ketemu, gak mungkin anotasinya null
        annotatedFunCache[newlyFoundAnnot]= newlyFoundFunc
        return newlyFoundFunc
    }

    final override fun <T : Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: KClass<T>,
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