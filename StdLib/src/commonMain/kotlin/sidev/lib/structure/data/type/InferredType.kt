package sidev.lib.structure.data.type

//import sidev.lib.universal._cob.isSametypeAs
import sidev.lib.reflex.type.isSameTypeAs
import kotlin.reflect.KType

/**
 * Kelas pembungkus [KType] yg didapat dari [Any.inferType] sehingga [KType.isMarkedNullable]
 * selalu `false`, namun diabaikan dalam perhitungan equality.
 */
data class InferredType(val type: KType): KType by type{
    override fun equals(other: Any?): Boolean {
        return when(other){
            is KType -> type.isSameTypeAs(other, false)
            else -> super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}