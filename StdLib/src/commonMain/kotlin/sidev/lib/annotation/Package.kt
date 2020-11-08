package sidev.lib.annotation

import sidev.lib.reflex.SiAnnotation

/**
 * Untuk memberikan informasi tambahan tentang paket dari suatu kode terkait [name], [title], [desc], [vendor], dan [author].
 * Anotasi ini berguna bagi platform yang tidak menyediakan informasi ttg paket pada runtime, seperti JavaScript dan native.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Package(
    val name: String, val title: String= "", val desc: String= "",
    val vendor: String= "", val author: String= ""
)

data class SiPackage(
    val name: String, val title: String= "", val desc: String= "",
    val vendor: String= "", val author: String= ""
): SiAnnotation