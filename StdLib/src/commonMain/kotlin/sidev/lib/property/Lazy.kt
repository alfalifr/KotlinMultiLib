package sidev.lib.property

import sidev.lib.`val`.SuppressLiteral
import kotlin.reflect.KProperty

interface Lazy_<T>: Lazy<T>{
    var getter: (() -> T)?
}

internal open class LazyImpl<T>(init: () -> T): Lazy_<T>{
    private var init: (() -> T)? = init
    private var _value: Any? = SI_UNINITIALIZED_VALUE
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

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getter?.invoke() ?: value

    override fun isInitialized(): Boolean = _value !== SI_UNINITIALIZED_VALUE
    override fun toString(): String = if(isInitialized()) value.toString() else "MutableLazy belum siap."
}