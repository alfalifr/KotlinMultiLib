package sidev.lib.reflex.full

import sidev.lib.check.asNotNullTo
import sidev.lib.collection.sequence.nestedSequence
import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.collection.iterator.skip
import sidev.lib.exception.TypeExc
import sidev.lib.property.UNINITIALIZED_VALUE
import sidev.lib.reflex.*
import sidev.lib.reflex.full.types.TypedValue
import sidev.lib.reflex.full.types.isAssignableFrom
import sidev.lib.reflex.native_.si
import sidev.lib.collection.sequence.NestedSequence
import kotlin.jvm.JvmName
import kotlin.reflect.KClass


internal expect fun <T, V> SiProperty1<T, V>.handleNativeForceGet(receiver: T, exceptionFromCommon: Throwable): V

/*
val SiClass<*>.memberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

val <T: Any> SiClass<T>.memberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>
 */

@get:JvmName("declaredMemberFunctions")
val SiClass<*>.declaredMemberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

@get:JvmName("declaredMemberProperties")
val <T: Any> SiClass<T>.declaredMemberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>


@get:JvmName("declaredMemberPropertiesTree")
val <T: Any> SiClass<T>.declaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= nestedSequence(classesTree){ cls: SiClass<*> -> cls.declaredMemberProperties.iterator() }
            as NestedSequence<SiProperty1<T, *>>

@get:JvmName("nestedDeclaredMemberPropertiesTree")
val SiClass<*>.nestedDeclaredMemberPropertiesTree: NestedSequence<SiProperty1<Any, *>>
    get()= nestedSequence<SiClass<*>, SiProperty1<Any, *>>(classesTree, {
        it.returnType.classifier.asNotNullTo { cls: SiClass<*> -> cls.classesTree.iterator() }
    })
    { cls: SiClass<*> -> cls.declaredMemberProperties.iterator() as Iterator<SiProperty1<Any, *>> } //as NestedSequence<SiProperty1<T, *>>

@get:JvmName("implementedMemberPropertiesTree")
val <T: Any> SiClass<T>.implementedMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= declaredMemberPropertiesTree.skip { it.isAbstract }

@get:JvmName("implementedNestedMemberPropertiesTree")
val SiClass<*>.implementedNestedMemberPropertiesTree: NestedSequence<SiProperty1<Any, *>>
    get()= nestedDeclaredMemberPropertiesTree.skip { it.isAbstract }


@get:JvmName("implementedPropertyValues")
val <T: Any> T.implementedPropertyValues: Sequence<Pair<SiProperty1<T, *>, Any?>>
    get(){
        return this::class.si.declaredMemberProperties.asSequence().map {
            val vals= (it as SiProperty1<T, Any?>).forceGet(this)
            Pair(it, vals)
        }
    }

@get:JvmName("implementedPropertyValuesTree")
val <T: Any> T.implementedPropertyValuesTree: NestedSequence<Pair<SiProperty1<T, *>, Any?>>
    get(){
        return nestedSequence(this::class.si.classesTree){ innerCls: SiClass<*> ->
            innerCls.declaredMemberProperties//.iterator()//.asSequence()
                .filter { !it.isAbstract }
                .map {
                    val vals= (it as SiProperty1<T, Any?>).forceGet(this)
                    Pair(it, vals)
                }
                .iterator()
        }
    }
@get:JvmName("implementedNestedPropertyValuesTree")
val Any.implementedNestedPropertyValuesTree: NestedSequence<Pair<SiProperty1<Any, *>, Any?>>
    get()= nestedSequenceSimple<Pair<SiProperty1<Any, *>, Any?>>(implementedPropertyValuesTree){
        it.second?.implementedPropertyValuesTree?.iterator()
    }
/*
/** Sama dg [implementedPropertyValuesTree], namun tidak mengambil property yg `private`. */
//@Suppress(SuppressLiteral.UNCHECKED_CAST)
val <T: Any> T.implementedAccesiblePropertyValuesTree: Sequence<Pair<SiProperty1<Any, *>, Any?>>
    get()= object : Sequence<Pair<KProperty1<Any, *>, Any?>>{
        override fun iterator(): Iterator<Pair<KProperty1<Any, *>, Any?>>
                = object: Iterator<Pair<KProperty1<Any, *>, Any?>>{
            private val memberPropsItr=
                this@implementedAccesiblePropertiesValueMapTree::class.memberProperties
                    .asSequence().filterNot { it.isAbstract }.iterator()
                        as Iterator<KProperty1<Any, *>>

            override fun hasNext(): Boolean = memberPropsItr.hasNext()

            override fun next(): Pair<KProperty1<Any, *>, Any?> {
                val prop= memberPropsItr.next()
                val value= if(prop.toString() != K_PROPERTY_ARRAY_SIZE_STRING)
                    prop.getter.forcedCall(this@implementedAccesiblePropertiesValueMapTree)
                else (this@implementedAccesiblePropertiesValueMapTree as Array<*>).size
                return Pair(prop, value)
            }
        }
    }
 */
