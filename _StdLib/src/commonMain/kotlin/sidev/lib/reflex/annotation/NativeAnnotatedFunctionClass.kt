package sidev.lib.reflex.annotation

import sidev.lib.reflex.native_.SiNativeParameter
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import sidev.lib.reflex.callBySafely
import sidev.lib.reflex.implementedAccesiblePropertiesValueMapTree
import sidev.lib.reflex.inner.declaredMemberFunctions
import sidev.lib.reflex.inner.isSuperclassOf
 */

expect interface NativeAnnotatedFunctionClass {
    val nativeAnnotatedFunctionCLassOwner: Any
    val nativeAnnotatedFunctionCLassManager: NativeAnnotatedFunctionClassManager?

    fun <T: Annotation> callAnnotatedFunction(
        annotationClass: KClass<T>,
        checkFun: ((T) -> Boolean) = {true},
        paramArgFun: (SiNativeParameter) -> Any?
    ): KFunction<*>?

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
        annotationClass: KClass<T>,
        paramContainer: Any?,
        checkFun: ((T) -> Boolean) = {true}
    ): KFunction<*>?
}


inline fun <reified T: Annotation> NativeAnnotatedFunctionClass.nativeCallAnnotatedFunction(
    noinline checkFun: ((T) -> Boolean) = {true},
    noinline paramArgFun: (SiNativeParameter) -> Any?
): KFunction<*>?{
    return callAnnotatedFunction(T::class, checkFun, paramArgFun)
}