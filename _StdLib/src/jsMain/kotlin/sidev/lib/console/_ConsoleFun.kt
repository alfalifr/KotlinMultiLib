package sidev.lib.console

import sidev.lib.text.Charset
import sidev.lib.text.charsetPrintWarningFirst

actual fun str(any: Any?): String = sidev.lib.reflex.js.str(any)

actual fun log(any: Any?) = js("console.log(any)")

actual fun nativePrint(message: Any?, charset: Charset) = charsetPrintWarningFirst { print(message) }
actual fun nativePrintln(message: Any?, charset: Charset) = charsetPrintWarningFirst { println(message) }