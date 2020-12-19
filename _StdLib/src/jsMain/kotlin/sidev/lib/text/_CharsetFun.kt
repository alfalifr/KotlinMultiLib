package sidev.lib.text

import sidev.lib.console.prinw


internal inline fun <T> charsetPrintWarningFirst(block: () -> T): T {
    prinw("Charset pada Js belum berfungsi dengan baik.")
    return block()
}

@ExperimentalStdlibApi
internal actual val defaultCharset: Charset = charsetPrintWarningFirst { CharsetJsImpl }
@ExperimentalStdlibApi
internal actual fun charsetForName(name: String): Charset = charsetPrintWarningFirst { CharsetJsImpl }