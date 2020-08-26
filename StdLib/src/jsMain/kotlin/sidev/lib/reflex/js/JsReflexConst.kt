package sidev.lib.reflex.js

object JsReflexConst {
    const val IDENTIFIER_NAME_PATTERN= "[a-zA-Z0-9\$_]+"
    const val ANONYMOUS_CLASS_NAME_SUFFIX= "\$ObjectLiteral"
    const val FUNCTION_PARAMETER_PATTERN= "\\((?<param>[a-zA-Z0-9\$_,\\s]+)?\\)"
    const val FUNCTION_BODY_PATTERN= "\\{(?<block>[\\S\\s]+)?\\}"
    const val FUNCTION_PATTERN= "function(?:\\s+(?<functionname>[a-zA-Z0-9_$]+))?\\s*$FUNCTION_PARAMETER_PATTERN\\s*$FUNCTION_BODY_PATTERN"
    const val FUNCTION_NEW_INSTANTIATION_PATTERN= "new\\s+(?<functionname>[a-zA-Z0-9_\$]+)\\s*$FUNCTION_PARAMETER_PATTERN"

    const val PARAMETER_CHECK_FUN_IS_WRAPPER = "__\$isFunTypeWrapper\$__"
}