package sidev.lib.reflex.common.full

import sidev.lib.check.asNotNull
import sidev.lib.check.notNullTo
import sidev.lib.console.prine
import sidev.lib.exception.ClassCastExc
import sidev.lib.exception.NonInstantiableTypeExc
import sidev.lib.reflex.clazz
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.native.isDynamicEnabled
import sidev.lib.reflex.common.native.si
import sidev.lib.reflex.copySimilarProperty
import sidev.lib.reflex.defaultPrimitiveValue
import sidev.lib.universal.`val`.SuppressLiteral
import kotlin.reflect.KParameter


/*
==========================
New Instance
==========================
 */
/**
 * Fungsi yg memiliki fungsi sama dg [clone], namun lebih runtime-safety karena tipe data
 * yg di-clone dg tipe data tujuan berbeda. Hal tersebut berguna saat `this.extension`
 * berupa [isShallowAnonymous] == true.
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T: Any> Any.anyClone(isDeepClone: Boolean= true, constructorParamValFunc: ((SiClass<*>, SiParameter) -> Any?)?= null): T{
    return clone(isDeepClone, constructorParamValFunc) as T
}
/**
 * Digunakan untuk meng-clone object `this.extension` [T] sehingga menciptakan instance baru dg
 * nilai properti yg sama.
 *
 * Fungsi ini dapat meng-clone instance yg merupakan [isShallowAnonymous], namun tidak menjamin
 * attribut overriding maupun attribut tambahan yg ada di dalamnya.
 *
 * @param [isDeepClone] `true` jika seluruh nilai properti yg berupa `object` di-instantiate
 *   menjadi `instance` yg baru. Operasi deep-clone juga berlaku terhadap properti yg dimiliki properti.
 *   [isDeepClone] `false` jika clone hanya dilakukan pada `this.extension` tidak termasuk properti.
 *
 * @param [valueSource] adalah instance yg memiliki property yg nilainya (value) sama dg `this.extension`.
 *
 * @return -> instance dg tipe [T] yg baru,
 *   -> supertype dari `this.extension` [T] jika [T] merupakan [isShallowAnonymous],
 *   -> throw [NonInstantiableTypeExc] jika tipe yg di-clone tidak memiliki kontruktor karena
 *   berupa interface, abstract, atau anonymous class.
 */
