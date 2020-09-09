package sidev.lib.reflex


internal abstract class SiFieldImpl<in R, out T>(private val getBlock: (R) -> T):
    SiDescriptorContainerImpl(), SiField<R, T>, SiAnnotatedElementImpl {
    override fun get(receiver: R): T = getBlock(receiver)
}


internal abstract class SiMutableFieldImpl<in R, T>(getBlock: (R) -> T, private val setBlock: (receiver: R, value: T) -> Unit)
    : SiFieldImpl<R, T>(getBlock), SiMutableField<R, T> {
    override fun set(receiver: R, value: T) = setBlock(receiver, value)
}