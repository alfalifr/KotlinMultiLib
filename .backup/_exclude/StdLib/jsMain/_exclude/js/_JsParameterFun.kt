package sidev.lib.reflex.js

import sidev.lib.check.notNull
import sidev.lib.check.notNullTo
import sidev.lib.collection.findIndexed
import sidev.lib.console.log
import sidev.lib.console.prine
import sidev.lib.number.toNumber
import sidev.lib.property.mutableLazy
import sidev.lib.property.reevaluateLazy
import sidev.lib.property.reevaluateMutableLazy
import sidev.lib.reflex.js.kotlin.KotlinJsConst
import sidev.lib.text.isDigit


fun createJsParam(index: Int, name: String?, isOptional: Boolean= false, type: JsType = JsType.dynamicType,
                  defaultParamValueStr: String?= null): JsParameter
        = object : JsParameterImpl(){
    override val index: Int = index
    override val name: String? = name
    override var isOptional: Boolean = isOptional
    override val type: JsType = type
    /** [defaultValue] diinspeksi scr lazy agar fungsi pada [defaultParamValueStr] didefinisikan dulu. */
    override var defaultValue: Any? by reevaluateMutableLazy {
        try{
            val v= if(defaultParamValueStr != null)
                getRealValue(defaultParamValueStr)
            else null
            it.value= true
            v
        } catch (e: Throwable){ null }
    }
}

fun getParam(func: Any): List<JsParameter>{
    val func= jsPureFunction(func) as Any
    if(!func.isFunction)
        throw IllegalArgumentException("func: \"${str(func)}\" bkn fungsi.") //Agar lebih kontekstual.

//    prine("getParam() func= $func")

    val funStr= func.toString() //js("func.toString()") as String
    var openParanthesesCount= 0
    var openBracesCount= 0
    val params= ArrayList<JsParameter?>()
    val paramNames= ArrayList<String>()
    var paramNameItr= ""
    var isOptionalItr= false

/*
    fun addParamToList(){
        val addedParam= createJsParam(params.size, paramNameItr, isOptionalItr) //as JsParameterImpl
        params.add(addedParam)
        prine("getParam() paramNameItr= $paramNameItr func= $func")
        paramNameItr= ""
        isOptionalItr= false
    }
 */
    fun addParamNameToList(){
        paramNames.add(paramNameItr)
        params.add(null)
        paramNameItr= ""
    }

    var indexItr= 0

    loop@ for((i, char) in funStr.withIndex()){
        indexItr= i
        when(char){
            '(' -> openParanthesesCount++
            '{' -> openBracesCount++
            ')' -> {
                if(--openParanthesesCount == 0){
                    if(paramNameItr.isNotBlank())
                        addParamNameToList()
                    break@loop
                }
            }
            '}' -> openBracesCount--
            else -> {
                if(openBracesCount == 0 && openParanthesesCount == 1){
                    if(!char.isWhitespace())
                        when(char){
                            '=' -> isOptionalItr= true
                            ',' -> addParamNameToList()
                            else -> paramNameItr += char
                        }
                }
            }
        }
    }

    KotlinJsConst.FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX.toRegex()
        .findAll(funStr, indexItr).forEachIndexed { i, matchResult ->
/*
            prine("getParam() FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX")
            for((i, res) in matchResult.groupValues.withIndex()){
                prine("param def i= $i res= $res")
            }
 */
            val paramName= matchResult.groupValues[1]
            val paramInd= paramNames.indexOfFirst { it ==  paramName }
            val defaultValueStr= matchResult.groupValues.last()
            params[paramInd]= createJsParam(paramInd, paramName, true,
                inferType(defaultValueStr), defaultValueStr)
        }

    for((i, param) in params.withIndex())
        if(param == null)
            params[i]= createJsParam(i, paramNames[i], false)


    return params as List<JsParameter>
}