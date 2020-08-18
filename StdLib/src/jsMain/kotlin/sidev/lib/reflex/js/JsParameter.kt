package sidev.lib.reflex.js

import kotlin.reflect.KType


interface JsParameter/*: KParameter*/{
    val index: Int
    val name: String?
    val isOptional: Boolean
        get()= true
    val type: KType
        get() = JsType.dynamicType
    /**
     * Berisi default value yg didefinisikan untuk param ini.
     * [defaultValue] akan berisi string statement instansiasi object jika tidak dapat diinstansiasi.
     */
    val defaultValue: Any?
        get()= null
}
internal interface JsParameterImpl: JsParameter{
    override var defaultValue: Any?
    override var isOptional: Boolean
}