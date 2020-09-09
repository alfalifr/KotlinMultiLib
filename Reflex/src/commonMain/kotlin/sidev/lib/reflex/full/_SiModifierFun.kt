package sidev.lib.reflex.full

import sidev.lib.reflex.SiCallable
import sidev.lib.reflex.SiDescriptorContainer
import sidev.lib.reflex.SiModifier
import sidev.lib.reflex.SiType
import kotlin.jvm.JvmName

//val SiReflex.isAbstract get()= SiModifier.isAbstract(this)
@get:JvmName("isOpen")
val SiDescriptorContainer.isOpen get()= SiModifier.isOpen(this)
@get:JvmName("isOverride")
val SiDescriptorContainer.isOverride get()= SiModifier.isOverride(this)
@get:JvmName("isDynamic")
val SiType.isDynamic get()= SiModifier.isDynamic(this)
@get:JvmName("isDynamic")
val SiCallable<*>.isDynamic get()= returnType.isDynamic