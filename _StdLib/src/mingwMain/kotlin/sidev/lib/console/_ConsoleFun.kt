package sidev.lib.console


actual fun str(any: Any?): String = any.toString()
actual fun log(any: Any?) = println(any)
