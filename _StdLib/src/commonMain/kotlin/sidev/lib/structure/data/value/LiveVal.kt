package sidev.lib.structure.data.value

import sidev.lib.`val`.Assignment

/**
 * [Val] yg dapat memberitahu observer-nya bahwa nilai nya berubah.
 * Pemanggilan observer tidak selalu menandakan perubahan nilai karena dapat dipanggil
 * melalui method [invoke]. Oleh karena itu, disarankan untuk menangkap nilai [Assignment]
 * pada method [observe].
 */
interface LiveVal<out T>: Val<T> {
    fun observe(o: ((T) -> Unit)?)
    fun observe(o: ((T, Assignment) -> Unit)?)
    /**
     * Memanggil observer dg menge-pass nilai [value] terkini dan [Assignment.RE_ASSIGN].
     * Fungsi ini berguna saat [value] memiliki alamat referensi yang sama namun isinya beda.
     * Fungsi ini hanya memanggil observer tanpa meng-assign ke variabel.
     */
    operator fun invoke()
}

internal open class LiveValImpl<T>(v: T): ValImpl<T>(v), LiveVal<T> {
    protected var observer: ((T, Assignment) -> Unit)?= null

    override fun observe(o: ((T, Assignment) -> Unit)?) {
        observer= o
        invokeInit()
    }
    override fun observe(o: ((T) -> Unit)?) {
        observer= if(o != null) { `val`, _ ->
            o(`val`)
        } else null
        invokeInit()
    }

    override fun invoke() {
        observer?.invoke(value, Assignment.RE_ASSIGN)
    }
    private fun invokeInit() {
        observer?.invoke(value, Assignment.INIT)
    }
}