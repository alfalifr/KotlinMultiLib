package sidev.lib.environment

/**
 * Menaruh objek dg nama [name] dg nilai [value] pada global.
 * @return -> `true` jika berhasil menempatkan [name] pada global,
 *   -> `false` jika sudah ada objek dg nama [name] sebelumnya pada global dan [forceReplace] == false.
 */

@Deprecated(
    "setGlobalObject() pada mingw pada langsung dideklarasikan pada Static Object",
    ReplaceWith("false")
)
actual fun setGlobalObject(name: String, value: Any, forceReplace: Boolean): Boolean = false

/**
 * @return `null` jika tidak ada objek global dg nama [name].
 */
@Deprecated(
    "getGlobalObject() pada mingw pada langsung dideklarasikan pada Static Object",
    ReplaceWith("null")
)
actual fun getGlobalObject(name: String): Any? = null


@Deprecated(
    "putInternalObjectOnGlobal() pada mingw pada langsung dideklarasikan pada Static Object"
)
internal actual fun putInternalObjectOnGlobal(obj: Any) {}

@Deprecated(
    "putInternalObjectOnGlobal() pada mingw pada langsung dideklarasikan pada Static Object",
    ReplaceWith("throw IllegalStateException(\"Fungsi getInternalObject() tidak tersedia di MinGW.\")")
)
internal actual fun getInternalObject(name: String): Any = throw IllegalStateException("Fungsi getInternalObject() tidak tersedia di MinGW.")