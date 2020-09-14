package sidev.lib.reflex.js.kotlin

import sidev.lib.reflex.js.*
import kotlin.reflect.KClass

/** Untuk mengambil supertype yg disuntikan pada metadata oleh Kotlin Js. */
fun kotlinSupertypes(func: Any): List<dynamic> = jsConstructor(func).unsafeCast<Any>().kotlinMetadata.interfaces

/** Untuk mengambil fungsi yg sesuai dg konfigurasi Kotlin Js. */
fun kotlinFun(func: Any): KotlinJsFunction{
    jsNotNull(func)
    val meta= func.kotlinMetadata //.also { jsNotNull(it, "Metadata kosong, bkn kotlinFun.") }
    return KotlinJsFunction(func, meta)
}
/** Untuk mengambil metadata yg disuntikan pada constructor oleh Kotlin Js. */
val Any.kotlinMetadata: KotlinJsMetadata
    get(){
        jsNotNull(this)
        val metaOwner= if(this is KClass<*>) this.asDynamic().`jClass_1ppatx$_0` else this
        val meta= metaOwner.`$metadata$`
        val ints= (meta.interfaces as Array<dynamic>).toList()
        return try{ KotlinJsMetadata(meta.kind, meta.simpleName, ints, meta.`$kClass$`) }
        catch (e: Throwable){ throw IllegalStateException("Objek: \"${str(this)}\" bkn merupakan kotlinFun dan gak punya metadata.") }
    }

internal val Any.isKotlinFun: Boolean
    get()= try{ kotlinMetadata; true } catch (e: Throwable){ false }

/*
/**
 * Mengambil fungsi yg didefinisikan dg fungsi [js()][js] dari sebuah objek
 */
@Deprecated("gunakan KClass<*>.js", ReplaceWith("KClass<*>.js"))
internal val KClass<*>.function: dynamic
    get()= asDynamic().`jClass_1ppatx$_0`
 */