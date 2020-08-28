package sidev.lib.reflex.comp.native


internal abstract class SiNativeCallableImpl<out R>(protected val callBlock: (args: Array<out Any?>) -> R)
    : SiNativeImpl(), SiNativeCallable<R> {
    override fun call(vararg args: Any?): R {
        validateArgs(*args)
        return callBlock(args)
    }

    protected fun validateArgs(vararg processedArgs: Any?){
        if(processedArgs.size < parameters.size){
            for(i in processedArgs.size until parameters.size){
                if(parameters[i].isOptional.not())
                    throw IllegalArgumentException("""Fungsi: "$this" butuh ${parameters.size}, argumen yg tersedia sebanyak ${processedArgs.size} tidak dapat mencukupi paramater wajib.""")
            }
        }
    }
}