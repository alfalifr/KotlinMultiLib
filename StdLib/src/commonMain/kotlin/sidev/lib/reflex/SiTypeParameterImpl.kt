package sidev.lib.reflex

internal abstract class SiTypeParameterImpl: SiDescriptorContainerImpl(),
    SiTypeParameter {
    abstract override var upperBounds: List<SiType>
}