package sidev.lib.annotation

import sidev.lib.reflex.*

/**
 * Digunakan untuk menandai bahwa element yg ditandai dg anotasi ini memiliki nama yg berbeda
 * sesuai kebutuhan penggunaan anotasi ini.
 */
//@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Rename(val newName: String)

/**
 * Anotasi implicit yg dapat ditambahkan pada runtime.
 * Anotasi implicit berguna terutama pada platform yg blum mendkung anotasi pada runtime.
 *
 * Anotasi ini memiliki tujuan yg sama dg anotasi [Rename].
 */
data class SiRename(val newName: String): SiAnnotation