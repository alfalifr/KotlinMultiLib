package sidev.lib.reflex.common.full

import sidev.lib.reflex.common.*

//val SiReflex.isAbstract get()= SiModifier.isAbstract(this)
val SiReflex.isOpen get()= SiModifier.isOpen(this)
val SiReflex.isOverride get()= SiModifier.isOverride(this)
val SiType.isDynamic get()= SiModifier.isDynamic(this)
val SiCallable<*>.isDynamic get()= returnType.isDynamic