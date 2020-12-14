package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness

abstract class NumberProgressionIterator<T>(
    first: T, val last: T, val step: T,
    val startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    val endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE
): IntervalProgressionIterator<T, T> where T: Number, T: Comparable<T> {
//    private var hasNext: Boolean = true //if(step > 0) first <= last else first >= last
    private var next: T= first //if(hasNext) first else last
    private var status: Int= -2 //0 berhenti, 1 lanjut, -1 tidak diketahui, -2 init

    abstract override fun nextStep(prev: T, step: T): T
    abstract override fun hasNext(prev: T, next: T, last: T, exclusiveness: Exclusiveness): Boolean

    private fun calculateNext(){
        if(status >= 0) //status udah diketahui
            return

        val prev= next
        if(status == -1 || startExclusiveness == Exclusiveness.EXCLUSIVE)
            next= nextStep(next, step)

        status=
            if(status == -1){ // Jika tidak dalam init.
                if(hasNext(prev, next, last, endExclusiveness)) 1
                else 0
            } else { // Jika init,
                if(startExclusiveness == Exclusiveness.INCLUSIVE && endExclusiveness == Exclusiveness.INCLUSIVE){ //Jika sama-sama inklusif,
                    if(prev == last) 1 //Jika first == last, maka start jelas ikut.
                    else {
                        if(hasNext(prev, next, last, endExclusiveness)) 1 else 0
                          //Jika first != last, blum tentu start bisa ikut.
                          // Hal tersebut dikarenakan first dan last tidak sesuai dg step-nya.
                    }
                }
                else { //Jika salah satunya eksklusif,
                    if(prev != last){
                        if(hasNext(prev, next, last, endExclusiveness)) 1 else 0
                          //Jika first != last, blum tentu start bisa ikut.
                          // Hal tersebut dikarenakan first dan last tidak sesuai dg step-nya.
                    } else 0 //Jika first == last, jelas start tidak ikut.
                }
            }
    }

    override fun hasNext(): Boolean {
        if(status < 0)
            calculateNext()
        return status == 1
    }
    override fun next(): T {
        if(status < 0)
            calculateNext()
        if(status == 0)
            throw NoSuchElementException("Iterasi habis")
        status= -1
        return next
    }
}