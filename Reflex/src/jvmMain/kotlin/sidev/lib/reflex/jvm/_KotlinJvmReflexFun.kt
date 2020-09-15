package sidev.lib.reflex.jvm

import sidev.lib.check.asNotNullTo
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.collection.sequence.nestedSequence
import java.lang.reflect.Field
import kotlin.reflect.*
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.isAccessible


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