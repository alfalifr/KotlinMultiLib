package sidev.lib.annotation

/**
 * Penanda bahwa elemen yg di-anotasi adalah dummy.
 */
@Retention(AnnotationRetention.SOURCE)
annotation class Dummy(val msg: String = "")