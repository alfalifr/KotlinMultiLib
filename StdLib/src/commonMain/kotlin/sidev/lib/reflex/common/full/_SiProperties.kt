package sidev.lib.reflex.common.full

import sidev.lib.check.asNotNullTo
import sidev.lib.collection.iterator.nestedSequence
import sidev.lib.collection.iterator.nestedSequenceSimple
import sidev.lib.collection.iterator.skip
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
    get()= nestedSequence(classesTree){ cls: SiClass<*> -> cls.declaredMemberProperties.iterator() }
            as NestedSequence<SiProperty1<T, *>>

val <T: Any> SiClass<T>.nestedDeclaredMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= nestedSequence(classesTree, {
        it.returnType.classifier.asNotNullTo { cls: SiClass<*> -> cls.classesTree.iterator() }
    })
    { cls: SiClass<*> -> cls.declaredMemberProperties.iterator() } as NestedSequence<SiProperty1<T, *>>

val <T: Any> SiClass<T>.implementedMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= declaredMemberPropertiesTree.skip { it.isAbstract }

val <T: Any> SiClass<T>.implementedNestedMemberPropertiesTree: NestedSequence<SiProperty1<T, *>>
    get()= nestedDeclaredMemberPropertiesTree.skip { it.isAbstract }


val <T: Any> T.implementedPropertyValues: Sequence<Pair<SiProperty1<T, *>, Any?>>
    get(){
        return this::class.si.declaredMemberProperties.asSequence().map {
            val vals= (it as SiProperty1<T, Any?>).forceGet(this)
            Pair(it, vals)
        }
    }

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
val Any.implementedNestedPropertyValuesTree: NestedSequence<Pair<SiProperty1<Any, *>, Any?>>
    get()= nestedSequenceSimple<Pair<SiProperty1<Any, *>, Any?>>(implementedPropertyValuesTree.iterator()){
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

