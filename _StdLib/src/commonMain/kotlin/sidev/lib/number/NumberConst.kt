package sidev.lib.number

import sidev.lib.collection.findIndexed
import sidev.lib.exception.UnavailableOperationExc
import sidev.lib.reflex.isSubclassOf
import kotlin.reflect.KClass

/**
 * Objek yg berisi operasi untuk kelas turunan [Number] yang belum terdefinisi di modul [sidev.lib]
 */
object NumberConst {
    interface Operation<T: Number> {
        /**
         * Menentukan apakah [cls] merupakan turunan dari [T]
         */
        val thisNumberCls: KClass<T>
        fun isClassBelongToOperation(cls: KClass<out Number>): Boolean = cls.isSubclassOf(thisNumberCls)
        fun plus(n1: T, n2: Number): T
        fun minus(n1: T, n2: Number): T
        fun times(n1: T, n2: Number): T
        fun div(n1: T, n2: Number): T
        fun rem(n1: T, n2: Number): T
        fun compareTo(n1: T, n2: Number): Int
    }

    fun getUnavailableOperation(opName: String): (n1Cls: KClass<out Number>, n1: Number, n2: Number) -> Number =
        { n1Cls, n1, n2 ->
            throw UnavailableOperationExc(
                accessedElement = n1, detailMsg = "Operasi angka tidak diketahui, n1 $opName n2, `n1Cls`:\"$n1Cls\" `n1`:\"$n1\" `n2`:\"$n2\""
            ) }
    fun throwUnavailableOperation(opName: String, n1: Number, n2: Number): Nothing = throw UnavailableOperationExc(
        accessedElement = n1, detailMsg = "Operasi angka tidak diketahui, n1 $opName n2, `n1Cls`:\"${n1::class}\" `n1`:\"$n1\" `n2`:\"$n2\""
    )

    val operationDefList: List<Operation<Number>> = mutableListOf()
    fun <T: Number> addOperationDef(opDef: Operation<T>){
        (operationDefList as MutableList).add(opDef as Operation<Number>)
    }
    fun <T: Number> removeOperationDef(opDef: Operation<T>): Boolean =
        (operationDefList as MutableList).remove(opDef as Operation<Number>)
    fun <T: Number> removeOperationDef(receiverNumberCls: KClass<T>): Boolean =
        (operationDefList as MutableList).run {
            findIndexed { it.value.isClassBelongToOperation(receiverNumberCls) }?.let { this.removeAt(it.index) } != null
        }

    fun plus(n1Cls: KClass<out Number>, n1: Number, n2: Number): Number =
        operationDefList.find { it.isClassBelongToOperation(n1Cls) }?.run { plus(n1, n2) }
            ?: getUnavailableOperation("+")(n1Cls, n1, n2)

    fun minus(n1Cls: KClass<out Number>, n1: Number, n2: Number): Number =
        operationDefList.find { it.isClassBelongToOperation(n1Cls) }?.run { minus(n1, n2) }
            ?: getUnavailableOperation("-")(n1Cls, n1, n2)

    fun times(n1Cls: KClass<out Number>, n1: Number, n2: Number): Number =
        operationDefList.find { it.isClassBelongToOperation(n1Cls) }?.run { times(n1, n2) }
            ?: getUnavailableOperation("*")(n1Cls, n1, n2)

    fun div(n1Cls: KClass<out Number>, n1: Number, n2: Number): Number =
        operationDefList.find { it.isClassBelongToOperation(n1Cls) }?.run { div(n1, n2) }
            ?: getUnavailableOperation("/")(n1Cls, n1, n2)

    fun rem(n1Cls: KClass<out Number>, n1: Number, n2: Number): Number =
        operationDefList.find { it.isClassBelongToOperation(n1Cls) }?.run { rem(n1, n2) }
            ?: getUnavailableOperation("%")(n1Cls, n1, n2)

    fun compareTo(n1Cls: KClass<out Number>, n1: Number, n2: Number): Int =
        operationDefList.find { it.isClassBelongToOperation(n1Cls) }?.run { compareTo(n1, n2) }
            ?: getUnavailableOperation("%")(n1Cls, n1, n2).toInt()
}