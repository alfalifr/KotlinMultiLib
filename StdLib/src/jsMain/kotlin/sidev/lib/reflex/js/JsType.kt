package sidev.lib.reflex.js

import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection

enum class JsType(/*val jsName: String*/){
/*
    FUNCTION("function"),
    OBJECT("object"),
    UNDEFINED("undefined"),
    STRING("string"),
    NUMBER("number");
 */
    FUNCTION, OBJECT, UNDEFINED,
    STRING, NUMBER;

    val jsName: String = name.toLowerCase()

    companion object{
        val dynamicType: KType = object : KType {
            override val annotations: List<Annotation> = emptyList()
            override val arguments: List<KTypeProjection> = emptyList()
            override val classifier: KClassifier? = null
            override val isMarkedNullable: Boolean = true
            override fun toString(): String = "sidev.lib.reflex.js.JsType.dynamicType"
        }
    }
}