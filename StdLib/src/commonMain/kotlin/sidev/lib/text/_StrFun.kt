package sidev.lib.text

//TODO <29 Juli 2020> => Buat validator angka (menghilangkan 0 di depan angka).
fun <T> T.toString(func: (obj: T) -> String): String{
    return func(this)
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

fun CharSequence.nextNonWhitespaceChar(startInd: Int= 0): Char? {
    for(i in startInd until this.length){
        if(!this[i].isWhitespace()){
            return this[i]
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