//@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T: Any> T.clone(/*valueSource: T?= null, */isDeepClone: Boolean= true, constructorParamValFunc: ((SiClass<*>, SiParameter) -> Any?)?= null): T{
    if(isReflexUnit || isUninitializedValue) return this
    var clazz= this::class.si.also { if(it.isCopySafe) return this }
    var continueCreateNewInstance= true
    val valueMapTree= implementedPropertyValuesTree.filter { it.first.hasBackingField } //Hanya yg punya backingField agar setiap state pada kelas terjamin. //implementedPropertiesValueMapTree
    //clazz.declaredMemberPropertiesTree.filter { !it.isAbstract && it.hasBackingField }
    //Berguna untuk mengecek apakah `KProperty` merupakan properti dg mengecek kesamaannya dg `KParameter` di contrustor.

    val constr= try{
        try{ clazz.primaryConstructor } //Agar property yg brupa val pada konstruktor pada di-copy value-nya dari instance lama.
        catch (e: Exception){ clazz.leastRequiredParamConstructor } //Karena terjadi kesalahan saat mengecek primaryConstr milik Array<T>
    } catch (e: Exception){
        if(clazz.isShallowAnonymous){
            continueCreateNewInstance= false
            clazz= clazz.supertypes.first().classifier as SiClass<T>
            clazz.leastRequiredParamConstructor
        } //else if(clazz.isCopySafe || isReflexUnit || isUninitializedValue) return this //Karena pengecekan udah dilakukan di awal.
        else throw NonInstantiableTypeExc(typeClass = this::class,
            msg = "Tipe data tidak punya konstruktor dan bkn merupakan shallow-anonymous.")
    }

    val newInsConstrParamValFunc= constructorParamValFunc ?: { clazz, param ->
        if(constr.parameters.find { it == param } != null){
            valueMapTree.find { valueMap -> param.isPropertyLike(valueMap.first) }
                .notNullTo {
                    if(!isDeepClone) it.second
                    else it.second?.clone(true, constructorParamValFunc)
                }
        } else null
    }
    val newInsConstrParamValFuncWrapper= { paramOfNew: SiParameter ->
        newInsConstrParamValFunc.invoke(clazz, paramOfNew)
    }

    val newInstance= if(continueCreateNewInstance){
        if(!clazz.isArray)
            new(clazz, constructor = constr, defParamValFunc = newInsConstrParamValFuncWrapper)
                ?: throw NonInstantiableTypeExc(typeClass = this::class,
                    msg = "Tidak tersedia nilai default untuk di-pass ke konstruktor.")
        else
            arrayClone(isDeepClone, newInsConstrParamValFunc) //as T
    } else{
        new(clazz.supertypes.first().classifier as SiClass<out T>,  constructor = constr,
            defParamValFunc = newInsConstrParamValFuncWrapper)
            ?: throw NonInstantiableTypeExc(typeClass = this::class,
                msg = "Tidak tersedia nilai default untuk di-pass ke konstruktor.")
    }


    for(valueMap in valueMapTree){
        prine("clone() valMap= $valueMap")
        val prop= valueMap.first
        val continueCopy= prop is SiMutableProperty1<*, *>
                || (constr.parameters.isNotEmpty() //Jika property ada di primary constr walaupun val, maka copy aja.
                && { constr.parameters.find { it.isPropertyLike(prop) } != null }())
        if(continueCopy){
            @Suppress(SuppressLiteral.UNCHECKED_CAST)
            val value= valueMap.second
            if(value?.isUninitializedValue == true) continue
//            prine("clone() prop= $prop value= $value value.clazz.isPrimitive= ${value?.clazz?.isPrimitive} bool=${!isDeepClone || value == null || value.clazz.isPrimitive}")
            prop.asNotNull { mutableProp: SiMutableProperty1<T, Any?> ->
//                prine(" masukkkk... clone() prop= $prop value= $value value.clazz.isPrimitive= ${value?.clazz?.isPrimitive} bool=${!isDeepClone || value == null || (value.clazz.isPrimitive && constr.parameters.find { it.isPropertyLike(mutableProp, true) } == null)}")
                if(!isDeepClone || value == null || value.clazz.si.isCopySafe || value.isReflexUnit){
                    if(constr.parameters.find { it.isPropertyLike(mutableProp, true) } == null)
                    //Jika ternyata [mutableProp] terletak di konstruktor dan sudah di-instansiasi,
                    // itu artinya programmer sudah memberikan definisi nilainya sendiri saat intansiasi,
                    // maka jangan salin nilai lama [mutableProp] ke objek yg baru di-intansiasi.
                        mutableProp.forceSet(newInstance, value) //value.withType(mutableProp.returnType)
//                        mutableProp.forcedSetTyped(newInstance, value.withType(mutableProp.returnType))
                } else{
//                    prine("prop= $mutableProp mutableProp.returnType= ${mutableProp.returnType.classifier}")
//                    mutableProp.forcedSetTyped<T, Any?>(newInstance, value.clone(true, constructorParamValFunc).withType(mutableProp.returnType))
                    mutableProp.forceSet(newInstance, value.clone(true, constructorParamValFunc))
                }
            }
        }
    }
//    prine("this::class= ${this::class} newInstance::class= ${newInstance::class}")
    if(newInstance::class.si.isExclusivelySuperclassOf(this::class.si))
        prine("Kelas yg di-clone: \"${this::class}\" merupakan shallow-anonymous, newInstance yg di-return adalah superclass: \"${newInstance::class}\".")
    return newInstance
}


