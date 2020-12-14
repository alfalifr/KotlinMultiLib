package sidev.lib.reflex

internal abstract class SiParamterImpl: SiDescriptorContainerImpl(), SiParameter, SiAnnotatedElementImpl {
    override val annotations: MutableList<Annotation> = arrayListOf()
//    abstract override var type: SiType
}