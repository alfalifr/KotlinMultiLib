package sidev.lib.reflex

import sidev.lib.reflex.comp.SiProperty1
import sidev.lib.reflex.full.contructorPropertiesTree
import sidev.lib.reflex.full.declaredMemberProperties
import sidev.lib.reflex.full.forceGet
import sidev.lib.reflex.comp.native.si

/** Mengambil semua enum anggota `this.extension` [Enum] [Enum.ordinal] dan [Enum.name]. */
inline fun <reified E: Enum<E>> ordinalNamePairs(): Array<Pair<Int, String>>{
    val vals= enumValues<E>()
    return Array(vals.size){ Pair(vals[it].ordinal, vals[it].name) }
}
/** Mengubah semua enum anggota `this.extension` [Enum] menjadi array. */
inline fun <reified E: Enum<E>, reified A> Enum<E>.toArray(init: (E) -> A): Array<A>{
    val vals= enumValues<E>()
    return Array(vals.size){init(vals[it])}
}
/** Mengambil data Enum yg berada pada konstruktor. Selain di konstruktor tidak diambil. */
val <E: Enum<E>> E.data: Sequence<Pair<SiProperty1<*, *>, Any?>>
    get(){
        val constrProps=
            (this::class.si.contructorPropertiesTree - Enum::class.si.declaredMemberProperties).iterator()
        return object : Sequence<Pair<SiProperty1<*, *>, Any?>>{
            override fun iterator(): Iterator<Pair<SiProperty1<*, *>, Any?>>
                    = object : Iterator<Pair<SiProperty1<*, *>, Any?>>{
                override fun hasNext(): Boolean = constrProps.hasNext()

                override fun next(): Pair<SiProperty1<*, *>, Any?> {
                    val next= constrProps.next()
                    val value= (next as SiProperty1<E, *>).forceGet(this@data)
                    return Pair(next, value)
                }
            }
        }
    }
