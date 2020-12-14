package sidev.lib.reflex.native

import sidev.lib.reflex.mingw.MingwReflexConst
import sidev.lib.exception.ReflexStateExc

@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_PARAMETER_MSG,
    ReplaceWith(
        " throw ReflexStateExc(relatedReflexUnit = nativeParameter, currentState = \"getSiNativeParameter(nativeParameter: Any)\", detMsg = MingwReflexConst.KOTLIN_NATIVE_NO_PARAMETER_MSG)",
        "sidev.lib.exception.ReflexStateExc"
    )
)
internal actual fun getSiNativeParameter(nativeParameter: Any): SiNativeParameter
        = throw ReflexStateExc(
    relatedReflexUnit = nativeParameter, currentState = "getSiNativeParameter(nativeParameter: Any)",
    detMsg = MingwReflexConst.KOTLIN_NATIVE_NO_PARAMETER_MSG
)