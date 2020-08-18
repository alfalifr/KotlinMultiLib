package sidev.lib.reflex.common

/**
 * Penanda bahwa suatu kelas merupakan komponen refleksi pada library ini.
 */
interface SiReflex{
    val descriptor: SiDescriptor
}

internal abstract class SiReflexImpl: SiReflex{
    override fun toString(): String = try{ descriptor.toString() } catch (e: Exception){ """descriptor milik "${this::class}" belum siap.""" }
}