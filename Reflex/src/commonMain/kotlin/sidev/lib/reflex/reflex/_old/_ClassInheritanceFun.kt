package sidev.lib.reflex._old
/*
import sidev.lib.check.notNullTo
import sidev.lib.reflex._ReflexConst.K_CLASS_BASE_NAME
import sidev.lib.reflex.inner.createType
import sidev.lib.reflex.inner.isSubclassOf
import sidev.lib.reflex.inner.isSuperclassOf
import sidev.lib.reflex.inner.superclasses
import sidev.lib.reflex.type.inferredType
import sidev.lib.collection.iterator.NestedIteratorSimple
import sidev.lib.collection.iterator.NestedIteratorSimpleImpl
import sidev.lib.collection.sequence.NestedSequence
import kotlin.reflect.KClass
import kotlin.reflect.KType


/**
 * [includeInterfaces] true jika iterasi hanya dilakukan pada super class dg jenis Class, bkn Interface.
 * Fungsi ini mendefinisikan supertype sbg interface berdasarkan refleksi Java.
 *
 * Fungsi ini msh bergantung dari Java Reflection.
 */
fun KClass<*>.supertypes(includeInterfaces: Boolean= true): NestedSequence<KType> {
    return object :
        NestedSequence<KType> {
        override fun iterator(): NestedIteratorSimple<KType>
                = object: NestedIteratorSimpleImpl<KType>(supertypes){

            override fun getOutputIterator(nowInput: KType): Iterator<KType>? {
                return if((nowInput.classifier as? KClass<*>)?.simpleName == K_CLASS_BASE_NAME) null
                else (nowInput.classifier as? KClass<*>)?.supertypes?.iterator() //Jika (now.classifier as? KClass<*>)?.simpleName menghasilkan null, maka cabang ini tetap aman.
            }

            override fun skip(now: KType): Boolean
                    = ((now.classifier as? KClass<*>)?.isInterface ?: false) && !includeInterfaces
        }
    }
}


val KClass<*>.supertypesTree: NestedSequence<KType>
    get()= object :
        NestedSequence<KType> {
        override fun iterator(): NestedIteratorSimple<KType>
                = object: NestedIteratorSimpleImpl<KType>(supertypes){
            override fun getOutputIterator(nowInput: KType): Iterator<KType>? {
                return if((nowInput.classifier as? KClass<*>)?.simpleName == K_CLASS_BASE_NAME) null
                else (nowInput.classifier as? KClass<*>)?.supertypes?.iterator() //Jika (now.classifier as? KClass<*>)?.simpleName menghasilkan null, maka cabang ini tetap aman.
            }
        }
    }
val KClass<*>.superclassesTree: NestedSequence<KClass<*>>
    get()= object :
        NestedSequence<KClass<*>> {
        override fun iterator(): NestedIteratorSimple<KClass<*>>
                = object: NestedIteratorSimpleImpl<KClass<*>>(superclasses){
            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KClass<*>>? {
                return if(nowInput.simpleName == K_CLASS_BASE_NAME) null
                else nowInput.superclasses.iterator() //Jika (now.classifier as? KClass<*>)?.simpleName menghasilkan null, maka cabang ini tetap aman.
            }
        }
    }

/** Sama seperti [supertypesTree] namun termasuk `this.extension` [KClass]. */
val KClass<*>.typesTree: NestedSequence<KType>
    get()= object : NestedSequence<KType> {
        override fun iterator(): NestedIteratorSimple<KType>
                = object: NestedIteratorSimpleImpl<KType>(
            try{ this@typesTree.createType() } catch (e: Exception){ this@typesTree.inferredType.type }
        ){
            override fun getOutputIterator(nowInput: KType): Iterator<KType>? {
                return if((nowInput.classifier as? KClass<*>)?.simpleName == K_CLASS_BASE_NAME) null
                else (nowInput.classifier as? KClass<*>)?.supertypes?.iterator() //Jika (now.classifier as? KClass<*>)?.simpleName menghasilkan null, maka cabang ini tetap aman.
            }
        }
    }
