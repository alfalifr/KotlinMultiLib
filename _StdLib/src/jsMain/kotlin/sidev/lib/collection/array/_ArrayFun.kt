package sidev.lib.collection.array

/**
 * Fungsi yang melakukan copy array scr native.
 *
 * [length] adalah banyaknya elemen yg akan di-copy dari [src] yang dihitung dari index [srcStart]
 * menuju [dest] pada index yg dimulai dari [destStart].
 */
actual fun <T> arrayCopy(
    src: Array<T>, srcStart: Int,
    dest: Array<T>, destStart: Int,
    length: Int
){
    for(i in 0 until length)
        dest[destStart+ i]= src[srcStart +i]
}


//TODO: 20 Feb 2021 -> Tambah pengecekan untuk mengecek tipe primitif di JS.
actual fun arrayCopyAll(
    src: Any, srcStart: Int,
    dest: Any, destStart: Int,
    length: Int
){
    when {
        src is Array<*> -> {
            when {
                dest is Array<*> -> {
                    dest as Array<Any?>
                    for(i in 0 until length){
                        dest[destStart+ i]= src[srcStart +i]
                    }
                }
            }
        }
    }
}