package sidev.lib.math

import kotlin.jvm.JvmOverloads

/**
 * Permutasi untuk [n] item yang diambil sebanyak [r] item dalam waktu bersamaan.
 * Permutasi adalah banyak kemungkinan yang memperhatikan urutan item.
 */
@JvmOverloads
fun permutation(n: Int, r: Int= n): Int = factorial(n) / factorial(n-r)

/**
 * Kombinasi untuk [n] item yang diambil sebanyak [r] item dalam waktu bersamaan.
 * Kombinasi adalah banyak kemungkinan yang tidak memperhatikan urutan item.
 */
@JvmOverloads
fun combination(n: Int, r: Int= n): Int = factorial(n) / (factorial(n-r) * factorial(r))

/**
 * Kemungkinan banyaknya handshake yg terjadi pada kumpulan orang berjumlah [n].
 */
fun possibleHandshakes(n: Int): Int = (n * (n-1)) / 2

/**
 * Kemungkinan semua rute yang terdiri dari [n] node.
 * Fungsi ini enganggap semua node saling terhubung.
 */
fun possibleRoutes(n: Int): Int = factorial(n-1) / 2