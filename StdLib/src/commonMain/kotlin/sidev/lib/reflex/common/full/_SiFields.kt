package sidev.lib.reflex.common.full

import sidev.lib.check.asNotNullTo
import sidev.lib.check.notNull
import sidev.lib.check.notNullTo
import sidev.lib.collection.iterator.nestedSequence
import sidev.lib.collection.iterator.nestedSequenceSimple
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.native.getIsAccessible
import sidev.lib.reflex.common.native.setIsAccessible
import sidev.lib.reflex.common.native.si
import sidev.lib.universal.structure.collection.sequence.NestedSequence


var SiField<*, *>.isAccessible: Boolean
    get()= descriptor.native.notNullTo { getIsAccessible(it) } ?: false
    set(v){ descriptor.native.notNull { setIsAccessible(it, v) } }

fun <T: Any, R> SiField<T, R>.forceGet(receiver: T): R {
    val initAccessible= isAccessible
    isAccessible= true
    val vals= get(receiver) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
    return vals
}

fun <T: Any, R> SiMutableField<T, R>.forceSet(receiver: T, value: R) {
    val initAccessible= isAccessible
    isAccessible= true
    set(receiver, value) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
}

val <T, R> SiField<T, R>.property: SiProperty1<T, R>
    get()= descriptor.host as SiProperty1<T, R>

val <T, R> SiMutableField<T, R>.property: SiMutableProperty1<T, R>
    get()= descriptor.host as SiMutableProperty1<T, R>


val <T: Any> SiClass<T>.declaredFields: Sequence<SiField<T, *>>
    get()= declaredMemberProperties.mapNotNull { it.backingField }

val <T: Any> SiClass<T>.declaredFieldsTree: NestedSequence<SiField<T, *>>
    get()= nestedSequence(classesTree){ cls: SiClass<*> -> cls.declaredFields.iterator() }
            as NestedSequence<SiField<T, *>>

val SiClass<*>.nestedDeclaredFieldsTree: NestedSequence<SiField<Any, *>>
    get()= nestedSequence<SiClass<*>, SiField<Any, *>>(classesTree, {
        it.type.classifier.asNotNullTo { cls: SiClass<*> -> cls.classesTree.iterator() }
    })
    { cls: SiClass<*> -> cls.declaredFields.iterator() as Iterator<SiField<Any, *>> } //as NestedSequence<SiProperty<T, *>>

/*
<23 Agustus 2020> => Gakda field yg abstrak.
val SiClass<*>.implementedFieldsTree: Sequence<SiField<*, *>>
    get()= implementedMemberPropertiesTree.mapNotNull { it.backingField }

val SiClass<*>.implementedNestedFieldsTree: Sequence<SiField<*, *>>
    get()= implementedNestedMemberPropertiesTree.mapNotNull { it.backingField }
 */

val <T: Any> T.fieldValues: Sequence<Pair<SiField<T, *>, Any?>>
    get(){
        return this::class.si.declaredFields.asSequence().map {
            val vals= (it as SiField<T, Any?>).forceGet(this)
            Pair(it, vals)
        }
    }

val <T: Any> T.fieldValuesTree: NestedSequence<Pair<SiField<T, *>, Any?>>
    get(){
        return nestedSequence(this::class.si.classesTree){ innerCls: SiClass<*> ->
            innerCls.declaredFields //.iterator()//.asSequence()
                .map {
                    val vals= (it as SiField<T, Any?>).forceGet(this)
                    Pair(it, vals)
                }
                .iterator()
        }
    }

val Any.nestedFieldValuesTree: NestedSequence<Pair<SiField<Any, *>, Any?>>
    get()= nestedSequenceSimple<Pair<SiField<Any, *>, Any?>>(fieldValuesTree.iterator()){
        it.second?.fieldValuesTree?.iterator()
    }