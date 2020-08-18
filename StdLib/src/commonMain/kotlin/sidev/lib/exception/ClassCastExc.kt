package sidev.lib.exception

import sidev.lib.reflex.fullName
import kotlin.reflect.KClass

open class ClassCastExc(
    relatedClass: KClass<*>?= ClassCastExc::class,
    fromClass: KClass<*>?= null,
    toClass: KClass<*>?= null,
    msg: String= "")
    : Exc(
        relatedClass,
        "Tipe data \"${fromClass?.fullName}\" tidak dapat di-cast jadi \"${toClass?.fullName}\"",
        msg
    )