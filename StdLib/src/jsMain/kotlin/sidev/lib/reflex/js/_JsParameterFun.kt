package sidev.lib.reflex.js

import sidev.lib.check.notNull
import sidev.lib.number.toNumber
import sidev.lib.reflex.js.kotlin.KotlinJsConst
import sidev.lib.text.isDigit
import kotlin.reflect.KType


fun createJsParam(index: Int, name: String?, isOptional: Boolean= false, type: KType = JsType.dynamicType, defaultParamValue: Any?= null): JsParameter
        = object : JsParameterImpl{
    override val index: Int = index
    override val name: String? = name
    override var isOptional: Boolean = isOptional
    override val type: KType = type
    override var defaultValue: Any? = defaultParamValue
    override fun toString(): String = "JsParameter [$index] $name${if (this.isOptional) "?" else "" }: $type"
}

fun getParam(func: Any): List<JsParameter>{
    if(!func.isFunction)
        throw IllegalArgumentException("func: \"${str(func)}\" bkn fungsi.") //Agar lebih kontekstual.
    val funStr= func.toString() //js("func.toString()") as String
    var openParanthesesCount= 0
    var openBracesCount= 0
    var params= ArrayList<JsParameterImpl>()
    var paramNameItr= ""
    var isOptionalItr= false

    fun addParamToList(){
        val addedParam= createJsParam(params.size, paramNameItr, isOptionalItr) as JsParameterImpl
        params.add(addedParam)
        paramNameItr= ""
        isOptionalItr= false
    }

    var indexItr= 0

    loop@ for((i, char) in funStr.withIndex()){
        indexItr= i
        when(char){
            '(' -> openParanthesesCount++
            '{' -> openBracesCount++
            ')' -> {
                if(--openParanthesesCount == 0){
                    addParamToList()
                    break@loop
                }
            }
            '}' -> openBracesCount--
            else -> {
                if(openBracesCount == 0 && openParanthesesCount == 1){
                    if(!char.isWhitespace())
                        when(char){
                            '=' -> isOptionalItr= true
                            ',' -> addParamToList()
                            else -> paramNameItr += char
                        }
                }
            }
        }
    }

    KotlinJsConst.FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX.toRegex()
        .findAll(funStr, indexItr).forEachIndexed { i, matchResult ->
            params[i].defaultValue= matchResult.groupValues.last().let {
                (when{
                    it.contains("new") -> {
                        try{ eval(it) }
                        catch (e: Throwable){ it }
                    }
                    it.contains('\'') -> it.replace("'", "")
                    it[0].isDigit() -> it.toNumber()
                    else -> eval(it)
                } as? Any)
                    .notNull { params[i].isOptional= true }
            }
        }

    return params
}