package sidev.lib.structure.data.value

interface NullableVal<T>: Val<T?>

internal open class NullableValImpl<T>(value: T?): ValImpl<T?>(value), NullableVal<T> {
    override fun toString(): String = "Val?($value)"
}