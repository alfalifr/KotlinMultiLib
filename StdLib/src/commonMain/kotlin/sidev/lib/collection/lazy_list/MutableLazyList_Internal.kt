package sidev.lib.collection.lazy_list

import sidev.lib.collection.takeFirst
import sidev.lib.exception.isUninitializedExc

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

        while(!hasNext && iteratorList.isNotEmpty()){
            builderIterator= iteratorList.takeFirst()
            hasNext= builderIterator.hasNext()
        }
        return hasNext
    }
    override fun addIterator(itr: Iterator<T>): Boolean = iteratorList.add(itr)
}