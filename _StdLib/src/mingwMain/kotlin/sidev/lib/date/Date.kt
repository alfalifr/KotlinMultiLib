package sidev.lib.date

import platform.windows.DATETIMEPICK_CLASSA
import platform.windows.DATE_LONGDATE
import sidev.lib.annotation.Unsafe
import sidev.lib.annotation.Unused
import sidev.lib.util.Locale

//TODO: hasil dari Date pada platform MinGW msh aneh.
/**
 * Pada platform MinGW, sementara ini hanya bisa mengambil Date dg waktu skrg.
 */
actual class Date {
    actual constructor()

    @Unsafe("Parameter `str` tidak akan dipakai")
    @Deprecated(
        "Parameter `str` tidak akan dipakai",
        ReplaceWith("Date()")
    )
    actual constructor(@Unused str: String)

    actual val time: Long
        get()= DATE_LONGDATE.toLong()

    actual fun toFormatString(
        @Unused("Tidak dapat memformat date pada MinGW") format: String,
        @Unused("Locale pada MinGW belum didukung") locale: Locale?
    ): String = DATETIMEPICK_CLASSA

    actual companion object{
        actual fun nowString(
            @Unused("Tidak dapat memformat date pada MinGW") format: String,
            @Unused("Locale pada MinGW belum didukung") locale: Locale?
        ): String = Date().toFormatString(format, locale)
        actual fun nowTime(): Long = Date().time
    }
}