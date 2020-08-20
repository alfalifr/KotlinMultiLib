package sidev.lib.console

actual fun str(any: Any?): String = sidev.lib.reflex.js.str(any)

fun log(any: Any?) = js("console.log(any)")