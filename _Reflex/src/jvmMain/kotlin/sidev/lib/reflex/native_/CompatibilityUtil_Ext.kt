package sidev.lib.reflex.native_

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.check.notNullTo
import sidev.lib.console.prine
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.defaultPrimitiveValue
import sidev.lib.reflex.native_.CompatibilityUtil.Java7.getParameters
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.TypeVariable
import kotlin.reflect.KClass

/*
//@ExperimentalStdlibApi
object CompatibilityUtil_Ext {
    object Java7{
        fun <T: Any> nativeNew(clazz: KClass<T>, defParamValFunc: ((param: SiNativeParameter) -> Any?)?): T?{
            val javaClass= clazz.java
            val constr =  try{ getLeastParamConstructor(javaClass) }
            catch (e: Exception){
                prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak punya konstruktor publik, return `null`.""")
                return null
            }
            val args= arrayListOf<Any?>()

            for(param in getParameters(constr)){
                args.add(
                    defParamValFunc?.invoke(NativeReflexFactory._createNativeParameter(param))
                        ?: param.type.notNullTo { defaultPrimitiveValue(it) }
                )
            }

            return try{
                constr.newInstance(*args.toTypedArray())
            } catch (e: Exception){
                prine("""nativeNew(): Tidak dapat meng-instansiasi kelas: "$clazz" karena tidak tersedianya argumen atau karena merupakan kelas yg tidak dapat di-init, return `null`.""")
                null
            }
        }

        fun <T> getLeastParamConstructor(cls: Class<T>): Constructor<T>{
            val constrs= cls.constructors.also {
                if(it.isEmpty()) throw  NoSuchMemberExc(
                    targetOwner = cls.kotlin, expectedMember = "leastParamConstructor",
                    msg = """Kelas: "$this" gak punya konstruktor public."""
                )
            }
            var constrRes= constrs.first()
            var paramCount= constrRes.parameterCount
            for(i in 1 until constrs.size){
                val constr= constrs[i]
                if(constr.parameterCount < paramCount){
                    constrRes= constr
                    paramCount= constr.parameterCount
                    if(paramCount == 0) break //Karena gakda yg lebih sedikit dari 0
                }
            }
            @Suppress(SuppressLiteral.UNCHECKED_CAST)
            return constrRes as Constructor<T>
        }
    }
}
 */