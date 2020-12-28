package sidev.lib.algo

import sidev.lib.`val`.Order
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.number.*
import sidev.lib.structure.data.Arrangeable
import sidev.lib.structure.data.RangeCopyable


/*
====================
Arrangeable
====================
 */
fun <E> Arrangeable<E>.swap(i: Int, u: Int) {
    val tmp= this[i]
    set_(i, this[u])
    set_(u, tmp)
}

//inline fun <R: Comparable<R>> inlineCompareFun(func: (R, R) -> Boolean, n1: R, n2: R): Boolean = func(n1, n2)


fun <E: Comparable<*>> Arrangeable<E>.selectionSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    selectionSortBy (from, until, order) { it }
fun <E, R: Comparable<*>> Arrangeable<E>.selectionSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (E) -> R
) = selectionSortWith(from, until, order) { n1, n2 ->
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    (toComparableFun(n1) as Comparable<R>).compareTo(toComparableFun(n2))
}
/*
{
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::univMoreThan else ::univLessThan
//        if(order == Order.ASC) { n1, n2 -> n1 > n2 } else { n1, n2 -> n1 < n2 }
    for(i in from until until){
        var iVal= this[i]
        var iValComparable= toComparableFun(iVal)
        for(u in i+1 until until){
            val uVal= this[u]
            if(replaceFun(iValComparable, toComparableFun(uVal))){
                set_(i, uVal)
                set_(u, iVal)
                iVal= uVal
                iValComparable= toComparableFun(iVal)
            }
        }
    }
}
 */
fun <E> Arrangeable<E>.selectionSortWith(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, comparator: (n1: E, n2: E) -> Int
){
    val replaceFun: (Int, Int) -> Boolean = if(order == Order.ASC) ::univMoreThan else ::univLessThan
//        if(order == Order.ASC) { n1, n2 -> n1 > n2 } else { n1, n2 -> n1 < n2 }
    for(i in from until until){
        var iVal= this[i]
//        var iValComparable= toComparableFun(iVal)
        for(u in i+1 until until){
            val uVal= this[u]
            if(replaceFun(comparator(iVal, uVal), 0)){
                set_(i, uVal)
                set_(u, iVal)
                iVal= uVal
//                iValComparable= toComparableFun(iVal)
            }
        }
    }
}


fun <E: Comparable<*>> Arrangeable<E>.insertionSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    insertionSortBy(from, until, order) { it }
fun <E, R: Comparable<*>> Arrangeable<E>.insertionSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (E) -> R
) = insertionSortWith(from, until, order) { n1, n2 ->
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    (toComparableFun(n1) as Comparable<R>).compareTo(toComparableFun(n2))
}
/*
{
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::univMoreThan else ::univLessThan
    for(i in from until until-1){
        val tailVal= this[i+1]
        val tailValComparable= toComparableFun(tailVal)
        if(replaceFun(toComparableFun(this[i]), tailValComparable)){
            var head= from
            for(u in i downTo from){
                val uVal= this[u]
                if(replaceFun(tailValComparable, toComparableFun(uVal))){
                    head= u+1
                    break
                }
                set_(u+1, uVal)
            }
            set_(head, tailVal)
        }
    }
}
 */
fun <E> Arrangeable<E>.insertionSortWith(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, comparator: (n1: E, n2: E) -> Int
){
    val replaceFun: (Int, Int) -> Boolean = if(order == Order.ASC) ::univMoreThan else ::univLessThan
    for(i in from until until-1){
        val tailVal= this[i+1]
//        val tailValComparable= toComparableFun(tailVal)
        if(replaceFun(comparator(this[i], tailVal), 0)){
            var head= from
            for(u in i downTo from){
                val uVal= this[u]
                if(replaceFun(comparator(tailVal, uVal), 0)){
                    head= u+1
                    break
                }
                set_(u+1, uVal)
            }
            set_(head, tailVal)
        }
    }
}
//1,2,4,1,5,1,5,12,42,23,11,10,15,14
//0 1 2 3 4 5 6 7  8  9  10 11 12 13 //14
//l           r
//
fun <T, E: Comparable<*>> T.mergeSort(from: Int = 0, until: Int = size, order: Order= Order.ASC)
        where T: Arrangeable<E>, T: RangeCopyable<T> = mergeSortBy(from, until, order) { it }
