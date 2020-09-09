package sidev.lib.reflex

internal abstract class SiClassImpl<T: Any>: SiDescriptorContainerImpl(), SiClass<T>, SiAnnotatedElementImpl{
    override val annotations: MutableList<Annotation> = arrayListOf()
    abstract override var members: Collection<SiCallable<*>>
    abstract override var constructors: List<SiFunction<T>>
//    abstract override var typeParameters: List<SiTypeParameter>
//    abstract override var supertypes: List<SiType>
}