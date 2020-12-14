package sidev.lib.reflex.js

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.console.prine
import kotlin.js.Json
import sidev.lib.reflex.js.call as _call
import sidev.lib.reflex.js.new as _new

/**
 * <11 Agustus 2020>
 * Container untuk menampung semua jenis object refleksi pada JS.
 * Container ini menganggap bahwa semua properti pada JS bisa diakses.
 * Untuk kasus properti, [parameters] memiliki size == 0.
 *
 * Interface ini meng-extend [JsValueOf] dan [JsPrototype] agar nilai
 * yg dikembalikan saat mereferensi pada interface ini sama dg fungsi [JsClass] yg dibungkusnya.
 */
interface JsCallable<out T>: JsReflex, JsValueOf, JsPrototype {
    val name: String
    val parameters: List<JsParameter>
    val innerName: String
    override val prototype: Any
    /**
     * TODO <20 Agustus 2020> => Untuk sementara, tipe yg didapat berdasarkan value saat deklarasi
     *   sehingga tidak menjamin tipe yg sesungguhnya. Terutama bagi `lateinit var`.
     */
    val returnType: JsType
    fun call(vararg args: Any?): Any?
    fun callBy(args: Json): Any?
    fun new(vararg args: Any?): T
    fun newBy(args: Json): T
    override fun valueOf(): dynamic
}



/*
internal class JsCallableImpl_Class<T: Any>(private val clazz: JsClass<T>): JsCallable<T>{
    override val parameters: List<JsParameter> = getParam(clazz)

    override fun call(vararg args: dynamic): Any? = _call(clazz, *args)
    override fun callBy(args: Json): Any? = call(*args.sliceWithParam(parameters).toTypedArray())

    override fun new(vararg args: dynamic): T = _new(clazz, *args) as T
    override fun newBy(args: Json): T = new(*args.sliceWithParam(parameters).toTypedArray())
}
 */
/** <18 Agustus 2020>
 * [func] jadi public untuk kepentingan akses internal library.
 */
internal open class JsCallableImpl<out T>(open val func: Any) : JsCallable<T>{
    constructor(block: (args: Array<out Any?>) -> T): this(block as Any){
        isPureJsFunction= false
    }
    constructor(): this("any"){ //Untuk mengakomodasi JsPropertyImpl
        isPureJsFunction= false
    }

    enum class CallKind{ CALL, NEW }


    protected var isPureJsFunction: Boolean= true //hanya boleh diubah pada constructor.

    override val innerName: String
        get() = jsNativeName(func)

    override val name: String by lazy{ when{
        isPureJsFunction -> {
            @Suppress(SuppressLiteral.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE)
            try{ (func as JsClass<*>).name }
            catch (e: Throwable){ jsName(jsPureFunction(func)) }
        }
        else -> "<kotlin-lambda>"
    } }
    override val parameters: List<JsParameter> by lazy {
        if(isPureJsFunction) getParam(jsPureFunction(func))
        else emptyList()
    }
    override val prototype: Any by lazy { jsPureFunction(func).unsafeCast<Any>().prototype }
    override val returnType: JsType = JsType.dynamicType

    private fun invoke(args: Array<out Any?>, kind: CallKind): Any?{
        return if(isPureJsFunction) when(kind){
            CallKind.CALL -> _call(func, *args)
            CallKind.NEW -> _new(func, *args)
        } else (func as (args: Array<out Any?>) -> T)(args)
    }

    override fun call(vararg args: Any?): Any?{
        validateArgs(*args)
        return invoke(args, CallKind.CALL)
    }
    override fun callBy(args: Json): Any? = call(*args.sliceWithParam(parameters).toTypedArray())

    override fun new(vararg args: Any?): T {
        validateArgs(*args)
        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        return invoke(args, CallKind.NEW) as T
    }
    override fun newBy(args: Json): T = new(*args.sliceWithParam(parameters).toTypedArray())
    override fun toString(): String = if(isPureJsFunction) func.toString() else "<kotlin-lambda>"
    override fun valueOf(): dynamic = when{
        isPureJsFunction -> jsPureFunction(func).valueOf()
        this is JsProperty<*, *> -> ::get
        else -> "<kotlin-lambda>"
    }

    private fun validateArgs(vararg processedArgs: Any?){
        if(processedArgs.size < parameters.size){
            for(i in processedArgs.size until parameters.size)
                if(parameters[i].isOptional.not())
                    throw IllegalArgumentException("""Fungsi: "$name" memiliki ${parameters.size} parameter, namun tersedia ${processedArgs.size} argumen yg tidak dapat memenuhi parameter wajib.""")
        }
    }

    override fun equals(other: Any?): Boolean = when(other) {
        is JsClassImpl_<*> -> func == other.func
        is JsCallable<*> -> hashCode() == other.hashCode()
//        is JsClass<*> -> hashCode() == other.hashCode()
        else -> hashCode() == other.hashCode() //super.equals(other)
    }
    override fun hashCode(): Int = func.hashCode()
}