fun <T, E, R: Comparable<*>> T.mergeSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (E) -> R
) where T: Arrangeable<E>, T: RangeCopyable<T> = mergeSortWith(from, until, order) { n1, n2 ->
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    (toComparableFun(n1) as Comparable<R>).compareTo(toComparableFun(n2))
}
/*
{
    val checkFun: (R, R) -> Boolean = if(order == Order.ASC) ::univLessThanEqual else ::univMoreThanEqual

    fun merge(left: Int, middle: Int, right: Int){
//        prine("mergeSort.merge() left= $left right= $right size= $size")
        var all= left
        var l= 0
//        val mid= (left + right) ushr 1
        var r= 0 //(right - left) ushr 1

        val nl= middle - left +1 //Ditambah 1 agar saat pembagian array dg size 2 dapat dibagi menjadi 1 dan 1.
        val nr= right - middle //+ 1
        val lArrOuterIndex= middle +1

//        prine("mergeSort.merge() left= $left right= $right nl= $nl nr= $nr l= $l r= $r mid= $middle")

        val lArr= copy(left, lArrOuterIndex)
//            .also { prine("mergeSort.merge() lArr= '${it.joinToString()}'") }
        val rArr= copy(lArrOuterIndex, right +1)
//            .also { prine("mergeSort.merge() rArr= '${it.joinToString()}'") }

        if(l < nl && r < nr){
            var lVal= lArr[l]
            var rVal= rArr[r]
            while(l < nl && r < nr){
//                prine("mergeSort.merge() lVal= $lVal rVal= $rVal")
                if(checkFun(toComparableFun(lVal), toComparableFun(rVal))){
                    set_(all++, lVal)
                    if(++l < nl)
                        lVal= lArr[l]
                } else {
                    set_(all++, rVal)
                    if(++r < nr)
                        rVal= rArr[r]
                }
            }
        }

//        prine("mergeSort.merge() left= $left right= $right l= $l r= $r m= $middle all= $all nl= $nl nr= $nr")

        while(l < nl)
            set_(all++, lArr[l++])
//                .also { prine("mergeSort.merge() lVal= $it") }

        while(r < nr)
            set_(all++, rArr[r++])
//                .also { prine("mergeSort.merge() rVal= $it") }

//        if(all < right){ }
    }

    fun sort(
        left: Int, right: Int,
    ){
//        prine("mergeSort.sort() AWAL l= $left r= $right size= $size this= ${joinToString()}")

        if(left >= right) return
        val mid= (left + right) ushr 1

//        prine("mergeSort.sort() l= $left r= $right mid= $mid")

        sort(left, mid) //Sort left part
        sort(mid+1, right) //Sort right part
        merge(left, mid, right)

//        prine("mergeSort.sort() AKHIR this= ${joinToString()}")
    }

    sort(from, until -1)
}
 */
fun <T, E> T.mergeSortWith(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, //tieBreaker: ((n1: E, n2: E) -> Int),
    comparator: (n1: E, n2: E) -> Int
) where T: Arrangeable<E>, T: RangeCopyable<T> {
    val checkFun: (Int, Int) -> Boolean = if(order == Order.ASC) ::univLessThanEqual else ::univMoreThanEqual

    fun merge(left: Int, middle: Int, right: Int){
//        prine("mergeSort.merge() left= $left right= $right size= $size")
        var all= left
        var l= 0
//        val mid= (left + right) ushr 1
        var r= 0 //(right - left) ushr 1

        val nl= middle - left +1 //Ditambah 1 agar saat pembagian array dg size 2 dapat dibagi menjadi 1 dan 1.
        val nr= right - middle //+ 1
        val lArrOuterIndex= middle +1

//        prine("mergeSort.merge() left= $left right= $right nl= $nl nr= $nr l= $l r= $r mid= $middle")

        val lArr= copy(left, lArrOuterIndex)
//            .also { prine("mergeSort.merge() lArr= '${it.joinToString()}'") }
        val rArr= copy(lArrOuterIndex, right +1)
//            .also { prine("mergeSort.merge() rArr= '${it.joinToString()}'") }

        if(l < nl && r < nr){
            var lVal= lArr[l]
            var rVal= rArr[r]
            while(l < nl && r < nr){
//                prine("mergeSort.merge() lVal= $lVal rVal= $rVal")
                if(checkFun(comparator(lVal, rVal), 0)){
                    set_(all++, lVal)
                    if(++l < nl)
                        lVal= lArr[l]
                } else {
                    set_(all++, rVal)
                    if(++r < nr)
                        rVal= rArr[r]
                }
            }
        }

//        prine("mergeSort.merge() left= $left right= $right l= $l r= $r m= $middle all= $all nl= $nl nr= $nr")

        while(l < nl)
            set_(all++, lArr[l++])
//                .also { prine("mergeSort.merge() lVal= $it") }

        while(r < nr)
            set_(all++, rArr[r++])
//                .also { prine("mergeSort.merge() rVal= $it") }

//        if(all < right){ }
    }

    fun sort(
        left: Int, right: Int,
    ){
//        prine("mergeSort.sort() AWAL l= $left r= $right size= $size this= ${joinToString()}")

        if(left >= right) return
        val mid= (left + right) ushr 1

//        prine("mergeSort.sort() l= $left r= $right mid= $mid")

        sort(left, mid) //Sort left part
        sort(mid+1, right) //Sort right part
        merge(left, mid, right)

//        prine("mergeSort.sort() AKHIR this= ${joinToString()}")
    }

    sort(from, until -1)
}


