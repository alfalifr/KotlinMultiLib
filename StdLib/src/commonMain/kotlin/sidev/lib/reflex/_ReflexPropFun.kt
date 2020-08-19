package sidev.lib.reflex

import sidev.lib.reflex._ReflexConst.K_ARRAY_CLASS_STRING
//import sidev.lib.reflex.inner.isSubclassOf
import kotlin.reflect.*

val KClass<*>.fullName: String
    get()= nativeFullName //qualifiedName ?: nativeFullName
val KClass<*>.simpleName: String
    get()= this.simpleName ?: nativeSimpleName

/** Berguna untuk mengambil kelas dari instance dg tipe generic yg tidak punya batas atas Any. */
val Any.clazz: KClass<*>
    get()= this::class
/*
val <T: Any> KClass<T>.isInterface: Boolean
    get()= isAbstract && !isInstantiable

val <T: Any> KClass<T>.isInstantiable: Boolean
    get()= constructors.isNotEmpty()

val KType.isInterface: Boolean
    get()= (this.classifier as? KClass<*>)?.isInterface ?: false

expect val <T: Any> KClass<T>.isPrimitive: Boolean

/**
 * Mengindikasikan bahwa data dg tipe `this.extension` [KClass] ini aman untuk di-copy scr langsung
 * tanpa harus di-instantiate agar tidak menyebabkan masalah yg berkaitan dg referential.
 *
 * Contoh tipe data yg aman untuk di-copy scr langsung tanpa harus di-instantiate adalah
 * tipe data primitif dan String. Jika ada tipe data lain yg immutable, tipe data tersebut juga
 * copy-safe.
 */
val <T: Any> KClass<T>.isCopySafe: Boolean
    get()= isPrimitive || this == String::class || isSubclassOf(Enum::class)
/*
//        prine("isCopySafe @$this isPrimitive= $isPrimitive this == String::class= ${this == String::class} this.isSubclassOf(Enum::class)= ${this.isSubclassOf(Enum::class)} isKReflectionElement= $isKReflectionElement")
        val res
//                || isKReflectionElement
        return res
    }
 */

val <T> T.isKReflectionElement: Boolean
    get()= when(this){
        is KParameter -> true
        is KCallable<*> -> true
        is KClass<*> -> true
        is KType -> true
        is KTypeParameter -> true
        is KClassifier -> true
        else -> false
    }

/**
 * Menunjukan jika kelas `this.extension` ini merupakan anonymous karena di-extend
 * oleh variabel lokal dan kelas yg di-extend bkn merupakan kelas abstract.
 */
val <T: Any> KClass<T>.isShallowAnonymous: Boolean
    get()= qualifiedName == null && supertypes.size == 1
            && !(supertypes.first().classifier as KClass<*>).isAbstract
            && isAllMembersImplemented

/**
 * Menunjukan apakah `this.extension` [KClass] abstrak scr sederhana, yaitu
 * tidak memiliki fungsi atau properti yg abstrak dan supertype hanya satu.
 * Berguna untuk operasi [new] pada kelas abstrak sehingga dapat mengembalikan
 * instance dg superclass.
 */
val <T: Any> KClass<T>.isShallowAbstract: Boolean
    get()= isAbstract && supertypes.size == 1
            && !(supertypes.first().classifier as KClass<*>).isAbstract
            && isAllMembersImplemented

val <T: Any> KClass<T>.isAllMembersImplemented: Boolean
    get()= members.find { it.isAbstract } == null

val <T: Any> KClass<T>.isArray: Boolean
    get()= isObjectArray || isPrimitiveArray

val <T: Any> KClass<T>.isObjectArray: Boolean
    get()= toString() == K_ARRAY_CLASS_STRING

val <T: Any> KClass<T>.isPrimitiveArray: Boolean
    get()= when(this){
        IntArray::class -> true
        LongArray::class -> true
        FloatArray::class -> true
        DoubleArray::class -> true
        CharArray::class -> true
        ShortArray::class -> true
        BooleanArray::class -> true
        ByteArray::class -> true
        else -> false
    }

 */