package sidev.lib.annotation

/**
 * Penanda bahwa elemen yang dianotasi merupakan lemen yang belum stabil dan mudah berubah di masa depan.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class Experimental(val msg: String= "")