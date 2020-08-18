package sidev.lib.reflex.js

/**
 * Mengambil fungsi yg didklarasikan pada konstruktor dari [obj]
 * atau [obj] itu sendiri jika merupakan fungsi.
 * Fungsi yg diambil hanya yg publik.
 */
fun getDeclaredFunction(obj: Any): List<JsCallable<*>>{
    if(obj.isUndefined)
        throw IllegalArgumentException("obj: undefined.") //Agar lebih kontekstual.
    return obj.prototype.properties.filter { it.second?.isFunction == true }
        .map { JsCallableImpl<Any?>(it.second!!) }.toList()
}

val Any.prototype: Any
    get()= if(isFunction) this.asDynamic().prototype
    else try{ asDynamic().__proto__ }
    catch (e: Throwable){
        throw IllegalStateException("objek: \"${str(this)}\" tidak punya prototype.")
    }

//val Any.constructor: Any