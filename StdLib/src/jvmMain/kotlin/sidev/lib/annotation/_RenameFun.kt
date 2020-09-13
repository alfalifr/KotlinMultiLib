@file:JvmName("_RenameFunJvm")
package sidev.lib.annotation

import sidev.lib.reflex.*
import sidev.lib.reflex.native_.si
import kotlin.reflect.*
import kotlin.reflect.full.findAnnotation


val KAnnotatedElement.renamedName: String
    get(){
        return (findAnnotation<Rename>()?.newName ?: findAnnotation<SiRename>()?.newName)
            ?: when(this){
                is KParameter -> name ?: "<parameter: ${toString()}>"
                is KCallable<*> -> name
                is KClass<*> -> qualifiedName ?: "<class: ${toString()}>"
                is KType -> (classifier as? SiClass<*>)?.qualifiedName ?: toString()
                is KTypeParameter -> name
                is KClassifier -> toString()
                is Annotation -> this::class.si.qualifiedName ?: "<annotation: ${toString()}>"
                else -> toString()
            }
    }