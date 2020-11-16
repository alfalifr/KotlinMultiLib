package sidev.lib.math.number

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.reflex.clazz
import sidev.lib.exception.UndefinedDeclarationExc
///TODO 15 Nov 2020

/*
========================
Operator Overriding
========================
 */
//operator fun <T: Number> T.plus(other: T): T = this + other
/**
 * Bentuk generic dari plus() yg inputnya berupa [Number].
 * @return angka hasil operasi,
 *   dan angka `this.extension` ini sendiri jika tipe pasti [other] tidak diketahui.
 */
infix operator fun <T: Number> T.plus(other: Number): T{
    @Suppress(SuppressLiteral.UNCHECKED_CAST) //Kotlin dapat meng-cast sendiri tipe data number.
    return when(this){
        is Int -> this + other
        is Long -> this + other
        is Float -> this + other
        is Double -> this + other
        is Byte -> this + other
        is Short -> this + other
        else -> this
    } as T
}
infix operator fun <T: Number> T.minus(other: Number): T{
    @Suppress(SuppressLiteral.UNCHECKED_CAST) //Kotlin dapat meng-cast sendiri tipe data number.
    return when(this){
        is Int -> this - other
        is Long -> this - other
        is Float -> this - other
        is Double -> this - other
        is Byte -> this - other
        is Short -> this - other
        else -> this
    } as T
}
infix operator fun <T: Number> T.times(other: Number): T{
    @Suppress(SuppressLiteral.UNCHECKED_CAST) //Kotlin dapat meng-cast sendiri tipe data number.
    return when(this){
        is Int -> this * other
        is Long -> this * other
        is Float -> this * other
        is Double -> this * other
        is Byte -> this * other
        is Short -> this * other
        else -> this
    } as T
}
infix operator fun <T: Number> T.div(other: Number): T{
    @Suppress(SuppressLiteral.UNCHECKED_CAST) //Kotlin dapat meng-cast sendiri tipe data number.
    return when(this){
        is Int -> this / other
        is Long -> this / other
        is Float -> this / other
        is Double -> this / other
        is Byte -> this / other
        is Short -> this / other
        else -> this
    } as T
}
infix operator fun <T: Number> T.rem(other: Number): T{
    @Suppress(SuppressLiteral.UNCHECKED_CAST) //Kotlin dapat meng-cast sendiri tipe data number.
    return when(this){
        is Int -> this % other
        is Long -> this % other
        is Float -> this % other
        is Double -> this % other
        is Byte -> this % other
        is Short -> this % other
        else -> this
    } as T
}
infix operator fun Number.compareTo(other: Number): Int{
    return when(this){
        is Int -> this.compareTo(other)
        is Long -> this.compareTo(other)
        is Float -> this.compareTo(other)
        is Double -> this.compareTo(other)
        is Byte -> this.compareTo(other)
        is Short -> this.compareTo(other)
        else -> 0
    }
}

operator fun <T: Number> T.inc(): T = this +1
operator fun <T: Number> T.dec(): T = this -1
operator fun <T: Number> T.unaryPlus(): T = this
operator fun <T: Number> T.unaryMinus(): T = this * -1



//=========compareTo interType============
infix operator fun Number.compareTo(other: String): Int = this.toString().compareTo(other)
infix operator fun String.compareTo(other: Number): Int = this.compareTo(other.toString())
infix operator fun Number.compareTo(other: Char): Int = this.toChar().compareTo(other)
infix operator fun Char.compareTo(other: Number): Int = this.compareTo(other.toChar())
infix operator fun String.compareTo(other: Char): Int = this.compareTo(other.toString())
infix operator fun Char.compareTo(other: String): Int = this.toString().compareTo(other)

infix operator fun <T1, T2> Comparable<T1>.compareTo(other: T2): Int
        = when(this){
            is Number -> when(other){
                is Number -> compareTo(other)
                is String -> compareTo(other)
                is Char -> compareTo(other)
                else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this::class}.compareTo(${other?.clazz})")
            }
            is String -> when(other){
                is Number -> compareTo(other)
                is String -> compareTo(other)
                is Char -> compareTo(other)
                else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this::class}.compareTo(${other?.clazz})")
            }
            is Char -> when(other){
                is Number -> compareTo(other)
                is String -> compareTo(other)
                is Char -> compareTo(other)
                else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this::class}.compareTo(${other?.clazz})")
            }
            else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this::class}.compareTo(${other?.clazz})")
        }


