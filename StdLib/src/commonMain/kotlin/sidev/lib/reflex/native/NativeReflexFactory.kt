package sidev.lib.reflex.native
/*
Untuk smtr gak dipake karena ada di modul Reflex agar fungsi _createNativeParameter bisa memanggil fungsi getSiNativeParameter
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

    internal fun _createNativeParameter(
        nativeParameter: Any
    ): SiNativeParameter = getSiNativeParameter(nativeParameter)
}
 */