/**
 * Meng-clone `this.extension` [Array] beserta elemen di dalamnya. Untuk clone elemen di dalamnya,
 * apakah clone element dilakukan scr deep atau tidak bergantung dari [isElementDeepClone].
 * Array hasil clone berisi element yg di-clone, atau element dg instance yg sama jika element
 * tidak dapat di-instantiate.
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Array<T>.deepClone(isElementDeepClone: Boolean= true, elementConstructorParamValFunc: ((SiClass<*>, SiParameter) -> Any?)?= null): Array<T>{
    val newArray= sliceArray(indices) //this.nativeArrayClone() //.clone()
    for((i, e) in this.withIndex()){
        if(e != null)
            newArray[i]= try{ (e as Any).clone(isElementDeepClone, elementConstructorParamValFunc) }
            catch (e: NonInstantiableTypeExc){ e } as T
    }
    return newArray
}

/**
 * Meng-clone array berupa apapun itu.
 * @return -> array baru hasil deepClone() jika `this.extension` berupa [Array],
 *   -> array hasil clone() biasa jika `this.extension` berupa array primitif,
 *   -> `this.extension` sendiri jika berupa array yg lain.
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T: Any> T.arrayClone(isElementDeepClone: Boolean= true, elementConstructorParamValFunc: ((SiClass<*>, SiParameter) -> Any?)?= null): T{
    if(!this::class.si.isArray) throw ClassCastExc(fromClass = this::class, toClass = Array<Any>::class, msg = "Instance yg di-arrayClone() bkn array")

    val res: Any = if(this::class.si.isObjectArray)
        (this as Array<*>).deepClone(isElementDeepClone, elementConstructorParamValFunc)
    else when(this){
        is IntArray -> sliceArray(indices)
        is LongArray -> sliceArray(indices)
        is FloatArray -> sliceArray(indices)
        is DoubleArray -> sliceArray(indices)
        is CharArray -> sliceArray(indices)
        is ShortArray -> sliceArray(indices)
        is BooleanArray -> sliceArray(indices)
        is ByteArray -> sliceArray(indices)
        else -> this
    }
    return res as T
}

/** Sama dg [IntArray.clone], namun agar serasi dg [Array.deepClone]. */
fun IntArray.deepClone(): IntArray = sliceArray(indices)
fun LongArray.deepClone(): LongArray = sliceArray(indices)
fun FloatArray.deepClone(): FloatArray = sliceArray(indices)
fun DoubleArray.deepClone(): DoubleArray = sliceArray(indices)
fun CharArray.deepClone(): CharArray = sliceArray(indices)
fun ShortArray.deepClone(): ShortArray = sliceArray(indices)
fun BooleanArray.deepClone(): BooleanArray = sliceArray(indices)
fun ByteArray.deepClone(): ByteArray = sliceArray(indices)



/**
 * <14 Juli 2020> => Versi baru fungsi inline yg kecil.
 */
inline fun <reified T: Any> new(constructorParamClass: Array<SiClass<*>>?= null,
                                constructor: SiFunction<T>? = null,
                                noinline defParamValFunc: ((param: SiParameter) -> Any?)?= null): T?
        = new(T::class.si, constructorParamClass, constructor, defParamValFunc)

/**
 * Digunakan untuk meng-instatiate instance baru menggunakan Kotlin Reflection.
 *
 * [defParamValFunc] dipanggil jika nilai default dari [defParamValFunc.param] dg tipe [KParameter]
 * tidak dapat diperoleh.
 *
 * Nilai default suatu [KParameter] tidak diperoleh dari fungsi di dalam
 * framework ini disebabkan karena [KParameter] bkn merupakan tipe primitif atau
 * tidak terdefinisi pada fungsi [defaultPrimitiveValue].
 *
 * Fungsi ini melakukan instansiasi objek baru dg menggunakan konstruktor yg memiliki parameter
 * dg tipe data sesuai dg [constructorParamClass]. Jika [constructorParamClass] == null, maka
 * scr default fungsi akan mencari konstruktor dg jumlah parameter paling sedikit yg tersedia.
 * Parameter opsional tidak dihitung.
 *
 * <14 Juli 2020> => Tidak jadi inline karena fungsi ini besar. Sbg gantinya, fungsi [new] di atas
 *   adalah inline namun dg kode yg kecil.
 */
