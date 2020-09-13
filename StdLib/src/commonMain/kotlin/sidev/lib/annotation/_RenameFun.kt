package sidev.lib.annotation

import sidev.lib.reflex.*
import sidev.lib.reflex.native_.si


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