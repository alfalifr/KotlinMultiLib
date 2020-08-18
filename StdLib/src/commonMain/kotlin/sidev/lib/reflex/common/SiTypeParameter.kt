package sidev.lib.reflex.common


interface SiTypeParameter: SiClassifier /*: KTypeParameter -> agar dapat dijalankan dari Js*/{

    /**
     * The name of this type parameter as it was declared in the source code.
     */
    val name: String

    /**
     * Upper bounds, or generic constraints imposed on this type parameter.
     * See the [Kotlin language documentation](https://kotlinlang.org/docs/reference/generics.html#upper-bounds)
     * for more information.
     */
    val upperBounds: List<SiType>

    /**
     * Declaration-site variance of this type parameter.
     * See the [Kotlin language documentation](https://kotlinlang.org/docs/reference/generics.html#declaration-site-variance)
     * for more information.
     */
    val variance: SiVariance
}

internal abstract class SiTypeParameterImpl: SiReflexImpl(), SiTypeParameter