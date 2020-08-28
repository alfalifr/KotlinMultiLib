package sidev.lib.reflex


/**
 * Turunan [SiReflex] yg punya [SiDescriptor].
 */
interface SiDescriptorContainer: SiReflex {
    val descriptor: SiDescriptor
}