package sidev.lib.reflex.native_

import kotlin.reflect.KClass

object NativeReflexFactory {
    fun createNativeParameter(
        nativeParameter: Any,
        name: String,
        type: KClass<*>,
        isGeneric: Boolean
    ): SiNativeParameter = object : SiNativeParameter{
        override val implementation: Any = nativeParameter
        override val name: String = name
        override val type: KClass<*> = type
        override val isGeneric: Boolean = isGeneric
    }

    /**
     * Fungsi ini dkhususkan untuk penggunaan internal dalam library.
     */
    //Fungsi ini gak punya kompatibilitas dg Java 7 karena parameter pada Java 7
    //  akan lebih masuk akal diambil scr agregat, bkn satu-satu
    /*internal */fun _createNativeParameter(
        nativeParameter: Any
    ): SiNativeParameter = getSiNativeParameter(nativeParameter)
}