@file:JvmName("_ClassMappingFunJvm")
package sidev.lib.reflex

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.exception.IllegalStateExc
import sidev.lib.reflex.core.ReflexLoader
import sidev.lib.reflex.native_.getKClass
import kotlin.jvm.JvmName
import kotlin.reflect.KClass

/*
internal val Any.nativeClass: SiNativeClassifier
    get()= try{ NativeReflexFactory.createClassifier(this) }
        catch (e: ReflexComponentExc){ NativeReflexFactory.createClassifier(this::class) }
 */

@Suppress(SuppressLiteral.UNCHECKED_CAST)
val <T: Any> Class<T>.si: SiClass<T>
    @JvmName("getSiClass")
    get()= ReflexLoader.loadClass(kotlin) as SiClass<T>

@Suppress(SuppressLiteral.UNCHECKED_CAST)
@get:JvmName("getJavaClass")
val <T: Any> SiClass<T>.java: Class<T> get()= when(val cls = descriptor.native){
    is KClass<*> -> cls.java
    is Class<*> -> cls
    else -> if(cls != null) getKClass<T>(cls).java
    else throw IllegalStateExc(
        stateOwner = this::class,
        currentState = "descriptor.native == null", expectedState = "descriptor.native != null",
        detMsg = """Kelas: "$this" tidak memiliki native class."""
    )
} as Class<T>


/*
/**
 * Berisi fungsi pemetaan dari Kotlin.reflect ke sidev.lib.reflex.common.native
 */
val KClassifier.si: SiKClassifier
    get()= object : SiKClassifierImpl(this),
        SiNativeClassifier by NativeReflexFactory.createClassifier(this) {}

//expect val <T> KCallable<T>.si: SiNativeCallable<T>
 */