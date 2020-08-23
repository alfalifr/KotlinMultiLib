package sidev.lib.reflex.common

import sidev.lib.console.prine

interface SiDescriptor {
    /** Komponen [SiReflex] yg memiliki descriptor ini. */
    val owner: SiReflex

    /**
     * String tambahan yg menjelaskan lebih lanjut ttg [owner],
     * seperti:
     *   -> `val|var` pada [SiProperty],
     *   -> `fun` pada [SiFunction],
     *   -> `class` pada [SiClass],
     */
    val type: ElementType

    /**
     * Tampat kompononen [owner] menempel pada [SiReflex] lain.
     *
     * Jika [owner] berupa, maka [host] berupa:
     *   -> [SiClass] : [SiClass] lainnya atau `null`,
     *   -> [SiProperty] : [SiClass] atau `null` jika berupa top declaration,
     *   -> [SiFunction] : [SiClass] atau `null` jika berupa top declaration,
     *   -> [SiProperty.Accessor] : [SiProperty],
     *   -> [SiParameter] : [SiCallable]
     *   -> [SiType] : `null` karena tipe tidak terikat pada apapun.
     */
    val host: SiReflex?

    /**
     * Implementasi native code dari [owner].
     * `null` jika [owner] tidak punya padanan pada implementasi native.
     */
    val native: Any?

    /** Nama asli dari [owner] yg diambil dari [native]. */
    val innerName: String?

    /** Modifier tambahan untuk [owner]. */
    val modifier: Int

    enum class ElementType(val description: String){
        TYPE(""),
        TYPE_PARAMETER("type parameter"),
        CLASS("class"),
        PROPERTY("val"),
        MUTABLE_PROPERTY("var"),
        PARAMETER("parameter"),
        FUNCTION("fun"),

        /**
         * Untuk tipe dinamis yg tidak dapat digambarkan dalam refleksi.
         * Penggunaan tipe ini sangat jarang.
         */
        ANY("<reflex-unit>"),
    }
}


internal abstract class SiDescriptorImpl: SiDescriptor {
    abstract override var native: Any? //Agar dapat diganti nilainya setelah interface ini di-init.
    final override var host: SiReflex?= null //Untuk mengakomodasi ketergantungan cyclic, misalnya SiCallable butuh SiParameter, namun SiParameter juga butuh SiCallable sbg hostnya dalam descriptor.
        set(v){
            field= v
            isDescStrCalculated= false
        }
    protected var isDescStrCalculated= false
    override var modifier: Int = 0
}