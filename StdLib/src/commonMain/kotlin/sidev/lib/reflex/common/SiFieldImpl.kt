package sidev.lib.reflex.common


@ExperimentalStdlibApi
internal abstract class SiFieldImpl<in R, out T>(private val getBlock: (R) -> T): SiReflexImpl(), SiField<R, T>{
    override fun get(receiver: R): T = getBlock(receiver)
}

@ExperimentalStdlibApi
internal abstract class SiMutableFieldImpl<in R, T>(getBlock: (R) -> T, private val setBlock: (receiver: R, value: T) -> Unit)
    : SiFieldImpl<R, T>(getBlock), SiMutableField<R, T>{
    override fun set(receiver: R, value: T) = setBlock(receiver, value)
}