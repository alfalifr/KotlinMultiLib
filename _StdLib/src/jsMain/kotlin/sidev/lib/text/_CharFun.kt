package sidev.lib.text


actual fun Char.isDigit(): Boolean = "[0-9]".toRegex().find(toString()) != null
/** Hanya bisa mendeteksi alfabet standar. */
actual fun Char.isLetter(): Boolean = "[A-Za-z]".toRegex().find(toString()) != null
//actual fun Char.isWhiteSpace(): Boolean = "[\\s]".toRegex().find(toString()) != null
