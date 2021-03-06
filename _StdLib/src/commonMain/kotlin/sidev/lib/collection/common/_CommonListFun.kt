package sidev.lib.collection.common

//import sidev.lib.collection.sort
import sidev.lib.`val`.Order
import sidev.lib.collection.string
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.comparator.NaturalOrderComparator
import sidev.lib.collection.comparator.NumberNaturalOrderComparator
import sidev.lib.collection.comparator.NumberReversedOrderComparator
import sidev.lib.collection.comparator.ReversedOrderComparator
import kotlin.reflect.KProperty


val CommonList<*, *>.indices: IntRange
    get()= 0 until size
/*
fun <K, V> CommonIterable<V>.toCommonList(): CommonList<K, V> = when(this){
    is CommonList<*, *> -> this as CommonList<K, V>
    else -> (this as Iterable<V>).toMutableList().toCommonList() as CommonList<K, V>
}
fun <K, V> CommonIterable<V>.toCommonMutableList(): CommonMutableList<K, V> = when(this){
    is CommonMutableList<*, *> -> this as CommonMutableList<K, V>
    else -> (this as Iterable<V>).toMutableList().toCommonMutableList() as CommonMutableList<K, V>
}
 */

fun <V> CommonList<*, V>.first(): V = if(!isEmpty()) this[0] else throw NoSuchElementException()
fun <V> CommonList<*, V>.last(): V = if(!isEmpty()) this[lastIndex] else throw NoSuchElementException()

