package sidev.lib.exception

import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass

open class NotYetSupportedExc (
    relatedClass: KClass<*>?= NotYetSupportedExc::class,
    accessedElement: Any = "<accessed-element>",
    versionToSupport: Any = "<later>",
    detailMsg: String= ""
) : Exc(relatedClass, """Sebuah operasi belum didukung, accessedElement= "$accessedElement" akan didukung pada versi= "$versionToSupport".""", detailMsg) {
    companion object {
        @JvmStatic
        val stillOnResearch: Nothing
            get()= throw NotYetSupportedExc(detailMsg = "Operasi masih dalam tahap penelitian.")
    }
}