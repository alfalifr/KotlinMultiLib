package sidev.lib.collection


/*
=============================
Grow List Function
=============================
 */

//======List==========
fun <T> MutableList<T>.growIncremental(factor: Int){
    val initSize= this.size
    for(i in 0 until factor)
        this.add(this[i % initSize])
}
fun <T> MutableList<T>.growTimely(factor: Int){
    val initSize= this.size
    for(i in 0 until factor)
        for(u in 0 until initSize)
            this.add(this[u])
}
fun <T> MutableList<T>.growExponentially(factor: Int){
    val initSize= this.size
    for(i in 0 until factor)
        for(u in 0 until factor)
            for(e in 0 until initSize)
                this.add(this[e])
}

fun <T> List<T>.copyGrowIncremental(factor: Int, collIn: MutableCollection<T>?= null): MutableCollection<T>{
    val newList= collIn ?: ArrayList(this)
    val initSize= this.size
    for(i in 0 until factor)
        newList.add(this[i % initSize])
    return newList
}
fun <T> List<T>.copyGrowTimely(factor: Int, collIn: MutableCollection<T>?= null): MutableCollection<T>{
    val newList= collIn ?: ArrayList(this)
    val initSize= this.size
    for(i in 0 until factor)
        for(u in 0 until initSize)
            newList.add(this[u])
    return newList
}
fun <T> List<T>.copyGrowExponentially(factor: Int, collIn: MutableCollection<T>?= null): MutableCollection<T>{
    val newList= collIn ?: ArrayList(this)
    val initSize= this.size
    for(i in 0 until factor)
        for(u in 0 until factor)
            for(e in 0 until initSize)
                newList.add(this[e])
    return newList
}

//======Array==========
fun <T> Array<T>.copyGrowIncremental(factor: Int, collIn: MutableCollection<T>?= null): MutableCollection<T>{
    val newList= collIn ?: ArrayList(asList())
    val initSize= this.size
    for(i in 0 until factor)
        newList.add(this[i % initSize])
    return newList
}
fun <T> Array<T>.copyGrowTimely(factor: Int, collIn: MutableCollection<T>?= null): MutableCollection<T>{
    val newList= collIn ?: ArrayList(asList())
    val initSize= this.size
    for(i in 0 until factor)
        for(u in 0 until initSize)
            newList.add(this[u])
    return newList
}
fun <T> Array<T>.copyGrowExponentially(factor: Int, collIn: MutableCollection<T>?= null): MutableCollection<T>{
    val newList= collIn ?: ArrayList(asList())
    val initSize= this.size
    for(i in 0 until factor)
        for(u in 0 until factor)
            for(e in 0 until initSize)
                newList.add(this[e])
    return newList
}
