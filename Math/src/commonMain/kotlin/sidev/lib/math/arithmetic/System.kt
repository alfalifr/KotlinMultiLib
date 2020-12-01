package sidev.lib.math.arithmetic

/**
 * Interface yang berisi dari bbrp [SimpleEquation].
 */
interface System {
    val equations: List<SimpleEquation>
    fun solve(): List<SimpleEquation>
}