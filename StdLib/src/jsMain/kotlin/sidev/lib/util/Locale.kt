package sidev.lib.util

import sidev.lib.console.prine

actual class Locale {
    actual constructor(id: String){
        delegate= js("new Intl.Locale(id)")
    }
    constructor(){
        val lang= try{ eval("navigator.language") }
        catch (e: Throwable){ eval("process.env.LANG") ?: "en-US" }

        delegate= js("new Intl.Locale(lang)")
    }

    private val delegate: dynamic

    /** Berisi id yg memiliki format {language}-{country}. */
    actual val qualifiedId: String
        get()= "$language-$country"
    actual val displayName: String
        get()= delegate.baseName
    actual val country: String
        get()= delegate.region
    actual val language: String
        get()= delegate.language
    actual val script: String
        get()= delegate.script

    actual companion object{
        actual fun getDefault(): Locale = Locale()
    }
}