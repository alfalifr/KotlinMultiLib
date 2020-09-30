package sidev.lib.property

import sidev.lib.structure.data.value.Val
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.check.isNull
import sidev.lib.check.notNull
import kotlin.reflect.KProperty

/**
 * Delegasi lazy yg dapat mere-evaluasi dan menjalankan lagi blok [init] selama
 * [isEvaluationDone] == false
 */
interface ReevaluateMutableLazy<T> : ReevaluateLazy<T>, MutableLazy<Any?, T>

/**
 * [init] memiliki parameter [evaluationBox: Val<Boolean>] yg isinya dapat diubah dan digunakan
 * sbg evaluasi untuk mengubah [isEvaluationDone].
 */
internal open class ReevaluateMutableLazyImpl<T>(init: (evaluationBox: Val<Boolean>) -> T) : ReevaluateMutableLazy<T>{
    private var init: ((evaluationBox: Val<Boolean>) -> T)? = init
    private var _value: Any? = SI_UNINITIALIZED_VALUE
    override var isEvaluationDone: Boolean = true
    override var setter: ((value: T) -> Unit)?= null
    override var getter: (() -> T)?= null

    override var value: T
        set(v){ _value= v }
        get() {
            val v1= _value
            if(isEvaluationDone && v1 !== SI_UNINITIALIZED_VALUE){
                init= null
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                return v1 as T
            }

            val evaluationBox= Val(false)
            val res= init!!(evaluationBox)
            _value= res
//            init= null
            isEvaluationDone= evaluationBox.value!!
            return res
        }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = getter?.invoke() ?: value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setter?.invoke(value) ?: run { this.value= value }
    }

    override fun isInitialized(): Boolean = isEvaluationDone
    override fun toString(): String = if(isInitialized()) value.toString() else "ReevaluateMutableLazy belum siap."
}