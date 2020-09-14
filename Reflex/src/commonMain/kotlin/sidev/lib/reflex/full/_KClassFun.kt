package sidev.lib.reflex.full

import sidev.lib.check.notNullTo
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.collection.sequence.skip
import kotlin.reflect.KClass


expect val KClass<*>.isPrimitive: Boolean
expect val KClass<*>.isArray: Boolean
expect val KClass<*>.isCopySafe: Boolean
expect val KClass<*>.isCollection: Boolean

expect val KClass<*>.classesTree: NestedSequence<KClass<*>>
expect val KClass<*>.isInterface: Boolean
expect val KClass<*>.isCommonSealed: Boolean


/**
 * Digunakan untuk mengambil nama qualified dari turunan sealed class.
 * Qualified name yg diambil dg pola berikut <Super>...<Class> dg super merupakan superclass.
 * Bagian paling depan dari qualified name adalah nama class dg kata kunci sealed yg paling dekat
 * dg this [KClass] tempat fungsi ini dipanggil.
 *
 * Fungsi ini msh bergantung pada Java Reflection.
 *
 * [isQualifiedName] -true jika nama yg diambil adalah nama lengkap dimulai dari sealed super class
 *                    hingga kelas ini yg dipidahkan oleh titik (.).
 *                   -false jika nama yg diambil hanyalah nama kelas ini.
 * @return -null jika kelas ini gak punya [KClass.simpleName]
 *         -[KClass.simpleName] jika ternyata kelas ini gak punya sealed super class.
 */
fun KClass<*>.getSealedClassName(isQualifiedName: Boolean= true): String?{
//    Log.e("getSealedClassName", ".getSealedClassName() MAULAI")
    return this.simpleName.notNullTo { thisName ->
        var thisNameRes= thisName
        if(isQualifiedName){
//            Log.e("getSealedClassName", ".getSealedClassName() QUALIFIED MAULAI")
            var superName= ""
            var isSealedSuperFound= false
            for(clazz in classesTree.skip { it.isInterface }){
//                if(clazz == Any::class) continue
                superName= clazz.simpleName!! +"." +superName
                if(clazz.isCommonSealed){
                    isSealedSuperFound= true
                    break
                }
            }
/*
            this.iterateSuperClass_k { clazz ->
                try{
                    superName= clazz.simpleName!! +"." +superName
                    if(clazz.isSealed){
                        isSealedSuperFound= true
                        return@iterateSuperClass_k
                    }
                }
                catch (e: KotlinNullPointerException){ return@iterateSuperClass_k }
            }
 */
//            Log.e("getSealedClassName", ".getSealedClassName() QUALIFIED SELESAI")
            if(isSealedSuperFound)
                thisNameRes= superName +thisNameRes
        }
//        println("getSealedClassName(): thisNameRes= $thisNameRes")
//        Log.e("getSealedClassName", "thisNameRes= $thisNameRes")
        thisNameRes
    }
}