package sidev.lib.text

internal expect fun charsetForName(name: String): Charset
internal expect val defaultCharset: Charset