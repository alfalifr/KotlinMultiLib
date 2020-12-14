package sidev.lib.environment


/**
 * Menaruh objek dg nama [name] dg nilai [value] pada global.
 * @return -> `true` jika berhasil menempatkan [name] pada global,
 *   -> `false` jika sudah ada objek dg nama [name] sebelumnya pada global dan [forceReplace] == false.
 */
expect fun setGlobalObject(name: String, value: Any, forceReplace: Boolean = false): Boolean

/**
 * @return `null` jika tidak ada objek global dg nama [name].
 */
expect fun getGlobalObject(name: String): Any?

internal expect fun putInternalObjectOnGlobal(obj: Any)
internal expect fun getInternalObject(name: String): Any