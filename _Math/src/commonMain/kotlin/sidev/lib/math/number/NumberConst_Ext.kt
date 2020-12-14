package sidev.lib.math.number

import sidev.lib.number.NumberConst
import kotlin.reflect.KClass

///TODO 15 Nov 2020

private object NumberConst_Ext {
    init {
        NumberConst.addOperationDef(object : NumberConst.Operation<WholeNumber>{
            override val thisNumberCls: KClass<WholeNumber> = WholeNumber::class

            override fun plus(n1: WholeNumber, n2: Number): WholeNumber = wholeNumber(when(n2){
                is Int -> n1 + n2
                is Long -> n1 + n2
                is FloatingNumber -> n1 + n2
                is Double -> n1 + n2
                is Float -> n1 + n2
                is Short -> n1 + n2
                is Byte -> n1 + n2
                else -> NumberConst.throwUnavailableOperation("+", n1, n2)
            })

            override fun minus(n1: WholeNumber, n2: Number): WholeNumber = wholeNumber(when(n2){
                is Int -> n1 - n2
                is Long -> n1 - n2
                is FloatingNumber -> n1 - n2
                is Double -> n1 - n2
                is Float -> n1 - n2
                is Short -> n1 - n2
                is Byte -> n1 - n2
                else -> NumberConst.throwUnavailableOperation("-", n1, n2)
            })

            override fun times(n1: WholeNumber, n2: Number): WholeNumber = wholeNumber(when(n2){
                is Int -> n1 * n2
                is Long -> n1 * n2
                is FloatingNumber -> n1 * n2
                is Double -> n1 * n2
                is Float -> n1 * n2
                is Short -> n1 * n2
                is Byte -> n1 * n2
                else -> NumberConst.throwUnavailableOperation("*", n1, n2)
            })

            override fun div(n1: WholeNumber, n2: Number): WholeNumber = wholeNumber(when(n2){
                is Int -> n1 / n2
                is Long -> n1 / n2
                is FloatingNumber -> n1 / n2
                is Double -> n1 / n2
                is Float -> n1 / n2
                is Short -> n1 / n2
                is Byte -> n1 / n2
                else -> NumberConst.throwUnavailableOperation("/", n1, n2)
            })

            override fun rem(n1: WholeNumber, n2: Number): WholeNumber = wholeNumber(when(n2){
                is Int -> n1 % n2
                is Long -> n1 % n2
                is FloatingNumber -> n1 % n2
                is Double -> n1 % n2
                is Float -> n1 % n2
                is Short -> n1 % n2
                is Byte -> n1 % n2
                else -> NumberConst.throwUnavailableOperation("%", n1, n2)
            })

            override fun compareTo(n1: WholeNumber, n2: Number): Int = when(n2){
                is Int -> n1.compareTo(n2)
                is Long -> n1.compareTo(n2)
                is FloatingNumber -> n1.compareTo(n2)
                is Double -> n1.compareTo(n2)
                is Float -> n1.compareTo(n2)
                is Short -> n1.compareTo(n2)
                is Byte -> n1.compareTo(n2)
                else -> NumberConst.throwUnavailableOperation("compareTo", n1, n2)
            }
        })

        NumberConst.addOperationDef(object : NumberConst.Operation<FloatingNumber>{
            override val thisNumberCls: KClass<FloatingNumber> = FloatingNumber::class

            override fun plus(n1: FloatingNumber, n2: Number): FloatingNumber = floatingNumber(when(n2){
                is Int -> n1 + n2
                is Long -> n1 + n2
                is FloatingNumber -> n1 + n2
                is Double -> n1 + n2
                is Float -> n1 + n2
                is Short -> n1 + n2
                is Byte -> n1 + n2
                else -> NumberConst.throwUnavailableOperation("+", n1, n2)
            })

            override fun minus(n1: FloatingNumber, n2: Number): FloatingNumber = floatingNumber(when(n2){
                is Int -> n1 - n2
                is Long -> n1 - n2
                is FloatingNumber -> n1 - n2
                is Double -> n1 - n2
                is Float -> n1 - n2
                is Short -> n1 - n2
                is Byte -> n1 - n2
                else -> NumberConst.throwUnavailableOperation("-", n1, n2)
            })

            override fun times(n1: FloatingNumber, n2: Number): FloatingNumber = floatingNumber(when(n2){
                is Int -> n1 * n2
                is Long -> n1 * n2
                is FloatingNumber -> n1 * n2
                is Double -> n1 * n2
                is Float -> n1 * n2
                is Short -> n1 * n2
                is Byte -> n1 * n2
                else -> NumberConst.throwUnavailableOperation("*", n1, n2)
            })

            override fun div(n1: FloatingNumber, n2: Number): FloatingNumber = floatingNumber(when(n2){
                is Int -> n1 / n2
                is Long -> n1 / n2
                is FloatingNumber -> n1 / n2
                is Double -> n1 / n2
                is Float -> n1 / n2
                is Short -> n1 / n2
                is Byte -> n1 / n2
                else -> NumberConst.throwUnavailableOperation("/", n1, n2)
            })

            override fun rem(n1: FloatingNumber, n2: Number): FloatingNumber = floatingNumber(when(n2){
                is Int -> n1 % n2
                is Long -> n1 % n2
                is FloatingNumber -> n1 % n2
                is Double -> n1 % n2
                is Float -> n1 % n2
                is Short -> n1 % n2
                is Byte -> n1 % n2
                else -> NumberConst.throwUnavailableOperation("%", n1, n2)
            })

            override fun compareTo(n1: FloatingNumber, n2: Number): Int = when(n2){
                is Int -> n1.compareTo(n2)
                is Long -> n1.compareTo(n2)
                is FloatingNumber -> n1.compareTo(n2)
                is Double -> n1.compareTo(n2)
                is Float -> n1.compareTo(n2)
                is Short -> n1.compareTo(n2)
                is Byte -> n1.compareTo(n2)
                else -> NumberConst.throwUnavailableOperation("compareTo", n1, n2)
            }
        })
    }
}