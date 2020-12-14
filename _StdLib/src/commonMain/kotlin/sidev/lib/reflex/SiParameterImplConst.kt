package sidev.lib.reflex

import sidev.lib.reflex.core.ReflexFactory
import sidev.lib.reflex.core.ReflexTemplate


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
        null, null, 0, ReflexTemplate.typeAny, "\$receiver0"
    )
    val setterValue1: SiParameter = ReflexFactory.createParameter(
        null, null, 1, ReflexTemplate.typeAnyNullable, "\$setterValue"
    )
}

//class Poin()