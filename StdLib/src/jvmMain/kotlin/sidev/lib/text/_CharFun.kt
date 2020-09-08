@file:JvmName("_CharFunJvm")
package sidev.lib.text


actual fun Char.isDigit(): Boolean = Character.isDigit(this)
actual fun Char.isLetter(): Boolean = Character.isLetter(this)
