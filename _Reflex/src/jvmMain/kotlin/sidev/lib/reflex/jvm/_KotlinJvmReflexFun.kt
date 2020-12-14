package sidev.lib.reflex.jvm

import sidev.lib._config_.SidevLibConfig
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.ChangeLog
import sidev.lib.check.asNotNullTo
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.collection.sequence.nestedSequence
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.reflex.native_.CompatibilityUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import kotlin.reflect.*
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.isAccessible



@ChangeLog("Jumat, 2 Okt 2020", "leastParamConstructor juga ada di KClass<T> untuk kepentingan refleksi yg melibatkan paramName.")
@get:JvmName("leastParamConstructor")
val <T: Any> KClass<T>.leastParamConstructor: KFunction<T>
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

fun <R, T> KProperty1<R, T>.forceGet(receiver: R): T{
    val initAccessible= isAccessible
    isAccessible= true
    val vals= get(receiver) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
    return vals
}
fun <R, T> KMutableProperty1<R, T>.forceSet(receiver: R, value: T){
    val initAccessible= isAccessible
    isAccessible= true
    set(receiver, value) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
}

fun <R> KCallable<R>.forceCall(vararg args: Any?): R{
    val initAccessible= isAccessible
    isAccessible= true
    val vals= call(*args) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
    return vals
}

fun <R> KCallable<R>.forceCallBy(args: Map<KParameter, Any?>): R{
    val initAccessible= isAccessible
    isAccessible= true
    val vals= callBy(args)
    isAccessible= initAccessible
    return vals
}