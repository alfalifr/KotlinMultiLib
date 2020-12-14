package sidev.lib.reflex.js.kotlin

import sidev.lib.reflex.js.JsReflexConst

object KotlinJsConst{
    /** Karena Kotlin menargetkan ES5 yg blum support default param. */
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN = "if ($ === void 0)"
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX = "if\\s*\\([a-z0-9]+\\s*={2,3}\\s*void\\s*[(0)]+\\)\\s*(?<param>[a-z0-9]+)\\s*=\\s*(?<val>[.'a-z0-9]+.*);"
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX_CHECK = "if\\s*\\([a-z0-9]+\\s*={2,3}\\s*void\\s*[(0)]+\\)"
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX_ASSIGNMENT = "(?<param>[a-z0-9]+)\\s*=\\s*(?<val>[.'a-z0-9]+.+);"
    const val FUNCTION_INNER_NAME_PATTERN = "(?<innerfun>[a-zA-Z0-9\$_]+)_[a-zA-Z0-9\$_]{4,6}\\\$"
    const val PROPERTY_LATEINIT_PATTERN = "(?:(?<lateprop>[a-zA-Z0-9\$_]+)_[a-zA-Z0-9\$_]{6}\\\$_0)"
    const val PROPERTY_LAZY_DELEGATE_PATTERN = "(?<classname>${JsReflexConst.IDENTIFIER_NAME_PATTERN})\\$(?<propname>${JsReflexConst.IDENTIFIER_NAME_PATTERN})\\\$lambda"
    const val PROPERTY_PRIVATE_PATTERN = "(?:(?<privprop>[a-zA-Z0-9\$_]+)_0)"
    const val PROPERTY_INITIALIZATION_PATTERN = "this\\.(?<prop>[a-zA-Z0-9\$_]+)[\\s=]+(?<value>this\\.$PROPERTY_LATEINIT_PATTERN|$PROPERTY_PRIVATE_PATTERN|.+);" //Karena prop yg gak di-initialize brarti bkn property.
    const val FUNCTION_CONTRUCTOR_SECONDARY = "function\\s*(?<innername>[a-zA-Z0-9\$_]+)_init(?:_(?<declarnumber>\\d)+)*\\((?<param>[a-zA-Z0-9\$_,\\s]+)\\)\\s*\\{\\s*(?<initblock>\\\$this = \\\$this \\|\\| Object\\.create\\([a-zA-Z0-9\$_.]+\\);)[\\S\\s]+\\}"
    const val FUNCTION_CONTRUCTOR_SUPER_CALL = "(?<constructor>[a-zA-Z0-9\$_]+)\\.call\\(this.*\\);"
    const val FUNCTION_PATTERN = "function(?:\\s+(?<functionname>$FUNCTION_INNER_NAME_PATTERN|[a-zA-Z0-9_$]+))?\\s*${JsReflexConst.FUNCTION_PARAMETER_PATTERN}\\s*${JsReflexConst.FUNCTION_BODY_PATTERN}"

    const val K_CLASS_IMPL_STRING = "class null"
    const val K_CLASS_IMPL_NAME = "PrimitiveKClassImpl"
    const val K_FUNCTION_NAME_PREFIX = "Function"
    const val LAZY_DELEGATE_NAME= "UnsafeLazyImpl"
    const val LAZY_DELEGATE_INITIALIZER_NAME= "initializer_0"

    const val KOTLIN_JS_NOT_SUPPORT_ANNOTATION_YET_MSG= "Kotlin/Js belum support anotasi pada runtime"
//    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_VALUE_ASSIGNMENT = "$ = "
}