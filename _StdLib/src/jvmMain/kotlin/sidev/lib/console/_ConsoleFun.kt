@file:JvmName("_ConsoleFunJvm")
package sidev.lib.console

import sidev.lib.`val`.StringLiteral
import sidev.lib.text.Charset
import sidev.lib.text.native
import java.io.PrintStream


actual fun str(any: Any?): String = any.toString()
actual fun log(any: Any?) = println(any)
actual fun nativePrint(message: Any?, charset: Charset) {
    if(charset == Charset.default) print(message)
    else PrintStream(System.out, true, charset.native).print(message)
}
actual fun nativePrintln(message: Any?, charset: Charset) {
    if(charset == Charset.default) println(message)
    else PrintStream(System.out, true, charset.native).println(message)
}
/*
fun prind(any: Any?) = prind(any, true)
fun prinr(any: Any?) = prinr(any, true)
fun prinw(any: Any?) = prinw(any, true)
fun prine(any: Any?) = prine(any, true)
fun prin(any: Any?) = prin(any, StringLiteral.ANSI_RESET, true)
 */

@JvmOverloads
fun prind(any: Any?, charset: Charset = Charset.default) = prind(any, true, charset)
@JvmOverloads
fun prinr(any: Any?, charset: Charset = Charset.default) = prinr(any, true, charset)
@JvmOverloads
fun prinw(any: Any?, charset: Charset = Charset.default) = prinw(any, true, charset)
@JvmOverloads
fun prine(any: Any?, charset: Charset = Charset.default) = prine(any, true, charset)
@JvmOverloads
fun prin(any: Any?, charset: Charset = Charset.default) = prin(any, StringLiteral.ANSI_RESET, true, charset)

