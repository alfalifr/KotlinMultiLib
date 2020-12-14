package sidev.lib.reflex.js

import sidev.lib.reflex.core.SiReflexConst

interface JsType: JsReflex {
    /**
     * Classifier yg membedakan antar sesama [primitive].
     * Dalam konteks Js, [classifier] dapat dibedakan dari nama dari constructor.
     *
     * Contoh: [primitive] sama-sama [JsPrimitiveType.OBJECT],
     * namun yg satu berupa objek [ArrayList] dan yg satunya berupa [Sequence].
     */
    val classifier: JsClass_<*>?
    val primitive: JsPrimitiveType

    /**
     * Penanda apakah [classifier] sudah diresolve atau tidak karena
     * masalah ReferenceError saat memanggil nama sebuah fungsi constructor [classifier].
     */
    val isClassifierResolved: Boolean

    companion object{
        val dynamicType= createType(JsPrimitiveType.DYNAMIC)
    }
}

internal abstract class JsTypeImpl: JsType{
    abstract override var isClassifierResolved: Boolean
    override fun toString(): String = "JsType ${primitive.jsName} ${if(isClassifierResolved) classifier?.name else SiReflexConst.NOTE_CLASSIFIER_NOT_READY}"
}