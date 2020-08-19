package sidev.lib.property

import sidev.lib.universal.`val`.SuppressLiteral
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface MutableLazy<in R, T>: ReadWriteProperty<R, T>, Lazy<T>

internal open class MutableLazyImpl<in R, T>(init: () -> T): MutableLazy<R, T>{
    private var init: (() -> T)? = init
    private var _value: Any? = UNINITIALIZED_VALUE

    override var value: T
        set(v){ _value = v }
        get() {
            val v1= _value
            if(v1 !== UNINITIALIZED_VALUE)
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                return v1 as T

            val res= init!!()
            _value= res
            init= null
            return res
        }

    override fun getValue(thisRef: R, property: KProperty<*>): T = value
    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this.value= value
    }

    override fun isInitialized(): Boolean = _value !== UNINITIALIZED_VALUE
    override fun toString(): String = if(isInitialized()) value.toString() else "MutableLazy belum siap."
}