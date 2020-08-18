package sidev.lib.reflex.common

import sidev.lib.reflex.common.core.ReflexFactory
import sidev.lib.reflex.common.core.ReflexTemplate


interface SiParameter: SiReflex {
    val index: Int
    val name: String?
    val isOptional: Boolean
    val type: SiType
    val kind: Kind

    enum class Kind{
        /** Parameter yg menunjukan pemilik (instance) dari callable yg memiliki parameter ini. */
        INSTANCE,

        /** Instance yg digunakan untuk konteks `this` pada sebuah fungsi extension. */
        RECEIVER,

        /** Parameter yg memiliki nilai sebenarnya sesuai parameter yg dideklarasikan pada kode. */
        VALUE
    }
}

internal abstract class SiParamterImpl: SiReflexImpl(), SiParameter{
    abstract override var type: SiType
}

internal object SiParameterImplConst{
/*
    val nativeReceiver0: SiNativeParameter =
        NativeReflexFactory.createParameter(
            NativeReflexConst.NATIVE_PROPERTY_RECEIVER0_PARAMETER, 0, false, ReflexTemplate.typeAny
        )

    val nativeSetterValue: SiNativeParameter =
        NativeReflexFactory.createParameter(
            NativeReflexConst.NATIVE_PROPERTY_SETTER_VALUE_PARAMETER, 1, false, ReflexTemplate.typeAnyNullable //Untuk smtr tipenya ini.
        )
 */

    val receiver0: SiParameter = ReflexFactory.createParameter(
        null, null, 0, false, ReflexTemplate.typeAny, "\$receiver0"
    )
    val setterValue1: SiParameter = ReflexFactory.createParameter(
        null, null, 1, false, ReflexTemplate.typeAnyNullable, "\$setterValue"
    )
}