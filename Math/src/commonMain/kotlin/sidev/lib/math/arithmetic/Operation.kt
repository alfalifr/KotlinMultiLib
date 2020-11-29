package sidev.lib.math.arithmetic

import sidev.lib.exception.IllegalArgExc
import sidev.lib.exception.NotYetSupportedExc
import sidev.lib.number.*
import kotlin.jvm.JvmStatic

/**
 * Operasi matematika dasar.
 * Semakin tinggi [level], semakin besar prioritas untuk didulukan operasi dalam ekuasi.
 * [opFun] adalah fungsi operasi antar 2 angka.
 * [symbol] adalah simbol yang digunakan dalam ekuasi matematika.
 */
enum class Operation(
    val level: Int,
    val opFun: (Number, Number) -> Number,
    val calcOpFun: (Calculable, Calculable) -> Calculable,
    vararg val symbol: Char
): Operationable<Number> {
    PLUS(1, { n1, n2 -> n1 + n2 }, { n1, n2 -> n1 + n2 },'+'),
    MINUS(1, { n1, n2 -> n1 - n2 }, { n1, n2 -> n1 - n2 }, '-'),
    TIMES(2, { n1, n2 -> n1 * n2 }, { n1, n2 -> n1 * n2 }, '*'),
    DIVIDES(2, { n1, n2 -> n1 / n2 }, { n1, n2 -> n1 / n2 }, '/'),
    MODULO(3, { n1, n2 -> n1 % n2 }, { n1, n2 -> n1 % n2 }, '%'),
    POWER(4, { n1, n2 -> n1 powCast n2 }, { n1, n2 -> n1 pow n2 }, '^'),
    ROOT(4, { n1, n2 -> n1 rootCast n2 }, { n1, n2 -> n1 root n2 }, '~');

    companion object {
        @JvmStatic
        fun from(char: Char): Operation = when(char){
            '+' -> PLUS
            '-' -> MINUS
            '*' -> TIMES
            '/' -> DIVIDES
            '%' -> MODULO
            '^' -> POWER
            '~' -> ROOT
            else -> throw IllegalArgExc(
                paramExcepted = arrayOf("char"),
                detailMsg = "Char operator \"$char\" tidak diketahui dalam matematika."
            )
        }
    }
    override fun doOperation(n1: Number, n2: Number): Number = opFun(n1, n2)
    fun doOperation(element1: Calculable, element2: Calculable): Calculable = calcOpFun(element1, element2)

    operator fun invoke(element1: Calculable, element2: Calculable): Calculable = doOperation(element1, element2)

    val opposite: Operation get()= when(this) {
        PLUS -> MINUS
        MINUS -> PLUS
        TIMES -> DIVIDES
        DIVIDES -> TIMES
        POWER -> ROOT
        ROOT -> POWER
        else -> throw NotYetSupportedExc(accessedElement = this, detailMsg = "Operasi $this belum didefinisikan `opposite`-nya.")
    }
    override fun toString(): String = symbol.first().toString()
}