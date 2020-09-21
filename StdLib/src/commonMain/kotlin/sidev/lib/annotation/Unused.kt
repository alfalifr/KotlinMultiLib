package sidev.lib.annotation

/** Penanda bahwa elemen yg di-anotasi tidak akan digunakan pada suatu implementasi operasi. */
@Retention(AnnotationRetention.RUNTIME)
annotation class Unused(val msg: String = "")