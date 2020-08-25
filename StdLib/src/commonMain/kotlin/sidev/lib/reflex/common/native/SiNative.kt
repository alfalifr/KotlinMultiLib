package sidev.lib.reflex.common.native

import sidev.lib.reflex.common.SiDescriptorContainer
import sidev.lib.reflex.common.SiDescriptorContainerImpl

/**
 * Pembungkus komponen implementasi native pada tiap platform.
 * Tidak berupa
 */
interface SiNative : SiDescriptorContainer {
    val implementation: Any
    val name: String
}

internal abstract class SiNativeImpl: SiDescriptorContainerImpl(), SiNative