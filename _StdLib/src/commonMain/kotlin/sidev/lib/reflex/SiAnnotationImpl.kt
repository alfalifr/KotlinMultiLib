package sidev.lib.reflex

import sidev.lib.reflex.core.ReflexDescriptor
import sidev.lib.reflex.core.createDescriptor

/**
 * Interface penanda bahwa kelas turunan merupakan anotasi.
 * Interface ini berguna sbg wadah alternatif untuk anotasi pada platform
 * yg belum mendukung anotasi scr internal, sprti Js.
 */
internal abstract class SiAnnotationImpl: SiDescriptorContainerImpl(), SiAnnotation

//fun <T> SiAnnotation.get(key: String): T = data[key] as T