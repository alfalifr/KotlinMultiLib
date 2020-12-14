package sidev.lib.reflex.annotation

import sidev.lib.annotation.renamedName
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.collection.sequence.nestedSequence
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.jvm.extendingClassesTree
import sidev.lib.reflex.native_.NativeReflexFactory
import sidev.lib.reflex.native_.SiNativeParameter
import sidev.lib.reflex.realAnnotation
import java.lang.reflect.Parameter
import kotlin.reflect.*
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSuperclassOf

/** Interface turunan dari [NativeAnnotatedFunctionClass] dg implementasi default tiap fungsinya. */
interface NativeAnnotatedFunctionClassDef: NativeAnnotatedFunctionClass {
    override val nativeAnnotatedFunctionCLassOwner: Any
        get()= this
    override val nativeAnnotatedFunctionCLassManager: NativeAnnotatedFunctionClassManager?

    override fun <T: Annotation> callAnnotatedFunction(
        annotationClass: KClass<T>,
        checkFun: ((T) -> Boolean),
        paramArgFun: (SiNativeParameter) -> Any?
    ): KFunction<*>? {
        if(nativeAnnotatedFunctionCLassManager != null)
            return nativeAnnotatedFunctionCLassManager!!.callAnnotatedFunction(annotationClass, checkFun, paramArgFun)
        var foundAnnotation: T?= null
        for(func in nativeAnnotatedFunctionCLassOwner::class.declaredMemberFunctions){
//            prine("callAnnotatedFunction() func= $func func.annotations= ${func.annotations}")
            if(func.annotations.find { annotationClass.isSuperclassOf(it.realAnnotation::class) }?.also { foundAnnotation= it.realAnnotation as T } != null
                && checkFun(foundAnnotation!!)){

                val paramValMap= HashMap<KParameter, Any?>()
                for(param in func.parameters){
                    paramValMap[param]= if(param.kind == KParameter.Kind.VALUE) paramArgFun(NativeReflexFactory._createNativeParameter(param)) else nativeAnnotatedFunctionCLassOwner
//                    prine("Annot param= $param paramValMap[param]= ${paramValMap[param]}")
                }
                func.callBy(paramValMap)
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
    override fun <T: Annotation> callAnnotatedFunctionWithParamContainer(
        annotationClass: KClass<T>,
        paramContainer: Any?,
        checkFun: ((T) -> Boolean)
    ): KFunction<*>? {
        if(nativeAnnotatedFunctionCLassManager != null)
            return nativeAnnotatedFunctionCLassManager!!.callAnnotatedFunctionWithParamContainer(annotationClass, paramContainer, checkFun)
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
}