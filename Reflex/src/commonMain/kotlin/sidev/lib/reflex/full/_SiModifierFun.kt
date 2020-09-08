package sidev.lib.reflex.full

import sidev.lib.reflex.SiCallable
import sidev.lib.reflex.SiDescriptorContainer
import sidev.lib.reflex.SiModifier
import sidev.lib.reflex.SiType

//val SiReflex.isAbstract get()= SiModifier.isAbstract(this)
val SiDescriptorContainer.isOpen get()= SiModifier.isOpen(this)
val SiDescriptorContainer.isOverride get()= SiModifier.isOverride(this)
val SiType.isDynamic get()= SiModifier.isDynamic(this)
val SiCallable<*>.isDynamic get()= returnType.isDynamic