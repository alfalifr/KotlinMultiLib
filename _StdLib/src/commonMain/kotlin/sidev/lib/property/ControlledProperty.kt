package sidev.lib.property

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Note
import sidev.lib.exception.IllegalAccessExc
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
interface ControlledProperty<in R, out T>: ReadOnlyProperty<R, T> {
    val isGetterPrivate: Boolean
}

@ExperimentalStdlibApi
internal open class ControlledPropertyImpl<in R, T>(
    private var owner: R?,
    field: T?,
    final override val isGetterPrivate: Boolean,
): ControlledProperty<R, T> {
    constructor(isGetterPrivate: Boolean): this(null, null, isGetterPrivate) {
        field= SI_UNINITIALIZED_VALUE
    }
    constructor(owner: R?, isGetterPrivate: Boolean): this(owner, null, isGetterPrivate) {
        field= SI_UNINITIALIZED_VALUE
    }
    protected var field: Any?= field

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        checkAccess(thisRef, property)
        checkGetValue(thisRef, property)
        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        return field as T
    }

    protected fun checkAccess(accessor: R, property: KProperty<*>) {
        if(isGetterPrivate && owner != null && owner !== accessor) throw IllegalAccessExc(
            relatedClass = accessor!!::class,
            msg = "Property `${property.name}` private, namun diakses dari luar instance."
        )
        if(owner == null) // Anggapannya gak mungkin null accessor nya
            owner= accessor!!
    }
    protected fun checkGetValue(accessor: R, property: KProperty<*>){
        if(field === SI_UNINITIALIZED_VALUE) throw IllegalAccessExc(
            relatedClass = accessor!!::class,
            msg = "Property `${property.name}` msh blum memiliki nilai"
        )
    }
}