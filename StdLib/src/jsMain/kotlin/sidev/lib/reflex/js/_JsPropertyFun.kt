package sidev.lib.reflex.js

import sidev.lib.check.isNull
import sidev.lib.check.notNull
import sidev.lib.console.log
import sidev.lib.console.prine
import sidev.lib.reflex.js.kotlin.KotlinJsConst
import kotlin.js.json

fun <T: Any, R> createJsProperty(
    name: String, isLateinit: Boolean, innerName: String= name, type: JsType = JsType.dynamicType
): JsProperty<T, R> = object : JsPropertyImpl<T, R>(){
    override val name: String = name
    override val innerName: String = innerName
    override val isLateinit: Boolean = isLateinit
    override val returnType: JsType = type
    override operator fun get(receiver: T): R = getPropRealValue(receiver) //eval("receiver.$innerName") as R
}
fun <T: Any, R> createJsMutableProperty(
    name: String, isLateinit: Boolean, innerName: String= name, type: JsType = JsType.dynamicType
): JsMutableProperty<T, R> = object : JsPropertyImpl<T, R>(), JsMutableProperty<T, R>{
    override val name: String = name
    override val innerName: String = innerName
    override val isLateinit: Boolean = isLateinit
    override val returnType: JsType = type
    override operator fun get(receiver: T): R = getPropRealValue(receiver) //eval("receiver.$innerName") as R
    override fun set(receiver: T, value: R) = eval("receiver.$innerName = value")
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
//            prine("getDeclaredProperty() vals= $vals")
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

            //TODO <20 Agustus 2020> => Untuk sementara tipe dari property yg ada di konstruktor sama dg OBJECT.
            val type= if(propName == vals[lastIndex-1]) createTypeLazyly(JsPrimitiveType.OBJECT)
                else inferType(vals[lastIndex-1])
//            prine("getDeclaredProp()= type= $type val= ${vals[lastIndex-1]}")
//            prine("getDeclaredProperty() propName= $propName propList= ${str(propList)} propList== undefined => ${propList == undefined}")
            propList[propName].isNull {
                propList[propName]= createJsMutableProperty<T, Any?>(propName, isLateinit, propInnerName, type)
            }
        }
    return propList.values.toList()
}


/**
 * Mengambil nilai sesungguhnya dari property, terutama bagi properti dg lazy delegate.
 */
fun <R> JsProperty<*, R>.getPropRealValue(receiver: Any): R{
    val vals= eval("receiver.$innerName")
//    prine("JsProperty<*, R>.getPropRealValue vals= ${str(vals)}")
//    log(vals)
//    prine("JsProperty<*, R>.getPropRealValue vals == null= ${vals == null}")
//    prine("JsProperty<*, R>.getPropRealValue vals == undefined= ${vals == undefined}")
//    prine("JsProperty<*, R>.getPropRealValue vals::class= ${vals::class}")
    val finalVal= try{ when{
        vals == undefined -> undefined
        vals == null -> null
        jsName(vals) == KotlinJsConst.LAZY_DELEGATE_NAME -> vals[KotlinJsConst.LAZY_DELEGATE_INITIALIZER_NAME]()
        else -> vals
    } }
    catch (e: Throwable){ json() } //vals //Kemungkinan karena `vals` == null

    return finalVal as R
}