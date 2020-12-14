package sidev.lib.annotation

/**
 * Menandakan bahwa yg di-anotasi membutuhkan [specs].
 */
annotation class Requires(vararg val specs: String)