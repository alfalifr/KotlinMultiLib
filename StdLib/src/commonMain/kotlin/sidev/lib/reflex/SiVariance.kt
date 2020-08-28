package sidev.lib.reflex

import sidev.lib.reflex.SiReflexImpl

//Agar dapat dijalankan di Js.
enum class SiVariance: SiReflex by SiReflexImpl() {
    /**
     * The affected type parameter or type is *invariant*, which means it has no variance applied to it.
     */
    INVARIANT,

    /**
     * The affected type parameter or type is *contravariant*. Denoted by the `in` modifier in the source code.
     */
    IN,

    /**
     * The affected type parameter or type is *covariant*. Denoted by the `out` modifier in the source code.
     */
    OUT,
}