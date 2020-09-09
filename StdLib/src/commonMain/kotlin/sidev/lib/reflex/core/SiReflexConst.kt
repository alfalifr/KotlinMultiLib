package sidev.lib.reflex.core

import sidev.lib.platform.Platform
import sidev.lib.platform.getGlobalObject
import sidev.lib.platform.getInternalObject
import sidev.lib.platform.platform
import sidev.lib.type.Null

object SiReflexConst {
    const val TEMPLATE_REFLEX_UNIT_DESC = "<reflex-unit>"
    const val TEMPLATE_REFLEX_UNIT_NAME = "<sireflex>"
    const val TEMPLATE_TYPE_NAME = "<type>"
    const val TEMPLATE_NATIVE_CLASSIFIER_NAME = "<native-classifier>"
    const val TEMPLATE_NATIVE_ANNOTATION_NAME = "<native-annotation>"
    const val TEMPLATE_NO_NAME = "<no-name>"

    const val IDENTIFIER_NAME_PATTERN= "a-zA-Z0-9\$_"
    const val FUNCTION_CONSTRUCTOR_NAME_PATTERN = "fun(?: (?<typeparam>[$IDENTIFIER_NAME_PATTERN,:&\\s<>]+))? (?<classname>[$IDENTIFIER_NAME_PATTERN.]+)?<init>\\((?<param>.*)\\)(?:: (?<returntype>.+))?" //.toRegex()

    const val SI_REFLEX_METADATA_KEY= "\$reflex\$"
    const val SI_ANNOTATION_METADATA_KEY= "\$annotations\$"
    const val SI_METADATA_KEY= "__\$si_metadata\$__"
    const val TEMP_VAR_NAME= "_____$\$__$$\$___"
    const val SI_JS_GLOBAL_OBJECT_NAME= "__\$si_global\$__"
    const val SI_JS_GLOBAL_OBJECT_NAME_PREFIX= "__\$si\$__"
    const val SI_JS_GLOBAL_INTERNAL_OBJECT_NAME= "__\$si_internal\$__"

    const val NOTE_CLASSIFIER_NOT_READY= "<classifier belum siap>"

    val nullObj get()= if(platform == Platform.JVM) Null else {
        Null
        getInternalObject("Null") as Null
    }
    val nullClass get()= nullObj.clazz
    val nullType get()= nullObj.type

//    const val PROPERTY_ARRAY_SIZE_STRING= "val "
}