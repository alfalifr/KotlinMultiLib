package sidev.lib.reflex.js

import kotlin.js.Json


interface JsProperty<T, out R>: JsCallable<R>{
    override val parameters: List<JsParameter>
        get() = emptyList()

    /**
     * Nama yg diberikan oleh compiler Kotlin.
     * Biasanya digunakan untuk mengakses scr langsung sebuah property.
     */
    override val innerName: String

    /**
     * <14 Agustu 2020>
     * Untuk menyesuaikan karakteristik Kotlin.
     */
    val isLateinit: Boolean

    /** Karena receiver pada konteks Js hanya satu. */
    override fun call(vararg args: Any?): Any? = try{ get(args.first() as T) }
        catch (e: Throwable){
            throw IllegalArgumentException("Property $name.call() harus dg argumen receiver.")
        }
    override fun callBy(args: Json): Any? = call(
        try{ args.properties.iterator().next() }
        catch (e: Throwable){ throw IllegalArgumentException("Property $name.callBy() harus dg argumen receiver.") }
    )

    /**
     * New pada konteks property digunakan untuk meng-clone nilai dari property.
     * Namun, clone dilakukan scr shallow. [args] akan dipass ke constructor property,
     * dan semuanya opsional.
     */
    override fun new(vararg args: Any?): R

    operator fun get(receiver: T): R
}

interface JsMutableProperty<T, R>: JsProperty<T, R>{
    operator fun set(receiver: T, value: R)
}

/** [func] adalah constructor dari property. Pada konteks mutable */
internal abstract class JsPropertyImpl<T, out R> : JsCallableImpl<R>(), JsProperty<T, R>{
    abstract override val name: String
    override val parameters: List<JsParameter> = super<JsProperty>.parameters
    override fun call(vararg args: Any?): Any? = super<JsProperty>.call(*args, name)
    override fun callBy(args: Json): Any? = super<JsProperty>.callBy(args)
    override fun toString(): String = "JsProperty $name"
    /*
    override fun new(vararg args: Any?): R {
        return super.new(*args) //TODO clone() blum ada implementasi
    }
    override fun newBy(args: Json): R {
        return super.newBy(args)
    }
 */
}