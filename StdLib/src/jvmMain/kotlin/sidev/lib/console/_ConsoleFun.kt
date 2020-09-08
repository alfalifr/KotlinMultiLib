@file:JvmName("_ConsoleFunJvm")
package sidev.lib.console

import sidev.lib.`val`.StringLiteral

actual fun str(any: Any?): String = any.toString()

fun prind(any: Any?) = prind(any, true)
fun prinr(any: Any?) = prinr(any, true)
fun prinw(any: Any?) = prinw(any, true)
fun prine(any: Any?) = prine(any, true)
fun prin(any: Any?) = prin(any, StringLiteral.ANSI_RESET, true)

