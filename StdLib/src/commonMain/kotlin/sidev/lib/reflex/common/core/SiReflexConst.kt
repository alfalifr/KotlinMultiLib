package sidev.lib.reflex.common.core

object SiReflexConst {
    const val TEMPLATE_REFLEX_UNIT_DESC = "<reflex-unit>"
    const val TEMPLATE_REFLEX_UNIT_NAME = "<sireflex>"
    const val TEMPLATE_TYPE_NAME = "<type>"
    const val TEMPLATE_NATIVE_CLASSIFIER_NAME = "<native-classifier>"
    const val TEMPLATE_NO_NAME = "<no-name>"

    const val IDENTIFIER_NAME_PATTERN= "a-zA-Z0-9\$_"
    const val FUNCTION_CONSTRUCTOR_NAME_PATTERN = "fun(?: (?<typeparam>[$IDENTIFIER_NAME_PATTERN,:&\\s<>]+))? (?<classname>[$IDENTIFIER_NAME_PATTERN.]+)?<init>\\((?<param>.*)\\)(?:: (?<returntype>.+))?" //.toRegex()

    const val SI_REFLEX_METADATA_KEY= "\$si_reflex\$"
    const val TEMP_VAR_NAME= "_____$\$__$$\$___"
    const val SI_JS_GLOBAL_OBJECT_NAME= "__\$si_global\$__"

    const val NOTE_CLASSIFIER_NOT_READY= "<classifier belum siap>"

//    const val PROPERTY_ARRAY_SIZE_STRING= "val "
}