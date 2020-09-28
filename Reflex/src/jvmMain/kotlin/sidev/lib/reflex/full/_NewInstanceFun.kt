@file:JvmName("_NewInstanceFunJvm")

package sidev.lib.reflex.full

import sidev.lib._config_.SidevLibConfig
import sidev.lib.annotation.ChangeLog
import sidev.lib.annotation.Modified
import sidev.lib.check.notNull
import sidev.lib.check.notNullTo
import sidev.lib.collection.takeLast
import sidev.lib.console.prine
import sidev.lib.exception.NonInstantiableTypeExc
import sidev.lib.reflex.clazz
import sidev.lib.reflex.defaultPrimitiveValue
import sidev.lib.reflex.jvm.JvmReflexConst
import sidev.lib.reflex.jvm.forceSet
import sidev.lib.reflex.jvm.javaFieldValuesTree
import sidev.lib.reflex.jvm.leastParamConstructor
import sidev.lib.reflex.native_.*
import java.lang.reflect.Field
import java.lang.reflect.Parameter
import kotlin.reflect.KClass
import kotlin.reflect.KParameter


/*
==========================
New Instance - Native
==========================
 */

@ChangeLog("Senin, 28 Sep 2020", "Penambahan cek komptabilitas untuk Java 7")
actual fun <T: Any> T.nativeCloneOp(
    clonedObjOriginStack: MutableList<Any>, clonedObjStack: MutableList<Any>,
    isDeepClone: Boolean, constructorParamValFunc: ((KClass<*>, SiNativeParameter) -> Any?)?
): T{
    clonedObjOriginStack.indexOf(this).also { ind ->
        if(ind >= 0)
            return clonedObjStack[ind] as T
    }

    /**
     * Agar jml stack untuk obj asli [clonedObjOriginStack] dg hasil clone [clonedObjStack] sama.
     */
    fun T.preReturnObj(): T{
//        clonedObjStack!!.add(this)
        clonedObjOriginStack.takeLast()
        return this
    }
    clonedObjOriginStack.add(this)


//    if(isReflexUnit || isUninitializedValue) return this
    val clazz= try{ this::class.also { if(it.isCopySafe) return this.preReturnObj() } as KClass<T> }
    catch (e: UnsupportedOperationException){
        prine("""This: "$this" merupakan komponen refleksi yg tidak dapat di-clone, return `this`.""")
        return this.preReturnObj()
    }
    val valueMapTree= javaFieldValuesTree

    val constructorPropertyList= mutableListOf<Field>()
    val newInsConstrParamValFunc= constructorParamValFunc ?: { clazz, param ->
        valueMapTree.find { (field, value) ->
//            prine("nativeClone() clazz= $clazz field= $field value= $value param.type.java.isAssignableFrom(value::class.java) => ${value?.clazz?.java.notNullTo { param.type.java.isAssignableFrom(it) }}")
//            prine("nativeClone() clazz= $clazz param.name= ${param.name} field.name= ${field.name}")
            (param.name == field.name || (SidevLibConfig.java7SupportEnabled.not() /*Added*/ && JvmReflexConst.isParamDefault(param.implementation as Parameter)))
                    && ((value == null && param.type.java == Object::class.java)
                            || (value != null && param.type.java.isAssignableFrom(value::class.java))
                    ) }
            .notNullTo {
                constructorPropertyList.add(it.first) //Agar loop for di bawah gak usah menyalin lagi property yg udah di-clone di konstruktor.
                if(!isDeepClone) it.second
                else it.second?.nativeCloneOp(clonedObjOriginStack, clonedObjStack, true, constructorParamValFunc)
            }
    }
    val newInsConstrParamValFuncWrapper= { paramOfNew: SiNativeParameter ->
        newInsConstrParamValFunc.invoke(clazz, paramOfNew)
    }

    val newInstance= when{
        clazz.isArray -> return nativeArrayClone(isDeepClone, newInsConstrParamValFunc).preReturnObj() //as T
        clazz.isCollection -> return ((this as Collection<T>).nativeDeepClone(isDeepClone, newInsConstrParamValFunc) as T).preReturnObj()
        else -> nativeNew(clazz, newInsConstrParamValFuncWrapper)
            ?: if(isDelegate) {
                prine("""This: "$this" merupakan delegate dan tidak tersedia nilai default untuk konstruktornya, return `this`.""")
                return this.preReturnObj() //Jika `this` merupakan built-in delegate yg gk bisa di-init, maka return this.
            } else throw NonInstantiableTypeExc(typeClass = this::class,
                msg = "Tidak tersedia nilai default untuk di-pass ke konstruktor.")
    }

    clonedObjStack.add(newInstance)

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
            field.forceSet(newInstance, value.nativeCloneOp(clonedObjOriginStack, clonedObjStack, true, constructorParamValFunc))
        }
    }

    clonedObjOriginStack.takeLast()
    clonedObjStack.takeLast()

    return newInstance
}

fun <T: Any> nativeNewK(clazz: KClass<T>, defParamValFunc: ((param: KParameter) -> Any?)?= null): T? = nativeNew(
    clazz,
    if(defParamValFunc != null) { it: SiNativeParameter ->
        defParamValFunc(it.implementation as KParameter)
    } else null
)
@ChangeLog("Senin, 28 Sep 2020", "Penambahan cek komptabilitas untuk Java 7")
actual fun <T: Any> nativeNew(clazz: KClass<T>, defParamValFunc: ((param: SiNativeParameter) -> Any?)?): T?{
    val javaClass= clazz.java
    val constr =  try{ javaClass.leastParamConstructor }
    catch (e: Exception){
        prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak punya konstruktor publik, return `null`.""")
        return null
    }
    val args= arrayListOf<Any?>()

    if(SidevLibConfig.java7SupportEnabled){
        for(param in CompatibilityUtil.Java7.getParameters(constr)){
            args.add(
                defParamValFunc?.invoke(param) ?: param.type.notNullTo { defaultPrimitiveValue(it) }
            )
        }
    } else {
        for(param in constr.parameters){
            args.add(
                defParamValFunc?.invoke(NativeReflexFactory._createNativeParameter(param))
                    ?: param.type.notNullTo { defaultPrimitiveValue(it.kotlin) }
            )
        }
    }

    return try{
        constr.newInstance(*args.toTypedArray())
    } catch (e: Exception){
        prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak tersedianya argumen atau karena merupakan kelas yg tidak dapat di-init, return `null`.""")
        null
    }
}
