package sidev.lib.reflex.js

import sidev.lib.reflex.js.kotlin.KotlinJsConst
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection

enum class JsPrimitiveType(/*val jsName: String*/){
/*
    FUNCTION("function"),
    OBJECT("object"),
    UNDEFINED("undefined"),
    STRING("string"),
    NUMBER("number");
 */
    FUNCTION, OBJECT, UNDEFINED, ARRAY,
    STRING, NUMBER, BOOLEAN, DYNAMIC;

    val jsName: String = name.toLowerCase()
    val jsConstructorName: String = jsName.capitalize()

    companion object{
/*
        val dynamicType: KType = object : KType {
            override val annotations: List<Annotation> = emptyList()
            override val arguments: List<KTypeProjection> = emptyList()
            override val classifier: KClassifier? = null
            override val isMarkedNullable: Boolean = true
            override fun toString(): String = "sidev.lib.reflex.js.JsType.dynamicType"
        }
 */
        fun infer(value: String): JsPrimitiveType = when{
            value.matches("'.*'".toRegex()) -> STRING
            value.matches("[\\d.]+".toRegex()) -> NUMBER
            value.startsWith("new") -> OBJECT
            value.matches("\\[.*\\]".toRegex()) -> ARRAY
            value.startsWith("function") -> FUNCTION
            value == "null" -> OBJECT
            value.matches(KotlinJsConst.PROPERTY_LATEINIT_PATTERN.toRegex()) -> OBJECT
            else -> UNDEFINED
        }
    }
}