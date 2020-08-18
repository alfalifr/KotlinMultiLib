package sidev.lib.reflex.common


//Agar dapat dijalankan di Js.
data class SiTypeProjection(
    /**
     * The use-site variance specified in the projection, or `null` if this is a star projection.
     */
    val variance: SiVariance?,
    /**
     * The type specified in the projection, or `null` if this is a star projection.
     */
    val type: SiType?
){
    companion object{
        val STAR = SiTypeProjection(null, null)
    }
}