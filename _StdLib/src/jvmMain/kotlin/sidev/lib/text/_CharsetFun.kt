@file:JvmName("_CharsetFunJvm")
package sidev.lib.text

import java.nio.charset.Charset as JvmCharset


internal actual val defaultCharset: Charset = CharsetImpl(JvmCharset.defaultCharset())
internal actual fun charsetForName(name: String): Charset = CharsetImpl(JvmCharset.forName(name))

val Charset.native: java.nio.charset.Charset
    get()= JvmCharset.forName(name)