package sidev.lib.annotation

/** Penanda bahwa elemen yg di-anotasi dg anotasi ini merupakan element dg operasi yg belum didudkung. */
@Retention(AnnotationRetention.BINARY)
annotation class NotYetSupported(val msg: String= "", val until: String = "<unknown>")