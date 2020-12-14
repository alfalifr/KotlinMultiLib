package sidev.lib.reflex.full

import sidev.lib.annotation.NotYetSupported

@NotYetSupported("Fungsi ini msh blum didukung pada platform Js.")
@Deprecated(
    "Fungsi ini msh blum didukung pada platform Js.",
    ReplaceWith("emptyArray()"),
)
@get:JsName("enumValues")
actual val <E: Enum<E>> E.enumValues: Array<E>
    get() = emptyArray() //eval("${this::class.simpleName}\$values()")