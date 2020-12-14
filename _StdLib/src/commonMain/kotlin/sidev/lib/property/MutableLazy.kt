package sidev.lib.property

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.ChangeLog
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@ChangeLog("Rabu, 30 Sep 2020", "Penambahan custom setter dan getter")
interface MutableLazy<in R, T>: ReadWriteProperty<R, T>, Lazy<T>{
    var setter: ((value: T) -> Unit)?
    var getter: (() -> T)?
}

internal open class MutableLazyImpl<in R, T>(init: () -> T): MutableLazy<R, T>{
    private var init: (() -> T)? = init
    private var _value: Any? = SI_UNINITIALIZED_VALUE
    override var setter: ((value: T) -> Unit)? = null
    override var getter: (() -> T)? = null

    override var value: T
        set(v){ _value = v }
        get() {
            val v1= _value
            if(v1 !== SI_UNINITIALIZED_VALUE)
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                return v1 as T

            val res= init!!()
            _value= res
            init= null
            return res
        }

    override fun getValue(thisRef: R, property: KProperty<*>): T = getter?.invoke() ?: value
    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        setter?.invoke(value) ?: run { this.value= value }
    }

    override fun isInitialized(): Boolean = _value !== SI_UNINITIALIZED_VALUE
    override fun toString(): String = if(isInitialized()) value.toString() else "MutableLazy belum siap."
}