package sidev.lib.reflex.native_

import sidev.lib.reflex.mingw.MingwReflexConst

@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_PARAMETER_MSG +". Error jika memanggil fungsi ini.",
    ReplaceWith("unavailableReflexAcces_throw(\"getSiNativeParameter(nativeParameter: Any)\", nativeParameter, \"SiNativeParameter\")")
)
internal actual fun getSiNativeParameter(nativeParameter: Any): SiNativeParameter
        = unavailableReflexAcces_throw("getSiNativeParameter(nativeParameter: Any)", nativeParameter, "SiNativeParameter")