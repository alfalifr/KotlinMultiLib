package sidev.lib.exception

import sidev.lib.reflex.fullName
import kotlin.reflect.KClass

open class TypeExc(
    relatedClass: KClass<*>?= TypeExc::class,
    expectedType: KClass<*>?= null,
    actualType: KClass<*>?= null,
    msg: String= ""
) : Exc(
    relatedClass,
    "Tipe data tidak sesuai, seharusnya: \"${expectedType?.fullName}\" tapi yg ada: \"${actualType?.fullName}\".",
    msg
)