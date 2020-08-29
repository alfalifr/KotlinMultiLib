package sidev.lib.reflex.native

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.check.asNotNullTo
import sidev.lib.check.notNullTo
import sidev.lib.collection.iterator.iteratorSimple
import sidev.lib.collection.iterator.nestedSequence
import sidev.lib.collection.iterator.nestedSequenceSimple
import sidev.lib.collection.sequence.emptyNestedSequence
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiProperty1
import sidev.lib.reflex.full.*
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import java.lang.constant.Constable
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.util.*

val <T: Any> Class<T>.leastParamConstructor: Constructor<T>
    get(){
        val constrs= constructors.also {
            if(it.isEmpty()) throw  NoSuchMemberExc(
                targetOwner = kotlin, expectedMember = "leastParamConstructor",
                msg = """Kelas: "$this" gak punya konstruktor public."""
            )
        }
        var constrRes= constrs.first()
        var paramCount= constrRes.parameterCount
        for(i in 1 until constrs.size){
            val constr= constrs[i]
            if(constr.parameterCount < paramCount){
                constrRes= constr
                paramCount= constr.parameterCount
                if(paramCount == 0) break //Karena gakda yg lebih sedikit dari 0
            }
        }
        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        return constrRes as Constructor<T>
    }


/**
 * Mengambil semua kelas yg masuk dalam pohon keturunan dari kelas `this.extension`, termasuk yg berupa kelas maupun interface.
 * Properti ini juga menyertakan `this.extension` dalam sequence.
 */
val Class<*>.classesTree: NestedSequence<Class<*>>
    get()= nestedSequenceSimple(this){ it.classes.iterator() }

/**
 * Mirip dg [classesTree], namun hanya superclass yg bkn brp interface.
 * Khusus untuk `this.extension`, walaupun merupakan anonymous class yg berasal dari instansisasi interface,
 * tetap masuk dalam sequence.
 */
val Class<*>.extendingClassesTree: NestedSequence<Class<*>>
    get()= nestedSequenceSimple(this){ it.classes.iterator() }

/** Mirip dg [classesTree], namun tidak menyertakan `this.extension` dalam sequence. */
val Class<*>.superclassesTree: NestedSequence<Class<*>>
    get()= nestedSequenceSimple<Class<*>>(classes.iterator()){ it.classes.iterator() }

/** Mirip dg [extendingClassesTree], namun tidak menyertakan `this.extension` dalam sequence. */
val Class<*>.extendingSuperclassesTree: NestedSequence<Class<*>>
    get()= superclass.notNullTo { supr ->
        nestedSequenceSimple<Class<*>>(supr){
            it.superclass.notNullTo { iteratorSimple(it) }
        }
    } ?: emptyNestedSequence()


val Class<*>.declaredFieldsTree: NestedSequence<Field>
    get()= nestedSequence(classesTree){ cls: Class<*> -> cls.declaredFields.iterator() }


val Class<*>.nestedDeclaredMemberPropertiesTree: NestedSequence<Field>
    get()= nestedSequence(classesTree, {
        iteratorSimple(it.type)
    })
    { cls: Class<*> -> cls.declaredFields.iterator() } //as NestedSequence<SiProperty1<T, *>>

fun <T> Field.forceGet(receiver: Any): T{
    val initAccessible= isAccessible
    isAccessible= true
    val vals= get(receiver) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
    return vals as T
}
fun <T> Field.forceSet(receiver: Any, value: T){
    val initAccessible= isAccessible
    isAccessible= true
    set(receiver, value) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
}

val Any.javaFieldValues: Sequence<Pair<Field, Any?>>
    get(){
        return this::class.java.declaredFields.asSequence().map {
            val vals= it.forceGet<Any?>(this)
            Pair(it, vals)
        }
    }

val Any.javaFieldValuesTree: NestedSequence<Pair<Field, Any?>>
    get(){
        return nestedSequence(this::class.java.classesTree){ innerCls: Class<*> ->
            innerCls.declaredFields //.iterator()//.asSequence()
                .map {
                    val vals= it.forceGet<Any?>(this)
                    Pair(it, vals)
                }
                .iterator()
        }
    }
val Any.javaNestedFieldValuesTree: NestedSequence<Pair<Field, Any?>>
    get()= nestedSequenceSimple<Pair<Field, Any?>>(javaFieldValuesTree.iterator()){
        it.second?.javaFieldValuesTree?.iterator()
    }
/*
val Class<*>.isCopySafe: Boolean
    get()= isPrimitive || this == String::class.java
            || Enum::class.java.isAssignableFrom(this)
 */

/*
val Class<*>.isCollection: Boolean
    get()= Collection::class.java.isAssignableFrom(this)
            || java.util.Collection::class.java.isAssignableFrom(this)

 */