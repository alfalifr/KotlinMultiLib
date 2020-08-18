package sidev.lib.structure.data.type

import kotlin.reflect.KTypeParameter
import kotlin.reflect.KTypeProjection


/**
 * Digunakan untuk menyimpan data hubungan [typeParam] dg [upperBoundTypeParam] dalam satu [KClass].
 * [upperBoundTypeParam.size] == 0 jika [typeParam.upperBounds] tidak memiliki [KTypeParameter]
 * yg sama dg yg terdefinisi pada kelas [KClass] yg sama.
 */
data class LinkedTypeParameter(val typeParam: KTypeParameter, val upperBoundTypeParam: List<KTypeParameter>)

/** Struktur data yg merepresentasikan pasangan antara [KTypeParameter] dg [KTypeProjection]-nya. */
interface ProjectedTypeParameter{
    val typeParam: KTypeParameter
    val projection: KTypeProjection
}
/** Struktur data yg mirip dg [LinkedTypeParameter], namun disertaik data ttg [KTypeProjection]. */
data class LinkedProjectedTypeParameter(val typeParam: ProjectedTypeParameter, val upperBoundTypeParam: List<ProjectedTypeParameter>)


/** Implementasi internal dari [ProjectedTypeParameter] dg properti [projection] yg mutable. */
internal data class ProjectedTypeParameterImpl(override val typeParam: KTypeParameter): ProjectedTypeParameter {
    override var projection: KTypeProjection = KTypeProjection.STAR
}

internal fun KTypeParameter.asProjectedTypeParameter(): ProjectedTypeParameterImpl = ProjectedTypeParameterImpl(this)