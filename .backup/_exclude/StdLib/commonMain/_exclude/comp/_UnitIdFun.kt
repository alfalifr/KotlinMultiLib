package sidev.lib.reflex.comp

fun getHashCode(vararg fromObj: Any?): Int{
    if(fromObj.isEmpty())
        return 0
    var result = 0 //fromObj.first().hashCode()
    for(obj in fromObj)
        result = 31 * result + when(obj){
            is Collection<*> -> getContentHashCode(obj)
            is Array<*> -> getHashCode(*obj)
            else -> obj.hashCode()
        }
    return result
}

fun getContentHashCode(collection: Collection<Any?>): Int = getHashCode(*collection.toTypedArray())