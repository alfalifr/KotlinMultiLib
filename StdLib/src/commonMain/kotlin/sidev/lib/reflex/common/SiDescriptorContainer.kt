package sidev.lib.reflex.common


/**
 * Turunan [SiReflex] yg punya [SiDescriptor].
 */
interface SiDescriptorContainer: SiReflex{
    val descriptor: SiDescriptor
}

internal abstract class SiDescriptorContainerImpl: SiReflexImpl(), SiDescriptorContainer{
    override fun toString(): String = try{ descriptor.toString() } catch (e: Exception){ """descriptor milik "${this::class}" belum siap.""" }
    override fun equals(other: Any?): Boolean {
//        val thisHash= hashCode()
//        val otherHash= other.hashCode()
//        prine("this= $this other= $other thisHash= $thisHash otherHash= $otherHash")
        return  hashCode() == other.hashCode()
    }
    override fun hashCode(): Int = descriptor.hashCode()
}