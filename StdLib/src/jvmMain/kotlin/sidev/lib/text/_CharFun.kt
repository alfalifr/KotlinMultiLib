@file:JvmName("_CharFunJvm")
package sidev.lib.text


actual fun Char.isDigit(): Boolean = Character.isDigit(this)
actual fun Char.isLetter(): Boolean = Character.isLetter(this)
//actual fun Char.isWhiteSpace(): Boolean = Character.isWhitespace(this)
