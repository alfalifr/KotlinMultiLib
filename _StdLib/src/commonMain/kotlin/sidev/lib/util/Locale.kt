package sidev.lib.util

expect class Locale
/**
 * [id] dapat berupa string kode untuk bahasa saja maupun disertai kode negara.
 * Pemisah dari kode bahasa dan negara adalah `-`.
 */
constructor(id: String){
    /** Berisi id yg memiliki format {language}-{country}. */
    val qualifiedId: String
    val displayName: String
    val country: String
    val language: String
    val script: String

    companion object{
        fun getDefault(): Locale
    }
}