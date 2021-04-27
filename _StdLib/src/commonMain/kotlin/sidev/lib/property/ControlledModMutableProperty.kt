package sidev.lib.property

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.structure.data.value.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@ExperimentalStdlibApi
interface ControlledModMutableProperty<in R, T>
    : ControlledModProperty<R, T>, ControlledMutableProperty<R, T> {
    val setter: ((field: WriteVal<T>, value: T) -> Unit)?
}

@ExperimentalStdlibApi
internal open class ControlledModMutablePropertyImpl<in R, T>(
    owner: R?,
    field: T?,
    isGetterPrivate: Boolean,
    final override val isSetterPrivate: Boolean,
    getter: ((T) -> T)?,
    final override val setter: ((field: WriteVal<T>, value: T) -> Unit)?,
): ControlledModPropertyImpl<R, T>(owner, field, isGetterPrivate, getter),
    ControlledModMutableProperty<R, T> {
    constructor(
        isGetterPrivate: Boolean,
        isSetterPrivate: Boolean,
        getter: ((T) -> T)?,
        setter: ((field: WriteVal<T>, value: T) -> Unit)?,
    ): this(null, null, isGetterPrivate, isSetterPrivate, getter, setter) {
        field= SI_UNINITIALIZED_VALUE
    }
    constructor(
        owner: R?,
        isGetterPrivate: Boolean,
        isSetterPrivate: Boolean,
        getter: ((T) -> T)?,
        setter: ((field: WriteVal<T>, value: T) -> Unit)?,
    ): this(owner, null, isGetterPrivate, isSetterPrivate, getter, setter) {
        field= SI_UNINITIALIZED_VALUE
    }

    private var backingField: NullableVar<T>?= null
    init {
        if(setter != null)
            backingField= nullableVarOf()
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        checkAccess(thisRef, property)
        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        setter?.also {
            val bf= backingField
            it.invoke(bf as WriteVal<T>, value)
            field= bf.value
        } ?: { field= value }()
    }
}