/** Sama dg [KClass.typesTree]. */
val KType.typesTree: NestedSequence<KType>
    get()= object : NestedSequence<KType> {
        override fun iterator(): NestedIteratorSimple<KType>
                = object: NestedIteratorSimpleImpl<KType>(this@typesTree){
            override fun getOutputIterator(nowInput: KType): Iterator<KType>? {
                return if((nowInput.classifier as? KClass<*>)?.simpleName == K_CLASS_BASE_NAME) null
                else (nowInput.classifier as? KClass<*>)?.supertypes?.iterator() //Jika (now.classifier as? KClass<*>)?.simpleName menghasilkan null, maka cabang ini tetap aman.
            }
        }
    }
/** Sama seperti [superclassesTree] namun termasuk `this.extension` [KClass]. */
val KClass<*>.classesTree: NestedSequence<KClass<*>>
    get()= object :
        NestedSequence<KClass<*>> {
        override fun iterator(): NestedIteratorSimple<KClass<*>>
                = object: NestedIteratorSimpleImpl<KClass<*>>(this@classesTree){
            override val tag: String
                get() = "classesTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KClass<*>>? {
                return if(nowInput.simpleName == K_CLASS_BASE_NAME) null
                else nowInput.superclasses.iterator() //Jika (now.classifier as? KClass<*>)?.simpleName menghasilkan null, maka cabang ini tetap aman.
            }
        }
    }

val KClass<*>.sealedSubclassesTree: NestedSequence<KClass<*>>
    get()= object :
        NestedSequence<KClass<*>> {
        override fun iterator(): NestedIteratorSimple<KClass<*>>
                = object: NestedIteratorSimpleImpl<KClass<*>>(this@sealedSubclassesTree){
            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KClass<*>>?
                    = nowInput.sealedSubclasses.iterator()
        }
    }



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
            for(supertype in this.supertypes(false)){
                val clazz= (supertype.classifier as? KClass<*>)
//                if(clazz == Any::class) continue
                if(clazz != null){
                    superName= clazz.simpleName!! +"." +superName
                    if(clazz.isSealed){
                        isSealedSuperFound= true
                        break
                    }
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



/** Memiliki fungsi sama dg [isSuperclassOf] namun berguna untuk variabel generic tanpa batas atas Any. */
fun <T1, T2> T1.isSuperClassOf(derived: T2): Boolean{
    return try{
        val thisClass= if(this !is KClass<*>) (this as Any)::class else this
        val derivedClass= if(derived !is KClass<*>) (derived as Any)::class else derived
        thisClass.isSuperclassOf(derivedClass)
    } catch (e: Exception){ false }
}
/** Sama seperti [isSuperClassOf], namun @return `false` jika `this.extension` bertipe sama dg [derived]. */
fun <T1, T2> T1.isExclusivelySuperClassOf(derived: T2): Boolean{
    return try{
        val thisClass= if(this !is KClass<*>) (this as Any)::class else this
        val derivedClass= if(derived !is KClass<*>) (derived as Any)::class else derived
        thisClass.isSuperclassOf(derivedClass) && thisClass != derivedClass
    } catch (e: Exception){ false }
}

/** Memiliki fungsi sama dg [isSubclassOf] namun berguna untuk variabel generic tanpa batas atas Any. */
fun <T1, T2> T1.isSubClassOf(base: T2): Boolean{
    return try{
        val thisClass= if(this !is KClass<*>) (this as Any)::class else this
        val baseClass= if(base !is KClass<*>) (base as Any)::class else base
        thisClass.isSubclassOf(baseClass)
    } catch (e: Exception){ false }
}
/** Sama seperti [isSubClassOf], namun @return `false` jika `this.extension` bertipe sama dg [base]. */
fun <T1, T2> T1.isExclusivelySubClassOf(base: T2): Boolean{
    return try{
        val thisClass= if(this !is KClass<*>) (this as Any)::class else this
        val baseClass= if(base !is KClass<*>) (base as Any)::class else base
        thisClass.isSubclassOf(baseClass) && thisClass != baseClass
    } catch (e: Exception){ false }
}

 */