package sidev.lib.reflex

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.addIfAbsent
import sidev.lib.reflex.core.ReflexFactory
import sidev.lib.reflex.core.createNativeWrapper
import kotlin.reflect.KClass


expect fun <T: Annotation> SiAnnotatedElement.setAnnotation(vararg annotations: KClass<T>): Boolean

/**
 * Mengambil anotasi yg sebenarnya. Hal tersebut dikarenakan semua anotasi yg melewati fungsi
 * [setAnnotation] akan di-wrap sehingga pengecekan menggunakan klausa `is` akan bermasalah.
 * Oleh karena itu, property ini berfungsi untuk melakukan un-wrap terhadap anotasi yg melewati fungsi [setAnnotation].
 */
val Annotation.realAnnotation: Annotation
    get(){
        return if(this is SiAnnotationImpl)
            descriptor.native as Annotation
        else this
    }

inline fun <reified T: Annotation> SiAnnotatedElement.findAnnotation(): T?
        = annotations.find { it.realAnnotation is T }?.realAnnotation as? T

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T: Annotation> SiAnnotatedElement.findAnnotation(clazz: KClass<T>): T?
        = annotations.find { it.realAnnotation::class == clazz }?.realAnnotation as? T

inline fun <reified T: Annotation> SiAnnotatedElement.hasAnnotation(): Boolean = findAnnotation<T>() != null
fun <T: Annotation> SiAnnotatedElement.hasAnnotation(clazz: KClass<T>): Boolean = findAnnotation(clazz) != null


fun SiAnnotatedElement.setAnnotation(vararg annotations: Annotation): Boolean{
    return try{
        this as SiAnnotatedElementImpl
        var res= false
        for(ann in annotations){
            val usedAnnot= ReflexFactory.createAnnotation(createNativeWrapper(ann), this)
            res= res || this.annotations.addIfAbsent(usedAnnot)
        }
        res
    } catch (e: ClassCastException){ false }
}