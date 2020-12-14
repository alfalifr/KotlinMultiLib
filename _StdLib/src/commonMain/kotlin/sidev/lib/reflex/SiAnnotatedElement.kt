package sidev.lib.reflex

/**
 * Komponen [SiReflex] yang dapat dianotasi.
 */
interface SiAnnotatedElement: SiReflex {
    val annotations: List<Annotation>
}