package sidev.lib.text

import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset as JvmCharset


internal actual open class CharsetImpl(val origin: JvmCharset): Charset {

    override val name: String
        get() = origin.name()

    override fun encode(str: String): ByteArray = origin.encode(str).array()
    override fun encode(charArray: CharArray): ByteArray = origin.encode(CharBuffer.wrap(charArray)).array()
    override fun decode(byteArray: ByteArray): CharArray = origin.decode(ByteBuffer.wrap(byteArray)).array()
    override fun contains(charset: Charset): Boolean = origin.contains(native)
    override fun equals(other: Any?): Boolean = other is Charset && other.name == name
    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = origin.toString()
}