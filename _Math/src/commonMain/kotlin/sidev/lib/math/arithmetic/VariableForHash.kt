package sidev.lib.math.arithmetic

import sidev.lib.exception.IllegalAccessExc

/**
 * [Variable] yang digunakan pada hashTable, yaitu menggunakan [Variable.name]
 * dan mengabaikan [Variable.coeficient].
 */
interface VariableForHash: Variable<Number> {
    override val coeficient: Int
        get() = throw IllegalAccessExc(msg = "`VariableForHash` tidak punya coeficient karena hanya untuk hashing.")
}


internal class VariableForHashMap(name: String): VariableImpl<Number>(name, 1), VariableForHash {
    constructor(origin: Variable<*>): this(origin.name)

    override val coeficient: Int
        get() = super<VariableForHash>.coeficient

    override fun hashCode(): Int = name.hashCode()
    override fun equals(other: Any?): Boolean = when(other){
        is Variable<*> -> name == other.name
        else -> super.equals(other)
    }
}