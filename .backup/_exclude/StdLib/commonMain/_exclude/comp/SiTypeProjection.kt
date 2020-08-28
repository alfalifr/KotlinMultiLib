package sidev.lib.reflex.comp


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
): SiReflex by SiReflexImpl() {
    companion object{
        val STAR = SiTypeProjection(null, null)
    }
}