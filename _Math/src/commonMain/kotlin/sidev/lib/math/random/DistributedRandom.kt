package sidev.lib.math.random

import sidev.lib.collection.copy
import sidev.lib.collection.findIndexed
//import sidev.lib.console.prine
import sidev.lib.exception.IllegalStateExc
import sidev.lib.reflex.getContentHashCode
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


interface DistributedRandom<T> {
    val distSum: Int
    val keys: Set<T>
    val distributions: Collection<Int>
    val entries: Collection<Pair<T, Int>>

    operator fun get(e: T): Int?
    operator fun set(e: T, distribution: Int)
    /**
     * Menambah distribusi [e] sebanyak [distribution].
     * Fungsi ini dapat digunakan untuk mengurangi distribusi
     * dengan memasukan [distribution] negatif.
     * Mengembalikan distribusi lama.
     */
    fun add(e: T, distribution: Int = 1): Int
    fun remove(e: T): Int?
    fun next(): T
    /** `true` jika tidak terdapat distribusi pada `this`. */
    fun isEmpty(): Boolean
}

internal class DistributedRandomImpl<T>: DistributedRandom<T> {
    internal val distributions_: MutableList<Pair<T, Int>> = mutableListOf()
    private val keys_: MutableSet<T> = mutableSetOf()
    override val keys: Set<T>
        get() = keys_.copy()
    override val distributions: Collection<Int>
        get() = distributions_.map { it.second }
    override val entries: Collection<Pair<T, Int>>
        get() = distributions_.copy()
    override var distSum: Int= 0
        private set
    //private var maxDist= 0

    private fun search(dist: Int, left: Int= 0, right: Int= distributions_.lastIndex): Int {
        val mid= (left + right) / 2
        val midLeft= max(mid - 1, left)
        val midRight= min(mid + 1, right)
        val midVal= distributions_[mid].second
        val midLeftVal= distributions_[midLeft].second
        val midRightVal= distributions_[midRight].second
        //prine("search() dist= $dist left= $left right= $right mid= $mid midLeft= $midLeft midRight= $midRight midLeftVal= $midLeftVal midRightVal= $midRightVal")
        return when {
            dist in midLeftVal..midRightVal -> {
                if(midVal < dist) mid + 1
                else mid
            }
            dist > midRightVal -> {
                if(midRight > mid) search(dist, midRight, right)
                else mid + 1
            }
            else /*dist < midLeftVal*/ -> {
                if(midLeft < mid) search(dist, left, midLeft)
                else mid //- 1
            }
        }
    }

    /**
     * [isIncreasing] == `true`, maka pencarian dilakukan dengan arah dari kiri ke kanan
     * dan mengganggap [checkDist] lebih besar dari elemen di kanannya dan perlu digeser kanan.
     */
    private fun searchNextSortedIndex(from: Int, checkDist: Int, isIncreasing: Boolean = true): Int {
        var isFirstFound= false
        if(isIncreasing){
            val lastIndex= distributions_.lastIndex
            for(i in from until distributions_.size){
                val (e, dist)= distributions_[i]
                if(!isFirstFound && dist <= checkDist)
                    isFirstFound= true
                else if(isFirstFound && (dist >= checkDist || i == lastIndex))
                    return i -1
            }
            if(isFirstFound)
                return lastIndex
        } else {
            for(i in from downTo 0){
                val (e, dist)= distributions_[i]
                if(!isFirstFound && dist >= checkDist)
                    isFirstFound= true
                else if(isFirstFound && (dist <= checkDist || i == 0))
                    return i +1
            }
            if(isFirstFound)
                return 0
        }
        return -1
    }

    override fun get(e: T): Int? = distributions_.find { it.first == e }?.second
    override fun set(e: T, distribution: Int) {
        //var computeMaxDist= false
        //var index: Int
        if(e in keys_){
            val (i, value)= distributions_.findIndexed { it.value.first == e }!!
            val old= value.second
            distSum -= old
            //computeMaxDist= old == maxDist
            distributions_[i] = e to distribution
        } else {
            val index= if(distributions_.isNotEmpty()) search(distribution) else 0
            //prine("prev dists= $distributions")
            distributions_.add(index, e to distribution)
            keys_.add(e)
        }
        distSum += distribution
    }

