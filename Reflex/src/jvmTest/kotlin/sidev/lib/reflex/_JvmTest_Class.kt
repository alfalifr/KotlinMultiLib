package sidev.lib.reflex

import sidev.lib.console.prin
import sidev.lib.reflex.annotation.AnnotatedFunctionClass
import sidev.lib.reflex.annotation.AnnotatedFunctionClassManager
import sidev.lib.reflex.annotation.NativeAnnotatedFunctionClassDef
import sidev.lib.reflex.annotation.NativeAnnotatedFunctionClassManager

annotation class JvmAnot(val x: Int)
class JvmAc: AnnotatedFunctionClass, NativeAnnotatedFunctionClassDef{
    override val annotatedFunctionClassManager: AnnotatedFunctionClassManager?= AnnotatedFunctionClassManager(annotatedFunctionClassOwner)
    override val nativeAnnotatedFunctionCLassManager: NativeAnnotatedFunctionClassManager?= NativeAnnotatedFunctionClassManager(nativeAnnotatedFunctionCLassOwner)

    @JvmAnot(10)
    fun someFun(x: Int)= prin(" ====== someFun() x= $x ================ ")

    @JvmAnot(2)
    fun someOtherFun(x: Int= 10, y: Int, z: Int= 54)= prin(" ====== someOtherFun() x= $x y= $y z= $z ================ ")
}