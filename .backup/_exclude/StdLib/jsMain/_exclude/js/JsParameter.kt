package sidev.lib.reflex.js

import kotlin.reflect.KType


interface JsParameter: JsReflex /*: KParameter*/{
    val index: Int
    val name: String?
    val isOptional: Boolean
        get()= true
    /**
     * TODO <20 Agustus 2020> => Untuk sementara, tipe yg didapat berdasarkan default value sehingga
     *   tidak menjamin tipe yg sesungguhnya.
     */
    val type: JsType
        get() = JsType.dynamicType
    /**
     * Berisi default value yg didefinisikan untuk param ini.
     * [defaultValue] akan berisi string statement instansiasi object jika tidak dapat diinstansiasi.
     */
    val defaultValue: Any?
        get()= null
}
internal abstract class JsParameterImpl: JsParameter{
    abstract override var defaultValue: Any?
    abstract override var isOptional: Boolean
    override fun toString(): String = "JsParameter #$index $name${if (this.isOptional) "?" else "" }: $type"
}