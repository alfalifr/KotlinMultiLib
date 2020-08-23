package sidev.lib.reflex.common.full.types

//import sidev.lib.universal._cob.isSametypeAs
import sidev.lib.reflex.common.SiType
import kotlin.reflect.KType

/**
 * Kelas pembungkus [KType] yg didapat dari [Any.inferType] sehingga [KType.isMarkedNullable]
 * selalu `false`, namun diabaikan dalam perhitungan equality.
 */
data class InferredType(val type: SiType): SiType by type{
    override fun equals(other: Any?): Boolean {
        return when(other){
            is SiType -> type.isSameTypeAs(other, false)
            else -> super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

