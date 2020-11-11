package sidev.lib.structure.data

import sidev.lib.annotation.Unsafe
import sidev.lib.structure.data.value.indexes

/**
 * [Data] yg digunakan untuk `data class` yg disesuaikan dg bahasa Java.
 */
interface JvmData<T: Data<T>>: Data<T> {
    @Unsafe("Nama parameter `diabaikan` dan urutan berpengaruh.")
    @Deprecated(
        "Tidak aman karena java tidak punya named parameter.",
        ReplaceWith("copy_(*Array(prop.size) { prop[it].second })")
    )
    override fun copy_(vararg prop: Pair<String, Any?>): T =
        copy_(*Array(prop.size){ prop[it].second })

    @Unsafe("Nama parameter `diabaikan` dan urutan berpengaruh.")
    @Deprecated(
        "Tidak aman karena java tidak punya named parameter.",
        ReplaceWith(
        """
        val vals= prop.values.toTypedArray()
        return copy_(*Array(vals.size){ vals[it] })
        """,
            "sidev.lib.structure.data.value.indexes"
        )
    )
    override fun copy_(prop: Map<String, Any?>): T {
        val vals= prop.values.toTypedArray()
        return copy_(*Array(vals.size){ vals[it] })
    }

    fun copy_(vararg prop: IndexedValue<Any?>): T {
        val list= ArrayList<Any?>(prop.size)
        for((i, v) in prop){
            if(i > list.size){
                val diff= i - list.size
                for(u in 0 until diff)
                    list += null
                list[i]= v
            } else { // Ini pasti case-nya i == list.size
                list += v
            }
        }
        return copy_(list.toTypedArray())
    }

    /**
     * Urutan pada [vals] sesuai dg urutan parameter pada method di Java.
     */
    fun copy_(vararg vals: Any?): T

    /**
     * Fungsi ini berguna agar tidak terjadi konflik antara
     * `copy_(vararg vals: Any?)` dan `copy_(vararg prop: Pair<String, Any?>)`.
     */
    fun copy_(): T = copy_(*arrayOfNulls(0))
}