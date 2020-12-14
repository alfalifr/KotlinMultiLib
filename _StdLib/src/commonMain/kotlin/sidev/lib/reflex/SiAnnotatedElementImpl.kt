package sidev.lib.reflex


interface SiAnnotatedElementImpl: SiAnnotatedElement {
    override val annotations: MutableList<Annotation>
}