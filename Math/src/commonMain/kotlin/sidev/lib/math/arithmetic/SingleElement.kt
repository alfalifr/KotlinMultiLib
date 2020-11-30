package sidev.lib.math.arithmetic

interface SingleElement<T: Number> : Calculable {
    val numberComponent: T
}