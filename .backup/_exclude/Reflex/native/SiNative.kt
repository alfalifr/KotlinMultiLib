package sidev.lib.reflex.native

import sidev.lib.reflex.SiDescriptorContainer
import sidev.lib.reflex.SiDescriptorContainerImpl

/**
 * Pembungkus komponen implementasi native pada tiap platform.
 * Tidak berupa
 */
interface SiNative : SiDescriptorContainer {
    val implementation: Any
    val name: String
}

internal abstract class SiNativeImpl: SiDescriptorContainerImpl(), SiNative