fun <T: Any> new(clazz: SiClass<out T>, constructorParamClass: Array<SiClass<*>>?= null,
                 constructor: SiFunction<T>? = null,
                 defParamValFunc: ((param: SiParameter) -> Any?)?= null): T?{

//    prine("new() clazz= $clazz new")

    if(clazz.isCopySafe)
        return defaultPrimitiveValue(clazz.kotlin)

//    prine("new() clazz= $clazz after")

    //1. Cari constructor dg parameter tersedikit.
/*
    val constrs= clazz.constructors //T::class.constructors
    var constr: KFunction<T>? = null //constrs.first()
    var minParams= Int.MAX_VALUE
    for(cons in constrs){
        if(minParams > cons.parameters.size){
            constr= cons
            minParams= cons.parameters.size
        }
    }
 */
    val usedClazz=
        if(!clazz.isShallowAbstract) clazz
        else try{
            val superclazz= clazz.supertypes.first().classifier as SiClass<out T>
            prine("""Kelas: "$clazz" merupakan shallow-abstract, newInstance yg di-intanstiate adalah superclass: "$superclazz".""")
            superclazz
        } catch (e: ClassCastException){
            prine("""Kelas: "$clazz" abstrak sehingga tidak dapat di-intanstiate.""")
            return null
        }

    val constr = constructor ?:
    try {
        if (constructorParamClass.isNullOrEmpty()) usedClazz.leastRequiredParamConstructor
        else usedClazz.findConstructorWithParam(*constructorParamClass)
    } catch (e: Exception){
        return if(!usedClazz.isShallowAnonymous) { //Kelas udah gak bisa di-instantiate.
            prine("""Kelas: "$clazz" tidak punya konstruktor sehingga tidak dapat di-intanstiate.""")
            null
        } else {
            val superclazz= clazz.supertypes.first().classifier as SiClass<out T>
            prine("""Kelas: "$clazz" merupakan shallow-anonymous, newInstance yg di-intanstiate adalah superclass: "$superclazz".""")
            new(superclazz, constructorParamClass, constructor, defParamValFunc)
        }
    }

    val params=  constr.parameters
    val defParamVal= HashMap<SiParameter, Any?>()

//    prine("new() clazz= $clazz usedClazz= $usedClazz constr= $constr param.size= ${params.size}")

    for(param in params){
        val type= param.type
//        prine("new() param= $param type= $type type.classifier= ${type.classifier} native= ${type.classifier?.descriptor?.native}")
//        val typeClass= type.classifier as SiClass<*>
        val paramVal=
            try{
                prine("new() defParamValFunc!!(param) mulai")
                val vals= defParamValFunc!!(param)
                prine("new() defParamValFunc!!(param) val= $vals")
                vals
            } //Yg diprioritaskan pertama adalah definisi value dari programmer.
            catch (e: Exception) { //Jika programmer tidak mendefinisikan, coba cari nilai default.
                if(param.isOptional) continue //Tapi sebelum ke nilai default, cek apakah param opsional.
                //Jika opsional, maka artinya programmer udah memberikan definisi sendiri untuk param itu.
                // Maka gak perlu ditambahkan ke konstruktor.
                try{
                    if(!isDynamicEnabled) defaultPrimitiveValue((type.classifier as SiClass<*>).kotlin)!!
                    else null
                } //Jika ternyata gak opsional, maka coba cari nilai default.
                catch (e: Exception){ null }
            }
        if(paramVal != null){
            defParamVal[param]= paramVal
        } else{
            if(type.isMarkedNullable)
                defParamVal[param]= null
            else if(!param.isOptional) //Harusnya bisa langsung else, tapi biar readable aja.
                return null //Karena class udah gak bisa di-instantiate.
        }
    }
/*
    if(defParamValFunc == null){
        /* implementasi lama di atas */
    }else{
        for(param in params){
            val paramVal= defParamValFunc(param)
            if(paramVal != null){
                defParamVal.add(paramVal)
            } else{
                if(param.type.isMarkedNullable)
                    defParamVal.add(null)
                else
                    return throw Exception("parameter: ${param.name} tipe: ${(param.type.classifier as KClass<*>).simpleName} gak boleh null.")
            }
        }
    }
 */
//    val resNew= constr.forceCallBy(defParamVal) //.forcedCall()//.callBy(defParamVal)
//    prine("new() resNew= $resNew")
    return constr.forceCallBy(defParamVal)
}

