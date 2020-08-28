package sidev.lib.reflex.comp.native


interface SiNativeProperty<out R>: SiNativeCallable<R> {
    /**
     * Fungsi yg digunakan untuk mendapatkan nilai dari property.
     * [receivers] dapat jumlahnya dapat bbrp tergantung definisi platform.
     */
    fun get(vararg receivers: Any): R
}
interface SiNativeMutableProperty<R>: SiNativeProperty<R> {
    /**
     * Fungsi yg digunakan untuk merubah nilai dari property.
     * [receivers] dapat jumlahnya dapat bbrp tergantung definisi platform.
     */
    fun set(vararg receivers: Any, value: R)
}

internal abstract class SiNativePropertyImpl<out R>(getBlock: (receivers: Array<out Any>) -> R)
    : SiNativeCallableImpl<R>(getBlock as (receivers: Array<out Any?>) -> R), SiNativeProperty<R>{
    override fun get(vararg receivers: Any): R = call(*receivers)
}

internal abstract class SiNativeMutablePropertyImpl<R>(
    getBlock: (receivers: Array<out Any>) -> R,
    protected val setBlock: (receivers: Array<out Any>, value: R) -> Unit
) : SiNativePropertyImpl<R>(getBlock), SiNativeMutableProperty<R>{
    override fun set(vararg receivers: Any, value: R) {
        validateArgs(*receivers, value)
        return setBlock(receivers, value)
    }
}