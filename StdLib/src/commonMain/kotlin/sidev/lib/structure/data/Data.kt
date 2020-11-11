package sidev.lib.structure.data

/**
 * Common interface yg dapat digunakan oleh `data class` agar dapat di-copy scr generic.
 */
interface Data<T: Data<T>>: Serializable {
    fun copy_(vararg prop: Pair<String, Any?>): T = copy_(mapOf(*prop))
    fun copy_(prop: Map<String, Any?>): T
}