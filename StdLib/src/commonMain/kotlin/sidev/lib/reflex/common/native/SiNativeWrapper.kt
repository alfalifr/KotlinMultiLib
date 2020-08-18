package sidev.lib.reflex.common.native

/**
 * Hanya sbg pembungkus native implementation dari reflexUnit agar extension function sesuai konteks.
 */
interface SiNativeWrapper {
    val implementation: Any
}