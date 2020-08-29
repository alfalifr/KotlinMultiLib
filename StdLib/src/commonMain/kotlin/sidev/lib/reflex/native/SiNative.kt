package sidev.lib.reflex.native

import sidev.lib.reflex.SiReflex

/**
 * Hanya sbg pembungkus native implementation dari reflexUnit agar extension function sesuai konteks.
 */
interface SiNative: SiReflex {
    val implementation: Any
}