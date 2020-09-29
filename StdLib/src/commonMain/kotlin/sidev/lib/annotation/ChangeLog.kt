package sidev.lib.annotation

import sidev.lib._config_.CodeModification

/**
 * Anotasi yg digunakan untuk memberikan info tambahan mengenai log pada source code.
 */
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
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.FILE,
    AnnotationTarget.TYPEALIAS
)
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class ChangeLog(val date: String, val log: String, val kind: CodeModification = CodeModification.MODIFIED)