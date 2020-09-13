package sidev.lib.reflex.annotation

import sidev.lib.reflex.mingw.MingwReflexConst
import sidev.lib.reflex.native_.SiNativeParameter
import kotlin.reflect.*

@Deprecated(MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG)
actual interface NativeAnnotatedFunctionClass {
    actual val nativeAnnotatedFunctionCLassOwner: Any
    actual val nativeAnnotatedFunctionCLassManager: NativeAnnotatedFunctionClassManager?

    actual fun <T: Annotation> callAnnotatedFunction(
        annotationClass: KClass<T>,
        checkFun: ((T) -> Boolean),
        paramArgFun: (SiNativeParameter) -> Any?
    ): KFunction<*>?
/*
    {
        if(manager != null)
            return manager!!.callAnnotatedFunction(annotationClass, checkFun, callFun)
        var foundAnnotation: T?= null
        for(func in owner::class.declaredMemberFunctions){
//            prine("callAnnotatedFunction() func= $func func.annotations= ${func.annotations}")
            if(func.annotations.find { annotationClass.isSuperclassOf(it.realAnnotation::class) }?.also { foundAnnotation= it.realAnnotation as T } != null
                && checkFun(foundAnnotation!!)){

                val paramValMap= HashMap<KParameter, Any?>()
                for(param in func.parameters){
                    paramValMap[param]= if(param.kind == KParameter.Kind.VALUE) callFun(NativeReflexFactory._createNativeParameter(param)) else owner
//                    prine("Annot param= $param paramValMap[param]= ${paramValMap[param]}")
                }
                func.callBy(paramValMap)
                return func
            }
        }
        return null
    }
 */

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
    actual fun <T: Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: KClass<T>,
        paramContainer: Any?,
        checkFun: ((T) -> Boolean)
    ): KFunction<*>?
/*
    {
        if(manager != null)
            return manager!!.callAnnotatedFunctionWithParamContainer(annotationClass, paramContainer, checkFun)
        fun <T: Any> T.implementedPropertyTree(): NestedSequence<KProperty1<T, *>>
            = nestedSequence<KClass<*>, KProperty1<T, *>>(this::class.extendingClassesTree){
            it.declaredMemberProperties.iterator() as Iterator<KProperty1<T, *>>
        }
        return callAnnotatedFunction(annotationClass, checkFun){ param ->
            val paramName= when(val nativeParam= param.implementation){
                is KParameter -> nativeParam.renamedName
                is Parameter -> nativeParam.name
                else -> throw ReflexComponentExc(currentReflexedUnit = nativeParam, detMsg = "nativeParam bkn parameter.")
            }
            if(paramContainer != null){
                var value: Any?= null
                for(prop in paramContainer.implementedPropertyTree()){ //implementedAccesiblePropertiesValueMapTree
//                    prine("callAnnotatedFunctionWithParamContainer valMap= $valMap valMap.first.renamedName= ${valMap.first.renamedName} param.renamedName ${param.renamedName}")
                    if(prop.renamedName == paramName){
                        value= prop.get(paramContainer)
                        break
                    }
                }
                value
            } else null
        }
    }
 */
}