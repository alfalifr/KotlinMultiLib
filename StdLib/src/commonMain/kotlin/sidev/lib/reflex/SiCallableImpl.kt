package sidev.lib.reflex

import sidev.lib.console.prine

internal abstract class SiCallableImpl<out R>
    : SiDescriptorContainerImpl(),
    SiCallable<R>, SiAnnotatedElementImpl {
    override val annotations: MutableList<Annotation> = arrayListOf()
    protected abstract val callBlock: (args: Array<out Any?>) -> R

    //Format fungsi default (fungsi dg param opsional) di Kotlin:
    // <nama>(Object arg0 <biasanya instance receiver>, <type> arg1, <type> arg2, ... , int mask, Object <gak tau ini untuk apa, mungkin marker>)
    // param mask digunakan untuk menunjukan param mana yg perlu diisi dg nilai default.
    /** Digunakan untuk memanggil fungsi yg memiliki param dg nilai default. */
    protected abstract val defaultCallBlock: ((args: Array<out Any?>) -> R)?

    private var requiredParamCount= -1

    override fun call(vararg args: Any?): R {
        validateArgs(*args)
        return callBlock(args)
    }

    override fun callBy(args: Map<SiParameter, Any?>): R {
        var mask= 0
        val masks= ArrayList<Int>(1) //Jika parameter melebihi 32, maka mask-nya ada lebih dari satu, kemungkinan Kotlin melakukannya sprti itu.

        var isAnyOptional= false
        val params= args.keys
        val passedArgs= mutableListOf<Any?>()
        var i= 0

        for(param in parameters){
            if(i > 0 && i % Int.SIZE_BITS == 0){
                masks.add(mask)
                mask= 0
            }

            when{
                param in params -> passedArgs.add(args[param])
                param.isOptional -> {
                    val defaultValue= param.defaultValue
                        ?: defaultPrimitiveValue(
                            (param.type.classifier as? SiClass<*>)?.kotlin ?: Any::class
                        )
                    passedArgs.add(defaultValue)

//                    prine("""callBy(): param= "$param" nilai default= $defaultValue""")

                    mask= mask or (1 shl (i % Int.SIZE_BITS))
                    isAnyOptional= true
                }
                else -> throw IllegalArgumentException("Parameter: \"$param\" tidak opsional dan tidak ada nilai yg di-pass.")
            }
            if(param.kind == SiParameter.Kind.VALUE)
                i++
        }
        masks.add(mask)

        if(!isAnyOptional)
            return call(*passedArgs.toTypedArray())

        passedArgs.addAll(masks)

        //Hanya sbg param tambahan di akhir, krg tau gunanya.
        // Mungkin marker, misalkan DefaultConstructorMarker dan Object pada fungsi biasa.
        passedArgs.add(null)

//        prine("callBy: passedArgs= $passedArgs")

        return defaultCallBlock!!(passedArgs.toTypedArray())
    }
/*
            = call(
        *args.asSequence().mapNotNull { (param, value) ->
            parameters.find { it.name == param.name && it.index == param.index }
                .notNullTo { Pair(it, value) }
        }
            .sortedBy { it.first.index }
            .map { it.second }
            .toList()
            .toTypedArray()
    )
 */

    protected fun validateArgs(vararg processedArgs: Any?){
        if(processedArgs.size < parameters.size){
            for(i in processedArgs.size until parameters.size){
                if(parameters[i].isOptional.not())
                    throw IllegalArgumentException("""Fungsi: "$this" butuh ${parameters.size}, argumen yg tersedia sebanyak ${processedArgs.size} tidak dapat mencukupi paramater wajib.""")
            }
        }
    }
}
