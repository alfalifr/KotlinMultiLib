package sidev.lib.reflex

object InnerReflex{
    const val K_CLASS_BASE_NAME= "KClassImpl"
    const val K_FUNCTION_CONSTRUCTOR_NAME_PREFIX= "fun <init>"
    const val K_FUNCTION_CONSTRUCTOR_NAME= "<init>"
    const val K_DEFAULT_FUNCTION_NAME_SUFFIX= "\$default"
    val K_PROPERTY_ARRAY_SIZE_STRING= Array<Any>::size.toString()
    val K_CLASS_ENUM_STRING= Enum::class
    val K_ARRAY_CLASS_STRING: String = Array<Any>::class.toString()
}