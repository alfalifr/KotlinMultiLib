package sidev.lib.annotation

import sidev.lib.reflex.*
import sidev.lib.reflex.native_.si

/**
 * Digunakan untuk menandai bahwa element yg ditandai dg anotasi ini memiliki nama yg berbeda
 * sesuai kebutuhan penggunaan anotasi ini.
 */
//@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Rename(val newName: String)

data class SiRename(val newName: String): SiAnnotation


val SiAnnotatedElement.renamedName: String
    get(){
        return (findAnnotation<Rename>()?.newName ?: findAnnotation<SiRename>()?.newName)
            ?: when(this){
                is SiParameter -> name ?: "<parameter: ${toString()}>"
                is SiCallable<*> -> name
                is SiClass<*> -> qualifiedName ?: "<class: ${toString()}>"
                is SiType -> (classifier as? SiClass<*>)?.qualifiedName ?: toString()
                is SiTypeParameter -> name
                is SiClassifier -> toString()
                is SiAnnotation -> this::class.si.qualifiedName ?: "<annotation: ${toString()}>"
                else -> toString()
            }
    }