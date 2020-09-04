package sidev.lib.text

import kotlin.text.isDigit as nativeIsDigit
import kotlin.text.isLetter as nativeIsLetter

actual fun Char.isDigit(): Boolean = nativeIsDigit()
actual fun Char.isLetter(): Boolean = nativeIsLetter()
