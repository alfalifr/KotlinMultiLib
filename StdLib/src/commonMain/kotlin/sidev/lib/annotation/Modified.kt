package sidev.lib.annotation

/** Penanda bahwa elemen yg di-anotasi adalah elemen yg dimodifikasi dalam sebuah api standar library. */
@Retention(AnnotationRetention.RUNTIME)
annotation class Modified(val arg: String = "", val date: String = "<unknown>", val author: String = "<nobody>")