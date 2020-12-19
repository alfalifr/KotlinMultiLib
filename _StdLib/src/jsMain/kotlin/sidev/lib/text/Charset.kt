package sidev.lib.text


@ExperimentalStdlibApi
internal object CharsetJsImpl: CharsetImpl()

@ExperimentalStdlibApi
internal actual open class CharsetImpl: Charset {

    override val name: String
        get() = "UTF-8"

    override fun encode(str: String): ByteArray = charsetPrintWarningFirst { str.encodeToByteArray() }
    override fun encode(charArray: CharArray): ByteArray = charsetPrintWarningFirst { encode(charArray.concatToString()) }

    override fun decode(byteArray: ByteArray): CharArray = charsetPrintWarningFirst {
        val list= byteArray.map { it.toChar() }
        CharArray(list.size){ list[it] }
    }
    override fun contains(charset: Charset): Boolean = charsetPrintWarningFirst { true }

    override fun equals(other: Any?): Boolean = other is Charset && other.name == name
    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = name
}