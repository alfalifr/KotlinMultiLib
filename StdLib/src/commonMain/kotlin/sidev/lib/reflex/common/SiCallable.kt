package sidev.lib.reflex.common

import sidev.lib.check.notNullTo


interface SiCallable<out R>: SiReflex {
    val name: String
    val returnType: SiType
    val parameters: List<SiParameter>
    val typeParameters: List<SiTypeParameter>
    /** Visibilitas akses dari komponen SiReflex ini, default public. */
    val visibility: SiVisibility
    val isAbstract: Boolean
    fun call(vararg args: Any?): R
    fun callBy(args: Map<SiParameter, Any?>): R
}


internal abstract class SiCallableImpl<out R>
    : SiReflexImpl(), SiCallable<R> {
    protected abstract val callBlock: (args: Array<out Any?>) -> R
    override fun call(vararg args: Any?): R {
        validateArgs(*args)
        return callBlock(args)
    }

    override fun callBy(args: Map<SiParameter, Any?>): R = call(
        *args.asSequence().mapNotNull { (param, value) ->
            parameters.find { it.name == param.name && it.index == param.index }
                .notNullTo { Pair(it, value) }
        }
            .sortedBy { it.first.index }
            .map { it.second }
            .toList()
            .toTypedArray()
    )

    protected fun validateArgs(vararg processedArgs: Any?){
        if(processedArgs.size < parameters.size){
            for(i in processedArgs.size until parameters.size){
                if(parameters[i].isOptional.not())
                    throw IllegalArgumentException("""Fungsi: "$this" butuh ${parameters.size}, argumen yg tersedia sebanyak ${processedArgs.size} tidak dapat mencukupi paramater wajib.""")
            }
        }
    }
}
