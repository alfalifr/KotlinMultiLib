package sidev.lib.reflex.full

import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiFunction
import sidev.lib.reflex.js.*
import sidev.lib.`val`.SuppressLiteral
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
    get()= (descriptor.native as JsClass_<*>).isArray //((this.descriptor.native as? JsClassImpl_<*>)?.func as? JsClass<*>)?.kotlin == Array<Any>::class

actual val SiClass<*>.isPrimitiveArray: Boolean get()= false

actual val Any.isNativeReflexUnit: Boolean
    get()= this is JsReflex

//TODO <23 Agustus 2020> => Pengecekan pada Js hanya dilakukan pada pengecekan tipe kelass, bkn adanya method getValue() / setValue().
internal actual val Any.isNativeDelegate: Boolean get()= false

//TODO <23 Agustus 2020> => Untuk Js, implementasi seperti default karena belum diketahui pengecekan interface.
internal actual val SiClass<*>.isNativeInterface: Boolean get()= isAbstract && !isInstantiable

actual val <T: Any> SiClass<T>.primaryConstructor: SiFunction<T> get() = constructors.first()

//TODO <26 Agustus 2020> => Karena Kotlin/Js blum support refleksi, dan agak gak mungkin kalo ngambil langsung dari kode.
actual val SiClass<*>.sealedSubclasses: Sequence<SiClass<*>> get()= emptySequence()

actual val SiClass<*>.isAnonymous: Boolean get()= (descriptor.native as JsClass_<*>).name.endsWith(JsReflexConst.ANONYMOUS_CLASS_NAME_SUFFIX)