package sidev.lib.reflex.common.full

import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiDescriptor
import sidev.lib.reflex.common.SiReflex
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.js.*

//TODO <20 Agustus 2020> => kelas String, Number, dan Boolean belum sesuai kelas yg asli di Kotlin.
actual val SiClass<*>.isPrimitive: Boolean
    get(){
        val native= (descriptor.native!! as JsClassImpl_<*>).func
        return native.isString || native.isNumber || native.isBoolean
    }
//TODO <20 Agustus 2020> => Untuk sementara semua array dianggap sbg objek
actual val SiClass<*>.isObjectArray: Boolean get()= true
actual val SiClass<*>.isPrimitiveArray: Boolean get()= false
actual val Any.isNativeReflexUnit: Boolean
    get()= this is JsReflex
