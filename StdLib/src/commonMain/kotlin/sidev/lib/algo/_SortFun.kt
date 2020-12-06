package sidev.lib.algo

import sidev.lib.`val`.Order
import sidev.lib.console.prine
import sidev.lib.number.univLessThan
import sidev.lib.number.univLessThanEqual
import sidev.lib.number.univMoreThan
import sidev.lib.number.univMoreThanEqual

fun <T> Array<T>.swap(i: Int, u: Int) {
    val tmp= this[i]
    this[i]= this[u]
    this[u]= tmp
}

fun <T: Comparable<T>> Array<T>.selectionSort(from: Int = 0, until: Int = size, order: Order= Order.ASC) =
    selectionSortBy (from, until, order) { it }
fun <T, R: Comparable<R>> Array<T>.selectionSortBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (T) -> R
){
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::univMoreThan else ::univLessThan
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
    val checkFun: (R, R) -> Boolean = if(order == Order.ASC) ::univLessThanEqual else ::univMoreThanEqual

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
    val replaceFun: (R, R) -> Boolean = if(order == Order.ASC) ::univLessThan else ::univMoreThan

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