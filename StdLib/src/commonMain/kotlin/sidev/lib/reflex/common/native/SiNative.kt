package sidev.lib.reflex.common.native

import sidev.lib.reflex.common.SiParameter
import sidev.lib.reflex.common.SiReflex
import sidev.lib.reflex.common.SiReflexImpl

/**
 * Pembungkus komponen implementasi native pada tiap platform.
 * Tidak berupa
 */
interface SiNative : SiReflex {
    val implementation: Any
    val name: String
}

internal abstract class SiNativeImpl: SiReflexImpl(), SiNative