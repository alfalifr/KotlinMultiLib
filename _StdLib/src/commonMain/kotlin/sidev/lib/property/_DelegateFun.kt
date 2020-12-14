@file:ChangeLog("Rabu, 30 Sep 2020", "Penambahan custom setter dan getter")
package sidev.lib.property

import sidev.lib.annotation.ChangeLog
import sidev.lib.annotation.Note
import sidev.lib.structure.data.value.Var
import sidev.lib.structure.data.value.VarImpl


@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahawa Java.")
fun <T> lazy(
    getter: (() -> T)?= null,
    initializer: () -> T
): Lazy<T> = LazyImpl(initializer).apply {
    this.getter= getter
}

@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahawa Java.")
fun <T> mutableLazy(
    setter: ((value: T) -> Unit)?= null,
    getter: (() -> T)?= null,
    initializer: () -> T
): MutableLazy<Any?, T> = MutableLazyImpl<Any?, T>(initializer).apply{
    this.setter= setter
    this.getter= getter
}

@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahawa Java.")
fun <T> reevaluateLazy(
    getter: (() -> T)?= null,
    initializer: (evaluationBox: Var<Boolean>) -> T
): ReevaluateLazy<T> = ReevaluateLazyImpl(initializer).apply {
    this.getter= getter
}

@Note("Tidak di-anotasi `@JvmOverloads` karena tidak berguna dari bahawa Java.")
fun <T> reevaluateMutableLazy(
    setter: ((value: T) -> Unit)?= null,
    getter: (() -> T)?= null,
    initializer: (evaluationBox: Var<Boolean>) -> T
): ReevaluateMutableLazy<T> = ReevaluateMutableLazyImpl(initializer).apply {
    this.setter= setter
    this.getter= getter
}

/*
fun <T> getDelegateValue(delegate: Any, ref: Any?, prop: KProperty<*>): T = when(delegate){
    is Lazy<*> -> delegate.value as T
}
 */