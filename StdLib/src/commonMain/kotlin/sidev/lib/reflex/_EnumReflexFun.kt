package sidev.lib.reflex
/*
import sidev.lib.reflex.inner.declaredMemberProperties
import kotlin.reflect.KProperty1

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
val <E: Enum<E>> E.data: Sequence<Pair<KProperty1<*, *>, Any?>>
    get(){
        val constrProps=
            (this::class.contructorPropertiesTree - Enum::class.declaredMemberProperties).iterator()
        return object : Sequence<Pair<KProperty1<*, *>, Any?>>{
            override fun iterator(): Iterator<Pair<KProperty1<*, *>, Any?>>
                    = object : Iterator<Pair<KProperty1<*, *>, Any?>>{
                override fun hasNext(): Boolean = constrProps.hasNext()

                override fun next(): Pair<KProperty1<*, *>, Any?> {
                    val next= constrProps.next()
                    val value= (next as KProperty1<E, *>).forcedGet(this@data)
                    return Pair(next, value)
                }
            }
        }
    }

 */