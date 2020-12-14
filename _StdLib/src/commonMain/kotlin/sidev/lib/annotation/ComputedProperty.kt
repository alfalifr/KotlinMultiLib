package sidev.lib.annotation

/**
 * Property yg ditandai dg anotasi ini memiliki nilai yg dikomputasi setiap kali dipanggil.
 * Anotasi ini berbeda dg [TooExpensiveForBackingField], yaitu anotasi ini sudah jelas
 * bahwa property tidak punya backingField, sementara [TooExpensiveForBackingField] masih memiliki kemungkinan
 * untuk memiliki backingField.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY)
annotation class ComputedProperty