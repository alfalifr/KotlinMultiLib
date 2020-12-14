package sidev.lib.reflex.full

import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.SkippableIteratorImpl
import sidev.lib.collection.lazy_list.asCached
import sidev.lib.collection.string
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiFunction
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.SiProperty1
import sidev.lib.collection.iterator.NestedIterator
import sidev.lib.collection.sequence.NestedSequence
import kotlin.jvm.JvmName
import kotlin.reflect.KClass


/*
================================
Constructor
================================
 */

/** Mengambil konstruktor dg jumlah parameter paling sedikit. */
@get:JvmName("leastParamConstructor")
val <T: Any> SiClass<T>.leastParamConstructor: SiFunction<T>
    get(){
        var constr= try{ constructors.first() }
        catch (e: NoSuchElementException){ throw NoSuchElementException("Kelas \"$qualifiedName\" tidak punya konstruktor (interface, abstract, anonymous class, atau null)") }

        var minParamCount= constr.parameters.size
        for(constrItr in constructors){
            if(minParamCount > constrItr.parameters.size){
                constr= constrItr
                minParamCount= constrItr.parameters.size
            }
            if(minParamCount == 0) break //Karena gakda fungsi yg jumlah parameternya krg dari 0
        }
        return constr
    }

/** Mirip dg [leastParamConstructor], namun param opsional tidak disertakan. Nullable tetap disertakan. */
@get:JvmName("leastRequiredParamConstructor")
val <T: Any> SiClass<T>.leastRequiredParamConstructor: SiFunction<T>
    get(){
        var constr= try{ constructors.first() }
        catch (e: NoSuchElementException){ throw NoSuchElementException("Kelas \"$this\" tidak punya konstruktor (interface, abstract, anonymous class, atau null)") }
        var minParamCount= constr.parameters.size
        //<20 Juli 2020> => Konstruktor dg jml param tersedikit belum tentu merupakan konstruktor dg jml param wajib paling sedikit.
        for(constrItr in constructors){
//            prine("leastRequiredParamConstructor class= $simpleName constrItr.parameters.size= ${constrItr.parameters.size}")
            if(minParamCount > constrItr.parameters.size){
                constr= constrItr
                minParamCount= constrItr.parameters.size
                for(param in constrItr.parameters)
                    if(param.isOptional) minParamCount--
            }
            if(minParamCount == 0) break //Karena gakda fungsi yg jumlah parameternya krg dari 0
        }
        val paramList= ArrayList<SiParameter>()
        for(param in constr.parameters)
            if(!param.isOptional) paramList.add(param)

        //Bungkus constructor yg ditemukan dengan fungsi yg parameter listnya hanya terlihat yg non-optional.
        return object: SiFunction<T> by constr{
            override val parameters: List<SiParameter> = paramList
            override fun toString(): String = constr.toString()
        }
    }

/** Mengambil konstruktor dg param yg memiliki tipe data sesuai [paramClass]. Jika tidak ketemu, maka throw [NoSuchMethodException]. */
fun <T: Any> SiClass<T>.findConstructorWithParam(vararg paramClass: SiClass<*>): SiFunction<T> {
    if(!isInstantiable) throw NoSuchElementException("Kelas \"$this\" tidak punya konstruktor (interface, abstract, anonymous class, atau null)")

    for(constr in constructors){
        var paramClassMatch= true
        if(constr.parameters.size == paramClass.size){
            for(param in constr.parameters){
                paramClassMatch= paramClassMatch && param.type.classifier == paramClass[param.index]
            }
        } else continue

        if(paramClassMatch)
            return constr
    }
    val kclass= descriptor.native as KClass<T>
    throw NoSuchMemberExc(kclass, kclass, "constructors","Tidak ada konstruktor \"$qualifiedName\" dg parameter \"${paramClass.string}\"")
}


/** Mengambil semua konstruktor yg tersedia mulai dari `this.extension` [KClass] hingga superclass. */
@get:JvmName("contructorsTree")
val SiClass<*>.contructorsTree: NestedSequence<SiFunction<*>>
    get()= object : NestedSequence<SiFunction<*>> {
        override fun iterator(): NestedIterator<SiClass<*>, SiFunction<*>>
                = object : NestedIteratorImpl<SiClass<*>, SiFunction<*>>(this@contructorsTree.classesTree.iterator()){
            override val tag: String
                get() = "contrustorsTree"

            override fun getOutputIterator(nowInput: SiClass<*>): Iterator<SiFunction<*>>? = nowInput.constructors.iterator()
            override fun getInputIterator(nowOutput: SiFunction<*>): Iterator<SiClass<*>>? = null
        }
    }

/** Mengambil semua parameter yg tersedia dari [contructorsTree]. */
@get:JvmName("contructorParamsTree")
val SiClass<*>.contructorParamsTree: NestedSequence<SiParameter>
    get()= object : NestedSequence<SiParameter> {
        override fun iterator(): NestedIterator<SiFunction<*>, SiParameter> //!!!<20 Juli 2020> => Blum bisa pake NestedIterator.
                = object : NestedIteratorImpl<SiFunction<*>, SiParameter>(this@contructorParamsTree.contructorsTree.iterator()){
            override val tag: String
                get() = "contructorParamsTree"

            override fun getOutputIterator(nowInput: SiFunction<*>): Iterator<SiParameter>? = nowInput.parameters.iterator()
            override fun getInputIterator(nowOutput: SiParameter): Iterator<SiFunction<*>>? = null
        }
    }

/** Mengambil semua parameter konstruktor yg diambil dari [contructorParamsTree] yg merupakan properti. */
@get:JvmName("contructorPropertiesTree")
val SiClass<*>.contructorPropertiesTree: Sequence<SiProperty1<*, *>>
    get()= object : Sequence<SiProperty1<*, *>>{
        override fun iterator(): Iterator<SiProperty1<*, *>>
                = object : SkippableIteratorImpl<SiProperty1<*, *>>(this@contructorPropertiesTree.implementedMemberPropertiesTree.iterator()){
            val constrsParams= this@contructorPropertiesTree.contructorParamsTree.asCached()

            override fun skip(now: SiProperty1<*, *>): Boolean
                    = constrsParams.find { it.isPropertyLike(now, true) } == null
        }
    }
// */
