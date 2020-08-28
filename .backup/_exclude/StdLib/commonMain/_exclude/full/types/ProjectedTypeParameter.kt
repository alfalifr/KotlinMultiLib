package sidev.lib.reflex.full.types

import sidev.lib.reflex.comp.SiTypeParameter
import sidev.lib.reflex.comp.SiTypeProjection
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KTypeProjection


/**
 * Digunakan untuk menyimpan data hubungan [typeParam] dg [upperBoundTypeParam] dalam satu [KClass].
 * [upperBoundTypeParam.size] == 0 jika [typeParam.upperBounds] tidak memiliki [KTypeParameter]
 * yg sama dg yg terdefinisi pada kelas [KClass] yg sama.
 */
data class LinkedTypeParameter(val typeParam: SiTypeParameter, val upperBoundTypeParam: List<SiTypeParameter>)

/** Struktur data yg merepresentasikan pasangan antara [KTypeParameter] dg [KTypeProjection]-nya. */
interface ProjectedTypeParameter{
    val typeParam: SiTypeParameter
    val projection: SiTypeProjection
}
/** Struktur data yg mirip dg [LinkedTypeParameter], namun disertaik data ttg [KTypeProjection]. */
data class LinkedProjectedTypeParameter(val typeParam: ProjectedTypeParameter, val upperBoundTypeParam: List<ProjectedTypeParameter>)


/** Implementasi internal dari [ProjectedTypeParameter] dg properti [projection] yg mutable. */
internal data class ProjectedTypeParameterImpl(override val typeParam: SiTypeParameter): ProjectedTypeParameter {
    override var projection: SiTypeProjection = SiTypeProjection.STAR
}

internal fun SiTypeParameter.asProjectedTypeParameter(): ProjectedTypeParameterImpl = ProjectedTypeParameterImpl(this)