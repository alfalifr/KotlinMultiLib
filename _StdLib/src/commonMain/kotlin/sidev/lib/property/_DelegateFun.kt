@file:ChangeLog("Rabu, 30 Sep 2020", "Penambahan custom setter dan getter")
package sidev.lib.property

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.ChangeLog
import sidev.lib.annotation.Note
import sidev.lib.exception.IllegalAccessExc
import sidev.lib.reflex.clazz
import sidev.lib.structure.data.value.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahasa Java.")
fun <T> lazy(
    getter: (() -> T)?= null,
    initializer: () -> T
): Lazy<T> = LazyImpl(initializer).apply {
    this.getter= getter
}

@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahasa Java.")
fun <T> mutableLazy(
    setter: ((value: T) -> Unit)?= null,
    getter: (() -> T)?= null,
    initializer: () -> T
): MutableLazy<Any?, T> = MutableLazyImpl<Any?, T>(initializer).apply{
    this.setter= setter
    this.getter= getter
}

@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahasa Java.")
fun <T> reevaluateLazy(
    getter: (() -> T)?= null,
    initializer: (evaluationBox: Var<Boolean>) -> T
): ReevaluateLazy<T> = ReevaluateLazyImpl(initializer).apply {
    this.getter= getter
}

@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahasa Java.")
fun <T> reevaluateMutableLazy(
    setter: ((value: T) -> Unit)?= null,
    getter: (() -> T)?= null,
    initializer: (evaluationBox: Var<Boolean>) -> T
): ReevaluateMutableLazy<T> = ReevaluateMutableLazyImpl(initializer).apply {
    this.setter= setter
    this.getter= getter
}

/**
 * Delegate `var` yang hanya dapat di-init 1x. Tidak bisa di-assign lagi.
 * [setterPrecondition] return `true` maka fungsi [setValue] akan throw [IllegalAccessExc].
 * [mod] merupakan jumlah modifikasi yg dilakukan terhadap property ini.
 * [mod] akan bertambah jika [setValue] dipanggil dan berhasil (tidak mengahsilkan IllegalAccessExc).
 */
fun <T> oneInitVar(
    setter: ((field: WriteVal<T>, value: T) -> Unit)? = null,
    getter: ((field: Val<T>) -> T)? = null,
    setterPrecondition: ((mod: Int, value: T) -> Boolean)? = null,
): ReadWriteProperty<Any?, T> = object: ReadWriteProperty<Any?, T> {
    private var field: Any?= SI_UNINITIALIZED_VALUE
    private var backingField: NullableVar<T>?= null
    private var mod= 0
    //private val setter: ((field: WriteVal<T>, value: T) -> Unit)?= setter
    //private val getter: ((field: Val<T>) -> T)?= getter

    init {
        if(getter != null || setter != null)
            backingField= nullableVarOf()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if((setterPrecondition == null && field !== SI_UNINITIALIZED_VALUE)
            || setterPrecondition?.invoke(mod +1, value) == false
        ) throw IllegalAccessExc(
            thisRef?.clazz,
            "Property `${property.name}` hanya boleh di-assign 1x."
        )
        setter?.also {
            val bf= backingField!!
            @Suppress(SuppressLiteral.UNCHECKED_CAST)
            it(bf as WriteVal<T>, value)
            field= bf.value
        } ?: { field= value }()
        mod++
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if(field === SI_UNINITIALIZED_VALUE) throw IllegalAccessExc(
            thisRef?.clazz,
            "Property `${property.name}` blum di-init."
        )
        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        return getter?.invoke(backingField as Val<T>) ?: field as T
    }
}


@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.vall(
    initField: T?,
    isGetterPrivate: Boolean = false,
): ControlledProperty<R, T> = ControlledPropertyImpl(this, initField, isGetterPrivate)

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.vall(
    isGetterPrivate: Boolean = false,
): ControlledProperty<R, T> = ControlledPropertyImpl(this, isGetterPrivate)

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.vall(
    initField: T?,
    isGetterPrivate: Boolean = false,
    getter: ((field: T) -> T)?,
): ControlledModProperty<R, T> = ControlledModPropertyImpl(this, initField, isGetterPrivate, getter)

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.vall(
    isGetterPrivate: Boolean = false,
    getter: ((field: T) -> T)?,
): ControlledModProperty<R, T> = ControlledModPropertyImpl(this, isGetterPrivate, getter)


@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.varr(
    initField: T?,
    isGetterPrivate: Boolean = false,
    isSetterPrivate: Boolean = false,
): ControlledMutableProperty<R, T> = ControlledMutablePropertyImpl(this, initField, isGetterPrivate, isSetterPrivate)

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.varr(
    isGetterPrivate: Boolean = false,
    isSetterPrivate: Boolean = false,
): ControlledMutableProperty<R, T> = ControlledMutablePropertyImpl(this, isGetterPrivate, isSetterPrivate)

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.varr(
    initField: T?,
    isGetterPrivate: Boolean = false,
    isSetterPrivate: Boolean = false,
    setter: ((field: WriteVal<T>, value: T) -> Unit)? = null,
    getter: ((field: T) -> T)?,
): ControlledModMutableProperty<R, T> = ControlledModMutablePropertyImpl(
    this, initField, isGetterPrivate, isSetterPrivate, getter, setter
)

@ExperimentalStdlibApi
@Note("Access modifier belum punya efek.")
fun <R, T> R.varr(
    isGetterPrivate: Boolean = false,
    isSetterPrivate: Boolean = false,
    setter: ((field: WriteVal<T>, value: T) -> Unit)? = null,
    getter: ((field: T) -> T)?,
): ControlledModMutableProperty<R, T> = ControlledModMutablePropertyImpl(
    this, isGetterPrivate, isSetterPrivate, getter, setter
)

/*
fun <T> getDelegateValue(delegate: Any, ref: Any?, prop: KProperty<*>): T = when(delegate){
    is Lazy<*> -> delegate.value as T
}
 */