package sidev.lib.math.stat

import sidev.lib.collection.common.first
import sidev.lib.collection.common.toCommonIndexedList
import sidev.lib.number.*
import kotlin.jvm.JvmOverloads


/*
=========================
Central Tendency
=========================
 */

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
fun medianNodeIndex(len: Int): Int = (len / 2) +(len % 2)

/**
 * Sama dg [medianNodeIndex], namun jika [len] genap, maka nilai yg di-return sebanyak 2 titik tengah.
 * Jika [len] ganjil, maka nilai yg di-return hanya 1, sehingga Pair.second null.
 */
fun medianNodesIndex(len: Int): Pair<Int, Int?> = (len % 2).let {
    val medianLen= len / 2
    Pair(
        medianLen +it, if(it == 0) medianLen +1 else null
    )
}

/**
 * Mengambil node scr utuh yang terletak pada index tengah dari `this.extension` Iterable.
 * Prinsipnya sama dg [medianNodeIndex].
 * Fungsi ini tidak mengurutkan terlebih dahulu elemennya.
 */
@Throws(IndexOutOfBoundsException::class)
internal fun <T> Any.medianNode_internal(): T {
    val list= toCommonIndexedList<T>()
    if(list.isEmpty()) throw NoSuchElementException()

    val medianIndex= medianNodeIndex(list.size) -1
    return list[medianIndex]
}

/**
 * Mengambil node scr utuh yang terletak pada index tengah dari `this.extension` Iterable.
 * Prinsipnya sama dg [medianNodesIndex].
 * Jika `this.extension` Iterable memiliki ukuran genap, maka elemen yg dikembalikan sebanyak 2
 * dan jika ganjil, maka elemen yg dikembalikan berjumlah 1.
 * Fungsi ini tidak mengurutkan terlebih dahulu elemennya.
 */
fun <T> Any.medianNodes_internal(): Pair<T, T?> {
    val list= toCommonIndexedList<T>()
    if(list.isEmpty()) throw NoSuchElementException()

    val (medianIndex1, medianIndex2) = medianNodesIndex(list.size)
//    val medianIndex= medianNode(list.size) -1
    return Pair(
        list[medianIndex1],
        if(medianIndex2 != null) list[medianIndex2] else null
    )
}
/*
@Throws(IndexOutOfBoundsException::class)
internal fun <T> Array<T>.medianNodes_internal(): Pair<T, T?> {
    if(isEmpty())
        throw IndexOutOfBoundsException("this Array<T> kosong.")

    val (medianIndex1, medianIndex2) = medianNodesIndex(size)
//    val medianIndex= medianNode(size) -1
    return Pair(
        this[medianIndex1],
        if(medianIndex2 != null) this[medianIndex2] else null
    )
}
 */

/**
 * Mengambil scr utuh titik tengah dari `this.extension` [Iterable].
 * Jika ukuran `this.extension` [Iterable] ganjil, maka titik tengah yg dikembalikan merupakan titik tepat tengah.
 * Jika ukuran `this.extension` [Iterable] genap, maka titik tengah yg dikembalikan merupakan titik tepat berada pada size / 2.
 */
@JvmOverloads
fun <T> Iterable<T>.medianNode(sortFirst: Boolean = true, toComparableFun: (T) -> Comparable<Any>): T =
    (if(sortFirst) sortedBy { toComparableFun(it) } else this).medianNode_internal()
@JvmOverloads
fun <T: Comparable<T>> Iterable<T>.medianNode(sortFirst: Boolean = true): T =
    (if(sortFirst) sorted() else this).medianNode_internal()

/**
 * Mengambil scr utuh titik tengah dari `this.extension` [Iterable].
 * Jika ukuran `this.extension` [Iterable] ganjil, maka titik tengah yg dikembalikan merupakan titik tepat tengah.
 * Jika ukuran `this.extension` [Iterable] genap, maka titik tengah yg dikembalikan merupakan titik tepat berada pada size / 2.
 */
