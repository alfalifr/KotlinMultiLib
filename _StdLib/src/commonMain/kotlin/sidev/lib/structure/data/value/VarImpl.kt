package sidev.lib.structure.data.value

import sidev.lib.exception.IllegalArgExc

/*
/**
 * Versi ringan untuk wadah suatu nilai.
 */
internal open class VarImpl_<T>(value: T?= null): Val<T> {
//    constructor(): this(isNullable){ this.value= value }
    override var value: T?= value
        set(v){
            if(v != null || isNullable)
                field= v
            else throw IllegalArgExc(detailMsg = "`value` dari '$this' tidak boleh `null`.")
        }
//        get()= if(!isNullable && field == null) throw IllegalAccessExc(msg = "`value` msh null")

    override fun equals(other: Any?): Boolean {
        return if(other is VarImpl<*>) value == other.value
        else this === other
    }
/*
    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + (vala?.hashCode() ?: 0)
        return result
    }
 */
    override fun hashCode(): Int {
        return value?.hashCode() ?: super.hashCode()
    }

    override fun toString(): String
            = "${this::class.simpleName}(value=$value)"
/*
    fun copy(): BoxedVal<T>{
        val box= BoxedVal<T>()
//        val aa= SparseArra
        box.value= value//try{ Gson()(value as Object).clone() as? T } catch (e: Exception){ value }
        return box
    }
 */
}
 */