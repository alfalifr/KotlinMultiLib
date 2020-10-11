package sidev.lib.math

import kotlin.jvm.JvmOverloads

/**
 * Mengambil titik tengah dari panjang [len].
 * Nilai yg dikembalikan merupakan bilangan bulat index dari titik yang menyusun panjang [len].
 * Fungsi ini menganggap titik penyusun dari panjang [len] merupakan bilangan bulat.
 *
 * Nilai yang di-return:
 *   1. Jika [len] ganjil, maka yg diambil adalah titik tengah yg membagi [len] tepat jadi 2 (kanan dan kiri).
 *   1. Jika [len] genap, maka yg diambil adalah titik awal tepat sebelum titik tengah [len] yg merupakan bilangan float.
 *
 * Contoh:
 *   1. 1-2-3-4-5 -> titik tengah: 3
 *   2. 1-2-3-4 -> titik tengah: 2
 */
fun medianNode(len: Int): Int = (len / 2) +(len % 2)

/**
 * Sama dg [medianNode], namun jika [len] genap, maka nilai yg di-return sebanyak 2 titik tengah.
 * Jika [len] ganjil, maka nilai yg di-return hanya 1, sehingga Pair.second null.
 */
fun medianNodes(len: Int): Pair<Int, Int?> = (len % 2).let {
    val medianLen= len / 2
    Pair(
        medianLen +it, if(it == 0) medianLen +1 else null
    )
}

/**
 * Mengambil node scr utuh yang terletak pada index tengah dari `this.extension` Iterable.
 * Prinsipnya sama dg [medianNode].
 * Fungsi ini tidak mengurutkan terlebih dahulu elemennya.
 */
@Throws(IndexOutOfBoundsException::class)
fun <T> Iterable<T>.medianNode(): T {
    val list= (if(this is List) this else this.toList()).also {
        if(it.isEmpty()) throw IndexOutOfBoundsException("this Iterable<T> kosong.")
    }

    val medianIndex= medianNode(list.size) -1
    return list[medianIndex]
}
@Throws(IndexOutOfBoundsException::class)
fun <T> Array<T>.medianNode(): T {
    if(isEmpty())
        throw IndexOutOfBoundsException("this Array<T> kosong.")
    val medianIndex= medianNode(size) -1
    return this[medianIndex]
}

/**
 * Mengambil node scr utuh yang terletak pada index tengah dari `this.extension` Iterable.
 * Prinsipnya sama dg [medianNodes].
 * Jika `this.extension` Iterable memiliki ukuran genap, maka elemen yg dikembalikan sebanyak 2
 * dan jika ganjil, maka elemen yg dikembalikan berjumlah 1.
 * Fungsi ini tidak mengurutkan terlebih dahulu elemennya.
 */
@Throws(IndexOutOfBoundsException::class)
fun <T> Iterable<T>.medianNodes(): Pair<T, T?> {
    val list= (if(this is List) this else this.toList()).also {
        if(it.isEmpty()) throw IndexOutOfBoundsException("this Iterable<T> kosong.")
    }

    val medianIndex= medianNode(list.size) -1
    return Pair(
        list[medianIndex],
        if(list.size % 2 == 0) list[medianIndex +1] else null
    )
}
@Throws(IndexOutOfBoundsException::class)
fun <T> Array<T>.medianNodes(): Pair<T, T?> {
    if(isEmpty())
        throw IndexOutOfBoundsException("this Array<T> kosong.")

    val medianIndex= medianNode(size) -1
    return Pair(
        this[medianIndex],
        if(size % 2 == 0) this[medianIndex +1] else null
    )
}

/**
 * Mengambil titik tengah dari panjang [len].
 * Nilai yang dikembalikan merupakan bilangan float hasil pembagian [len] / 2.
 */
fun median(len: Int): Double = len / 2.0

/**
 * Mengambil rata-rata nilai angka dari tiap elemen pada `this.extension` Iterable.
 * [digitizer] adalah fungsi yang mengubah elemen [T] menjadi [Number].
 * Nilai yang di-return adalah Double.
 */
@JvmOverloads
fun <T> Iterable<T>.average(digitizer: ((T) -> Number)?= null): Double {
    val digitizer: (T) -> Number = digitizer ?: { it as Number }
    var count= 0
    var sum= 0.0
    try{
        for(e in this){
            sum += digitizer(e).toDouble()
            count++
        }
    } catch (e: ClassCastException) {
        throw IllegalArgumentException("this: Iterable<Non-Number> dan tidak tersedia nilai untuk param: digitizer.", e)
    }
    return if(count == 0) Double.NaN else sum / count
}
fun <T> Array<T>.average(digitizer: ((T) -> Number)?= null): Double = this.toList().average(digitizer)

