package sidev.lib.reflex.common.full

import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiDescriptor
import sidev.lib.reflex.common.SiReflex
import sidev.lib.reflex.common.SiType


expect val SiClass<*>.isPrimitive: Boolean
expect val SiClass<*>.isObjectArray: Boolean
expect val SiClass<*>.isPrimitiveArray: Boolean
expect val Any.isNativeReflexUnit: Boolean

val SiType.isPrimitive: Boolean
    get()= (classifier as? SiClass<*>)?.isPrimitive == true


val SiClass<*>.isArray: Boolean
    get()= isObjectArray || isPrimitiveArray

val Any.isReflexUnit: Boolean
    get()= this is SiReflex || this is SiDescriptor
            || isNativeReflexUnit

val SiClass<*>.isInterface: Boolean
    get()= isAbstract && !isInstantiable

val SiClass<*>.isInstantiable: Boolean
    get()= constructors.isNotEmpty()

val SiType.isInterface: Boolean
    get()= (this.classifier as? SiClass<*>)?.isInterface ?: false


/**
 * Menunjukan jika kelas `this.extension` ini merupakan anonymous karena di-extend
 * oleh variabel lokal dan kelas yg di-extend bkn merupakan kelas abstract.
 */
val SiClass<*>.isShallowAnonymous: Boolean
    get()= qualifiedName == null && supertypes.size == 1
            && !(supertypes.first().classifier as SiClass<*>).isAbstract
            && isAllMembersImplemented

/**
 * Menunjukan apakah `this.extension` [KClass] abstrak scr sederhana, yaitu
 * tidak memiliki fungsi atau properti yg abstrak dan supertype hanya satu.
 * Berguna untuk operasi [new] pada kelas abstrak sehingga dapat mengembalikan
 * instance dg superclass.
 */
val SiClass<*>.isShallowAbstract: Boolean
    get()= isAbstract && supertypes.size == 1
            && !(supertypes.first().classifier as SiClass<*>).isAbstract
            && isAllMembersImplemented

val SiClass<*>.isAllMembersImplemented: Boolean
    get()= members.find { it.isAbstract } == null