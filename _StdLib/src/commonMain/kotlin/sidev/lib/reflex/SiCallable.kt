package sidev.lib.reflex


interface SiCallable<out R>: SiDescriptorContainer, SiAnnotatedElement {
    val name: String
    val returnType: SiType
    val parameters: List<SiParameter>
    val typeParameters: List<SiTypeParameter>
    /** Visibilitas akses dari komponen SiReflex ini, default public. */
    val visibility: SiVisibility
    val isAbstract: Boolean
    fun call(vararg args: Any?): R
    fun callBy(args: Map<SiParameter, Any?>): R
}