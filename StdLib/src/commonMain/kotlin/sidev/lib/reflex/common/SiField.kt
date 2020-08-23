package sidev.lib.reflex.common

@ExperimentalStdlibApi
interface SiField<in R, out T>: SiReflex{
    val name: String
    val type: SiType
    fun get(receiver: R): T
}

@ExperimentalStdlibApi
interface SiMutableField<in R, T>: SiField<R, T>{
    fun set(receiver: R, value: T)
    fun afa(){
        (descriptor.native)
    }
}

