package sidev.lib.reflex

internal abstract class SiTypeImpl: SiDescriptorContainerImpl(), SiType {
    abstract override var arguments: List<SiTypeProjection>
}