fun <V> CommonList<*, V>.firstOrNul(): V? = if(!isEmpty()) this[0] else null
fun <V> CommonList<*, V>.lastOrNull(): V? = if(!isEmpty()) this[lastIndex] else null


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <K, V> Any.toCommonList(): CommonList<K, V>{
    return when(this){
        is CommonList<*, *> -> this as CommonList<K, V>
        is List<*> -> (this as List<V>).toCommonList() as CommonList<K, V>
        is Map<*, *> -> (this as Map<K, V>).toCommonList()
        is Array<*> -> (this as Array<V>).toCommonList() as CommonList<K, V>
        is ArrayWrapper<*> -> (this as ArrayWrapper<V>).toCommonList() as CommonList<K, V>
        is Iterable<*> -> toCommonList() as CommonList<K, V>
        else -> throw ClassCastException("""`this` : "$this" tidak bisa dicast ke `CommonList`.""")//null
    }
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <K, V> Any.toCommonMutableList(): CommonMutableList<K, V>{
    return when(this){
        is CommonMutableList<*, *> -> this as CommonMutableList<K, V>
        is MutableList<*> -> (this as MutableList<V>).toCommonMutableList() as CommonMutableList<K, V>
        is MutableMap<*, *> -> (this as MutableMap<K, V>).toCommonMutableList()
        is Array<*> -> (this as Array<V>).toCommonMutableList() as CommonMutableList<K, V>
        is ArrayWrapper<*> -> (this as ArrayWrapper<V>).toCommonMutableList() as CommonMutableList<K, V>
        is Iterable<*> -> toMutableList().toCommonMutableList() as CommonMutableList<K, V>
        else -> throw ClassCastException("""`this` : "$this" tidak bisa dicast ke `CommonMutableList`.""")//null
    }
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <V> Any.toCommonIndexedList(): CommonIndexedList<V>{
    return when(this){
        is CommonList<*, *> -> (if(isIndexed) this else toCommonIndexedList()) as CommonIndexedList<V>
        is List<*> -> (this as List<V>).toCommonList() //as CommonIndexedList<V>
        is Map<*, *> -> (this as Map<*, V>).map { it.value }.toCommonList() //as CommonIndexedList<V>
        is Array<*> -> (this as Array<V>).toCommonList() //as CommonIndexedList<V>
        is ArrayWrapper<*> -> (this as ArrayWrapper<V>).toCommonList() //as CommonIndexedList<V>
        is Iterable<*> -> toCommonList() as CommonIndexedList<V>
        else -> throw ClassCastException("""`this` : "$this" tidak bisa dicast ke `CommonIndexedList`.""")//null
    }
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <V> Any.toCommonIndexedMutableList(): CommonIndexedMutableList<V>{
    return when(this){
        is CommonMutableList<*, *> -> (if(isIndexed) this else toCommonIndexedMutableList()) as CommonIndexedMutableList<V>
        is CommonList<*, *> -> toCommonIndexedMutableList() as CommonIndexedMutableList<V>
        is MutableList<*> -> (this as MutableList<V>).toCommonMutableList() //as CommonIndexedMutableList<V>
        is MutableMap<*, *> -> ((this as MutableMap<*, V>).map { it.value } as MutableList<V>).toCommonMutableList() //as CommonIndexedMutableList<V>
        is Array<*> -> (this as Array<V>).toCommonMutableList() //as CommonIndexedMutableList<V>
        is ArrayWrapper<*> -> (this as ArrayWrapper<V>).toCommonMutableList() //as CommonIndexedMutableList<V>
        is Iterable<*> -> toMutableList().toCommonMutableList() as CommonIndexedMutableList<V>
        else -> throw ClassCastException("""`this` : "$this" tidak bisa dicast ke `CommonIndexedMutableList`.""")//null
    }
}


fun <K, V> commonListOf(vararg elements: Pair<K, V>): CommonList<K, V>
        = CommonListImpl_Map(mapOf(*elements))
fun <K, V> commonMutableListOf(vararg elements: Pair<K, V>): CommonMutableList<K, V>
        = CommonMutableListImpl_Map(mutableMapOf(*elements))

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> commonIndexedListOf(vararg elements: T): CommonIndexedList<T>
        = CommonListImpl_Array(elements as Array<T>) // as CommonList<Int, T>
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> commonIndexedMutableListOf(vararg elements: T): CommonIndexedMutableList<T>
        = CommonMutableListImpl_Array(elements as Array<T>)


fun <T> Iterable<T>.toCommonList(): CommonIndexedList<T> = CommonListImpl_List(toList())
fun <T> List<T>.toCommonList(): CommonIndexedList<T> = CommonListImpl_List(this)
fun <T> MutableList<T>.toCommonMutableList(): CommonIndexedMutableList<T> = CommonMutableListImpl_List(this)

fun <T> Array<T>.toCommonList(): CommonIndexedList<T> = CommonListImpl_Array(this)
fun <T> Array<T>.toCommonMutableList(): CommonIndexedMutableList<T> = CommonMutableListImpl_Array(this)

fun <T> ArrayWrapper<T>.toCommonList(): CommonIndexedList<T> = CommonListImpl_ArrayWrapper(this)
fun <T> ArrayWrapper<T>.toCommonMutableList(): CommonIndexedMutableList<T>
        = CommonMutableListImpl_ArrayWrapper(this)

fun <K, V> Map<K, V>.toCommonList(): CommonList<K, V> = CommonListImpl_Map(this)
fun <K, V> MutableMap<K, V>.toCommonMutableList(): CommonMutableList<K, V>
        = CommonMutableListImpl_Map(this)

fun <V> CommonList<*, V>.withIndex(): Iterator<IndexedValue<V>> = iterator().withIndex()
fun <V> ArrayWrapper<V>.withIndex(): Iterator<IndexedValue<V>> = iterator().withIndex()
fun <V> CommonIterable<V>.withIndex(): Iterator<IndexedValue<V>> = iterator().withIndex()

fun <V> CommonList<*, V>.asList(): List<V> = this
fun <K, V> CommonList<K, V>.asMap(): Map<K, V> = this

fun <V> CommonMutableList<*, V>.asMutableList(): MutableList<V> = this
fun <K, V> CommonMutableList<K, V>.asMutableMap(): MutableMap<K, V> = when(this){
    is CommonMutableListImpl_Map<*, *> ->
        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        object : MutableMap<K, V> by (map as MutableMap<K, V>){
            override fun toString(): String = map.toString()
        }
    else -> {
        val res: MutableMap<K, V> by this
        res
    }
}


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <V> CommonList<*, V>.toMutableList(): MutableList<V> = when(this){
    is CommonMutableListImpl_List<*> -> list //as MutableList<V>
    is CommonMutableListImpl_Array<*> -> list
    is CommonListImpl_Map<*, *> -> map.values.toMutableList()
    is CommonMutableList<*, *> -> this
    is CommonListImpl_List<*> -> list.toMutableList()
    is CommonListImpl_Array<*> -> list.toMutableList()
    else -> {
        val list= mutableListOf<V>()
        for(e in this)
            list += e
        list
    }
}as MutableList<V>


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <K, V> CommonList<K, V>.toCommonMutableList(): CommonMutableList<K, V> = when(this){
    is CommonMutableList<*, *> -> this
    is CommonListImpl_List<*> -> CommonMutableListImpl_List(list.toMutableList() as MutableList<V>)
    is CommonListImpl_Array<*> -> CommonMutableListImpl_List(list.toMutableList() as MutableList<V>)
    is CommonListImpl_Map<*, *> -> CommonMutableListImpl_Map(map.toMutableMap() as MutableMap<K, V>)
    else -> CommonMutableListImpl_List(toMutableList())
} as CommonMutableList<K, V>


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <V> CommonList<*, V>.toCommonIndexedList(): CommonIndexedList<V> = when(this){
    is CommonIndexedList<*> -> this
    else -> CommonListImpl_List(this) //iterator().withIndex()
} as CommonIndexedList<V>


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <V> CommonList<*, V>.toCommonIndexedMutableList(): CommonIndexedMutableList<V> = when(this){
    is CommonIndexedMutableList<*> -> this
    else -> CommonMutableListImpl_List(when(this){
        is CommonListImpl_List<*> -> list.toMutableList()
        is CommonListImpl_Array<*> -> list.toMutableList()
        else -> this.toMutableList()
    } as MutableList<V> )
} as CommonIndexedMutableList<V>


///*
// */

//operator fun <K, V> CommonMutableList<K, V>.getValue(owner: Any?, property: KProperty<*>): MutableList<K, V>{}

/** Fungsi delegasi untuk [Array]. */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
inline operator fun <reified T> ArrayWrapper<T>.getValue(owner: Any?, prop: KProperty<*>): Array<T> = when(this){
    is ArrayWrapperImpl_Object -> toTypedArray()
/*
        <28 Agustus 2020> => Refleksi tidak jadi dipake karena berat untuk operasi yg simpel.
        (this::class.si.declaredMemberProperties.find { it.name == "array" }!! as SiProperty1<ArrayWrapper<T>, Any?>)
        .forceGet(this)!! as Array<T> //Pake Reflex karena kode ini inline smtr ArrayWrapperImpl merupakan internal.
 */
    else -> Array(size){ this[it] }
}

/** Extension delegate function ini dibuat dg alasan karena [CommonMutableList] tidak meng-extend [MutableMap]. */
operator fun <K, V> CommonMutableList<K, V>.getValue(owner: Any?, property: KProperty<*>): MutableMap<K, V>{
    return when(this){
        is CommonMutableListImpl_Map<*, *> ->
            @Suppress(SuppressLiteral.UNCHECKED_CAST)
            object : MutableMap<K, V> by (map as MutableMap<K, V>){
                override fun toString(): String = map.toString()
            }
        else -> {
            val thisEx= this
            object : MutableMap<K, V> {
                override val size: Int get() = thisEx.size
                override val keys: MutableSet<K> get() = thisEx.keys
                override val values: MutableCollection<V> get() = thisEx.values
                override val entries: MutableSet<MutableMap.MutableEntry<K, V>> get() = thisEx.entries
                override fun get(key: K): V? = thisEx[key]
                override fun put(key: K, value: V): V? = thisEx.put(key, value)
                override fun putAll(from: Map<out K, V>) = thisEx.putAll(from)
                override fun clear() = thisEx.clear()
                override fun containsKey(key: K): Boolean = thisEx.containsKey(key)
                override fun containsValue(value: V): Boolean = thisEx.containsValue(value)
                override fun remove(key: K): V? = thisEx.removeKey(key)
                override fun isEmpty(): Boolean = thisEx.isEmpty()
                override fun toString(): String = string
            }
        }
    }
}

/*
fun <K, V, M: MutableMap<K, V>> CommonMutableList<K, V>.mutableMapDelegate(): MutableMap<K, V>{
}
 */



@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <K, V> CommonList<out K, out V>.plus(other: CommonIterable<V>): CommonList<K, V>{
    if(other is CommonList<*, *>)
        return this + other as CommonList<K, V>

    val res= (
            if(!isIndexed) commonMutableListOf<K, V>()
            else commonIndexedMutableListOf<V>()
    ) as CommonMutableList<K, V>
    res += this
    res += other
    return res
}
@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <K, V> CommonList<out K, out V>.plus(other: CommonList<out K, out V>): CommonList<K, V>{
    val res= (
            if(!isIndexed || !other.isIndexed) commonMutableListOf<K, V>()
            else commonIndexedMutableListOf<V>()
    ) as CommonMutableList<K, V>
    res += this
    res += other
    return res
}


operator fun <V> CommonIterable<V>.minus(other: CommonIterable<V>): CommonIterable<V>
    = ((this as Iterable<V>) - (other as Iterable<V>)).toCommonIterable()

@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <K, V> CommonList<out K, out V>.minus(other: CommonIterable<V>): CommonList<K, V>{
    if(other is CommonList<*, *>)
        return this - other as CommonList<K, V>

    val res= (
            if(!isIndexed) commonMutableListOf<K, V>()
            else commonIndexedMutableListOf<V>()
    ) as CommonMutableList<K, V>
    res += this
    res -= other
    return res
}
@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <K, V> CommonList<out K, out V>.minus(other: CommonList<out K, out V>): CommonList<K, V>{
    val res= (
            if(!isIndexed || !other.isIndexed) commonMutableListOf<K, V>()
            else commonIndexedMutableListOf<V>()
    ) as CommonMutableList<K, V>
    res += this
    res -= other
    return res
}

/*
operator fun <V> CommonIterable<V>.plusAssign(other: CommonIterable<V>): Unit
    = ((this as Iterable<V>) + (other as Iterable<V>)).asCommonIterable()
 */
///*
@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <K, V> CommonMutableList<K, V>.plusAssign(other: CommonIterable<V>){
    try{
        when(other){
            is CommonList<*, *> -> plusAssign(other as CommonList<K, V>)
            else -> (this as CommonMutableList<Any?, Any?>).addAll(other as Iterable<V>)
        }
    } catch (e: ClassCastException){ //Jika ternyata Iterable juga CommonList, namun tipe data key-nya salah, maka tambah sprti Iterable biasa.
        (this as CommonMutableList<Any?, Any?>).addAll(other as Iterable<V>)
    }
}
operator fun <K, V> CommonMutableList<K, V>.plusAssign(other: CommonList<out K, out V>){
    when{
        other.isIndexed -> addAll(other as List<V>) //CommonIndexedList juga List.
        else -> putAll(other as Map<out K, V>)
    }
}
operator fun <K, V> CommonMutableList<K, V>.plusAssign(other: Map<out K, V>) = putAll(other)
// */


@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <K, V> CommonMutableList<K, V>.minusAssign(other: CommonIterable<V>){
    try{
        when(other){
            is CommonList<*, *> -> minusAssign(other as CommonList<K, V>)
            else -> (this as CommonMutableList<Any?, Any?>).removeAll(other as Iterable<V>)
        }
    } catch (e: ClassCastException){ //Jika ternyata Iterable juga CommonList, namun tipe data key-nya salah, maka tambah sprti Iterable biasa.
        (this as CommonMutableList<Any?, Any?>).removeAll(other as Iterable<V>)
    }
}
operator fun <K, V> CommonMutableList<K, V>.minusAssign(other: CommonList<out K, out V>){
    when{
        other.isIndexed -> removeAll(other as List<V>) //CommonIndexedList juga List.
        else -> removeAll(other as Map<out K, V>)
    }
}
operator fun <K, V> CommonMutableList<K, V>.minusAssign(other: Map<out K, V>){
    removeAll(other)
}


/*
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <C: CommonIndexedMutableList<V>, V: Comparable<V>> C.sort(func: (V, V) -> Boolean): C
        = (this as MutableList<V>).sort(func) as C
 */

/** Untuk indexed access dari [CommonMutableList]. */
operator fun <K, V> CommonMutableList<K, V>.set(key: K, element: V){
    put(key, element)
}
/** Untuk mengakomodasi [remove(key, value)]. */
fun <K, V> CommonMutableList<K, V>.remove(key: K, element: V): Boolean{
    return if (key in keys) {
        if(this[key] == element){
            removeKey(key)
            true
        } else false
    } else false
}


inline fun <K, V> CommonList<K, V>.forEachCommonEntry(action: (Map.Entry<K, V>) -> Unit){
    for (element in entries) action(element)
}
inline fun <V> CommonList<*, V>.forEachCommon(action: (V) -> Unit){
    for (element in this) action(element)
}


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Any.getElementAt(index: Int): T = when(this){
    is List<*> -> this[index]
    is Array<*> -> this[index]
    is CommonList<*, *> -> this[index]
    is Iterable<*> -> {
        var e: Any?= null
        val itr= iterator()
        for(i in 0 .. index)
            e= itr.next()
        e
    }
    else -> throw IllegalArgumentException("`this` : \"$this\" bkn List atau Array")
} as T


val Any.isCommonList: Boolean get()= when(this){
    is CommonList<*, *> -> true
    is List<*> -> true
    is Array<*> -> true
    else -> false
}
val Any.isCommonIterable: Boolean get()= when(this){
    is CommonIterable<*> -> true
    is Array<*> -> true
    else -> false
}


fun <K, V : Comparable<*>> CommonMutableList<K, V>.sort(order: Order= Order.ASC, withNumberSafety: Boolean = false) {
    sortWith(
        if(!withNumberSafety) {
            if(order == Order.ASC) NaturalOrderComparator
            else ReversedOrderComparator
        } else {
            if(order == Order.ASC) NumberNaturalOrderComparator
            else NumberReversedOrderComparator
        } as Comparator<in V>
    )
}
fun <K, V : Comparable<*>> CommonMutableList<K, V>.sortDescending(withNumberSafety: Boolean = false) {
    sortWith(
        (if(!withNumberSafety) ReversedOrderComparator
        else NumberReversedOrderComparator) as Comparator<in V>
    )
}
fun <K, V : Comparable<*>> CommonMutableList<K, V>.sortWith(c: Comparator<in V>) = sort_(c)
fun <K, V : Comparable<*>> CommonMutableList<K, V>.fastSort(order: Order= Order.ASC, withNumberSafety: Boolean= false) =
    sort(order, withNumberSafety)

fun <E : Comparable<*>> CommonIndexedMutableList<E>.sort(order: Order= Order.ASC, withNumberSafety: Boolean = false) {
    sortWith(
        if(!withNumberSafety) {
            if(order == Order.ASC) NaturalOrderComparator
            else ReversedOrderComparator
        } else {
            if(order == Order.ASC) NumberNaturalOrderComparator
            else NumberReversedOrderComparator
        } as Comparator<in E>
    )
}
fun <E : Comparable<*>> CommonIndexedMutableList<E>.sortDescending(withNumberSafety: Boolean = false) {
    sortWith(
        (if(!withNumberSafety) ReversedOrderComparator
        else NumberReversedOrderComparator) as Comparator<in E>
    )
}
fun <E : Comparable<*>> CommonIndexedMutableList<E>.sortWith(c: Comparator<in E>) = sort_(c)
fun <E : Comparable<*>> CommonIndexedMutableList<E>.fastSort(order: Order= Order.ASC, withNumberSafety: Boolean= false) =
    sort(order, withNumberSafety)