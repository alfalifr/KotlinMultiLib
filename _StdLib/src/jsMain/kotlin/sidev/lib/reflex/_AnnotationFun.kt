package sidev.lib.reflex

import sidev.lib.collection.addIfAbsent
import sidev.lib.reflex.core.ReflexFactory
import sidev.lib.reflex.core.createNativeWrapper
import sidev.lib.reflex.js.JsClass_
import sidev.lib.reflex.js.jsClass
import kotlin.reflect.KClass


actual fun <T: Annotation> SiAnnotatedElement.setAnnotation(vararg annotations: KClass<T>): Boolean{
    return try{
        this as SiAnnotatedElementImpl
        var res= false
        for(annCls in annotations){
            val ann= annCls.jsClass.new()
            val usedAnnot= ReflexFactory.createAnnotation(createNativeWrapper(ann), this)
            res= res || this.annotations.addIfAbsent(usedAnnot)
        }
        res
    } catch (e: ClassCastException){ false }
}