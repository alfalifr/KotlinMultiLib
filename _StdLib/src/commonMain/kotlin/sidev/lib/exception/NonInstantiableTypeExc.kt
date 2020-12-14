package sidev.lib.exception

import sidev.lib.reflex.fullName
import kotlin.reflect.KClass

open class NonInstantiableTypeExc(
    relatedClass: KClass<*>?= NonInstantiableTypeExc::class,
    typeClass: KClass<*>?= null,
    msg: String= "")
    : Exc(
    relatedClass,
    "Tipe data \"${typeClass?.fullName}\" tidak dapat di-instantiate karena merupakan interface, abstract, anonymous class, atau null.",
    msg
)