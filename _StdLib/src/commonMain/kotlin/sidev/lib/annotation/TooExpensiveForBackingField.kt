package sidev.lib.annotation

/**
 * Property yg ditandai dg anotasi ini memiliki nilai yang terlalu mahal untuk disimpan di backingField.
 * Property yg ditandai dg anotasi ini kemungkinan memiliki nilai yang dikomputasi setiap kali dipanggil.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY)
annotation class TooExpensiveForBackingField(val msg: String = "")