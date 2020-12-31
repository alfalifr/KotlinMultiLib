package sidev.lib.algo

import sidev.lib.`val`.SuppressLiteral


fun strGreedySearch(long: String, sub: String): Int {
    var start= -1
    val slen= sub.length
    var i= 0
    for(j in long.indices) {
        if(long[j] != sub[i]){
            i= 0
            start= -1
            continue
        }
        //else {
            if(start == -1)
                start= j
            if(++i == slen)
                return start
        //}
    }
    return -1
}

/**
 * Mencari index dimulainya [sub] dalam [long].
 * Return -1 jika [sub] tidak terdapat di dalam [long].
 */
fun knuthMorrisPratt(long: String, sub: String): Int {
    val slen= sub.length
//    prine("knuttMorrisPratt long= $long sub= $sub slen= $slen")

    // 1. Membuat prefix-suffix table untuk memudahkan pencarian panjangnya index untuk mundur.
    val prefTable = IntArray(slen)

    var prefLen= 1
    var i= 0
    var c1= sub[0]
    for(j in 1 until slen){
        if(c1 == sub[j]){
            prefTable[j]= prefLen++
            c1= sub[++i]
        }
    }

//    prine("knuttMorrisPratt prefTable= ${prefTable.joinToString()}")

    // 2. Proses mencocokan terhadap [long].
    i= 0 //index sub
    var j= 0
    val llen= long.length
    var start= -1
    while(j < llen){
        @Suppress(SuppressLiteral.NAME_SHADOWING)
        //val c1= sub[i]
        //val c2= long[j]
//        prine("c1= $c1 c2= $c2 i= $i j= $j")
        if(sub[i] != long[j]){
            start= -1
            if(i == 0) j++
            else i= prefTable[i-1]
            continue
        }
        //else {
            if(start == -1)
                start= j
            if(++i == slen)
                return start
            j++
        //}
    }
    return -1
}

/**
 * Boyer-Moore rule #1: Bad Character.
 */
fun boyerMoore1(long: String, sub: String): Int {
    val slen= sub.length
    val slast= slen-1
    val llen= long.length
    var i= slast
    var shift= 0
    while(i >= 0){
        val cl= long[i+shift]
        if(sub[i] != cl){
            while(--i >= 0){
                shift++
                if(sub[i] == cl)
                    break
            }
            if(shift + slen > llen)
                return -1
            i= slast
        } else
            i--
    }
    return shift
}