    override fun add(e: T, distribution: Int): Int {
        val old= if(e in keys_){
            val (i, value)= distributions_.findIndexed { it.value.first == e }!!
            val old= value.second
            //computeMaxDist= old == maxDist
            //prine("prev old= $old i= $i dists= $distributions")
            val newDist= old + distribution
            if(
                (distribution > 0 && i < distributions_.lastIndex && distributions_[i+1].second < newDist)
                || (distribution < 0 && i > 0 && distributions_[i-1].second > newDist)
            ){
                val fromInc: Int
                val isIncreasing= if(distribution > 0){
                    fromInc= 1
                    true
                } else {
                    fromInc= -1
                    false
                }
                if(newDist > 0){
                    val newIndex= searchNextSortedIndex(i + fromInc, newDist, isIncreasing)
                    distributions_.removeAt(i)
                    distributions_.add(newIndex, e to newDist)
                } else {
                    distributions_.removeAt(i)
                }
                //prine("prev dalem old= $old i= $i dists= $distributions")
            } else {
                distributions_[i] = e to newDist
            }
            old
        } else {
            if(distribution > 0){
                val index= if(distributions_.isNotEmpty()) search(distribution) else 0
                //prine("prev dists= $distributions")
                //prine("prev new= $distribution")
                distributions_.add(index, e to distribution)
                keys_.add(e)
            }
            0
        }
        distSum += distribution
        return old
    }

    override fun remove(e: T): Int? {
        val res= distributions_.findIndexed { it.value.first == e }
        return if(res != null){
            distributions_.removeAt(res.index)
            val dist= res.value.second
            distSum -= dist
            dist
        } else {
            null
        }
    }

    override fun next(): T {
/*
        //prine("DistRandom.next() distSum= $distSum maxDist= $maxDist")
        return if(distSum > maxDist){
            var maxProb= Random.nextDouble()
            val thisMaxProp= maxDist / distSum.toDouble()
            //prine("DistRandom.next() maxProb= $maxProb thisMaxProp= $thisMaxProp")
            while(maxProb < thisMaxProp){
                maxProb= Random.nextDouble()
                //prine("DistRandom.next() maxProb= $maxProb thisMaxProp= $thisMaxProp")
            }

            val keys= distributions.keys
            var chosen: T
            do {
                chosen= keys.random()
                val chosenProb= distributions[chosen]!! / distSum.toDouble()
                //prine("DistRandom.next() chosen= $chosen chosenProb= $chosenProb maxProb= $maxProb")
            } while(chosenProb > maxProb)
            chosen
        } else {
            distributions.keys.first()
        }
 */

        val randomRatio: Double = Random.nextDouble() //java.lang.Math.random()
        //val ratio = 1.0 / distSum
        var tempDist = 0
        //prine("DistRandom.next() distSum= $distSum maxDist= $maxDist rand= $randomRatio")
        for ((key, dist) in distributions_) {
            tempDist += dist
            //prine("DistRandom.next() distSum= $distSum rand= $randomRatio rand * distSum= ${randomRatio * distSum} tempDist= $tempDist")
            if (randomRatio * distSum <= tempDist) {
                return key
            }
        }
        throw IllegalStateExc(
            stateOwner = this::class,
            currentState = "`randomRatio` ($randomRatio) > 1",
            expectedState = "`randomRatio` ($randomRatio) <= 1",
            detMsg = "Terjadi kesalahan internal. Seharusnya tidak mungkin `randomRatio` melebihi 1 karena `randomRatio` merupakan kemungkinan (0-1)"
        )
    }

    override fun isEmpty(): Boolean = distSum == 0
    override fun toString(): String = "DistributedRandom{${distributions_.joinToString()}}"
    override fun hashCode(): Int = getContentHashCode(distributions_, false)
    override fun equals(other: Any?): Boolean = other is DistributedRandom<*> && other.hashCode() == hashCode()
}