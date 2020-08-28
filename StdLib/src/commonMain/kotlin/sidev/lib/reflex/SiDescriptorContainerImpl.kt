package sidev.lib.reflex

internal abstract class SiDescriptorContainerImpl: SiReflexImpl(),
    SiDescriptorContainer {
    override fun toString(): String = try{
//        prine("SiDescriptorContainerImpl toString() str= ${str(descriptor)} this class= ${this::class}")
//        prine("SiDescriptorContainerImpl toString() ${if(this is SiClass<*>) qualifiedName else "something else"} desc= ${str(descriptor)}")
        descriptor.toString() }
    catch (e: Throwable){ """descriptor milik "${this::class}" belum siap.""" }
    override fun equals(other: Any?): Boolean {
//        val thisHash= hashCode()
//        val otherHash= other.hashCode()
//        prine("this= $this other= $other thisHash= $thisHash otherHash= $otherHash")
        return  hashCode() == other.hashCode()
    }
    override fun hashCode(): Int = descriptor.hashCode()
}