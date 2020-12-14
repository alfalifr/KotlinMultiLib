package sidev.lib.util

import java.util.Locale as JavaLocale

actual class Locale{
    actual constructor(id: String){
        delegate= JavaLocale.forLanguageTag(id)
    }
    constructor(javaLocale: JavaLocale){
        delegate= javaLocale
    }
    internal val delegate: JavaLocale //internal agar dapat diakses dari luar instance dalam library ini.


    /** Berisi id yg memiliki format {language}-{country}. */
    actual val qualifiedId: String
        get()= "$language-$country"
    actual val displayName: String
        get()= delegate.displayName
    actual val country: String
        get()= delegate.country
    actual val language: String
        get()= delegate.language
    actual val script: String
        get()= delegate.script

    actual companion object{
        actual fun getDefault(): Locale = Locale(JavaLocale.getDefault())
    }
}