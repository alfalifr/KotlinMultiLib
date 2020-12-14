package sidev.lib.reflex

internal abstract class SiDescriptorImpl: SiReflexImpl(),
    SiDescriptor {
    abstract override var native: Any? //Agar dapat diganti nilainya setelah interface ini di-init.
    final override var host: SiDescriptorContainer?= null //Untuk mengakomodasi ketergantungan cyclic, misalnya SiCallable butuh SiParameter, namun SiParameter juga butuh SiCallable sbg hostnya dalam descriptor.
        set(v){
            field= v
            isDescStrCalculated= false
        }
    protected var isDescStrCalculated= false
    override var modifier: Int = 0
}