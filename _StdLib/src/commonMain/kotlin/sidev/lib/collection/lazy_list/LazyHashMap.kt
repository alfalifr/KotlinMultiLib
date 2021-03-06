package sidev.lib.collection.lazy_list

import sidev.lib.collection.toMutableMapEntry


/**
 * Mirip dg [HashMap], [LazyHashMap] memiliki sumber data yg berasal dari sequence sehingga
 * data yg disimpan tidak besar di awal dan sesuai kebutuhan.
 */
open class LazyHashMap<K, V>(private val hashMap: HashMap<K, V>)
    : MutableMap<K, V> by hashMap,
    MutableMappedCachedLazyList<K, V>, //by MutableLazyListImpl_Internal<Pair<K, V>>(),
    MutableIterable<MutableMap.MutableEntry<K, V>> {
    constructor(): this(HashMap())
    constructor(iterator: Iterator<Pair<K, V>>): this(HashMap()){
        builderIterator= iterator
    }
    constructor(iterable: Iterable<Pair<K, V>>): this(HashMap()){
        builderIterator= iterable.iterator()
    }
    constructor(inSequence: Sequence<Pair<K, V>>): this(HashMap()){
        builderIterator= inSequence.iterator()
    }

    /** Jika `true` maka jika value yg dipass ke [add] mengandung key yg sudah ada, maka nilai yg sudah ada akan ditimpa dg yg baru. */
    open var allowOverwrite: Boolean= true

    private var requestedAddedKey: K?= null

    private val delegate = MutableLazyListImpl_Internal<Pair<K, V>>()
    final override val iteratorList: List<Iterator<Pair<K, V>>>
        get() = delegate.iteratorList
    final override var builderIterator: Iterator<Pair<K, V>>
        private set(v){ delegate.builderIterator= v }
        get()= delegate.builderIterator
    final override fun addIterator(itr: Iterator<Pair<K, V>>): Boolean = delegate.addIterator(itr)
    final override fun iteratorHasNext(): Boolean = delegate.iteratorHasNext()

    override fun getExisting(key: K): V? = entries.find { it.key == key }?.value
    override fun getExistingKey(value: V): K? = entries.find { it.value == value }?.key
    override fun containsExistingValue(value: V): Boolean = values.find { it == value } != null //containsValue(value)
    override fun containsExistingKey(key: K): Boolean = keys.find { it == key } != null //containsKey(key)

    override fun isNextMatched(key: K, addedNext: V): Boolean = key == requestedAddedKey

    override fun addNext(key: K, value: V): Boolean{
        val canAdd= allowOverwrite || !containsExistingKey(key)
        if(canAdd) set(key, value)
        return canAdd
    }

    override fun get(key: K): V? {
        requestedAddedKey= key
        return findNext(key)
    }

    override fun containsValue(value: V): Boolean = containsNextValue(value)
    override fun containsKey(key: K): Boolean = containsNextKey(key)

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>>
        = object : MutableIterator<MutableMap.MutableEntry<K, V>>{
        var index= 0
//        val initialIndices= 0 until size
        val existingKeys= keys
        var nowKey: K?= null

        override fun hasNext(): Boolean = index < hashMap.size || iteratorHasNext()

        override fun next(): MutableMap.MutableEntry<K, V>{
            val next= if(index < hashMap.size) entries.find { it.key == existingKeys.elementAt(index).also { nowKey= it } }!!
                else getNext()!!.toMutableMapEntry()
            index++
            return next
        }

        override fun remove() { remove(nowKey) }
    }

    override fun isEmpty(): Boolean = hashMap.isEmpty() && !iteratorHasNext()

    override fun toString(): String {
        val containedStr= hashMap.toString()
        return if(iteratorHasNext()){
            var cachedStr= "...(lazyVal)}"
            if(hashMap.isNotEmpty())
                cachedStr= ", $cachedStr"
            containedStr.substring(0, containedStr.length-1) +cachedStr
        } else containedStr
    }

/*
    /*
    ===============================
    Rooted-Extension agar saat fungsi ekstensi dipanggil, fungsional khusus kelas LazyHashMap berjalan.
    ===============================
     */
    fun first(): Map.Entry<K, V>{
        return if(!isEmpty()) {
            if(size > 0) entries.elementAt(0)
            else getNext()!!.toMapEntry()
        }
        else throw IndexOutOfBoundsException("MappedCachedLazyList: ${this::class.simpleName} kosong.")
    }
    fun last(): Map.Entry<K, V>{
        return if(!isEmpty()) {
            if(iteratorHasNext()){
                var res: Pair<K, V>?= null
                while(iteratorHasNext()){
                    res= getNext()!!
                }
                res!!.toMapEntry()
            } else{
                entries.last()
            }
        }
        else throw IndexOutOfBoundsException("CachedSequence: ${this::class.simpleName} kosong.")
    }
 */
}

