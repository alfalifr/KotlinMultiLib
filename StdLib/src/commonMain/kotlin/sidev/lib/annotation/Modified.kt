package sidev.lib.annotation

import sidev.lib._config_.CodeModification

/** Penanda bahwa elemen yg di-anotasi adalah elemen yg dimodifikasi dalam sebuah api standar library. */
@Retention(AnnotationRetention.RUNTIME)
annotation class Modified(
    val arg: String = "",
    val kind: CodeModification = CodeModification.MODIFIED,
    val date: String = "<unknown>",
    val author: String = "<nobody>"
)