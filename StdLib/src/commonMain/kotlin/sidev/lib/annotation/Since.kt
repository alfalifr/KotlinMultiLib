package sidev.lib.annotation

/** Penanda bahwa elemen yg di-anotasi sudah ada sejak [version]. */
@Retention(AnnotationRetention.RUNTIME)
annotation class Since(val version: String, val author: String = "<nobody>")