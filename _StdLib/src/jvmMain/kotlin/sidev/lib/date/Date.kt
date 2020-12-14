package sidev.lib.date

import sidev.lib.util.Locale
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date as JavaDate

actual class Date {
    actual constructor(){
        delegate= JavaDate()
    }
    actual constructor(str: String){
        delegate= DateFormat.getDateInstance().parse(str)
    }
    private val delegate: JavaDate

    actual val time: Long
        get()= delegate.time

    actual fun toFormatString(format: String, locale: Locale?): String =
        SimpleDateFormat(format, (locale ?: Locale.getDefault()).delegate).format(delegate)

    actual companion object{
        actual fun nowString(format: String, locale: Locale?): String = Date().toFormatString(format, locale)
        actual fun nowTime(): Long = Date().time
    }
}