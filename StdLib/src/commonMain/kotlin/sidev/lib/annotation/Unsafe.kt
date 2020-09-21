package sidev.lib.annotation

/**
 * Anotasi yg digunakan untuk menandakan bahwa sesuatu yg di-anotasi adalah tidak aman.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class Unsafe(val reason: String = "")