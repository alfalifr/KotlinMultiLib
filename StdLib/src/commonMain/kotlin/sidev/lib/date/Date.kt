package sidev.lib.date

import sidev.lib.util.Locale

expect class Date constructor(){
    constructor(str: String)
    val time: Long
    fun toFormatString(format: String = "dd-MM-yyyy HH:mm:ss", locale: Locale?= null): String
    companion object{
        fun nowString(format: String = "dd-MM-yyyy HH:mm:ss", locale: Locale?= null): String
        fun nowTime(): Long
    }
}