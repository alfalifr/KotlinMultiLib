package sidev.lib.reflex.common.full

import sidev.lib.collection.string
import sidev.lib.console.prine
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.property.UNINITIALIZED_VALUE
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.native.si
import kotlin.reflect.KClass


expect val SiClass<*>.isPrimitive: Boolean
expect val SiClass<*>.isObjectArray: Boolean
expect val SiClass<*>.isPrimitiveArray: Boolean
expect val Any.isNativeReflexUnit: Boolean

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

val SiClass<*>.isInterface: Boolean
    get()= isAbstract && !isInstantiable

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

/*
//        prine("isCopySafe @$this isPrimitive= $isPrimitive this == String::class= ${this == String::class} this.isSubclassOf(Enum::class)= ${this.isSubclassOf(Enum::class)} isKReflectionElement= $isKReflectionElement")
        val res
//                || isKReflectionElement
        return res
    }
 */

fun SiClass<*>.isSubclassOf(base: SiClass<*>): Boolean = base in superclassesTree
fun SiClass<*>.isSuperclassOf(derived: SiClass<*>): Boolean = derived.isSubclassOf(this)

fun SiClass<*>.isExclusivelySuperclassOf(derived: SiClass<*>): Boolean
        = this != derived && isSuperclassOf(derived)
fun SiClass<*>.isExclusivelySubclassOf(base: SiClass<*>): Boolean
        = this != base && isSubclassOf(base)


/*
================================
Constructor
================================
 */

/** Mengambil konstruktor dg jumlah parameter paling sedikit. */
val <T: Any> SiClass<T>.leastParamConstructor: SiFunction<T>
    get(){
        var constr= try{ constructors.first() }
        catch (e: NoSuchElementException){ throw NoSuchElementException("Kelas \"$qualifiedName\" tidak punya konstruktor (interface, abstract, anonymous class, atau null)") }

        var minParamCount= constr.parameters.size
        for(constrItr in constructors){
            if(minParamCount > constrItr.parameters.size){
                constr= constrItr
                minParamCount= constrItr.parameters.size
            }
            if(minParamCount == 0) break //Karena gakda fungsi yg jumlah parameternya krg dari 0
        }
        return constr
    }

/** Mirip dg [leastParamConstructor], namun param opsional tidak disertakan. Nullable tetap disertakan. */
val <T: Any> SiClass<T>.leastRequiredParamConstructor: SiFunction<T>
    get(){
        var constr= try{ constructors.first() }
        catch (e: NoSuchElementException){ throw NoSuchElementException("Kelas \"$this\" tidak punya konstruktor (interface, abstract, anonymous class, atau null)") }
        var minParamCount= constr.parameters.size
        //<20 Juli 2020> => Konstruktor dg jml param tersedikit belum tentu merupakan konstruktor dg jml param wajib paling sedikit.
        for(constrItr in constructors){
//            prine("leastRequiredParamConstructor class= $simpleName constrItr.parameters.size= ${constrItr.parameters.size}")
            if(minParamCount > constrItr.parameters.size){
                constr= constrItr
                minParamCount= constrItr.parameters.size
                for(param in constrItr.parameters)
                    if(param.isOptional) minParamCount--
            }
            if(minParamCount == 0) break //Karena gakda fungsi yg jumlah parameternya krg dari 0
        }
        val paramList= ArrayList<SiParameter>()
        for(param in constr.parameters)
            if(!param.isOptional) paramList.add(param)

        //Bungkus constructor yg ditemukan dengan fungsi yg parameter listnya hanya terlihat yg non-optional.
        return object: SiFunction<T> by constr{
            override val parameters: List<SiParameter> = paramList
            override fun toString(): String = constr.toString()
        }
    }

/** Mengambil konstruktor dg param yg memiliki tipe data sesuai [paramClass]. Jika tidak ketemu, maka throw [NoSuchMethodException]. */
fun <T: Any> SiClass<T>.findConstructorWithParam(vararg paramClass: SiClass<*>): SiFunction<T> {
    if(!isInstantiable) throw NoSuchElementException("Kelas \"$this\" tidak punya konstruktor (interface, abstract, anonymous class, atau null)")

    for(constr in constructors){
        var paramClassMatch= true
        if(constr.parameters.size == paramClass.size){
            for(param in constr.parameters){
                paramClassMatch= paramClassMatch && param.type.classifier == paramClass[param.index]
            }
        } else continue

        if(paramClassMatch)
            return constr
    }
    val kclass= descriptor.native as KClass<T>
    throw NoSuchMemberExc(kclass, kclass, "constructors","Tidak ada konstruktor \"$qualifiedName\" dg parameter \"${paramClass.string}\"")
}