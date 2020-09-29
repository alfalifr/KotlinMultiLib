package sidev.lib.annotation

import sidev.lib._config_.CodeModification

/** Penanda bahwa elemen yg di-anotasi adalah elemen yg dimodifikasi dalam sebuah api standar library. */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.TYPE,
//    AnnotationTarget.EXPRESSION,
    AnnotationTarget.FILE,
    AnnotationTarget.TYPEALIAS
)
@Retention(AnnotationRetention.RUNTIME)
annotation class Modified(
    val arg: String = "",
    val kind: CodeModification = CodeModification.MODIFIED,
    val date: String = "<unknown>",
    val author: String = "<nobody>"
)