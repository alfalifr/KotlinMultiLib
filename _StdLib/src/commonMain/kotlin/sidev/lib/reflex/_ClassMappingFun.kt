package sidev.lib.reflex

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.exception.IllegalStateExc
import sidev.lib.reflex.core.ReflexLoader
import sidev.lib.reflex.core.createDescriptor
import sidev.lib.reflex.core.createNativeWrapper
import sidev.lib.reflex.native_.SiKClassifier
import sidev.lib.reflex.native_.SiKClassifierImpl
import sidev.lib.reflex.native_.getKClass
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.reflect.*

/*
internal val Any.nativeClass: SiNativeClassifier
    get()= try{ NativeReflexFactory.createClassifier(this) }
        catch (e: ReflexComponentExc){ NativeReflexFactory.createClassifier(this::class) }
 */

@Suppress(SuppressLiteral.UNCHECKED_CAST)
val <T: Any> KClass<T>.si: SiClass<T>
    @JvmName("getSiClass")
    @JsName("getSiClass")
    get()= ReflexLoader.loadClass(this) as SiClass<T>


internal val KClassifier.si: SiKClassifier
    @JvmName("getSiClassifier")
    @JsName("getSiClassifier")
    get()= object : SiKClassifierImpl(this){
        override val implementation: KClassifier = super.implementation
        override val descriptor: SiDescriptor = createDescriptor(nativeCounterpart = createNativeWrapper(this@si))
    }

@Suppress(SuppressLiteral.UNCHECKED_CAST)
@get:JvmName("getKotlinClass")
@get:JsName("getKotlinClass")
val <T: Any> SiClass<T>.kotlin: KClass<T> get()= when(val cls = descriptor.native){
    is KClass<*> -> cls as KClass<T>
    else -> if(cls != null) getKClass(cls)
    else throw IllegalStateExc(
        stateOwner = this::class,
        currentState = "descriptor.native == null", expectedState = "descriptor.native != null",
        detMsg = """Kelas: "$this" tidak memiliki native class."""
    )
}


/*
/**
 * Berisi fungsi pemetaan dari Kotlin.reflect ke sidev.lib.reflex.common.native
 */
val KClassifier.si: SiKClassifier
    get()= object : SiKClassifierImpl(this),
        SiNativeClassifier by NativeReflexFactory.createClassifier(this) {}

//expect val <T> KCallable<T>.si: SiNativeCallable<T>
 */