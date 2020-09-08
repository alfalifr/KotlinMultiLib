@file:JvmName("_NewInstanceFunJvm")

package sidev.lib.reflex.full

import sidev.lib.check.notNullTo
import sidev.lib.console.prine
import sidev.lib.exception.NonInstantiableTypeExc
import sidev.lib.reflex.clazz
import sidev.lib.reflex.defaultPrimitiveValue
import sidev.lib.reflex.native.*
import java.lang.reflect.Field
import kotlin.reflect.KClass


/*
==========================
New Instance - Native
==========================
 */

actual fun <T: Any> T.nativeClone(isDeepClone: Boolean, constructorParamValFunc: ((KClass<*>, SiNativeParameter) -> Any?)?): T{
    val clazz= this::class.also { if(it.isCopySafe) return this } as KClass<T>
    val valueMapTree= javaFieldValuesTree

    val constructorPropertyList= mutableListOf<Field>()
    val newInsConstrParamValFunc= constructorParamValFunc ?: { clazz, param ->
        valueMapTree.find { (field, value) ->
//            prine("nativeClone() clazz= $clazz field= $field value= $value")
            param.name == field.name && (
                    (value == null && param.type.java == Object::class.java)
                            || (value != null && param.type.java.isAssignableFrom(value::class.java))
                    ) }
            .notNullTo {
                constructorPropertyList.add(it.first) //Agar loop for di bawah gak usah menyalin lagi property yg udah di-clone di konstruktor.
                if(!isDeepClone) it.second
                else it.second?.nativeClone(true, constructorParamValFunc)
            }
    }
    val newInsConstrParamValFuncWrapper= { paramOfNew: SiNativeParameter ->
        newInsConstrParamValFunc.invoke(clazz, paramOfNew)
    }

    val newInstance= when{
        clazz.isArray -> return nativeArrayClone(isDeepClone, newInsConstrParamValFunc) //as T
        clazz.isCollection -> return (this as Collection<T>).nativeDeepClone(isDeepClone, newInsConstrParamValFunc) as T
        else -> nativeNew(clazz, newInsConstrParamValFuncWrapper)
            ?: if(isDelegate) {
                prine("""This: "$this" merupakan delegate dan tidak tersedia nilai default untuk konstruktornya, return `this`.""")
                return this //Jika `this` merupakan built-in delegate yg gk bisa di-init, maka return this.
            } else throw NonInstantiableTypeExc(typeClass = this::class,
                msg = "Tidak tersedia nilai default untuk di-pass ke konstruktor.")
    }

    for((field, value) in valueMapTree.filter { it.first !in constructorPropertyList }){
        if(value?.isUninitializedValue == true) continue
        if(!isDeepClone || value == null || value.clazz.isCopySafe || value.isReflexUnit){
//                if(constr.parameters.find { it.isPropertyLike((mutableField as SiField<*, *>).property, true) } == null)
            //<29 Agustus 2020> => filter kondisi if di atas tidak diperlukan karena udah dilakukan filter di awal saat di for.
            //Jika ternyata [mutableField] terletak di konstruktor dan sudah di-instansiasi,
            // itu artinya programmer sudah memberikan definisi nilainya sendiri saat intansiasi,
            // maka jangan salin nilai lama [mutableField] ke objek yg baru di-intansiasi.
            //<29 Agustus 2020> => Klarifikasi ttg filter if di atas.
            field.forceSet(newInstance, value) //value.withType(mutableField.returnType)
//                        mutableField.forcedSetTyped(newInstance, value.withType(mutableField.returnType))
        } else{
//                    mutableField.forcedSetTyped<T, Any?>(newInstance, value.clone(true, constructorParamValFunc).withType(mutableField.returnType))
            field.forceSet(newInstance, value.nativeClone(true, constructorParamValFunc))
        }
    }
    return newInstance
}

actual fun <T: Any> nativeNew(clazz: KClass<T>, defParamValFunc: ((param: SiNativeParameter) -> Any?)?): T?{
    val javaClass= clazz.java
    val constr =  try{ javaClass.leastParamConstructor }
    catch (e: Exception){
        prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak punya konstruktor publik, return `null`.""")
        return null
    }
    val args= arrayListOf<Any?>()

    for(param in constr.parameters){
        args.add(
            defParamValFunc?.invoke(NativeReflexFactory._createNativeParameter(param))
                ?: param.type.notNullTo { defaultPrimitiveValue(it.kotlin) }
        )
    }

    return try{
        constr.newInstance(*args.toTypedArray())
    } catch (e: Exception){
        prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak tersedianya argumen atau karena merupakan kelas yg tidak dapat di-init, return `null`.""")
        null
    }
}
