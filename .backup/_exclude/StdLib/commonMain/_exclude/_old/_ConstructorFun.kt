package sidev.lib.reflex._old
/*
import sidev.lib.collection.lazy_list.asCached
import sidev.lib.collection.string
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.reflex._ReflexConst.K_FUNCTION_CONSTRUCTOR_NAME_PREFIX
import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.SkippableIteratorImpl
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.*


/*
==========================
Constructor
==========================
 */

val KParameter.isInConstructor: Boolean
    get()= this.toString().contains(K_FUNCTION_CONSTRUCTOR_NAME_PREFIX)
//Karena tidak tersedia informasi mengenai fungsi pada interface KParameter,
// info yg bisa diambil berasal dari fungsi toString().

fun KParameter.isPropertyLike(prop: KProperty<*>, isInConstructorKnown: Boolean= false): Boolean{
    return if(isInConstructorKnown || isInConstructor){
        name == prop.name && type/*.classifier*/ == prop.returnType/*.classifier*/
    } else false
}


/** Mengambil konstruktor dg jumlah parameter paling sedikit. */
val <T: Any> KClass<T>.leastParamConstructor: KFunction<T>
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
val <T: Any> KClass<T>.leastRequiredParamConstructor: KFunction<T>
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
        val paramList= ArrayList<KParameter>()
        for(param in constr.parameters)
            if(!param.isOptional) paramList.add(param)

        //Bungkus constructor yg ditemukan dengan fungsi yg parameter listnya hanya terlihat yg non-optional.
        return object: KFunction<T> by constr{
            override val parameters: List<KParameter> = paramList
            override fun toString(): String = constr.toString()
        }
    }

/** Mengambil konstruktor dg param yg memiliki tipe data sesuai [paramClass]. Jika tidak ketemu, maka throw [NoSuchMethodException]. */
fun <T: Any> KClass<T>.findConstructorWithParam(vararg paramClass: KClass<*>): KFunction<T> {
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
    throw NoSuchMemberExc(this, this, "constructors","Tidak ada konstruktor \"$qualifiedName\" dg parameter \"${paramClass.string}\"")
}

/** Mengambil semua konstruktor yg tersedia mulai dari `this.extension` [KClass] hingga superclass. */
val KClass<*>.contructorsTree: NestedSequence<KFunction<*>>
    get()= object : NestedSequence<KFunction<*>> {
        override fun iterator(): NestedIterator<KClass<*>, KFunction<*>>
                = object : NestedIteratorImpl<KClass<*>, KFunction<*>>(this@contructorsTree.classesTree.iterator()){
            override val tag: String
                get() = "contrustorsTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KFunction<*>>? = nowInput.constructors.iterator()
            override fun getInputIterator(nowOutput: KFunction<*>): Iterator<KClass<*>>? = null
        }
    }

/** Mengambil semua parameter yg tersedia dari [contructorsTree]. */
val KClass<*>.contructorParamsTree: NestedSequence<KParameter>
    get()= object : NestedSequence<KParameter> {
        override fun iterator(): NestedIterator<KFunction<*>, KParameter> //!!!<20 Juli 2020> => Blum bisa pake NestedIterator.
                = object : NestedIteratorImpl<KFunction<*>, KParameter>(this@contructorParamsTree.contructorsTree.iterator()){
            override val tag: String
                get() = "contructorParamsTree"

            override fun getOutputIterator(nowInput: KFunction<*>): Iterator<KParameter>? = nowInput.parameters.iterator()
            override fun getInputIterator(nowOutput: KParameter): Iterator<KFunction<*>>? = null
        }
    }

/** Mengambil semua parameter konstruktor yg diambil dari [contructorParamsTree] yg merupakan properti. */
val KClass<*>.contructorPropertiesTree: Sequence<KProperty1<*, *>>
    get()= object : Sequence<KProperty1<*, *>>{
        override fun iterator(): Iterator<KProperty1<*, *>>
                = object : SkippableIteratorImpl<KProperty1<*, *>>(this@contructorPropertiesTree.implementedMemberPropertiesTree.iterator()){
            val constrsParams= this@contructorPropertiesTree.contructorParamsTree.asCached()

            override fun skip(now: KProperty1<*, *>): Boolean
                    = constrsParams.find { it.isPropertyLike(now, true) } == null
        }
    }
// */
