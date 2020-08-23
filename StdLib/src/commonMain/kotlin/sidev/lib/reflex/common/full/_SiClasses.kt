package sidev.lib.reflex.common.full

import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.SkippableIteratorImpl
import sidev.lib.collection.lazy_list.asCached
import sidev.lib.collection.string
import sidev.lib.console.prine
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.property.UNINITIALIZED_VALUE
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.native.si
import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass


expect val SiClass<*>.isPrimitive: Boolean
expect val SiClass<*>.isObjectArray: Boolean
expect val SiClass<*>.isPrimitiveArray: Boolean
internal expect val Any.isNativeReflexUnit: Boolean
internal expect val Any.isNativeDelegate: Boolean
internal expect val SiClass<*>.isNativeInterface: Boolean

expect val <T: Any> SiClass<T>.primaryConstructor: SiFunction<T>


val <T: Any> SiClass<T>.kotlin: KClass<T>
    get()= descriptor.native as KClass<T>


val SiType.isPrimitive: Boolean
    get()= (classifier as? SiClass<*>)?.isPrimitive == true


val SiClass<*>.isArray: Boolean
    get()= isObjectArray || isPrimitiveArray

val Any.isReflexUnit: Boolean
    get()= this is SiReflex || this is SiDescriptor
            || isNativeReflexUnit

val Any.isUninitializedValue: Boolean
    get()= this::class == UNINITIALIZED_VALUE::class

//<23 Agustus 2020> => implementasi diganti menjadi [isNativeInterface], karena jika isAbstract && !isInstantiable,
// belum tentu merupakan interface. Bisa jadi itu adalah abstract kelas yg hanya bisa di-instansiasi lewat builder di dalamnya.
val SiClass<*>.isInterface: Boolean
    get()= isNativeInterface //isAbstract && !isInstantiable

val SiClass<*>.isObject: Boolean
    get()= !isAbstract && !isInstantiable

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

/**
 * Mengindikasikan bahwa data dg tipe `this.extension` [KClass] ini aman untuk di-copy scr langsung
 * tanpa harus di-instantiate agar tidak menyebabkan masalah yg berkaitan dg referential.
 *
 * Contoh tipe data yg aman untuk di-copy scr langsung tanpa harus di-instantiate adalah
 * tipe data primitif dan String. Jika ada tipe data lain yg immutable, tipe data tersebut juga
 * copy-safe.
 */
val <T: Any> SiClass<T>.isCopySafe: Boolean
    get()= isPrimitive || this == String::class.si || isSubclassOf(Enum::class.si)
/*
    get(){
        val prim= isPrimitive
        prine("SiClass<T>.isCopySafe prim= $prim")
        val isStr= this == String::class.si
        prine("SiClass<T>.isCopySafe isStr= $isStr")
        val isEnum= isSubclassOf(Enum::class.si)
        prine("SiClass<T>.isCopySafe this= $this prim= $prim isStr= $isStr isEnum= $isEnum")
        return prim || isStr || isEnum
    }
 */

val Any.isDelegate: Boolean get()= when(this){
//    is Lazy<*> -> true //Gak semua Lazy adalah delegate. Hal teresebut dikarenakan Lazy gak punya fungsi getValue() sbg instance member.
    is ReadOnlyProperty<*, *> -> true
    is ReadWriteProperty<*, *> -> true
    else -> isNativeDelegate.let { if(!it) this is Lazy<*> else it } //Ternyata ada built-in delegate yaitu lazy yg gak punya fungsi getValue() / setValue().
}

/** @return `true` jika `this.extension` merupakan subclass atau sama dg [base]. */
fun SiClass<*>.isSubclassOf(base: SiClass<*>): Boolean = base in classesTree
fun SiClass<*>.isSuperclassOf(derived: SiClass<*>): Boolean = derived.isSubclassOf(this)

fun SiClass<*>.isExclusivelySuperclassOf(derived: SiClass<*>): Boolean
        = this != derived && isSuperclassOf(derived)
fun SiClass<*>.isExclusivelySubclassOf(base: SiClass<*>): Boolean
        = this != base && isSubclassOf(base)

