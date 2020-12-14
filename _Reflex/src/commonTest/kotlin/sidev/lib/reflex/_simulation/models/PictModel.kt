package sidev.lib.reflex._simulation.models


/**
 * Data class yang digunakan di dalam proyek ini untuk merepresentasikan gambar.
 * Gambar dalam app ini memiliki bitmap, direktori, dan file.
 */
data class PictModel(var bm: Any?= null, var dir: String?= null, var file: Any?= null, var resId: Int? = null, var uri: Any? = null)//: Serializable