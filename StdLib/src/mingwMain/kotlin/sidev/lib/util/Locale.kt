package sidev.lib.util

import platform.windows.DATE
import platform.windows.DATE_SHORTDATE
import platform.windows.GetLocaleInfo
import sidev.lib.annotation.NotYetSupported
import sidev.lib.unavailable.throwUnavailableOp

private const val msg= "Locale pada MinGW belum didukung"
@NotYetSupported(msg)
@Deprecated(msg)
actual class Locale
/**
 * [id] dapat berupa string kode untuk bahasa saja maupun disertai kode negara.
 * Pemisah dari kode bahasa dan negara adalah `-`.
 */
actual constructor(id: String){
    init { throwUnavailableOp(msg, Locale::class) }
    /** Berisi id yg memiliki format {language}-{country}. */
    actual val qualifiedId: String = throwUnavailableOp(msg, "val qualifiedId")
    actual val displayName: String = throwUnavailableOp(msg, "val displayName")
    actual val country: String = throwUnavailableOp(msg, "val country")
    actual val language: String = throwUnavailableOp(msg, "val language")
    actual val script: String = throwUnavailableOp(msg, "val script")

    actual companion object{
        actual fun getDefault(): Locale = throwUnavailableOp(msg, "Locale.getDefault()")
    }
}