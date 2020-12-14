package sidev.lib.text


expect fun Char.isDigit(): Boolean
expect fun Char.isLetter(): Boolean
//expect fun Char.isWhiteSpace(): Boolean
fun Char.isLetterOrDigit(): Boolean = isLetter() || isDigit()
fun Char.isSpecialChar(): Boolean = "[^A-Za-z0-9 ]".toRegex().find(toString()) != null


fun String.removeWhitespace(): String = replace("\\s".toRegex(), "")