@JvmOverloads
fun <T> Array<T>.medianNode(sortFirst: Boolean = true, toComparableFun: (T) -> Comparable<Any>): T =
    if(sortFirst) sortedBy { toComparableFun(it) }.medianNode_internal()
    else this.medianNode_internal()
@JvmOverloads
fun <T: Comparable<T>> Array<T>.medianNode(sortFirst: Boolean = true): T =
    if(sortFirst) sorted().medianNode_internal()
    else this.medianNode_internal()


/**
 * Mengambil scr utuh titik tengah dari `this.extension` [Iterable].
 * Jika ukuran `this.extension` [Iterable] ganjil, maka titik tengah yg dikembalikan hanya satu.
 * Jika ukuran `this.extension` [Iterable] genap, maka titik tengah yg dikembalikan ada 2.
 */
@JvmOverloads
fun <T> Iterable<T>.medianNodes(sortFirst: Boolean = true, toComparableFun: (T) -> Comparable<Any>): Pair<T, T?> =
    (if(sortFirst) sortedBy { toComparableFun(it) } else this).medianNodes_internal()
@JvmOverloads
fun <T: Comparable<T>> Iterable<T>.medianNodes(sortFirst: Boolean = true): Pair<T, T?> =
    (if(sortFirst) sorted() else this).medianNodes_internal()

/**
 * Mengambil scr utuh titik tengah dari `this.extension` [Iterable].
 * Jika ukuran `this.extension` [Iterable] ganjil, maka titik tengah yg dikembalikan hanya satu.
 * Jika ukuran `this.extension` [Iterable] genap, maka titik tengah yg dikembalikan ada 2.
 */
@JvmOverloads
fun <T> Array<T>.medianNodes(sortFirst: Boolean = true, toComparableFun: (T) -> Comparable<Any>): Pair<T, T?> =
    if(sortFirst) sortedBy { toComparableFun(it) }.medianNodes_internal()
    else this.medianNodes_internal()
@JvmOverloads
fun <T: Comparable<T>> Array<T>.medianNodes(sortFirst: Boolean = true): Pair<T, T?> =
    if(sortFirst) sorted().medianNodes_internal()
    else this.medianNodes_internal()

/**
 * Mengambil scr utuh titik tengah dari `this.extension` [Iterable].
 * Jika ukuran `this.extension` [Iterable] ganjil, maka nilai yg dikembalikan merupakan titik tepat di tengah.
 * Jika ukuran `this.extension` [Iterable] genap, maka nilai yg dikembalikan merupakan rata-rata dari 2 titik tengah.
 */
@JvmOverloads
internal fun Any.median_internal(sortFirst: Boolean = true): Double {
    val list= toCommonIndexedList<Number>()
    val (node1, node2) = (this as Array<Comparable<Number>>).medianNodes(sortFirst)

    return (if(node2 == null) node1 as Number
    else (node1 as Number + node2 as Number) / 2.0).toDouble()
}
@JvmOverloads
fun Iterable<Number>.median(sortFirst: Boolean = true): Double = median_internal(sortFirst)
@JvmOverloads
fun Array<out Number>.median(sortFirst: Boolean = true): Double = median_internal(sortFirst)

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
internal fun Any.mean_internal(): Double {
//    val digitizer: (T) -> Number = digitizer ?: { it as Number }
    val list= toCommonIndexedList<Number>()
    var count= 0
    var sum= 0.0
    for(e in list){
        sum += e.toDouble()
        count++
    }
    return if(count == 0) Double.NaN else sum / count
}
fun Array<out Number>.mean(): Double = mean_internal()
fun Iterable<Number>.mean(): Double = mean_internal()

fun Pair<Number, Number?>.mean(): Double {
    var mean= first.toDouble()
    second?.also { mean= (mean + it.toDouble()) / 2  }
    return mean
}

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



/*
=========================
Variability
=========================
 */
