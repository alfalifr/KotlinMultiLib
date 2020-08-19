package sidev.lib.reflex.js

import sidev.lib.check.isNull
import sidev.lib.check.notNull
import sidev.lib.console.prine
import sidev.lib.reflex.js.kotlin.KotlinJsConst

fun <T, R> createJsProperty(
    name: String, isLateinit: Boolean, innerName: String= name
): JsProperty<T, R> = object : JsPropertyImpl<T, R>(){
    override val name: String = name
    override val innerName: String = innerName
    override val isLateinit: Boolean = isLateinit
    override operator fun get(receiver: T): R = eval("$receiver.$name") as R
}
fun <T, R> createJsMutableProperty(
    name: String, isLateinit: Boolean, innerName: String= name
): JsMutableProperty<T, R> = object : JsPropertyImpl<T, R>(), JsMutableProperty<T, R>{
    override val name: String = name
    override val innerName: String = innerName
    override val isLateinit: Boolean = isLateinit
    override operator fun get(receiver: T): R = eval("$receiver.$name") as R
    override fun set(receiver: T, value: R) = eval("$receiver.$name = $value")
}

/**
 * Mengambil semua property yg dideklarasikan pada fungsi [func].
 * Tidak termasuk yg dideklarasikan pada super.
 * Fungsi ini tidak dapat mengambil property yg abstrak karena compiler Kotlin
 * tidak menulisnya pada kode Js.
 * Property yg diambil hanya yg publik.
 * Property yg dimaksud adalah property yg didefinisikan dg pola `this.property = value` pada
 * kode dalam fungsi [func], bkn property yg diambil dg `Object.getOwnPropertyDescriptors(func.prototype)`.
 *
 * Fungsi ini dibuat untuk menyesuaikan kode di Kotlin Js
 * sehingga property yg dimabil semuanya hanya yg mutable.
 */
//TODO <16 Agustus 2020> => Untuk smtr smua properti yg diambil merupakan mutable property
fun <T: Any> getDeclaredProperty(func: T): List<JsMutableProperty<T, *>>{
    val func= jsPureFunction(func) as Any
    if(!func.isFunction)
        throw IllegalArgumentException("func: \"${str(func)}\" bkn fungsi.") //Agar lebih kontekstual.
    val funStr= func.toString()
      //Pake map karena tiap property punya nama unik pada Js.
    val propList= mutableMapOf<String, JsMutableProperty<T, *>>()
    KotlinJsConst.PROPERTY_INITIALIZATION_PATTERN.toRegex()
        .findAll(funStr).forEach { res ->
            val vals= res.groupValues
            var isLateinit= false
            val lastIndex= vals.lastIndex-1
            var propName= if(vals[lastIndex].isNotBlank()){
                isLateinit= true
                vals[lastIndex]
            } else vals[1]

            val propInnerName= vals[1]

            KotlinJsConst.PROPERTY_LATEINIT_PATTERN.toRegex().find(propName).notNull {
                propName= it.groupValues.last()
            }
            KotlinJsConst.PROPERTY_PRIVATE_PATTERN.toRegex().find(propName).notNull {
                propName= it.groupValues.last()
            }

            propList[propName].isNull {
                propList[propName]= createJsMutableProperty<T, Any?>(propName, isLateinit, propInnerName)
            }
        }
    return propList.values.toList()
}

