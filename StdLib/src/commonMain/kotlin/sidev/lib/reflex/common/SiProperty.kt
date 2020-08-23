package sidev.lib.reflex.common


interface SiProperty<out R>: SiCallable<R> {
    val getter: Getter<R>
//    val hasBackingField: Boolean
    val backingField: SiField<*, R>?

    /** Call dari property sama dg call getter-nya. */
    override fun call(vararg args: Any?): R = getter.call(*args)

    interface Accessor<out R>{
        /** Property tempat [Accessor] ini menempel. */
        val property: SiProperty<R>
    }

    interface Getter<out R>: Accessor<R>, SiCallable<R>
}
interface SiMutableProperty<R>: SiProperty<R> {
    val setter: Setter<R>
    override val backingField: SiMutableField<*, R>?

    interface Setter<R>: SiProperty.Accessor<R>, SiCallable<Unit>
}

interface SiProperty1<T, out R>: SiProperty<R>/*, (T) -> R karena dilarang di Js*/{
    fun get(receiver: T): R
    override val getter: Getter<T, R>
    override val backingField: SiField<T, R>?

    interface Getter<T, out R>:
        SiProperty.Getter<R>/*, (T) -> R karena dilarang di Js*/
}
interface SiMutableProperty1<T, R>: SiMutableProperty<R>,
    SiProperty1<T, R> {
    fun set(receiver: T, value: R)
    override val setter: Setter<T, R>
    override val backingField: SiMutableField<T, R>?

    interface Setter<T, R>:
        SiMutableProperty.Setter<R>/*, (T, R) -> Unit karena dilarang di Js*/
}
