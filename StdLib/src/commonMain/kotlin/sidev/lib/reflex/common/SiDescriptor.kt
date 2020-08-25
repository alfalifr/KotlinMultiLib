package sidev.lib.reflex.common

interface SiDescriptor: SiReflex {
    /** Komponen [SiDescriptorContainer] yg memiliki descriptor ini. */
    val owner: SiDescriptorContainer

    /**
     * String tambahan yg menjelaskan lebih lanjut ttg [owner],
     * seperti:
     *   -> `val|var` pada [SiProperty],
     *   -> `fun` pada [SiFunction],
     *   -> `class` pada [SiClass],
     */
    val type: ElementType

    /**
     * Tampat kompononen [owner] menempel pada [SiDescriptorContainer] lain.
     *
     * Jika [owner] berupa, maka [host] berupa:
     *   -> [SiClass] : [SiClass] lainnya atau `null`,
     *   -> [SiProperty] : [SiClass] atau `null` jika berupa top declaration,
     *   -> [SiFunction] : [SiClass] atau `null` jika berupa top declaration,
     *   -> [SiProperty.Accessor] : [SiProperty],
     *   -> [SiParameter] : [SiCallable]
     *   -> [SiType] : `null` karena tipe tidak terikat pada apapun.
     */
    val host: SiDescriptorContainer?

    /**
     * Implementasi native code dari [owner].
     * `null` jika [owner] tidak punya padanan pada implementasi native.
     */
    val native: Any?

    /** Nama asli dari [owner] yg diambil dari [native]. */
    val innerName: String?

    /** Modifier tambahan untuk [owner]. */
    val modifier: Int

    /** Id unik yg digunakan untuk mengidentifikasi apakah 2 [SiDescriptorContainer] sama walau instance-nya beda. */
    val identifier: Int

    enum class ElementType(val description: String){
        TYPE(""),
        TYPE_PARAMETER("type parameter"),
        CLASS("class"),
        PROPERTY("val"),
        MUTABLE_PROPERTY("var"),
        FIELD("field"),
        MUTABLE_FIELD("mutable field"),
        PARAMETER("parameter"),
        FUNCTION("fun"),

        /**
         * Untuk tipe dinamis yg tidak dapat digambarkan dalam refleksi.
         * Penggunaan tipe ini sangat jarang.
         */
        ANY("<reflex-unit>"),
    }
}


internal abstract class SiDescriptorImpl: SiReflexImpl(), SiDescriptor {
    abstract override var native: Any? //Agar dapat diganti nilainya setelah interface ini di-init.
    final override var host: SiDescriptorContainer?= null //Untuk mengakomodasi ketergantungan cyclic, misalnya SiCallable butuh SiParameter, namun SiParameter juga butuh SiCallable sbg hostnya dalam descriptor.
        set(v){
            field= v
            isDescStrCalculated= false
        }
    protected var isDescStrCalculated= false
    override var modifier: Int = 0
}