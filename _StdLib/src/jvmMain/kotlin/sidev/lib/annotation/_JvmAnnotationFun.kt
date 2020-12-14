package sidev.lib.annotation

import java.lang.reflect.AnnotatedElement
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

//import java.lang.annotation.Annotation as JvmAnnotation

inline fun <reified T: Annotation> AnnotatedElement.findAnnotation(): T? = annotations.find { it is T } as T?
fun <T: Annotation> AnnotatedElement.findAnnotation(clazz: Class<T>): T? = annotations.find { it::class.java == clazz } as T?

inline fun <reified T: Annotation> AnnotatedElement.hasAnnotation(): Boolean = findAnnotation<T>() != null
fun <T: Annotation> AnnotatedElement.hasAnnotation(clazz: Class<T>): Boolean = findAnnotation(clazz) != null

fun <T: Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T? = annotations.find { it::class == clazz } as? T
fun <T: Annotation> KAnnotatedElement.hasAnnotation(clazz: KClass<T>): Boolean = findAnnotation(clazz) != null