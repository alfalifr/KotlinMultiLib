package sidev.lib.property

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Note
import sidev.lib.exception.IllegalAccessExc
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@ExperimentalStdlibApi
interface ControlledModProperty<in R, T>: ControlledProperty<R, T> {
    val getter: ((field: T) -> T)?
}

@ExperimentalStdlibApi
internal open class ControlledModPropertyImpl<in R, T>(
    owner: R?,
    field: T?,
    isGetterPrivate: Boolean,
    final override val getter: ((field: T) -> T)?,
): ControlledPropertyImpl<R, T>(owner, field, isGetterPrivate),
    ControlledModProperty<R, T> {
    constructor(
        isGetterPrivate: Boolean,
        getter: ((field: T) -> T)?
    ): this(null, null, isGetterPrivate, getter) {
        field= SI_UNINITIALIZED_VALUE
    }
    constructor(
        owner: R?,
        isGetterPrivate: Boolean,
        getter: ((field: T) -> T)?
    ): this(owner, null, isGetterPrivate, getter) {
        field= SI_UNINITIALIZED_VALUE
    }
    //protected var field: Any?= field

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        checkAccess(thisRef, property)
        checkGetValue(thisRef, property)

        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        return (field as T).let { getter?.invoke(it) ?: it }
    }
}