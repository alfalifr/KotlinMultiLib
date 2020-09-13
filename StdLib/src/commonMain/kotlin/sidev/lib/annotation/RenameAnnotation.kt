package sidev.lib.annotation

import sidev.lib.reflex.*
import sidev.lib.reflex.native_.si

/**
 * Digunakan untuk menandai bahwa element yg ditandai dg anotasi ini memiliki nama yg berbeda
 * sesuai kebutuhan penggunaan anotasi ini.
 */
//@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Rename(val newName: String)

data class SiRename(val newName: String): SiAnnotation