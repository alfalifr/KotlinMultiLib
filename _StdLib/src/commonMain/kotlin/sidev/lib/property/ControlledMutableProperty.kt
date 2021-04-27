package sidev.lib.property

import sidev.lib.annotation.Note
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
interface ControlledMutableProperty<in R, T>
    : ControlledProperty<R, T>, ReadWriteProperty<R, T> {
    val isSetterPrivate: Boolean
}

@ExperimentalStdlibApi
internal open class ControlledMutablePropertyImpl<in R, T>(
    owner: R?,
    field: T?,
    isGetterPrivate: Boolean,
    final override val isSetterPrivate: Boolean,
): ControlledPropertyImpl<R, T>(
    owner,
    field,
    isGetterPrivate
), ControlledMutableProperty<R, T> {
    constructor(
        isGetterPrivate: Boolean,
        isSetterPrivate: Boolean,
    ): this(null, null, isGetterPrivate, isSetterPrivate) {
        field= SI_UNINITIALIZED_VALUE
    }
    constructor(
        owner: R?,
        isGetterPrivate: Boolean,
        isSetterPrivate: Boolean,
    ): this(owner, null, isGetterPrivate, isSetterPrivate) {
        field= SI_UNINITIALIZED_VALUE
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        checkAccess(thisRef, property)
        field= value
    }
}