@JvmOverloads
internal fun Any.stdDeviation_internal(mean: Double?= null, method: StdDeviationMethod= StdDeviationMethod.ABSOLUTE): Double {
    val list: List<Number> = toCommonIndexedList()
    val mean= mean ?: list.mean()
    val size: Double
    val func: (Number) -> Double = when(method){
        StdDeviationMethod.ABSOLUTE -> { it: Number -> if(it >= mean) it.toDouble() - mean else mean - it.toDouble() }
        StdDeviationMethod.SQUARE -> { it: Number -> (it.toDouble() - mean) powCast 2 }
    }
    return list.map(func).also { size= it.size.toDouble() }.sum() / size  //.sumByDouble { it.toDouble() } / size
}
@JvmOverloads
fun Iterable<Number>.stdDeviation(mean: Double?= null, method: StdDeviationMethod= StdDeviationMethod.ABSOLUTE): Double =
    stdDeviation_internal(mean, method)
@JvmOverloads
fun Array<out Number>.stdDeviation(mean: Double?= null, method: StdDeviationMethod= StdDeviationMethod.ABSOLUTE): Double =
    stdDeviation_internal(mean, method)


internal fun <T, C: Comparable<C>> Any.getMost_internal(compareFun: (e: C, mostE: C) -> Boolean, toComparableFun: (T) -> C): T {
    val list= toCommonIndexedList<T>()
    if(list.isEmpty()) throw NoSuchElementException()
    var most= list.first() //itr.next()
    var comparableMax= toComparableFun(most)
    for(i in 1 until list.size){
        val e= list[i]
        val comparableE= toComparableFun(e)
        if(compareFun(comparableE, comparableMax)){
            most= e
            comparableMax= comparableE
        }
    }
    return most
}

internal fun <T, C: Comparable<C>> Any.getMost(compareFun: (e: C, mostE: C) -> Boolean, toComparableFun: (T) -> C): T =
    getMost_internal(compareFun, toComparableFun)

fun <T, C: Comparable<C>> Iterable<T>.max(toComparableFun: (T) -> C): T = getMost(moreThan_(), toComparableFun)
fun <T, C: Comparable<C>> Iterable<T>.min(toComparableFun: (T) -> C): T = getMost(lessThan_(), toComparableFun)


internal fun <T, C: Comparable<C>> Array<T>.getMost(compareFun: (e: C, mostE: C) -> Boolean, toComparableFun: (T) -> C): T {
    val itr= iterator()
    if(!itr.hasNext()) throw NoSuchElementException()
    var max= itr.next()
    var comparableMax= toComparableFun(max)
    while(itr.hasNext()){
        val e= itr.next()
        val comparableE= toComparableFun(e)
        if(compareFun(comparableE, comparableMax)){
            max= e
            comparableMax= comparableE
        }
    }
    return max
}
fun <T, C: Comparable<C>> Array<T>.max(toComparableFun: (T) -> C): T = getMost(moreThan_(), toComparableFun)
fun <T, C: Comparable<C>> Array<T>.min(toComparableFun: (T) -> C): T = getMost(lessThan_(), toComparableFun)


internal fun Any.range_internal(): Double {
    val list= toCommonIndexedList<Number>()
    if(list.isEmpty()) throw NoSuchElementException()
    var max: Number= list.first()
    var min: Number= max
    for(i in 1 until list.size){
        val e= list[i]
        if(max < e)
            max= e
        else if(min > e)
            min= e
    }
    return (max - min).toDouble()
}
fun Iterable<Number>.range(): Double = range_internal()
fun Array<out Number>.range(): Double = range_internal()


/**
 * Mengambil titik yg membagi data sama besar pada titik [percent] dari semua data yg ada di `this.extension` [Iterable].
 */
internal fun <T> Any.quantileNodes_internal(percent: Double): Pair<T, T?> {
    val list= toCommonIndexedList<T>()
    val size= list.size
    val quantileIndexRaw= size * percent
    val quantileIndexInt= quantileIndexRaw.toInt()
    val isEven= (quantileIndexRaw - quantileIndexInt).compareTo(0) == 0

    return Pair(
        list[quantileIndexInt],
        if(isEven) list[quantileIndexInt + 1] else null
    )
}

/**
 * Mengambil titik yg membagi data sama besar pada titik [percent] dari semua data yg ada di `this.extension` [Iterable].
 */
