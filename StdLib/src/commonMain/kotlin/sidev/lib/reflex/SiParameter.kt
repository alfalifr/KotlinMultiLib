package sidev.lib.reflex


interface SiParameter: SiDescriptorContainer {
    val index: Int
    val name: String?
    val type: SiType
    val kind: Kind
    val isOptional: Boolean
    val isVararg: Boolean

    /** Property ini sementara hanya bisa didapatkan pada Js. */
    val defaultValue: Any?
        get()= null

    enum class Kind{
        /** Parameter yg menunjukan pemilik (instance) dari callable yg memiliki parameter ini. */
        INSTANCE,

        /** Instance yg digunakan untuk konteks `this` pada sebuah fungsi extension. */
        RECEIVER,

        /** Parameter yg memiliki nilai sebenarnya sesuai parameter yg dideklarasikan pada kode. */
        VALUE
    }
}