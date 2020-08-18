package sidev.lib.reflex.js.kotlin

object KotlinJsConst{
    /** Karena Kotlin menargetkan ES5 yg blum support default param. */
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN = "if ($ === void 0)"
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX = "if\\s*\\([a-z0-9]+\\s*={2,3}\\s*void\\s*[(0)]+\\)\\s*(?<param>[a-z0-9]+)\\s*=\\s*(?<val>[.'a-z0-9]+.+);"
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX_CHECK = "if\\s*\\([a-z0-9]+\\s*={2,3}\\s*void\\s*[(0)]+\\)"
    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_REGEX_ASSIGNMENT = "(?<param>[a-z0-9]+)\\s*=\\s*(?<val>[.'a-z0-9]+.+);"
    const val PROPERTY_LATEINIT_PATTERN = "(?<lateprop>[a-zA-Z0-9\$_]+)_[a-zA-Z0-9\$_]{6}\\\$_\\d"
    const val PROPERTY_INITIALIZATION_PATTERN = "this\\.(?<prop>[a-zA-Z0-9\$_]+)[\\s=]+(?<value>this\\.$PROPERTY_LATEINIT_PATTERN|.+);" //Karena prop yg gak di-initialize brarti bkn property.
    const val FUNCTION_CONTRUCTOR_SECONDARY = "function\\s*(?<innername>[a-zA-Z0-9\$_]+)_init(?:_(?<declarnumber>\\d)+)*\\((?<param>[a-zA-Z0-9\$_,\\s]+)\\)\\s*\\{\\s*(?<initblock>\\\$this = \\\$this \\|\\| Object\\.create\\([a-zA-Z0-9\$_.]+\\);)[\\S\\s]+\\}"
    const val FUNCTION_CONTRUCTOR_SUPER_CALL = "(?<constructor>[a-zA-Z0-9\$_]+)\\.call\\(this.*\\);"
//    const val FUNCTION_DEFAULT_PARAMETER_PATTERN_VALUE_ASSIGNMENT = "$ = "
}