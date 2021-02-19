@file:JvmName("_ArrayFunJvm")
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
) = System.arraycopy(src, srcStart, dest, destStart, length)


actual fun arrayCopyAll(
    src: Any, srcStart: Int,
    dest: Any, destStart: Int,
    length: Int
) = System.arraycopy(src, srcStart, dest, destStart, length)