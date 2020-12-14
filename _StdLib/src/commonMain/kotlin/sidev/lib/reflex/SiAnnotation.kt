package sidev.lib.reflex

/**
 * Interface penanda bahwa kelas turunan merupakan anotasi.
 * Interface ini berguna sbg wadah alternatif untuk anotasi pada platform
 * yg belum mendukung anotasi scr internal, sprti Js.
 */
interface SiAnnotation: Annotation

//fun <T> SiAnnotation.get(key: String): T = data[key] as T