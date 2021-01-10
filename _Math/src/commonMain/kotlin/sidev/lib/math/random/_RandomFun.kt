package sidev.lib.math.random

import sidev.lib.collection.array.countDuplication
import kotlin.random.Random


fun <T> distRandomOf(vararg e: T): DistributedRandom<T> = DistributedRandomImpl<T>().apply {
    if(e.isNotEmpty())
        e.countDuplication().forEach { (e_, dist) ->
            this[e_]= dist
        }
}

fun randomBoolean(prob: Double): Boolean {
    val rand= Random.nextDouble()
    //val limit= if(prob < 1.0) prob else Random.nextDouble()
    return rand <= prob
}
fun randomBoolean(): Boolean {
    val rand= Random.nextDouble()
    val limit= Random.nextDouble()
    return rand <= limit
}