package sidev.lib.reflex.js

import sidev.lib.check.notNullTo
import sidev.lib.platform.getGlobalObject
import sidev.lib.property.reevaluateLazy
import sidev.lib.reflex.core.SiReflexConst
import sidev.lib.reflex.comp.native.jsClass

//fun ada(){ js("class;") }

//<15 Agustus 2020> => Semua receiver non-null agar sesuai konteks.

val Any.isBoolean: Boolean
    get()= jsTypeOf(this) == JsPrimitiveType.BOOLEAN.jsName

val Any.isNumber: Boolean
    get()= jsTypeOf(this) == JsPrimitiveType.NUMBER.jsName

val Any.isString: Boolean
    get()= jsTypeOf(this) == JsPrimitiveType.STRING.jsName

val Any.isFunction: Boolean
    get()= jsTypeOf(this) == JsPrimitiveType.FUNCTION.jsName

val Any.isObject: Boolean
    get()= this != null //Karena pada Js, null juga termasuk `object`
            && jsTypeOf(this) == JsPrimitiveType.OBJECT.jsName
/*
val Any?.isNull: Boolean
    get()= this == null
 */

val Any.isUndefined: Boolean
    get()= jsTypeOf(this) == JsPrimitiveType.UNDEFINED.jsName


fun createType(primitiveType: JsPrimitiveType, classifier: JsClass_<*>?= null): JsType = object : JsTypeImpl(){
    override val classifier: JsClass_<*>? = classifier
    override val primitive: JsPrimitiveType = primitiveType
    override var isClassifierResolved: Boolean = true
}
//TODO krg sempurna untuk lazy nya
fun createTypeLazyly(primitiveType: JsPrimitiveType, classifierName: String?= null): JsType = object : JsTypeImpl(){
    override val classifier: JsClass_<*>? by reevaluateLazy {
        try {
            val type= if(classifierName != null) inferClassifier<Any>(classifierName) else null
            it.value= true
            isClassifierResolved= true
            type
        } catch (e: Throwable){ null }
    }
    override var isClassifierResolved: Boolean = false

    /*
                by reevaluateLazy {
            try{
                val value= if(classifierName != null) inferClassifier<Any>(classifierName) else null
                it.value= true
                value
            }
            catch (e: Throwable){ null }// as JsClass_<*>?
        }
     */
    override val primitive: JsPrimitiveType = primitiveType
}

fun inferType(value: String): JsType{
    val primitiveType= JsPrimitiveType.infer(value)
/*
    var typeClassifier: JsClass_<*>?= null
    if(primitiveType == JsPrimitiveType.OBJECT)
        JsReflexConst.FUNCTION_NEW_INITIALIZATION_PATTERN.toRegex().find(value).notNull {
            val funName= it.groupValues[1]
            val func= eval(funName) as Any
            typeClassifier= func.jsClass
        }
 */
    return createTypeLazyly(primitiveType, value)
}

fun <T: Any> inferClassifier(value: String): JsClass_<T>?{
    val primitiveType= JsPrimitiveType.infer(value)
    val typeClassifier: JsClass_<T>? = (when(primitiveType){
        JsPrimitiveType.OBJECT -> {
            JsReflexConst.FUNCTION_NEW_INSTANTIATION_PATTERN.toRegex().find(value).notNullTo {
                val funName= it.groupValues[1]
                getGlobalObject(funName)!!// as Any
            }
        }
        JsPrimitiveType.FUNCTION -> eval("${SiReflexConst.TEMP_VAR_NAME} = value")
        JsPrimitiveType.NUMBER -> eval(JsPrimitiveType.NUMBER.jsConstructorName)
        JsPrimitiveType.STRING -> eval(JsPrimitiveType.STRING.jsConstructorName)
        JsPrimitiveType.BOOLEAN -> eval(JsPrimitiveType.BOOLEAN.jsConstructorName)
        else -> null
    } as? Any)
        ?.jsClass as? JsClass_<T>
    return typeClassifier
}

/**
 * Mengambil nilai sesungguhnya dari sebuah string [valueStr].
 * Fungsi ini menggunakan fungsi [eval] disertai dg pengecekan apakah [valueStr]
 * berupa ekspresi instansiasi objek baru.
 *
 * @return `null` jika [valueStr] menunjukan string "null" atau "undefined".
 */
fun getRealValue(valueStr: String): Any?{
    return if(valueStr == "null" || valueStr == "undefined") null
    else try{
        eval(valueStr) //Apapun nilai yg ada pada [defaultParamValueStr], eval aja
        // biar yg number jadi number dan str jadi string.
    } catch (e: Throwable){
        JsReflexConst.FUNCTION_NEW_INSTANTIATION_PATTERN.toRegex().find(valueStr).notNullTo {
            eval(it.groupValues.first())
        } ?: valueStr
    } as Any
}