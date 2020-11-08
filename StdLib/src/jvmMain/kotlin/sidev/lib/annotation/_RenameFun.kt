@file:JvmName("_RenameFunJvm")
package sidev.lib.annotation

import sidev.lib.reflex.*
import sidev.lib.reflex.si
import java.lang.reflect.*
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
                is Annotation -> this::class.qualifiedName ?: "<annotation: ${toString()}>"
                else -> toString()
            }
    }

val AnnotatedElement.renamedName: String
    get(){
        return (findAnnotation<Rename>()?.newName ?: findAnnotation<SiRename>()?.newName)
            ?: when(this){
                is Class<*> -> name
                is Method -> name
                is Field -> name //?: "<class: ${toString()}>"
                is Constructor<*> -> name
                is Parameter -> name ?: "<parameter: ${toString()}>"
                is TypeVariable<*> -> name ?: "<typeVariable: ${toString()}>"
                is Annotation -> this::class.java.name ?: "<annotation: ${toString()}>"
                else -> toString()
            }
    }