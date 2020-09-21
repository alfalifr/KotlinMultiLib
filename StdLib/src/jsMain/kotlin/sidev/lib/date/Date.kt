package sidev.lib.date

import sidev.lib.annotation.Unused
import sidev.lib.util.Locale
import kotlin.js.Date as JsDate

actual class Date {
    actual constructor(){
        delegate= JsDate()
    }
    actual constructor(str: String){
        delegate= JsDate(str)
    }
    private val delegate: JsDate

    actual val time: Long
        get()= delegate.getTime().toLong()

    actual fun toFormatString(
        @Unused("Tidak dapat memformat date pada Js") format: String,
        locale: Locale?
    ): String = delegate.toLocaleString((locale ?: Locale.getDefault()).qualifiedId)

    actual companion object{
        actual fun nowString(format: String, locale: Locale?): String = Date().toFormatString(format, locale)
        actual fun nowTime(): Long = Date().time
    }
}