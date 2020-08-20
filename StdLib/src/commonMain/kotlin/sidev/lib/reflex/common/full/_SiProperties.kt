package sidev.lib.reflex.common.full

import sidev.lib.check.asNotNullTo
import sidev.lib.check.notNull
import sidev.lib.collection.iterator.nestedSequence
import sidev.lib.collection.iterator.nestedSequenceSimple
import sidev.lib.collection.iterator.skip
import sidev.lib.collection.lazy_list.isNotEmpty
import sidev.lib.property.UNINITIALIZED_VALUE
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiFunction
import sidev.lib.reflex.common.SiMutableProperty1
import sidev.lib.reflex.common.SiProperty1
import sidev.lib.reflex.common.native.si
import sidev.lib.universal.structure.collection.sequence.NestedSequence

/*
val SiClass<*>.memberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

val <T: Any> SiClass<T>.memberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>
 */

val SiClass<*>.declaredMemberFunctions: Sequence<SiFunction<*>>
    get()= members.asSequence().filter { it is SiFunction<*> } as Sequence<SiFunction<*>>

val <T: Any> SiClass<T>.declaredMemberProperties: Sequence<SiProperty1<T, *>>
    get()= members.asSequence().filter { it is SiProperty1<*, *> } as Sequence<SiProperty1<T, *>>



val <T: Any> SiClass<T>.declaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= nestedSequence(classesTree.iterator()){ cls: SiClass<*> -> cls.declaredMemberProperties.iterator() }
            as NestedSequence<SiProperty1<T, *>>

val <T: Any> SiClass<T>.nestedDeclaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= nestedSequence(classesTree.iterator(), {
        it.returnType.classifier.asNotNullTo { cls: SiClass<*> -> cls.classesTree.iterator() }
    })
    { cls: SiClass<*> -> cls.declaredMemberProperties.iterator() } as NestedSequence<SiProperty1<T, *>>

val <T: Any> SiClass<T>.implementedMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= declaredMemberPropertiesTree.skip { it.isAbstract }

val <T: Any> SiClass<T>.implementedNestedMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= nestedDeclaredMemberPropertiesTree.skip { it.isAbstract }


val <T: Any> T.implementedPropertyValuesTree: NestedSequence<Pair<SiProperty1<T, *>, Any?>>
    get(){
        return nestedSequence(this::class.si.classesTree.iterator()){ innerCls: SiClass<*> ->
            innerCls.declaredMemberProperties.iterator().asSequence()
                .filter { !it.isAbstract }
                .map {
                    val vals= (it as SiProperty1<T, Any?>).forceGet(this)
                    Pair(it, vals)
                }
                .iterator()
        }
    }
val Any.implementedNestedPropertyValuesTree: NestedSequence<Pair<SiProperty1<*, *>, Any?>>
    get()= nestedSequenceSimple<Pair<SiProperty1<*, *>, Any?>>(implementedPropertyValuesTree.iterator()){
        it.second?.implementedPropertyValuesTree?.iterator()
    }
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



val SiClass<*>.declaredMemberFunctionsTree: NestedSequence<SiFunction<*>>
    get()= nestedSequence(classesTree.iterator()){ cls: SiClass<*> -> cls.declaredMemberFunctions.iterator() }


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

expect fun <T, V> SiProperty1<T, V>.handleNativeForceGet(receiver: T, exceptionFromCommon: Throwable): V

fun <T, V> SiMutableProperty1<T, V>.forceSet(receiver: T, value: V){
    val initAccessible= isAccessible
    isAccessible= true
    set(receiver, value)
    isAccessible= initAccessible
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

