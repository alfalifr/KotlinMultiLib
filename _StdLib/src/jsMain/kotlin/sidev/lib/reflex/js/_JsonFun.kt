package sidev.lib.reflex.js

import sidev.lib.exception.TypeExc
import sidev.lib.reflex.native_.getNativeMutableProperties
import sidev.lib.reflex.native_.jsClass
import kotlin.js.Json

/*
//<15 Agustus 2020> => Dihapus, gunakan Any.asJson() agar sesuai konteks.
operator fun Any?.get(key: String): Any? {
    val thisRef= this
    return js("thisRef[key]")
}

operator fun Any?.set(key: String, value: Any?): Any? {
    val prevVal= this[key]
    val thisRef= this
    js("thisRef[key]= value")
    return prevVal
}
 */
/*
interface JsProperty<out R>: JsCallable<R>, KProperty<R> {
    override val annotations: List<Annotation> get() = emptyList()
    override val isAbstract: Boolean get() = false
    override val isFinal: Boolean get() = false
    override val isOpen: Boolean get() = true
    override val isSuspend: Boolean get() = false
    override val typeParameters: List<KTypeParameter> get() = emptyList()
    override val visibility: KVisibility? get() = null
    override val isConst: Boolean get() = false
    override val isLateinit: Boolean get() = false
    override val parameters: List<KParameter> get() = emptyList()
    override val jsParameters: List<JsParameter> get() = emptyList()

    override val returnType: KType get() = JsType.dynamicType
    override val getter: KProperty.Getter<R>
        get() = TODO("Not yet implemented")

    override fun call(vararg args: Any?): R {
        TODO("Not yet implemented")
    }
}
 */

fun Any.asJson(): Json = if(!this.isUndefined) this as Json //Semua objek di Js adalah Json.
        else throw TypeExc(expectedType = Any::class, actualType = this::class, msg = "instance $this undefined.")

/**
 * Mengambil semua pasangan nama dan nilai properti yg ada pada `this.extension`,
 * termasuk yg enumerable dan yg tidak.
 */
val Any.properties: Sequence<Pair<String, Any?>> get(){
    val thisFullJson= getPropertyDescriptors(this) //js("Object.getOwnPropertyDescriptors(thisRef)") as Json
    val keys= thisFullJson.keys
    return object : Sequence<Pair<String, Any?>>{
        override fun iterator(): Iterator<Pair<String, Any?>> =
            object : Iterator<Pair<String, Any?>>{
                var index= 0
                override fun hasNext(): Boolean = index < keys.size
                override fun next(): Pair<String, Any?>{
                    val key= keys[index++]
                    return Pair(key, thisFullJson[key].asDynamic().value)
                }
            }
    }
}

val <T: Any> T.typedProperties: Sequence<Pair<JsProperty<T, Any?>, Any?>>
    get(){
        val props= properties.toList()
        return getNativeMutableProperties(jsClass).map { prop ->
            prop as JsProperty<T, Any?>
            val value= props.find { it.first == prop.innerName }?.second
            Pair(prop, value)
        }
    }

val Json.keys: Array<String>
    get(){
        val thisRef= this
        return js("Object.keys(thisRef)") as? Array<String> ?: emptyArray()
    }

fun Json.toList(): List<Pair<String, Any?>>{
    val res= ArrayList<Pair<String, Any?>>()
    for(pair in properties)
        res.add(pair)
    return res
}