//=========compareTo============
infix operator fun Int.compareTo(other: Number): Int{
    return when(other){
        is Int -> this.compareTo(other)
        is Long -> this.compareTo(other)
        is Float -> this.compareTo(other)
        is Double -> this.compareTo(other)
        is Byte -> this.compareTo(other)
        is Short -> this.compareTo(other)
        else -> 0
    }
}
infix operator fun Long.compareTo(other: Number): Int{
    return when(other){
        is Int -> this.compareTo(other)
        is Long -> this.compareTo(other)
        is Float -> this.compareTo(other)
        is Double -> this.compareTo(other)
        is Byte -> this.compareTo(other)
        is Short -> this.compareTo(other)
        else -> 0
    }
}
infix operator fun Float.compareTo(other: Number): Int{
    return when(other){
        is Int -> this.compareTo(other)
        is Long -> this.compareTo(other)
        is Float -> this.compareTo(other)
        is Double -> this.compareTo(other)
        is Byte -> this.compareTo(other)
        is Short -> this.compareTo(other)
        else -> 0
    }
}
infix operator fun Double.compareTo(other: Number): Int{
    return when(other){
        is Int -> this.compareTo(other)
        is Long -> this.compareTo(other)
        is Float -> this.compareTo(other)
        is Double -> this.compareTo(other)
        is Byte -> this.compareTo(other)
        is Short -> this.compareTo(other)
        else -> 0
    }
}
infix operator fun Byte.compareTo(other: Number): Int{
    return when(other){
        is Int -> this.compareTo(other)
        is Long -> this.compareTo(other)
        is Float -> this.compareTo(other)
        is Double -> this.compareTo(other)
        is Byte -> this.compareTo(other)
        is Short -> this.compareTo(other)
        else -> 0
    }
}
infix operator fun Short.compareTo(other: Number): Int{
    return when(other){
        is Int -> this.compareTo(other)
        is Long -> this.compareTo(other)
        is Float -> this.compareTo(other)
        is Double -> this.compareTo(other)
        is Byte -> this.compareTo(other)
        is Short -> this.compareTo(other)
        else -> 0
    }
}



//=========Plus============
infix operator fun Int.plus(other: Number): Number{
//    val res= this + other
    return when(other){
        is Int -> this + other
        is Long -> this + other
        is Float -> this + other
        is Double -> this + other
        is Byte -> this + other
        is Short -> this + other
        else -> this
    }
}
infix operator fun Long.plus(other: Number): Number{
    return when(other){
        is Int -> this + other
        is Long -> this + other
        is Float -> this + other
        is Double -> this + other
        is Byte -> this + other
        is Short -> this + other
        else -> this
    }
}
infix operator fun Float.plus(other: Number): Number{
    return when(other){
        is Int -> this + other
        is Long -> this + other
        is Float -> this + other
        is Double -> this + other
        is Byte -> this + other
        is Short -> this + other
        else -> this
    }
}
infix operator fun Double.plus(other: Number): Number{
    return when(other){
        is Int -> this + other
        is Long -> this + other
        is Float -> this + other
        is Double -> this + other
        is Byte -> this + other
        is Short -> this + other
        else -> this
    }
}
infix operator fun Byte.plus(other: Number): Number{
    return when(other){
        is Int -> this + other
        is Long -> this + other
        is Float -> this + other
        is Double -> this + other
        is Byte -> this + other
        is Short -> this + other
        else -> this
    }
}
infix operator fun Short.plus(other: Number): Number{
    return when(other){
        is Int -> this + other
        is Long -> this + other
        is Float -> this + other
        is Double -> this + other
        is Byte -> this + other
        is Short -> this + other
        else -> this
    }
}


//===========Minus=============
infix operator fun Int.minus(other: Number): Number{
    return when(other){
        is Int -> this - other
        is Long -> this - other
        is Float -> this - other
        is Double -> this - other
        is Byte -> this - other
        is Short -> this - other
        else -> this
    }
}
infix operator fun Long.minus(other: Number): Number{
    return when(other){
        is Int -> this - other
        is Long -> this - other
        is Float -> this - other
        is Double -> this - other
        is Byte -> this - other
        is Short -> this - other
        else -> this
    }
}
infix operator fun Float.minus(other: Number): Number{
    return when(other){
        is Int -> this - other
        is Long -> this - other
        is Float -> this - other
        is Double -> this - other
        is Byte -> this - other
        is Short -> this - other
        else -> this
    }
}
infix operator fun Double.minus(other: Number): Number{
    return when(other){
        is Int -> this - other
        is Long -> this - other
        is Float -> this - other
        is Double -> this - other
        is Byte -> this - other
        is Short -> this - other
        else -> this
    }
}
infix operator fun Byte.minus(other: Number): Number{
    return when(other){
        is Int -> this - other
        is Long -> this - other
        is Float -> this - other
        is Double -> this - other
        is Byte -> this - other
        is Short -> this - other
        else -> this
    }
}
infix operator fun Short.minus(other: Number): Number{
    return when(other){
        is Int -> this - other
        is Long -> this - other
        is Float -> this - other
        is Double -> this - other
        is Byte -> this - other
        is Short -> this - other
        else -> this
    }
}


