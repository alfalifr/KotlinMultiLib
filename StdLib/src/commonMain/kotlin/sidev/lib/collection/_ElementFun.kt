package sidev.lib.collection
///*
import sidev.lib.console.prine
import sidev.lib.number.isZero
import sidev.lib.number.plus
import sidev.lib.reflex.clazz
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiParameter
import sidev.lib.reflex.common.full.clone
import sidev.lib.reflex.common.full.isInstantiable
import sidev.lib.reflex.common.full.isReflexUnit
import sidev.lib.reflex.common.native.si
import sidev.lib.universal.`val`.SuppressLiteral

/*
===============
Check
===============
 */
fun <T> Collection<T>?.isNotNullAndEmpty(): Boolean = this != null && this.isNotEmpty()
fun <T> Array<T>?.isNotNullAndEmpty(): Boolean = this != null && this.isNotEmpty()


inline fun <T> List<T>.notEmpty(block: (List<T>) -> Unit): List<T>{
    if(isNotEmpty()) block(this)
    return this
}
inline fun <T> Array<T>.notEmpty(block: (Array<T>) -> Unit): Array<T>{
    if(isNotEmpty()) block(this)
    return this
}

inline fun <T, O> List<T>.notEmptyTo(block: (List<T>) -> O): O?{
    return if(isNotEmpty()) block(this)
    else null
}
inline fun <T, O> Array<T>.notEmptyTo(block: (Array<T>) -> O): O?{
    return if(isNotEmpty()) block(this)
    else null
}


fun <T> Collection<Collection<T>>.isElementEmpty(): Boolean{
    for(coll in this){
        if(coll.isNotEmpty())
            return false
    }
    return true
}
fun <T> Array<Array<T>>.isElementEmpty(): Boolean{
    for(coll in this){
        if(coll.isNotEmpty())
            return false
    }
    return true
}

fun <T: Number> Array<T>.isElementZero(): Boolean{
    for(e in this)
        if(!e.isZero())
            return false
    return true
}
fun <T: Number> Collection<T>.isElementZero(): Boolean{
    for(e in this)
        if(!e.isZero())
            return false
    return true
}


fun IntArray.isElementZero(): Boolean{
    for(e in this)
        if(!e.isZero())
            return false
    return true
}
fun LongArray.isElementZero(): Boolean{
    for(e in this)
        if(!e.isZero())
            return false
    return true
}
fun DoubleArray.isElementZero(): Boolean{
    for(e in this)
        if(!e.isZero())
            return false
    return true
}
fun FloatArray.isElementZero(): Boolean{
    for(e in this)
        if(!e.isZero())
            return false
    return true
}

/*
===============
findElement()
===============
 */
inline fun <reified E> Array<out Any?>.findElementByType(): E?{
    return this.findElement { it is E } as? E
}
inline fun <reified E> Collection<Any?>.findElementByType(): E?{
    return this.findElement { it is E } as? E
}

fun <C> Array<C>.findElement(iterator: (C) -> Boolean): C?{
    for(e in this)
        if(iterator(e))
            return e
    return null
}
fun <C> Collection<C>.findElement(iterator: (C) -> Boolean): C?{
    for(e in this)
        if(iterator(e))
            return e
    return null
}


/*
===============
toString
===============
 */

val Map<*, *>.string: String
    get(){
        return if(!this::class.si.isInstantiable){
            var res= "{"
            for(e in entries)
                res += "${e.key}=${e.value}, "
            res= res.removeSuffix(", ")
            res += "}"
            res
        }
        else toString()
    }
val Map<*, *>.namedString: String
    get()= "${this::class.simpleName}$string"

val Collection<*>.string: String
    get(){
        return if(!this::class.si.isInstantiable){
            var res= "["
            for(e in this)
                res += "$e, "
            res= res.removeSuffix(", ")
            res += "]"
            res
        }
        else toString()
    }

val Collection<*>.namedString: String
    get()= "${this::class.simpleName}$string"



val Array<*>.string: String
    get(){
        var str= "${this::class.simpleName}["
        for(e in this){
            str += e.toString() +", "
        }
        str= str.removeSuffix(", ")
        return "$str]"
    }
/*
val Collection<*>.string: String
    get(){
        var str= "${this::class.simpleName}["
        for(e in this){
            str += e.toString() +", "
        }
        str= str.removeSuffix(", ")
        return "$str]"
    }
 */

val IntArray.string: String
    get(){
        var str= "${this::class.simpleName}["
        for(e in this){
            str += e.toString() +", "
        }
        str= str.removeSuffix(", ")
        return "$str]"
    }
val LongArray.string: String
    get(){
        var str= "${this::class.simpleName}["
        for(e in this){
            str += e.toString() +", "
        }
        str= str.removeSuffix(", ")
        return "$str]"
    }
val DoubleArray.string: String
    get(){
        var str= "${this::class.simpleName}["
        for(e in this){
            str += e.toString() +", "
        }
        str= str.removeSuffix(", ")
        return "$str]"
    }
val FloatArray.string: String
    get(){
        var str= "${this::class.simpleName}["
        for(e in this){
            str += e.toString() +", "
        }
        str= str.removeSuffix(", ")
        return "$str]"
    }


/*
=============================
New Unique Value Creation
=============================
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST, SuppressLiteral.IMPLICIT_CAST_TO_ANY)
fun <T> newUniqueValueIn(inCollection: Collection<T?>, default: T?= null, constructorParamValFunc: ((SiClass<*>, SiParameter) -> Any?)?= null): T? {
    var newVal= inCollection.lastOrNull()
    if(newVal == null){
        prine("inCollection kosong, nilai default: \"$default\" dikembalikan.")
        return default
    }
    while(newVal != null && newVal in inCollection){
        newVal= when(newVal){
            is Number -> newVal + 1
            is String -> "$newVal:@"
            else -> {
                if(!newVal.clazz.si.isReflexUnit)
                    try{ (newVal as Any).clone(constructorParamValFunc = constructorParamValFunc)!! }
                    catch (e: Exception){
                        prine("Tidak dapat meng-instantiate key dg kelas: \"${(newVal as Any).clazz}\", nilai default: \"$default\" dikembalikan.")
                        return default
                    }
                else return default
            }
        } as? T
    }
    return newVal
}