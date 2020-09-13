package sidev.lib.reflex.full

import sidev.lib.property.SI_UNINITIALIZED_VALUE
import sidev.lib.reflex.SiDescriptor
import sidev.lib.reflex.SiReflex
import kotlin.jvm.JvmName
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty


internal expect val Any.isNativeReflexUnit: Boolean
internal expect val Any.isNativeDelegate: Boolean


@get:JvmName("isReflexUnit")
val Any.isReflexUnit: Boolean
    get()= this is SiReflex || this is SiDescriptor
            || isNativeReflexUnit

@get:JvmName("isUninitializedValue")
val Any.isUninitializedValue: Boolean
    get()= this === SI_UNINITIALIZED_VALUE
            || this::class.simpleName == "UNINITIALIZED_VALUE" //this::class == UNINITIALIZED_VALUE::class

@get:JvmName("isDelegate")
val Any.isDelegate: Boolean get()= when(this){
//    is Lazy<*> -> true //Gak semua Lazy adalah delegate. Hal teresebut dikarenakan Lazy gak punya fungsi getValue() sbg instance member.
    is ReadOnlyProperty<*, *> -> true
    is ReadWriteProperty<*, *> -> true
    else -> isNativeDelegate.let { if(!it) this is Lazy<*> else it } //Ternyata ada built-in delegate yaitu lazy yg gak punya fungsi getValue() / setValue().
}