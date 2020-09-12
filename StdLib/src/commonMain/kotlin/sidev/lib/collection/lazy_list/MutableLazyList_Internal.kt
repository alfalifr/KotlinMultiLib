package sidev.lib.collection.lazy_list

import sidev.lib.collection.iterator.emptyIterator
import sidev.lib.collection.takeFirst
import sidev.lib.console.prine
import sidev.lib.exception.isUninitializedExc
//TODO <12 Sep 2020> => Msh menyebabkan keanehan pada fungsi hasNext().
internal interface MutableLazyList_Internal<T>:
    MutableLazyList<T> {
    override val iteratorList: MutableList<Iterator<T>>
    override var builderIterator: Iterator<T>

    override fun iteratorHasNext(): Boolean {
        var hasNext= try{ builderIterator.hasNext() }
        catch(e: Exception){
            if(e.isUninitializedExc) false
            else throw e
        }
//        prine("MutableLazyList_Internal.iteratorHasNext() hasNext= $hasNext iteratorList.size= ${iteratorList.size}")

        while(!hasNext && iteratorList.isNotEmpty()){
            builderIterator= iteratorList.takeFirst()
            hasNext= builderIterator.hasNext()
        }
        return hasNext
    }
    override fun addIterator(itr: Iterator<T>): Boolean = iteratorList.add(itr)
}

internal class MutableLazyListImpl_Internal<T>: MutableLazyList_Internal<T>{
    override val iteratorList: MutableList<Iterator<T>> = arrayListOf()
    override var builderIterator: Iterator<T> = emptyIterator()
    override val size: Int = -1 //Karena tidak dapat dihitung size sebenarnya.
    override fun isEmpty(): Boolean = !iteratorHasNext()
}