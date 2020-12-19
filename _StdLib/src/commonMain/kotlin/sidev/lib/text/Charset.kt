@file:OptIn(ExperimentalStdlibApi::class)
package sidev.lib.text


interface Charset {
    companion object {
        val default: Charset
            get()= defaultCharset

        val UTF_8: Charset
            get()= charsetForName("UTF-8")
        val UTF_16: Charset
            get()= charsetForName("UTF-16")
        val UTF_32: Charset
            get()= charsetForName("UTF-32")
        val CP_1252: Charset
            get()= charsetForName("CP1252")
        val US_ASCII: Charset
            get()= charsetForName("US-ASCII")
        val ISO_8859_1: Charset
            get()= charsetForName("ISO-8859-1")

        fun forName(name: String): Charset = charsetForName(name)
    }
    val name: String

    fun encode(str: String): ByteArray
    fun encode(charArray: CharArray): ByteArray

//    fun decode(byteSeq: Sequence<Byte>): CharArray
    fun decode(byteArray: ByteArray): CharArray

    fun contains(charset: Charset): Boolean
}

internal expect open class CharsetImpl: Charset