/**
 * Mengambil modus (nilai paling banyak muncul) dari sebuah [list].
 * Elemen pada [list] dikelompokan berdasarkan nilai return dari [keySelector].
 * Jika [keySelector] null, maka secara default pengemlompokan berdasarkan nilai alami (natural value) dari elemen
 * atau nilai return dari element.hashCode().
 * Nilai return dari fungsi ini adalah elemen pertama dari group list dg size paling besar.
 * Jika terdapat 2 atau lebih group list yg memiliki size sama, maka yang di-return adalah yg pertama kali muncul.
 */
fun <K, V> Iterable<V>.mode(keySelector: ((V) -> K)): V = findValueOnFrequency(
    { acc, list -> if(acc.size < list.size) list else acc },
    keySelector
)
/**
 * Sama dg fungsi [mode] diatas, namun type-param disederhanakan agar programmer tidak perlu
 * menginputkan type-param scr eksplisit.
 */
fun <V> Iterable<V>.mode(): V = findValueOnFrequency { acc, list ->
    if(acc.size < list.size) list else acc
}

fun <K, V> Array<V>.mode(keySelector: ((V) -> K)): V = findValueOnFrequency(
    { acc, list -> if(acc.size < list.size) list else acc },
    keySelector
)
fun <V> Array<V>.mode(): V = findValueOnFrequency { acc, list ->
    if(acc.size < list.size) list else acc
}

/**
 * Mengambil nilai yg paling jarang muncul pada `this.extension` List.
 * Kebalikan dari fungsi [mode].
 */
fun <K, V> Iterable<V>.leastFrequentValue(keySelector: ((V) -> K)): V = findValueOnFrequency(
    { acc, list -> if(acc.size > list.size) list else acc },
    keySelector
)
/**
 * Sama dg fungsi [leastFrequentValue] diatas, namun type-param disederhanakan agar programmer tidak perlu
 * menginputkan type-param scr eksplisit.
 */
fun <V> Iterable<V>.leastFrequentValue(): V = findValueOnFrequency { acc, list ->
    if(acc.size > list.size) list else acc
}

fun <K, V> Array<V>.leastFrequentValue(keySelector: ((V) -> K)): V = findValueOnFrequency(
    { acc, list -> if(acc.size > list.size) list else acc },
    keySelector
)
fun <V> Array<V>.leastFrequentValue(): V = findValueOnFrequency { acc, list ->
    if(acc.size > list.size) list else acc
}

/**
 * Mengmabil sebuah nilai dari `this.extension` Iterable yang kemunculannya dibandingkan oleh [comparator].
 * Tiap elemen yang ada pada `this.extension` Iterable dikelompokan berdasarkan nilai yang dikembalikan oleh fungsi [keySelector]
 * terlebih dahulu. Kemudian, tiap kelompok akan dibandingkan menggunakan fungsi [comparator].
 * Pada umumnya, fungsi [comparator] hanya membandingkan size dari [list1] maupun [list2], seperti pada
 * fungsi [mode] dimana [comparator] berisi `if(acc.size < list.size) list else acc`
 * dan [leastFrequentValue] berisi `if(acc.size > list.size) list else acc`.
 */
fun <K, V> Iterable<V>.findValueOnFrequency(
    comparator: (list1: List<V>, list2: List<V>) -> List<V>,
    keySelector: ((V) -> K)
): V = this.groupBy(keySelector).values
    .reduce { acc, list -> comparator(acc, list) }
    .first() //Anggapannya programmer sudah mengetahui konsekuensinya bahwa tiap elemen pada list sama semua.
/**
 * Sama dg fungsi [findValueOnFrequency] diatas, namun type-param disederhanakan agar programmer tidak perlu
 * menginputkan type-param scr eksplisit.
 */
fun <V> Iterable<V>.findValueOnFrequency(
    comparator: (list1: List<V>, list2: List<V>) -> List<V>,
): V = findValueOnFrequency(comparator){ it }


fun <K, V> Array<V>.findValueOnFrequency(
    comparator: (list1: List<V>, list2: List<V>) -> List<V>,
    keySelector: ((V) -> K)
): V = this.groupBy(keySelector).values
    .reduce { acc, list -> comparator(acc, list) }
    .first() //Anggapannya programmer sudah mengetahui konsekuensinya bahwa tiap elemen pada list sama semua.

fun <V> Array<V>.findValueOnFrequency(
    comparator: (list1: List<V>, list2: List<V>) -> List<V>,
): V = findValueOnFrequency(comparator){ it }