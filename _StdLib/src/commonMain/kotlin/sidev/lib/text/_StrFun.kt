package sidev.lib.text

import sidev.lib.console.prine
import sidev.lib.exception.IllegalArgExc
import kotlin.jvm.JvmOverloads
import kotlin.math.ceil

//TODO <29 Juli 2020> => Buat validator angka (menghilangkan 0 di depan angka).
fun <T> T.toString(func: (obj: T) -> String): String {
    return func(this)
}


fun CharSequence.findIndexed(start: Int = 0, predicate: (IndexedValue<Char>) -> Boolean): IndexedValue<Char>?{
    for(i in start until length){
        val vals= IndexedValue(i, this[i])
        if(predicate(vals))
            return vals
    }
    return null
}

fun CharSequence.indexOfWhere(start: Int = 0, predicate: (Char) -> Boolean): Int {
    for(i in start until length){
        if(predicate(this[i]))
            return i
    }
    return -1
}

fun CharSequence.nextNonWhitespaceChar(start: Int= 0): Char? = try{
    this[indexOfWhere(start) { !it.isWhitespace() }]
} catch (e: IndexOutOfBoundsException) {
    null
}

fun CharSequence.prevNonWhitespaceChar(start: Int= 0): Char? {
    for(i in start downTo 0){
        if(!this[i].isWhitespace()){
            return this[i]
        }
    }
    return null
}

/**
 * Mengambil prefix dari `this` yg tersedia di dalam [prefixes].
 * Prefix yg diambil adalah first occurrence.
 * return `null` jika tidak ditemukan prefix dari `this` dalam [prefixes].
 */
fun CharSequence.getPrefixIn(prefixes: Array<String>): String? {
    for(e in prefixes)
        if(this.startsWith(e))
            return e
    return null
}
fun CharSequence.getPrefixIn(prefixes: Collection<String>): String? {
    for(e in prefixes)
        if(this.startsWith(e))
            return e
    return null
}

fun CharSequence.startsWithAny(array: Array<String>): Boolean {
    for(e in array)
        if(this.startsWith(e))
            return true
    return false
}
fun CharSequence.startsWithAny(coll: Collection<String>): Boolean {
    for(e in coll)
        if(this.startsWith(e))
            return true
    return false
}

/**
 * Mengambil substring yg diapit oleh [quoter].
 * Pencarian dimulai dari index [startIndex].
 * Hasil yg di-return beserta quoter atau tidak itu tergantung [withQuote].
 */
fun CharSequence.getQuoted(quoter: CharSequence, startIndex: Int= 0, withQuote: Boolean= false): String? {
    for(i in startIndex until this.length){
        for(u in i+1 until this.length){
            if(this.substring(i, u) == quoter){
                for(o in startIndex until this.length)
                    for(p in i+1 until this.length)
                        if(this.substring(o, p) == quoter){
                            val value= this.substring(u, o)
                            return if(withQuote) "$quoter$value$quoter"
                                else value
                        }
            }
        }
    }
    return null
}


/**
 * Memendekan string `this` jika melebihi pjg [maxLen].
 * Hasil string yg dipendekan adalah substring prefix +[strInMid] +substring suffix.
 */
fun String.shorten(maxLen: Int, strInMid: String= " ... "): String{
    if(this.length <= maxLen) return this

//    val strInMid= " ... "

    val strInMidIndex= this.length /2
    val strLenDiff= this.length -maxLen

    val lenOfEachSideIsCut= (strLenDiff + strInMid.length) /2
    val lenOfEachSide= strInMidIndex -lenOfEachSideIsCut

    val strPrefix= this.substring(0, lenOfEachSide)
    val strSuffix= this.substring(this.lastIndex -lenOfEachSide)

    return strPrefix +strInMid +strSuffix
}

/**
 * Mengambil jumlah int code dari semua char yg ada di `this.extension`.
 * Hasil yg di-return berupa `Long` agar dapat mengakomodasi string yg panjang.
 *
 * `Long` yg dihasilkan juga menambahkan elemen index agar string dg komposisi sama
 * namun index berbeda menghasilkan Long yg berbeda. Contoh: "abc" berbeda dg "bac".
 */
@JvmOverloads
fun CharSequence.charCodeSum(calculateOrder: Boolean = true): Long {
    var res= 0L
    if(calculateOrder)
        for((i, c) in this.withIndex())
            res += c.toInt() +i //Penambahan index `i` agar string dg komposisi sama namun dg index beda menghasilkan long yg berbeda.
    else
        for(c in this)
            res += c.toInt()
    return res
}


fun CharSequence.getCommon(
    other: CharSequence,
    caseSensitive: Boolean = true,
    buffer: Int = ceil(length / 5.0f).toInt(), // ceil agar saat pembagian menghasil 0,... tetap dapat diiterasi dg buffer ini.
    decay: Float = 0.4f
): CharSequence? {
    val short: CharSequence
    val long = if(length <= other.length){
        short = this
        other
    } else {
        short = other
        this
    }

    val slen= short.length
    val llen= long.length

    val buffer = if(buffer in 1 until slen) buffer
    else ceil(slen / 5.0f).toInt()

    val start = long.indexOf(short[0])
    if(start < 0 || start == long.lastIndex && slen > 1) return null

    val end = short.bufferedGetPrefix(long, start, 1, buffer, decay, 1)
    return if(end >= 0) short.subSequence(start, end)
    else null
}

/**
 * Proses normal dilakukan dari kiri ke kanan atau i=0 sampai i=n.
 * Fungsi ini menganggap bahwa `this` memiliki prefix yg sama dg [long].
 * Return int end
 */
private fun CharSequence.bufferedGetPrefix(
    long: CharSequence,
    //isInBacktrack: Boolean = false, //`true` untuk menunjukan bahwa proses pencarian berada dalam tahap muncur setelah proses maju mengalami ketidak cocok char.
    start: Int,
    i: Int, //index dari `this`
    buffer: Int = ceil(length / 5.0f).toInt(), // ceil agar saat pembagian menghasil 0,... tetap dapat diiterasi dg buffer ini.
    decay: Float = 0.4f,
    direction: Int = 1, //1 = forward, -1 = rewind, 0 = illegal
    //sameDir: Boolean = true
): Int {
/*
    if(direction == 0)
        throw IllegalArgExc(
            paramExcepted = arrayOf("direction"),
            detailMsg = "Param `direction` hanya boleh + atau -."
        )
 */
    prine("this= $this long= $long i= $i buffer= $buffer decay= $decay dir= $direction")
    if(i < 0) return -1
    val len = length
    if(long.length < len)
        throw IllegalArgExc(
            paramExcepted = arrayOf("long"),
            detailMsg = "`long` lebih pendek dari `this`."
        )

    val i = if(i < len) i else len-1

    val u = i + start //index dari [long]
    if(len == 1 || buffer == 0)
        return if(this[i] == long[u]) u+1 else -1

/*
    if(buffer == 1 && isInBacktrack){
        val limit = long.lastIndex
        if(this[i] != long[u])
            return false
        val beforeBool = this[i-1] == long[u-1]
        if(buffer == limit)
            return beforeBool
        return beforeBool && this[i+1] == long[u+1]
    }
 */
    return if(this[i] == long[u]){
        bufferedGetPrefix(long, start, i + buffer, buffer, decay, 1)
    } else {
        val decBuff = ceil(buffer - buffer * decay).also { prine("ceil = $it") }.toInt()
        val sentBuff = if(direction < 0) decBuff else decBuff -1
        bufferedGetPrefix(long, start, i - decBuff, sentBuff, decay, -1)
    }
}