fun <E: Comparable<*>> Arrangeable<E>.quickSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    quickSortBy(from, until, order) { it }
fun <E, R: Comparable<*>> Arrangeable<E>.quickSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (E) -> R
) = quickSortWith(from, until, order) { n1, n2 ->
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    (toComparableFun(n1) as Comparable<R>).compareTo(toComparableFun(n2))
}
/*
{
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::univLessThan else ::univMoreThan

    /**
     * Return index dari pembatas partisi.
     */
    fun partition(left: Int, right: Int): Int{
//        prine("quickSort.partition() left= $left right= $right")
        val lastVal= this[right]
        val lastValComparable= toComparableFun(lastVal)

        var swapIndex= left //-1
        for(i in left until right){
            if(replaceFun(toComparableFun(this[i]), lastValComparable)){
                val temp= this[i]
                set_(i, this[swapIndex])
                set_(swapIndex++, temp)
            }
        }
//        swap(swapIndex, right)
        val temp= this[swapIndex]
        set_(swapIndex, lastVal)
        set_(right, temp)
        return swapIndex
    }

    fun sort(left: Int, right: Int){
        if(left >= right) return
        val mid= partition(left, right) //(left + right) ushr 1
        if(mid > 0)
            sort(left, mid-1)
        sort(mid+1, right)
    }

    sort(from, until -1)
}
 */
fun <E> Arrangeable<E>.quickSortWith(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, comparator: (n1: E, n2: E) -> Int
){
    val replaceFun: (Int, Int) -> Boolean = if(order == Order.ASC) ::univLessThan else ::univMoreThan

    /**
     * Return index dari pembatas partisi.
     */
    fun partition(left: Int, right: Int): Int{
//        prine("quickSort.partition() left= $left right= $right")
        val lastVal= this[right]
//        val lastValComparable= toComparableFun(lastVal)

        var swapIndex= left //-1
        for(i in left until right){
            if(replaceFun(comparator(this[i], lastVal), 0)){
                val temp= this[i]
                set_(i, this[swapIndex])
                set_(swapIndex++, temp)
            }
        }
//        swap(swapIndex, right)
        val temp= this[swapIndex]
        set_(swapIndex, lastVal)
        set_(right, temp)
        return swapIndex
    }

    fun sort(left: Int, right: Int){
        if(left >= right) return
        val mid= partition(left, right) //(left + right) ushr 1
        if(mid > 0)
            sort(left, mid-1)
        sort(mid+1, right)
    }

    sort(from, until -1)
}




/*
====================
Array
====================
 */
fun <T> Array<T>.swap(i: Int, u: Int) {
    val tmp= this[i]
    this[i]= this[u]
    this[u]= tmp
}

inline fun <R: Comparable<R>> inlineCompareFun(func: (R, R) -> Boolean, n1: R, n2: R): Boolean = func(n1, n2)


fun <T: Comparable<T>> Array<T>.selectionSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    selectionSortBy (from, until, order) { it }
fun <T, R: Comparable<R>> Array<T>.selectionSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (T) -> R
){
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::moreThan else ::lessThan
//        if(order == Order.ASC) { n1, n2 -> n1 > n2 } else { n1, n2 -> n1 < n2 }
    for(i in from until until){
        var iVal= this[i]
        var iValComparable= toComparableFun(iVal)
        for(u in i+1 until until){
            val uVal= this[u]
            if(replaceFun(iValComparable, toComparableFun(uVal))){
                this[i]= uVal
                this[u]= iVal
                iVal= uVal
                iValComparable= toComparableFun(iVal)
            }
        }
    }
}


