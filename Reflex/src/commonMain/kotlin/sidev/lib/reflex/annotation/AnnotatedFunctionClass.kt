package sidev.lib.reflex.annotation

import sidev.lib.annotation.renamedName
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiFunction
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.full.callBySafely
import sidev.lib.reflex.full.declaredMemberFunctions
import sidev.lib.reflex.full.implementedPropertyValuesTree
import sidev.lib.reflex.full.isSuperclassOf
import sidev.lib.reflex.native_.si
import sidev.lib.reflex.realAnnotation

interface AnnotatedFunctionClass {
    val annotatedFunctionClassOwner: Any
        get()= this
    val annotatedFunctionClassManager: AnnotatedFunctionClassManager?

    fun <T: Annotation> callAnnotatedFunction(
        annotationClass: SiClass<T>,
        checkFun: ((T) -> Boolean) = {true},
        paramArgFun: (SiParameter) -> Any?
    ): SiFunction<*>? {
        if(annotatedFunctionClassManager != null)
            return annotatedFunctionClassManager!!.callAnnotatedFunction(annotationClass, checkFun, paramArgFun)
        var foundAnnotation: T?= null
        for(func in annotatedFunctionClassOwner::class.si.declaredMemberFunctions){
//            prine("callAnnotatedFunction() func= $func func.annotations= ${func.annotations}")
            if(func.annotations.find { annotationClass.isSuperclassOf(it.realAnnotation::class.si) }?.also { foundAnnotation= it.realAnnotation as T } != null
                && checkFun(foundAnnotation!!)){

                val paramValMap= HashMap<SiParameter, Any?>()
                for(param in func.parameters){
                    paramValMap[param]= if(param.kind == SiParameter.Kind.VALUE) paramArgFun(param) else annotatedFunctionClassOwner
//                    prine("Annot param= $param paramValMap[param]= ${paramValMap[param]}")
                }
                func.callBySafely(paramValMap)
                return func
            }
        }
        return null
    }

    /**
     * Mirip dg [callAnnotatedFunction], namun parameter diperoleh dari [paramContainer] scr langsung.
     * Perlu diperhatikan bahwa nilai yg diambil dari [paramContainer] adalah properti di dalamnya.
     * Jika [paramContainer] berupa array atau collection, maka nilai yg diambil bkn nilai yg ada di dalamnya,
     * melainkan properti di dalamnya, seperti [Array.size] atau [Collection.size].
     *
     * Fungsi ini mengambil nilai dari properti di dalam [paramContainer] dg cara mencocokan nama
     * properti dan parameter yg sama. Jika ada perubahan nama menggunakan anotasi [Rename],
     * maka nama tersebut yg diambil.
     */
    fun <T: Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: SiClass<T>,
        paramContainer: Any?,
        checkFun: ((T) -> Boolean) = {true}
    ): SiFunction<*>? {
        if(annotatedFunctionClassManager != null)
            return annotatedFunctionClassManager!!.callAnnotatedFunctionWithParamContainer(annotationClass, paramContainer, checkFun)
        return callAnnotatedFunction(annotationClass, checkFun){ param ->
            if(paramContainer != null){
                var value: Any?= null
                for(valMap in paramContainer.implementedPropertyValuesTree){ //implementedAccesiblePropertiesValueMapTree
//                    prine("callAnnotatedFunctionWithParamContainer valMap= $valMap valMap.first.renamedName= ${valMap.first.renamedName} param.renamedName ${param.renamedName}")
                    if(valMap.first.renamedName == param.renamedName){
                        value= valMap.second
                        break
                    }
                }
                value
            } else null
        }
    }
}


inline fun <reified T: Annotation> AnnotatedFunctionClass.callAnnotatedFunction(
    noinline checkFun: ((T) -> Boolean) = {true},
    noinline paramArgFun: (SiParameter) -> Any?
): SiFunction<*>?{
    return callAnnotatedFunction(T::class.si, checkFun, paramArgFun)
}