package sidev.lib.reflex.full

import sidev.lib.reflex.*
import sidev.lib.reflex.mingw.MingwReflexConst
import sidev.lib.reflex.native.SiNativeParameter
import sidev.lib.exception.ReflexStateExc
import kotlin.reflect.KClass


/*
==========================
New Instance - Native
==========================
 */

/**
 * Fungsi ini melakukan clone sama sprti fungsi [clone], namun dilakukan dg menggunakan refleksi native
 * sehingga tidak me-load dulu komponen [SiReflex].
 *
 * <29 Agustus 2020> => Fungsi ini melakukan clone sederhana sehingga tidak mengecek apakah kelas dari `this.extension` [T]
 *   merupakan shallowAnonymous tidak, mengingat properti [isShallowAnonymous] hanya dimiliki oleh [SiClass].
 *   Kemungkinan ke depannya akan disediakan properti [isShallowAnonymous] untuk native dg receiver adalah [KClass].
 */
@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG,
    ReplaceWith(
        "throw ReflexStateExc(relatedReflexUnit = \"<Any>\", currentState = \"T.nativeClone()\", detMsg = \"clone() belum dapat dilakukan karena Kotlin/Native belum mendukung refleksi.\")",
        "sidev.lib.exception.ReflexStateExc"
    )
)
actual fun <T: Any> T.nativeClone(isDeepClone: Boolean, constructorParamValFunc: ((KClass<*>, SiNativeParameter) -> Any?)?): T
        = throw ReflexStateExc(
    relatedReflexUnit = "<Any>", currentState = "T.nativeClone()",
    detMsg = "clone() belum dapat dilakukan karena Kotlin/Native belum mendukung refleksi."
)

@Deprecated(
    MingwReflexConst.KOTLIN_NATIVE_NO_REFLEX_MSG,
    ReplaceWith("null")
)
actual fun <T: Any> T.nativeNew(clazz: KClass<T>, defParamValFunc: ((param: SiNativeParameter) -> Any?)?): T?
        = null