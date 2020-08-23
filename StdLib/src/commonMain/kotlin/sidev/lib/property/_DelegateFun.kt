package sidev.lib.property

import sidev.lib.structure.data.value.Val
import kotlin.reflect.KProperty

fun <R, T> mutableLazy(initializer: () -> T): MutableLazy<R, T> = MutableLazyImpl(initializer)
fun <T> reevaluateLazy(initializer: (evaluationBox: Val<Boolean>) -> T): ReevaluateLazy<T> = ReevaluateLazyImpl(initializer)
fun <T> reevaluateMutableLazy(initializer: (evaluationBox: Val<Boolean>) -> T): ReevaluateMutableLazy<T> = ReevaluateMutableLazyImpl(initializer)

/*
fun <T> getDelegateValue(delegate: Any, ref: Any?, prop: KProperty<*>): T = when(delegate){
    is Lazy<*> -> delegate.value as T
}
 */