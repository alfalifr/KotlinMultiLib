package sidev.lib.reflex.js.kotlin

import sidev.lib.reflex.js.isUndefined
import sidev.lib.reflex.js.jsNotNull
import sidev.lib.reflex.js.prototype
import sidev.lib.reflex.js.str

/** Untuk mengambil constructor pada objek Js. */
fun jsConstructor(func: Any): dynamic {
    val constr= func.prototype.asDynamic().constructor as? Any
    if(constr == null || constr.isUndefined)
        throw IllegalArgumentException("""Fungsi: "${str(func)}" tidak punya konstruktor.""")
    return constr
}
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
        val meta= this.asDynamic().`$metadata$`
        val ints= (meta.interfaces as Array<dynamic>).toList()
        return try{ KotlinJsMetadata(meta.kind, meta.simpleName, ints, meta.`$kClass$`) }
        catch (e: Throwable){ throw IllegalStateException("Objek: \"${str(this)}\" bkn merupakan kotlinFun dan ak punya metadata.") }
    }

internal val Any.isKotlinFun: Boolean
    get()= try{ kotlinMetadata; true } catch (e: Throwable){ false }