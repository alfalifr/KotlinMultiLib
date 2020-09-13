package sidev.lib.reflex.inner

import sidev.lib.reflex.core.SiReflexConst

object KotlinReflexConst{
    const val K_CLASS_BASE_NAME= "KClassImpl"
    const val K_FUNCTION_CONSTRUCTOR_NAME_PREFIX= "fun <init>"
    const val K_FUNCTION_CONSTRUCTOR_NAME= "<init>"
    const val K_DEFAULT_FUNCTION_NAME_SUFFIX= "\$default"
    val K_PROPERTY_ARRAY_SIZE_STRING= Array<Any>::size.toString()
    val K_CLASS_ENUM_STRING= Enum::class
    val K_ARRAY_CLASS_STRING: String = Array::class.toString()


    const val K_DELEGATE_GET_VALUE_FUNCTION_NAME= "getValue"
    const val K_DELEGATE_SET_VALUE_FUNCTION_NAME= "setValue"
    const val K_DELEGATE_GET_VALUE_FUNCTION= ".*$K_DELEGATE_GET_VALUE_FUNCTION_NAME\\(\\s*(?<refparam>[${SiReflexConst.IDENTIFIER_NAME_PATTERN}]+,)\\s*(?<propparam>[${SiReflexConst.IDENTIFIER_NAME_PATTERN}]+)\\s*\\)"
    const val K_DELEGATE_SET_VALUE_FUNCTION= ".*$K_DELEGATE_SET_VALUE_FUNCTION_NAME\\(\\s*(?<refparam>[${SiReflexConst.IDENTIFIER_NAME_PATTERN}]+,)\\s*(?<propparam>[${SiReflexConst.IDENTIFIER_NAME_PATTERN}]+,)\\s*(?<valueparam>[${SiReflexConst.IDENTIFIER_NAME_PATTERN}]+)\\s*\\)"
}