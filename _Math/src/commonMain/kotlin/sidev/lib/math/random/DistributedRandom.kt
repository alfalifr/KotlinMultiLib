package sidev.lib.math.random

import sidev.lib.collection.findIndexed
//import sidev.lib.console.prine
import sidev.lib.exception.IllegalStateExc
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


interface DistributedRandom<T> {
    val distSum: Int
    operator fun get(e: T): Int?
    operator fun set(e: T, distribution: Int)
    /**
     * Menambah distribusi [e] sebanyak [distribution].
     * Mengembalikan distribusi lama.
     */
    fun add(e: T, distribution: Int = 1): Int
    fun remove(e: T): Int?
    fun next(): T
    /** `true` jika tidak terdapat distribusi pada `this`. */
    fun isEmpty(): Boolean
}

internal class DistributedRandomImpl<T>: DistributedRandom<T> {
    internal val distributions: MutableList<Pair<T, Int>> = mutableListOf()
    private val keys: MutableSet<T> = mutableSetOf()
    override var distSum: Int= 0
        private set
    //private var maxDist= 0

    private fun search(dist: Int, left: Int= 0, right: Int= distributions.lastIndex): Int {
        val mid= (left + right) / 2
        val midLeft= max(mid - 1, left)
        val midRight= min(mid + 1, right)
        val midVal= distributions[mid].second
        val midLeftVal= distributions[midLeft].second
        val midRightVal= distributions[midRight].second
        //prine("search() dist= $dist left= $left right= $right mid= $mid midLeft= $midLeft midRight= $midRight")
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
                else mid - 1
            }
        }
    }

    override fun get(e: T): Int? = distributions.find { it.first == e }?.second
    override fun set(e: T, distribution: Int) {
        //var computeMaxDist= false
        //var index: Int
        if(e in keys){
            val (i, value)= distributions.findIndexed { it.value.first == e }!!
            val old= value.second
            distSum -= old
            //computeMaxDist= old == maxDist
            distributions[i] = e to distribution
        } else {
            val index= if(distributions.isNotEmpty()) search(distribution) else 0
            //prine("prev dists= $distributions")
            distributions.add(index, e to distribution)
            keys.add(e)
        }
        distSum += distribution
    }

    override fun add(e: T, distribution: Int): Int {
        val old= if(e in keys){
            val (i, value)= distributions.findIndexed { it.value.first == e }!!
            val old= value.second
            //computeMaxDist= old == maxDist
            //prine("prev old= $old")
            distributions[i] = e to old + distribution
            old
        } else {
            val index= if(distributions.isNotEmpty()) search(distribution) else 0
            //prine("prev dists= $distributions")
            //prine("prev new= $distribution")
            distributions.add(index, e to distribution)
            keys.add(e)
            0
        }
        distSum += distribution
        return old
    }

    override fun remove(e: T): Int? {
        val res= distributions.findIndexed { it.value.first == e }
        return if(res != null){
            distributions.removeAt(res.index)
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
        for ((key, dist) in distributions) {
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
}