fun <T: Comparable<T>> Array<T>.insertionSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    insertionSortBy(from, until, order) { it }
fun <T, R: Comparable<R>> Array<T>.insertionSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (T) -> R
){
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::moreThan else ::lessThan
    for(i in from until until-1){
        val tailVal= this[i+1]
        val tailValComparable= toComparableFun(tailVal)
        if(replaceFun(toComparableFun(this[i]), tailValComparable)){
            var head= from
            for(u in i downTo from){
                val uVal= this[u]
                if(replaceFun(tailValComparable, toComparableFun(uVal))){
                    head= u+1
                    break
                }
                this[u+1]= uVal
            }
            this[head]= tailVal
        }
    }
}
//1,2,4,1,5,1,5,12,42,23,11,10,15,14
//0 1 2 3 4 5 6 7  8  9  10 11 12 13 //14
//l           r
//
fun <T: Comparable<T>> Array<T>.mergeSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    mergeSortBy(from, until, order) { it }
fun <T, R: Comparable<R>> Array<T>.mergeSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (T) -> R
){
    val checkFun: (R, R) -> Boolean = if(order == Order.ASC) ::lessThanEqual else ::moreThanEqual

    fun Array<T>.merge(left: Int, middle: Int, right: Int){
//        prine("mergeSort.merge() left= $left right= $right size= $size")
        var all= left
        var l= 0
//        val mid= (left + right) ushr 1
        var r= 0 //(right - left) ushr 1

        val nl= middle - left +1 //Ditambah 1 agar saat pembagian array dg size 2 dapat dibagi menjadi 1 dan 1.
        val nr= right - middle //+ 1
        val lArrOuterIndex= middle +1

//        prine("mergeSort.merge() left= $left right= $right nl= $nl nr= $nr l= $l r= $r mid= $middle")

        val lArr= copyOfRange(left, lArrOuterIndex)
//            .also { prine("mergeSort.merge() lArr= '${it.joinToString()}'") }
        val rArr= copyOfRange(lArrOuterIndex, right +1)
//            .also { prine("mergeSort.merge() rArr= '${it.joinToString()}'") }

        if(l < nl && r < nr){
            var lVal= lArr[l]
            var rVal= rArr[r]
            while(l < nl && r < nr){
//                prine("mergeSort.merge() lVal= $lVal rVal= $rVal")
                if(checkFun(toComparableFun(lVal), toComparableFun(rVal))){
                    this[all++]= lVal
                    if(++l < nl)
                        lVal= lArr[l]
                } else {
                    this[all++]= rVal
                    if(++r < nr)
                        rVal= rArr[r]
                }
            }
        }

//        prine("mergeSort.merge() left= $left right= $right l= $l r= $r m= $middle all= $all nl= $nl nr= $nr")

        while(l < nl)
            this[all++]= lArr[l++]
//                .also { prine("mergeSort.merge() lVal= $it") }

        while(r < nr)
            this[all++]= rArr[r++]
//                .also { prine("mergeSort.merge() rVal= $it") }

//        if(all < right){ }
    }

    fun Array<T>.sort(
        left: Int, right: Int,
    ){
//        prine("mergeSort.sort() AWAL l= $left r= $right size= $size this= ${joinToString()}")

        if(left >= right) return
        val mid= (left + right) ushr 1

//        prine("mergeSort.sort() l= $left r= $right mid= $mid")

        sort(left, mid) //Sort left part
        sort(mid+1, right) //Sort right part
        merge(left, mid, right)

//        prine("mergeSort.sort() AKHIR this= ${joinToString()}")
    }

    sort(from, until -1)
}


fun <T: Comparable<T>> Array<T>.quickSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    quickSortBy(from, until, order) { it }
fun <T, R: Comparable<R>> Array<T>.quickSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (T) -> R
){
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::lessThan else ::moreThan

    /**
     * Return index dari pembatas partisi.
     */
    fun Array<T>.partition(left: Int, right: Int): Int{
//        prine("quickSort.partition() left= $left right= $right")
        val lastVal= this[right]
        val lastValComparable= toComparableFun(lastVal)

        var swapIndex= left //-1
        for(i in left until right){
            if(replaceFun(toComparableFun(this[i]), lastValComparable)){
                val temp= this[i]
                this[i]= this[swapIndex]
                this[swapIndex++]= temp
            }
        }
//        swap(swapIndex, right)
        val temp= this[swapIndex]
        this[swapIndex]= lastVal
        this[right]= temp
        return swapIndex
    }

    fun Array<T>.sort(left: Int, right: Int){
        if(left >= right) return
        val mid= partition(left, right) //(left + right) ushr 1
        if(mid > 0)
            sort(left, mid-1)
        sort(mid+1, right)
    }

    sort(from, until -1)
}