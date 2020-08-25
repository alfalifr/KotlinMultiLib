package sidev.lib.collection

/** Mengambil bbrp elemen dari `this.extension` List dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> List<T>.get(range: IntRange): List<T> = subList(range.first, range.last)

/** Mengambil bbrp elemen dari `this.extension` Array dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> Array<T>.get(range: IntRange): Array<T> = sliceArray(range)

/** Sama seperti [first] sekaligus mengahpus element pertama */
fun <T> MutableList<T>.takeFirst(): T = if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(0)

/** Sama seperti [firstOrNull] sekaligus mengahpus element pertama
 *  Di Kotlin sama seperti [removeFirstOrNull].
 * */
fun <T> MutableList<T>.takeFirstOrNull(): T? = if(isEmpty()) null else removeAt(0)

/** Sama seperti [last] sekaligus mengahpus element pertama */
fun <T> MutableList<T>.takeLast(): T = if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(lastIndex)

/** Sama seperti [lastOrNull] sekaligus mengahpus element pertama */
fun <T> MutableList<T>.takeLastOrNull(): T? = if(isEmpty()) null else removeAt(lastIndex)

/** Sama seperti [remove], namun menghapus last occurrence. */
fun <T> MutableList<T>.removeLast(element: T): Boolean{ //= if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(lastIndex)
    for(i in size -1 downTo 0)
        if(this[i] == element){
            removeAt(i)
            return true
        }
    return false
}


/**
 * @return true jika [element] tidak terdapat sebelumnya di list ini.
 * Fungsi ini menggunakan standard equals().
 */
inline fun <T> MutableList<T>.addIfAbsent(element: T, chekcFun: ((existingElement: T) -> Boolean)= {true}): Boolean{
    val existingElementIndex= indexOf(element)
    val canAdd= existingElementIndex < 0 || !chekcFun(this[existingElementIndex])
    if(canAdd)
        this.add(element)
    return canAdd
}


inline fun <reified T> Array<T>.copy(reversed: Boolean= false): Array<T> {
    return if(!reversed) this.copyOf()
    else Array(this.size){this[size -it -1]}
}

fun <T> List<T>.copy(reversed: Boolean= false): List<T> {
    val newList= mutableListOf<T>()
    val range= if(!reversed) this.indices
    else this.size .. 0
    for(i in range)
        newList.add(this[i])
    return newList
}