/*
open class LazyHashMap<K, V>(inSequence: Sequence<Pair<K, V>>)
    : HashMap<K, V>(), CachedLazyList<K, MutableMap.MutableEntry<K, V>>{
    /** Jika `true` maka jika value yg dipass ke [add] mengandung key yg sudah ada, maka nilai yg sudah ada akan ditimpa dg yg baru. */
    open var allowOverwrite: Boolean= true

    override val builderIterator: Iterator<MutableMap.MutableEntry<K, V>>
            = object : Iterator<MutableMap.MutableEntry<K, V>>{
        private val sequenceItr= inSequence.iterator()

        override fun hasNext(): Boolean = sequenceItr.hasNext()
        override fun next(): MutableMap.MutableEntry<K, V> {
            val next= sequenceItr.next()

            return object : MutableMap.MutableEntry<K, V>{
                override val key: K= next.first
                override var value: V= next.second

                override fun setValue(newValue: V): V {
                    val oldVal= value
                    value= newValue
                    return oldVal
                }
            }
        }
    }

    override fun contains(element: MutableMap.MutableEntry<K, V>): Boolean {
        val existing= this[element.key]
        return existing == element.value
    }

    override fun containsAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean {
        var res= true
        for(element in elements){
            val existing= this[element.key]
            res= res && existing == element.value
        }
        return res
    }

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>>
            = (this as MutableMap<K, V>).iterator()


    override fun getExisting(key: K): MutableMap.MutableEntry<K, V>? = entries.find { it.key == key }

    override fun getExistingKey(value: MutableMap.MutableEntry<K, V>): K? = keys.find { it == value.key }

    override fun isNextMatched(key: K, addedNext: MutableMap.MutableEntry<K, V>): Boolean = addedNext.key == key

    override fun add(value: MutableMap.MutableEntry<K, V>): Boolean {
        val canAdd= allowOverwrite || getExistingKey(value) == null
        if(canAdd)
            set(value.key, value.value)
        return canAdd
    }

    override fun get(key: K): V? = findNext(key)?.value
    override fun containsKey(key: K): Boolean = containsNextKey(key)
    override fun containsValue(value: V): Boolean = containsNextValue(value)

    override fun containsValue(value: MutableMap.MutableEntry<K, V>): Boolean = contains(value.key)
}
 */
/*
/**
 * Turunan [HashMap] yg sumber datanya berasal dari sequence sehingga
 * data yg disimpan tidak besar di awal dan sesuai kebutuhan.
 */
open class LazyHashMap<K, V>(inSequence: Sequence<Pair<K, V>>)
    : HashMap<K, V>(), LazyList<MutableMap.MutableEntry<K, V>>{

    override val builderIterator: Iterator<MutableMap.MutableEntry<K, V>>
        = object : Iterator<MutableMap.MutableEntry<K, V>>{
        private val sequenceItr= inSequence.iterator()

        override fun hasNext(): Boolean = sequenceItr.hasNext()
        override fun next(): MutableMap.MutableEntry<K, V> {
            val next= sequenceItr.next()

            return object : MutableMap.MutableEntry<K, V>{
                override val key: K= next.first
                override var value: V= next.second

                override fun setValue(newValue: V): V {
                    val oldVal= value
                    value= newValue
                    return oldVal
                }
            }
        }
    }

    override fun contains(element: MutableMap.MutableEntry<K, V>): Boolean {
        val existing= this[element.key]
        return existing == element.value
    }

    override fun containsAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean {
        var res= true
        for(element in elements){
            val existing= this[element.key]
            res= res && existing == element.value
        }
        return res
    }

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>>
            = (this as MutableMap<K, V>).iterator()

    override fun get(key: K): V? {
        val existing= super.get(key)
        if(existing == null && builderIterator.hasNext()){
            var pairItr = builderIterator.next()
            while(pairItr.key != key && builderIterator.hasNext()){
                pairItr= builderIterator.next()
            }
            return if(pairItr.key != key) null
            else {
                set(pairItr.key, pairItr.value)
                pairItr.value
            }
        }
        return existing
    }
}
 */