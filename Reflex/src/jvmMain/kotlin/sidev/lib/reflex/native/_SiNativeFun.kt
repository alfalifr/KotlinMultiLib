@file:JvmName("SiNativeFunKtJvm")

package sidev.lib.reflex.native

/*
internal actual fun <T> getReturnType(siNativeCallable: SiNativeCallable<T>): SiType = when(val native= siNativeCallable.implementation){
    is KCallable<*> -> native.returnType.si
    else -> throw ReflexComponentExc(currentReflexedUnit = siNativeCallable::class, detMsg = "siNativeCallable bkn callable.")
}
 */