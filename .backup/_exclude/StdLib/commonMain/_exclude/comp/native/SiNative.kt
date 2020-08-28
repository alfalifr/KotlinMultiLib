package sidev.lib.reflex.comp.native

import sidev.lib.reflex.comp.SiDescriptorContainer
import sidev.lib.reflex.comp.SiDescriptorContainerImpl

/**
 * Pembungkus komponen implementasi native pada tiap platform.
 * Tidak berupa
 */
interface SiNative : SiDescriptorContainer {
    val implementation: Any
    val name: String
}

internal abstract class SiNativeImpl: SiDescriptorContainerImpl(), SiNative