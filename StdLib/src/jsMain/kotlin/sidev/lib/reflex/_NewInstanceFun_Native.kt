package sidev.lib.reflex

import sidev.lib.console.prine
import sidev.lib.reflex.js.new
import kotlin.reflect.KClass

actual fun <T: Any> nativeSimpleNew(clazz: KClass<out T>, default: T?): T? {
    return try{
        val new= new(clazz.js)
        if(new != undefined) new
        else {
            prine("""nativeSimpleNew(): clazz: "$clazz" tidak dapat di-instansiasi, return `default`.""")
            default
        }
    } catch (e: Throwable){ default } as? T
}