fun <T> Iterable<T>.quantileNodes(percent: Double): Pair<T, T?> = quantileNodes_internal(percent)
/**
 * Mengambil titik yg membagi data sama besar pada titik [percent] dari semua data yg ada di `this.extension` [Iterable].
 */
fun <T> Array<T>.quantileNodes(percent: Double): Pair<T, T?> = quantileNodes_internal(percent)


/**
 * Mengambil Q1 dari `this.extension` [Iterable].
 */
internal fun <T> Any.q1_internal(): Pair<T, T?> = quantileNodes_internal(0.25)

/**
 * Mengambil Q3 dari `this.extension` [Iterable].
 */
internal fun <T> Any.q3_internal(): Pair<T, T?> = quantileNodes_internal(0.75)

/**
 * Mengambil Q1 dari `this.extension` [Iterable].
 */
fun <T> Iterable<T>.q1(): Pair<T, T?> = q1_internal()

/**
 * Mengambil Q3 dari `this.extension` [Iterable].
 */
fun <T> Iterable<T>.q3(): Pair<T, T?> = q3_internal()

/**
 * Mengambil Q1 dari `this.extension` [Iterable].
 */
fun <T> Array<T>.q1(): Pair<T, T?> = q1_internal()

/**
 * Mengambil Q3 dari `this.extension` [Iterable].
 */
fun <T> Array<T>.q3(): Pair<T, T?> = q3_internal()


/**
 * Mengambil Q1 dan Q3 dari `this.extension` [Iterable].
 */
@JvmOverloads
internal fun Any.interquartileNodes_internal(sortFirst: Boolean= true): Pair<Double, Double> {
    var list: List<Comparable<Any>> = toCommonIndexedList()
    if(sortFirst)
        list= list.sorted()
    val q1= list.q1_internal<Number>() //as Pair<Number, Number>
    val q3= list.q3_internal<Number>() //as Pair<Number, Number>

    return Pair(
        q1.mean(),
        q3.mean()
    )
}

@JvmOverloads
fun Iterable<Number>.interquartileNodes(sortFirst: Boolean= true): Pair<Double, Double> = interquartileNodes_internal(sortFirst)
@JvmOverloads
fun Array<out Number>.interquartileNodes(sortFirst: Boolean= true): Pair<Double, Double> = interquartileNodes_internal(sortFirst)


/**
 * Mengambil jangkauan antara Q1 dan Q3 dari `this.extension` [Iterable].
 */
@JvmOverloads
internal fun Any.interquartileRange_internal(sortFirst: Boolean= true, nodes: Pair<Double, Double>?= null): Double =
    (nodes ?: interquartileNodes_internal(sortFirst)).run { second - first }
@JvmOverloads
fun Iterable<Number>.interquartileRange(sortFirst: Boolean= true, nodes: Pair<Double, Double>?= null): Double =
    interquartileRange_internal(sortFirst, nodes)
@JvmOverloads
fun Array<out Number>.interquartileRange(sortFirst: Boolean= true, nodes: Pair<Double, Double>?= null): Double =
    interquartileRange_internal(sortFirst, nodes)


/**
 * Mengambil batas bawah dan batas atas untuk outlier dari `this.extension` [Iterable].
 */
@JvmOverloads
internal fun Any.interquartileLimit_internal(sortFirst: Boolean= true, nodes: Pair<Double, Double>?= null): Pair<Double, Double> {
    val (q1, q3) = nodes ?: interquartileNodes_internal(sortFirst)
    val iqr = q3 - q1 //interquartileRange(nodes = iq)

    return Pair(
        q1 - 1.5 * iqr,
        q3 + 1.5 * iqr,
    )
}

@JvmOverloads
fun Iterable<Number>.interquartileLimit(sortFirst: Boolean= true, nodes: Pair<Double, Double>?= null): Pair<Double, Double> =
    interquartileLimit_internal(sortFirst, nodes)
@JvmOverloads
fun Array<out Number>.interquartileLimit(sortFirst: Boolean= true, nodes: Pair<Double, Double>?= null): Pair<Double, Double> =
    interquartileLimit_internal(sortFirst, nodes)