//===========Times=============
infix operator fun Int.times(other: Number): Number{
    return when(other){
        is Int -> this * other
        is Long -> this * other
        is Float -> this * other
        is Double -> this * other
        is Byte -> this * other
        is Short -> this * other
        else -> this
    }
}
infix operator fun Long.times(other: Number): Number{
    return when(other){
        is Int -> this * other
        is Long -> this * other
        is Float -> this * other
        is Double -> this * other
        is Byte -> this * other
        is Short -> this * other
        else -> this
    }
}
infix operator fun Float.times(other: Number): Number{
    return when(other){
        is Int -> this * other
        is Long -> this * other
        is Float -> this * other
        is Double -> this * other
        is Byte -> this * other
        is Short -> this * other
        else -> this
    }
}
infix operator fun Double.times(other: Number): Number{
    return when(other){
        is Int -> this * other
        is Long -> this * other
        is Float -> this * other
        is Double -> this * other
        is Byte -> this * other
        is Short -> this * other
        else -> this
    }
}
infix operator fun Byte.times(other: Number): Number{
    return when(other){
        is Int -> this * other
        is Long -> this * other
        is Float -> this * other
        is Double -> this * other
        is Byte -> this * other
        is Short -> this * other
        else -> this
    }
}
infix operator fun Short.times(other: Number): Number{
    return when(other){
        is Int -> this * other
        is Long -> this * other
        is Float -> this * other
        is Double -> this * other
        is Byte -> this * other
        is Short -> this * other
        else -> this
    }
}



//===========Divide=============
infix operator fun Int.div(other: Number): Number{
//    val res= this + other
    return when(other){
        is Int -> this / other
        is Long -> this / other
        is Float -> this / other
        is Double -> this / other
        is Byte -> this / other
        is Short -> this / other
        else -> this
    }
}
infix operator fun Long.div(other: Number): Number{
    return when(other){
        is Int -> this / other
        is Long -> this / other
        is Float -> this / other
        is Double -> this / other
        is Byte -> this / other
        is Short -> this / other
        else -> this
    }
}
infix operator fun Float.div(other: Number): Number{
    return when(other){
        is Int -> this / other
        is Long -> this / other
        is Float -> this / other
        is Double -> this / other
        is Byte -> this / other
        is Short -> this / other
        else -> this
    }
}
infix operator fun Double.div(other: Number): Number{
    return when(other){
        is Int -> this / other
        is Long -> this / other
        is Float -> this / other
        is Double -> this / other
        is Byte -> this / other
        is Short -> this / other
        else -> this
    }
}
infix operator fun Byte.div(other: Number): Number{
    return when(other){
        is Int -> this / other
        is Long -> this / other
        is Float -> this / other
        is Double -> this / other
        is Byte -> this / other
        is Short -> this / other
        else -> this
    }
}
infix operator fun Short.div(other: Number): Number{
    return when(other){
        is Int -> this / other
        is Long -> this / other
        is Float -> this / other
        is Double -> this / other
        is Byte -> this / other
        is Short -> this / other
        else -> this
    }
}



//===========Remain=============
infix operator fun Int.rem(other: Number): Number{
//    val res= this + other
    return when(other){
        is Int -> this % other
        is Long -> this % other
        is Float -> this % other
        is Double -> this % other
        is Byte -> this % other
        is Short -> this % other
        else -> this
    }
}
infix operator fun Long.rem(other: Number): Number{
    return when(other){
        is Int -> this % other
        is Long -> this % other
        is Float -> this % other
        is Double -> this % other
        is Byte -> this % other
        is Short -> this % other
        else -> this
    }
}
infix operator fun Float.rem(other: Number): Number{
    return when(other){
        is Int -> this % other
        is Long -> this % other
        is Float -> this % other
        is Double -> this % other
        is Byte -> this % other
        is Short -> this % other
        else -> this
    }
}
infix operator fun Double.rem(other: Number): Number{
    return when(other){
        is Int -> this % other
        is Long -> this % other
        is Float -> this % other
        is Double -> this % other
        is Byte -> this % other
        is Short -> this % other
        else -> this
    }
}
infix operator fun Byte.rem(other: Number): Number{
    return when(other){
        is Int -> this % other
        is Long -> this % other
        is Float -> this % other
        is Double -> this % other
        is Byte -> this % other
        is Short -> this % other
        else -> this
    }
}
infix operator fun Short.rem(other: Number): Number{
    return when(other){
        is Int -> this % other
        is Long -> this % other
        is Float -> this % other
        is Double -> this % other
        is Byte -> this % other
        is Short -> this % other
        else -> this
    }
}