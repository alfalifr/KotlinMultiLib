package sidev.lib.annotation

import sidev.lib._config_.CodeModification

/**
 * Anotasi yg digunakan untuk memberikan info tambahan mengenai log pada source code.
 */
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class ChangeLog(val date: String, val log: String, val kind: CodeModification = CodeModification.MODIFIED)