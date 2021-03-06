package sidev.lib.reflex.full

import sidev.lib.check.notNullTo
import sidev.lib.collection.iterator.NestedIteratorSimple
import sidev.lib.collection.iterator.NestedIteratorSimpleImpl
import sidev.lib.collection.iterator.skip
import sidev.lib.property.SI_UNINITIALIZED_VALUE
import sidev.lib.reflex.*
//import sidev.lib.reflex.SiReflexImpl
import sidev.lib.reflex.si
import sidev.lib.collection.sequence.NestedSequence
import kotlin.jvm.JvmName
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass


expect val SiClass<*>.isPrimitive: Boolean
expect val SiClass<*>.isObjectArray: Boolean
expect val SiClass<*>.isPrimitiveArray: Boolean
internal expect val SiClass<*>.isNativeInterface: Boolean

expect val <T: Any> SiClass<T>.primaryConstructor: SiFunction<T>
expect val SiClass<*>.sealedSubclasses: Sequence<SiClass<*>>
expect val SiClass<*>.isAnonymous: Boolean

/*
val <T: Any> SiClass<T>.kotlin: KClass<T>
    get()= descriptor.native as KClass<T>
 */

@get:JvmName("isSealed")
val SiClass<*>.isSealed: Boolean
    get()= SiModifier.isSealed(this)


@get:JvmName("isPrimitive")
val SiType.isPrimitive: Boolean
    get()= (classifier as? SiClass<*>)?.isPrimitive == true


@get:JvmName("isArray")
val SiClass<*>.isArray: Boolean
    get()= isObjectArray || isPrimitiveArray

@get:JvmName("isCollection")
val SiClass<*>.isCollection: Boolean
    get()= isSubclassOf(Collection::class.si)

@get:JvmName("isMap")
val SiClass<*>.isMap: Boolean
    get()= isSubclassOf(Map::class.si)


//<23 Agustus 2020> => implementasi diganti menjadi [isNativeInterface], karena jika isAbstract && !isInstantiable,
// belum tentu merupakan interface. Bisa jadi itu adalah abstract kelas yg hanya bisa di-instansiasi lewat builder di dalamnya.
@get:JvmName("isInterface")
val SiClass<*>.isInterface: Boolean
    get()= isNativeInterface //isAbstract && !isInstantiable

@get:JvmName("isObject")
val SiClass<*>.isObject: Boolean
    get()= !isAbstract && !isInstantiable

@get:JvmName("isInstantiable")
val SiClass<*>.isInstantiable: Boolean
    get()= constructors.isNotEmpty()

@get:JvmName("isInterface")
val SiType.isInterface: Boolean
    get()= (this.classifier as? SiClass<*>)?.isInterface ?: false


/**
 * Menunjukan jika kelas `this.extension` ini merupakan anonymous karena di-extend
 * oleh variabel lokal dan kelas yg di-extend bkn merupakan kelas abstract.
 */
@get:JvmName("isShallowAnonymous")
val SiClass<*>.isShallowAnonymous: Boolean
    get()= isAnonymous && supertypes.size == 1
            && superclass?.isAbstract == false
            && isAllMembersImplemented

/**
 * Menunjukan apakah `this.extension` [KClass] abstrak scr sederhana, yaitu
 * tidak memiliki fungsi atau properti yg abstrak dan supertype hanya satu.
 * Berguna untuk operasi [new] pada kelas abstrak sehingga dapat mengembalikan
 * instance dg superclass.
 */
@get:JvmName("isShallowAbstract")
val SiClass<*>.isShallowAbstract: Boolean
    get()= isAbstract && supertypes.size == 1
            && superclass?.isAbstract == false
            && isAllMembersImplemented

@get:JvmName("isAllMembersImplemented")
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
@get:JvmName("isCopySafe")
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


/** @return `true` jika `this.extension` merupakan subclass atau sama dg [base]. */
fun SiClass<*>.isSubclassOf(base: SiClass<*>): Boolean = base in classesTree
fun SiClass<*>.isSuperclassOf(derived: SiClass<*>): Boolean = derived.isSubclassOf(this)

fun SiClass<*>.isExclusivelySuperclassOf(derived: SiClass<*>): Boolean
        = this != derived && isSuperclassOf(derived)
fun SiClass<*>.isExclusivelySubclassOf(base: SiClass<*>): Boolean
        = this != base && isSubclassOf(base)

fun SiClass<*>.isAssignableFrom(other: SiClass<*>): Boolean = other.isSubclassOf(this)

@get:JvmName("sealedSubclassesTree")
val SiClass<*>.sealedSubclassesTree: NestedSequence<SiClass<*>>
    get()= object : NestedSequence<SiClass<*>> {
        override fun iterator(): NestedIteratorSimple<SiClass<*>>
                = object: NestedIteratorSimpleImpl<SiClass<*>>(this@sealedSubclassesTree){
            override fun getOutputIterator(nowInput: SiClass<*>): Iterator<SiClass<*>>?
                    = nowInput.sealedSubclasses.iterator()
        }
    }



/**
 * Digunakan untuk mengambil nama qualified dari turunan sealed class.
 * Qualified name yg diambil dg pola berikut <Super>...<Class> dg super merupakan superclass.
 * Bagian paling depan dari qualified name adalah nama class dg kata kunci sealed yg paling dekat
 * dg this [KClass] tempat fungsi ini dipanggil.
 *
 * Fungsi ini msh bergantung pada Java Reflection.
 *
 * [isQualifiedName] -true jika nama yg diambil adalah nama lengkap dimulai dari sealed super class
 *                    hingga kelas ini yg dipidahkan oleh titik (.).
 *                   -false jika nama yg diambil hanyalah nama kelas ini.
 * @return -null jika kelas ini gak punya [KClass.simpleName]
 *         -[KClass.simpleName] jika ternyata kelas ini gak punya sealed super class.
 */
fun SiClass<*>.getSealedClassName(isQualifiedName: Boolean= true): String?{
//    Log.e("getSealedClassName", ".getSealedClassName() MAULAI")
    return this.simpleName.notNullTo { thisName ->
        var thisNameRes= thisName
        if(isQualifiedName){
//            Log.e("getSealedClassName", ".getSealedClassName() QUALIFIED MAULAI")
            var superName= ""
            var isSealedSuperFound= false
            for(clazz in classesTree.skip { it.isInterface }){ //this.supertypes(false)
//                val clazz= (supertype.classifier as? KClass<*>)
//                if(clazz == Any::class) continue
                superName= clazz.simpleName!! +"." +superName
                if(clazz.isSealed){
                    isSealedSuperFound= true
                    break
                }
            }
/*
            this.iterateSuperClass_k { clazz ->
                try{
                    superName= clazz.simpleName!! +"." +superName
                    if(clazz.isSealed){
                        isSealedSuperFound= true
                        return@iterateSuperClass_k
                    }
                }
                catch (e: KotlinNullPointerException){ return@iterateSuperClass_k }
            }
 */
//            Log.e("getSealedClassName", ".getSealedClassName() QUALIFIED SELESAI")
            if(isSealedSuperFound)
                thisNameRes= superName +thisNameRes
        }
//        println("getSealedClassName(): thisNameRes= $thisNameRes")
//        Log.e("getSealedClassName", "thisNameRes= $thisNameRes")
        thisNameRes
    }
}

fun SiClass<*>.annotateMember(name: String, annotation: Annotation): Boolean{
    return members.find { it.name == name }?.let {
        it.setAnnotation(annotation)
        true
    } ?: false
}