/*
    get(){
        var lastValue: Any?= this
        val propValueListTemp= arrayListOf(lastValue)

        return nestedSequence(this::class.si.classesTree.iterator(), {
            (it.first.returnType.classifier.asNotNullTo { cls: SiClass<*> -> cls.classesTree }
                .notNull { seq ->
                    if(seq.isNotEmpty()) propValueListTemp.add(lastValue)
                })?.iterator()
        }){ innerCls: SiClass<*> ->
            innerCls.declaredMemberProperties.iterator().asSequence()
                .filter { !it.isAbstract }
                .map {
                    val vals= (it as SiProperty1<T, Any?>).forceGet(this)
                    lastValue= vals
                    Pair(it, vals)
                }
                .iterator()
        }
    }
 */


@get:JvmName("declaredMemberFunctionsTree")
val SiClass<*>.declaredMemberFunctionsTree: NestedSequence<SiFunction<*>>
    get()= nestedSequence(classesTree){ cls: SiClass<*> -> cls.declaredMemberFunctions.iterator() }


/**
 * Memaksa [SiProperty1] untuk melakukan operasi get terhadap value pada field.
 * @return nilai [V] dari property, atau [UNINITIALIZED_VALUE] jika terjadi error karena
 * akses `lateinit var` yg blum di-init.
 */
fun <T, V> SiProperty1<T, V>.forceGet(receiver: T): V{
    return try{
        val initAccessible= isAccessible
        isAccessible= true
        val vals= get(receiver) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
        isAccessible= initAccessible
        vals
    } catch (e: Throwable){
        handleNativeForceGet(receiver, e)
    }
}

fun <T, V> SiMutableProperty1<T, V>.forceSet(receiver: T, value: V){
    val initAccessible= isAccessible
    isAccessible= true
    set(receiver, value)
    isAccessible= initAccessible
}


/** @return -> `true` jika operasi set berhasil,
 *   -> `false` jika refleksi dilarang,
 *   -> @throws [TypeExc] jika [value] yg dipass tidak sesuai tipe `this.extension`. */
fun <I, O> SiMutableProperty1<I, O>.forceSetTyped(receiver: I, typedValue: TypedValue<O>): Boolean{
    return try{
        if(returnType.isAssignableFrom(typedValue.type)){
            val oldIsAccesible= isAccessible
            isAccessible= true
            set(receiver, typedValue.value)
            isAccessible= oldIsAccesible
            true
        } else {
            throw TypeExc(
                expectedType = returnType.classifier as KClass<*>, actualType = (typedValue.value as? Any)?.clazz,
                msg = "Tidak dapat meng-assign value dg tipe tersebut ke properti: $this."
            )
        }
    } catch (e: Exception){
        //Jika Kotlin melarang melakukan call melalui refleksi
        // --- atau ---
        //Jika terjadi error secara internal pada refleksi Java.
        // Biasanya terjadi pada operasi call melibatkan `lateinit var`
        false
    }
}


/**
 * Melakukan copy properti dari [source] ke [destination] yg memiliki nama dan tipe data yg sama.
 *
 * Fungsi ini berbeda [clone] karena hanya melakukan copy referential scr langsung tanpa melakukan
 * instantiate. Operasi copy nilai hanya dilakukan pada permukaan.
 */
fun copySimilarProperty(source: Any, destination: Any){
    for(valMap in source.implementedPropertyValues){ //implementedAccesiblePropertiesValueMapTree
        if(valMap.second?.isUninitializedValue == true)
            continue
        (destination.implementedPropertyValues.find {
            it.first.name == valMap.first.name
                    && it.first.returnType.classifier == valMap.first.returnType.classifier
                    && it.first is SiMutableProperty1<*, *>
        }?.first as? SiMutableProperty1<Any, Any?>)
            ?.forceSet(destination, valMap.second)
    }
}

/*
val <T: Any> SiClass<T>.nestedDeclaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>> get()= object : NestedSequence<SiProperty1<T, *>>{
    override fun iterator(): NestedIterator<*, SiProperty1<T, *>> = object : NestedIteratorImpl<SiClass<*>, SiProperty1<T, *>>(
        this@nestedDeclaredMemberPropertiesTree.classesTree.iterator()
    ){
        override fun getOutputIterator(nowInput: SiClass<*>): Iterator<SiProperty1<T, *>>?
                = nowInput.declaredMemberProperties.iterator() as Iterator<SiProperty1<T, *>>
        override fun getInputIterator(nowOutput: SiProperty1<T, *>): Iterator<SiClass<*>>?
                = nowOutput.returnType
    }
}
 */

//val <T: Any> SiClass<T>.memberProperties

