package sidev.lib.reflex.js

object JsReflexConst {
    const val FUNCTION_PARAMETER_PATTERN= "\\((?<param>[a-zA-Z0-9\$_,\\s]+)?\\)"
    const val FUNCTION_BODY_PATTERN= "\\{(?<block>[\\S\\s]+)?\\}"
    const val FUNCTION_PATTERN= "function(?:\\s+(?<functionname>[a-zA-Z0-9_$]+))?\\s*$FUNCTION_PARAMETER_PATTERN\\s*$FUNCTION_BODY_PATTERN"
    const val SI_REFLEX_METADATA_KEY= "\$si_reflex\$"
}