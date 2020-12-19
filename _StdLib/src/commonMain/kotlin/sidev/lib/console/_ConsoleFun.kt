package sidev.lib.console

import sidev.lib.`val`.StringLiteral
import sidev.lib.text.Charset

object IoConfig{
    const val PRINT= true
    const val PRINT_DEBUG= PRINT && true
    const val PRINT_RESULT= PRINT && true
    const val PRINT_WARNING= PRINT && true
    const val PRINT_ERROR= PRINT && true
}

fun prind(any: Any?, endWithNewLine: Boolean = true, charset: Charset = Charset.default){
    if(IoConfig.PRINT_DEBUG)
        prin(any, StringLiteral.ANSI_CYAN, endWithNewLine, charset)
}
fun prinr(any: Any?, endWithNewLine: Boolean = true, charset: Charset = Charset.default){
    if(IoConfig.PRINT_RESULT)
        prin(any, StringLiteral.ANSI_GREEN, endWithNewLine, charset)
}
fun prinw(any: Any?, endWithNewLine: Boolean = true, charset: Charset = Charset.default){
    if(IoConfig.PRINT_WARNING)
        prin(any, StringLiteral.ANSI_YELLOW, endWithNewLine, charset)
}
fun prine(any: Any?, endWithNewLine: Boolean = true, charset: Charset = Charset.default){
    if(IoConfig.PRINT_ERROR)
        prin(any, StringLiteral.ANSI_RED, endWithNewLine, charset)
}
fun prinp(any: Any?, endWithNewLine: Boolean = true, charset: Charset = Charset.default){
    if(IoConfig.PRINT_ERROR)
        prin(any, StringLiteral.ANSI_BLUE, endWithNewLine, charset)
}
//@JvmOverloads
fun prin(
    any: Any?, color: String= StringLiteral.ANSI_RESET,
    endWithNewLine: Boolean = true,
    charset: Charset = Charset.default
){
    val str= when(color) {
        StringLiteral.ANSI_RESET, StringLiteral.ANSI_WHITE -> any.toString()
        else -> "$color$any${StringLiteral.ANSI_RESET}"
    }
    if(IoConfig.PRINT){
        if(endWithNewLine)
            nativePrintln(str, charset)
        else
            nativePrint(str, charset)
    }
}
//fun prin_(any: Any?) = prin(any)

expect fun log(any: Any?)
expect fun str(any: Any?): String
expect fun nativePrint(message: Any?, charset: Charset = Charset.default)
expect fun nativePrintln(message: Any?, charset: Charset = Charset.default)