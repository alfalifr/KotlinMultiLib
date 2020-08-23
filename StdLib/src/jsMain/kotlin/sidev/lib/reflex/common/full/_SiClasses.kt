package sidev.lib.reflex.common.full

import sidev.lib.console.prine
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.common.*
import sidev.lib.reflex.js.*
import sidev.lib.universal.`val`.SuppressLiteral
import kotlin.reflect.KClass

actual val SiClass<*>.isPrimitive: Boolean get() = when((descriptor.native!! as JsClass_<*>).name){
    JsPrimitiveType.STRING.jsConstructorName -> true
    JsPrimitiveType.NUMBER.jsConstructorName -> true
    JsPrimitiveType.BOOLEAN.jsConstructorName -> true
    else -> false
}
//TODO <20 Agustus 2020> => Untuk sementara semua array dianggap sbg objek
@Suppress(SuppressLiteral.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE)
actual val SiClass<*>.isObjectArray: Boolean
    get()= ((this.descriptor.native as? JsClassImpl_<*>)?.func as? JsClass<*>)?.kotlin == Array<Any>::class
actual val SiClass<*>.isPrimitiveArray: Boolean get()= false
actual val Any.isNativeReflexUnit: Boolean
    get()= this is JsReflex

actual val <T: Any> SiClass<T>.primaryConstructor: SiFunction<T> get() = constructors.first()