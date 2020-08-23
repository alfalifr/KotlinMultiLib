package sidev.lib.type

import sidev.lib.reflex.common.core.createType
import sidev.lib.reflex.common.native.si

/**
 * Tipe data yg digunakan untuk menunjukan tipe null.
 * Tipe ini digunakan pada proses [Any.inferredType] untuk menunjukan inferredType dari null.
 */
object Null{
    val clazz = this::class.si
    val type = clazz.createType(nullable = true)
}