package sidev.lib.collection


/** Mengambil element pada [index] pada semua list yg disimpan dalam `this.extension`. */
fun <T> List<List<T>>.getAtAllLevelAt(index: Int): List<T>{
    val res= ArrayList<T>()
    for(list in this){
        res += list[index]
    }
    return res
}
/** Mengambil element pada [index] pada semua array yg disimpan dalam `this.extension`. */
inline fun <reified T> Array<Array<T>>.getAtAllLevelAt(index: Int): Array<T> = Array(size){ this[it][index] }


/** Mengambil [Collection] dg jml size paling kecil. [includeEmpty] == true, maka hasil @return juga menyertakan empty-collection. */
fun <C: Collection<T>, T> minSize(vararg collections: C, includeEmpty: Boolean= true): C?{
    var collOut: C?= null //collections.first()
//    if(collOut.isEmpty() && includeEmpty || collOut.size == 1) return collOut

    for(c in collections)
        if(c.size < collOut?.size ?: 0 || collOut == null){
            if(c.isEmpty() && includeEmpty || c.size == 1) return c
            collOut= c
        }
    return collOut
}
/** Mengambil [Array] dg jml size paling kecil. [includeEmpty] == true, maka hasil @return juga menyertakan empty-array. */
fun <T> minSize(vararg arrays: Array<T>, includeEmpty: Boolean= true): Array<T>?{
    var arrayOut: Array<T>?= null //arrays.first()
//    if(arrayOut.isEmpty() && includeEmpty || arrayOut.size == 1) return arrayOut

    for(arr in arrays)
        if(arr.size < arrayOut?.size ?: 0 || arrayOut == null){
            if(arr.isEmpty() && includeEmpty || arr.size == 1) return arr
            arrayOut= arr
        }
    return arrayOut
}


/** Mengambil [Collection] dg jml size paling besar. [includeEmpty] == true, maka hasil @return juga menyertakan empty-collection. */
fun <C: Collection<T>, T> maxSize(vararg collections: C, includeEmpty: Boolean= true): C?{
    var collOut: C?= null //collections.first()
    for(c in collections)
        if(c.size > collOut?.size ?: 0 || collOut == null){
            if(c.isEmpty() && !includeEmpty) continue
            collOut= c
        }
    return collOut
}
/** Mengambil [Array] dg jml size paling besar. [includeEmpty] == true, maka hasil @return juga menyertakan empty-array. */
fun <T> maxSize(vararg arrays: Array<T>, includeEmpty: Boolean= true): Array<T>?{
    var arrayOut: Array<T>?= null //arrays.first()
    for(arr in arrays)
        if(arr.size > arrayOut?.size ?: 0 || arrayOut == null){
            if(arr.isEmpty() && !includeEmpty) continue
            arrayOut= arr
        }
    return arrayOut
}


/** Size paling kecil dari bbrp [Collection] yg disimpan dalam `this.extension`. */
val <T> Collection<Collection<T>>.minSize: Int
    get()= minSize(*toTypedArray())?.size ?: 0
/** Size paling kecil dari bbrp [Array] yg disimpan dalam `this.extension`. */
val <T> Array<Array<T>>.minSize: Int
    get()= minSize(*this)?.size ?: 0

/** Size paling besar dari bbrp [Collection] yg disimpan dalam `this.extension`. */
val <T> Collection<Collection<T>>.maxSize: Int
    get()= maxSize(*toTypedArray())?.size ?: 0
/** Size paling besar dari bbrp [Array] yg disimpan dalam `this.extension`. */
val <T> Array<Array<T>>.maxSize: Int
    get()= maxSize(*this)?.size ?: 0


/** Size paling besar dari bbrp [Collection] yg disimpan dalam `this.extension`. */
val <T> Collection<Collection<T>>.totalSize: Int
    get(){
        var size= 0
        for(coll in this)
            size += coll.size
        return size
    }
/** Size paling besar dari bbrp [Array] yg disimpan dalam `this.extension`. */
val <T> Array<Array<T>>.totalSize: Int
    get(){
        var size= 0
        for(array in this)
            size += array.size
        return size
    }

/** Size paling kecil dari bbrp [Collection] yg disimpan dalam `this.extension`. */
val <T> List<List<T>>.leveledIterator: Iterator<List<T>>
    get()= object: Iterator<List<T>>{
        val size= minSize
        var index= 0
        override fun hasNext(): Boolean = index < size
        override fun next(): List<T> = getAtAllLevelAt(index++)
    }
/** Size paling kecil dari bbrp [Collection] yg disimpan dalam `this.extension`. */
inline val <reified T> Array<Array<T>>.leveledIterator: Iterator<Array<T>>
    get()= object: Iterator<Array<T>>{
        val size= minSize
        var index= 0
        override fun hasNext(): Boolean = index < size
        override fun next(): Array<T> = getAtAllLevelAt(index++)
    }

/** Mengambil list yg berisi [Pair.first] dari `this.extension` [Array]. */
val <A, B> Array<out Pair<A, B>>.firstList: List<A>
    get(){
        val res= ArrayList<A>()
        for(pair in this)
            res += pair.first
        return res
    }
/** Mengambil list yg berisi [Pair.second] dari `this.extension` [Array]. */
val <A, B> Array<out Pair<A, B>>.secondList: List<B>
    get(){
        val res= ArrayList<B>()
        for(pair in this)
            res += pair.second
        return res
    }