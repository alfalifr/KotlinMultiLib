package sidev.lib.exception

import kotlin.reflect.KClass


open class NoSuchMemberExc(
    relatedClass: KClass<*>?= NoSuchMemberExc::class,
    targetOwner: KClass<*>?= null,
    expectedMember: Any?= "<member>",
    msg: String= ""
) : Exc(
    relatedClass,
    """Kelas: "$targetOwner" tidak punya member: "$expectedMember". """,
    msg
)