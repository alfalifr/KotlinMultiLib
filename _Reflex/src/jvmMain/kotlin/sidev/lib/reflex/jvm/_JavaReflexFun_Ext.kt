@file:JvmName("_JavaReflexFun_Ext")
package sidev.lib.reflex.jvm

import sidev.lib._config_.SidevLibConfig
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.ChangeLog
import sidev.lib.annotation.Unsafe
import sidev.lib.check.notNullTo
import sidev.lib.collection.iterator.LeveledNestedIterator
import sidev.lib.collection.iterator.LeveledNestedIteratorSimpleImpl
import sidev.lib.collection.iterator.iteratorSimple
import sidev.lib.collection.sequence.*
import sidev.lib.exception.NoSuchMemberExc
import sidev.lib.reflex.full.*
import sidev.lib.console.prine
import sidev.lib.reflex.clazz
import sidev.lib.reflex.native_.CompatibilityUtil
import sidev.lib.structure.data.value.LeveledValue
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

/*
19 Feb 2021 -> Dipindah ke StdLib karena potesial untuk digunakan sering.
@get:JvmName("isPrimitiveWrapper")
val Class<*>.isPrimitiveWrapper: Boolean get()= //_JavaReflexFun_java.isPrimitiveWrapper(this)
/// *
    when(this){
        Int::class.javaObjectType -> true
        Long::class.javaObjectType -> true
//        String::class.javaObjectType -> true
        Float::class.javaObjectType -> true
        Double::class.javaObjectType -> true
        Char::class.javaObjectType -> true
        Short::class.javaObjectType -> true
        Boolean::class.javaObjectType -> true
        Byte::class.javaObjectType -> true
        else -> false
/*
        Integer::class.javaObjectType -> true
        java.lang.Long::class.javaObjectType -> true
        java.lang.Float::class.javaObjectType -> true
        java.lang.Double::class.javaObjectType -> true
        Character::class.javaObjectType -> true
        java.lang.Short::class.javaObjectType -> true
        java.lang.Boolean::class.javaObjectType -> true
        java.lang.Byte::class.javaObjectType -> true
        else -> false
*/
    }
// */

val Class<*>.primitiveClass: Class<*>? get()= //_JavaReflexFun_java.isPrimitiveWrapper(this)
///*
    when(this){
        Int::class.javaObjectType -> Int::class.javaPrimitiveType
        Long::class.javaObjectType -> Long::class.javaPrimitiveType
        Float::class.javaObjectType -> Float::class.javaPrimitiveType
        Double::class.javaObjectType -> Double::class.javaPrimitiveType
        Char::class.javaObjectType -> Char::class.javaPrimitiveType
        Short::class.javaObjectType -> Short::class.javaPrimitiveType
        Boolean::class.javaObjectType -> Boolean::class.javaPrimitiveType
        Byte::class.javaObjectType -> Byte::class.javaPrimitiveType
        else -> null
    }

// */
val Class<*>.objectClass: Class<*> get()= when(this){
    Int::class.javaPrimitiveType -> Int::class.javaObjectType
    Long::class.javaPrimitiveType -> Long::class.javaObjectType
    Float::class.javaPrimitiveType -> Float::class.javaObjectType
    Double::class.javaPrimitiveType -> Double::class.javaObjectType
    Char::class.javaPrimitiveType -> Char::class.javaObjectType
    Short::class.javaPrimitiveType -> Short::class.javaObjectType
    Boolean::class.javaPrimitiveType -> Boolean::class.javaObjectType
    Byte::class.javaPrimitiveType -> Byte::class.javaObjectType
    else -> this
}
/**
 * Mengecek apakah `this.extension` merupakan tipe dasar,
 * yaitu tipe primitif, primitive wrapper, atau String.
 *
 * Return `true` jika `this.extension` merupakan [Class.isPrimitive]
 * atau [isPrimitiveWrapper] atau merupakan [String].
 */
@get:JvmName("isBaseType")
val Class<*>.isBaseType: Boolean
    get()= isPrimitive || isPrimitiveWrapper || this == String::class.java

///*
@get:JvmName("isCopySafe")
val Class<*>.isCopySafe: Boolean
    get()= isBaseType //isPrimitive || isPrimitiveWrapper || this == String::class.java
            || Enum::class.java.isAssignableFrom(this)
// */

///*
@get:JvmName("isCollection")
val Class<*>.isCollection: Boolean
    get()= Collection::class.java.isAssignableFrom(this)
            || java.util.Collection::class.java.isAssignableFrom(this)

@get:JvmName("isMap")
val Class<*>.isMap: Boolean
    get()= Map::class.java.isAssignableFrom(this)
            || java.util.Map::class.java.isAssignableFrom(this)
// */

@ChangeLog("Senin, 28 Sep 2020", "Penambahan kompatibilitas untuk Java 7")
@get:JvmName("leastParamConstructor")
val <T: Any> Class<T>.leastParamConstructor: Constructor<T>
    get(){
        val constrs= constructors.also {
            if(it.isEmpty()) throw  NoSuchMemberExc(
                targetOwner = kotlin, expectedMember = "leastParamConstructor",
                msg = """Kelas: "$this" gak punya konstruktor public."""
            )
        }
        var constrRes= constrs.first()
        var paramCount= if(SidevLibConfig.java7SupportEnabled) CompatibilityUtil.Java7.getParameterCount(constrRes)
            else constrRes.parameterCount

        for(i in 1 until constrs.size){
            val constr= constrs[i]
            val paramCountItr= if(SidevLibConfig.java7SupportEnabled) CompatibilityUtil.Java7.getParameterCount(constr)
                else constr.parameterCount
            if(paramCountItr < paramCount){
                constrRes= constr
                paramCount= paramCountItr //constr.parameterCount
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
@get:JvmName("classesTree")
val Class<*>.classesTree: NestedSequence<Class<*>>
    get()= nestedSequenceSimple(this){ it.classes.iterator() }

/**
 * Mirip dg [classesTree], namun hanya superclass yg bkn brp interface.
 * Khusus untuk `this.extension`, walaupun merupakan anonymous class yg berasal dari instansisasi interface,
 * tetap masuk dalam sequence.
 */
@get:JvmName("extendingClassesTree")
val Class<*>.extendingClassesTree: NestedSequence<Class<*>>
    get()= nestedSequenceSimple(this){ it.classes.iterator() }

/** Mirip dg [classesTree], namun tidak menyertakan `this.extension` dalam sequence. */
@get:JvmName("superclassesTree")
val Class<*>.superclassesTree: NestedSequence<Class<*>>
    get()= nestedSequenceSimple<Class<*>>(classes.asSequence()){ it.classes.iterator() }

/** Mirip dg [extendingClassesTree], namun tidak menyertakan `this.extension` dalam sequence. */
@get:JvmName("extendingSuperclassesTree")
val Class<*>.extendingSuperclassesTree: NestedSequence<Class<*>>
    get()= superclass.notNullTo { supr ->
        nestedSequenceSimple<Class<*>>(supr){
            it.superclass.notNullTo { iteratorSimple(it) }
        }
    } ?: emptyNestedSequence()


@get:JvmName("declaredFieldsTree")
val Class<*>.declaredFieldsTree: NestedSequence<Field>
    get()= nestedSequence(classesTree){ cls: Class<*> -> cls.declaredFields.iterator() }


@get:JvmName("nestedDeclaredMemberPropertiesTree")
val Class<*>.nestedDeclaredMemberPropertiesTree: NestedSequence<Field>
    get()= nestedSequence(classesTree, {
        iteratorSimple(it.type)
    })
    { cls: Class<*> -> cls.declaredFields.iterator() } //as NestedSequence<SiProperty1<T, *>>


inline fun <reified T: Any> Class<*>.getFieldOf(name: String= ""): Field? = getFieldOf(T::class.java, name)
fun <T: Any> Class<*>.getFieldOf(clazz: Class<T>, name: String= ""): Field?
    = clazz.declaredFieldsTree.find { clazz.isAssignableFrom(it.type) && (name.isBlank() || it.name == name) }

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

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Method.forceCall(receiver: Any, vararg args: Any?): T{
    val initAccessible= isAccessible
    isAccessible= true
    val vals= invoke(receiver, *args) // } catch (e: Throwable){ UNINITIALIZED_VALUE as V }
    isAccessible= initAccessible
    return vals as T
}

@get:JvmName("javaFieldValues")
val Any.javaFieldValues: Sequence<Pair<Field, Any?>>
    get(){
        return this::class.java.declaredFields.asSequence().map {
            val vals= it.forceGet<Any?>(this)
            Pair(it, vals)
        }
    }

@get:JvmName("javaFieldValuesTree")
val Any.javaFieldValuesTree: NestedSequence<Pair<Field, Any?>>
    get(){
        return nestedSequence(this::class.java.classesTree){ innerCls: Class<*> ->
            innerCls.declaredFields.asSequence() //.iterator()//.asSequence()
                .map {
                    val vals= try { it.forceGet<Any?>(this) }
                        catch (e: Exception){ null } //Jika ternyata ada bbrp field yg aksesnya dilarang.
                    Pair(it, vals)
                }
                .iterator()
        }
    }

@Unsafe("Dapat menyebabkan infinite loop saat terdapat cyclic field reference.")
@get:JvmName("unsafeJavaNestedFieldValuesTree")
val Any.unsafeJavaNestedFieldValuesTree: NestedSequence<Pair<Field, Any?>>
    get()= nestedSequenceSimple<Pair<Field, Any?>>(javaFieldValuesTree){
        it.second?.javaFieldValuesTree?.iterator()
    }
@get:JvmName("javaNestedFieldValuesTree")
val Any.javaNestedFieldValuesTree: NestedSequence<Pair<Field, Any?>>
    get() {
        val parentMap= mutableMapOf<Int, MutableList<Any>>()
        val linearSeq= javaFieldValuesTree
        //1. Buat leveled sequence dg iterator yang melakukan pengecekan cyclic field value reference.
        val leveledSeq= object: LeveledNestedSequence<Pair<Field, Any?>> {
            override var currentLevel: Int= 0
            fun setCurrLevel(level: Int){ currentLevel= level }

            override fun iterator(): LeveledNestedIterator<*, Pair<Field, Any?>> =
                object: LeveledNestedIteratorSimpleImpl<Pair<Field, Any?>>(linearSeq.iterator()){
                    init { parentMap.clear() }
                    fun Any?.valueEmittedBefore(): Boolean {
                        if(this == null)
                            return false
                        var exists= false
                        for(i in 0 until currentLevel){
//                            prine("implementedNestedPropertyValuesTree any it= $it nowInput= $nowInput")
                            if(parentMap[i]!!.any { it == this }){
                                exists= true
                                break
                            }
                        }
                        return exists
                    }
                    override fun skip(now: LeveledValue<Pair<Field, Any?>>): Boolean = now.value.second.valueEmittedBefore()
                    override fun getOutputValueIterator(nowInput: Pair<Field, Any?>): Iterator<Pair<Field, Any?>>? {
                        val exists= nowInput.second.valueEmittedBefore()
//                        prine("implementedNestedPropertyValuesTree exists= $exists nowInput= $nowInput currLevel= $currentLevel")
                        if(!exists && nowInput.second != null && !nowInput::class.isBaseType)
                            (parentMap[currentLevel] ?: mutableListOf<Any>().also { parentMap[currentLevel]= it }) += nowInput.second!!
                        //pair: Pair<SiProperty1<Any, *>, Any?> ->
                        return if(exists) null else nowInput.second?.javaFieldValuesTree?.iterator()
                    }

                    override fun onNext(currentNext: LeveledValue<Pair<Field, Any?>>) {
//                        prine("implementedNestedPropertyValuesTree onNext() currentNext= $currentNext")
                        super.onNext(currentNext)
                        setCurrLevel(currentLevel)
                    }
                }
        }
        //Bungkus [leveledSeq] menjadi sequence yang mengeluarkan hanya nilai.
        return leveledSeq.asValueSequence()
    }

/**
 * Sama dg [unsafeJavaNestedFieldValuesTree], namun tidak mengambil property
 * dari tipe data [Collection] dan [Map] karena menganggap bahwa yg penting dari tipe data tersebut
 * adalah isinya, bkn internal state-nya.
 */
@Unsafe("Dapat menyebabkan infinite loop saat terdapat cyclic field reference.")
@get:JvmName("unsafeJavaNonExhaustiveNestedFieldValuesTree")
val Any.unsafeJavaNonExhaustiveNestedFieldValuesTree: NestedSequence<Pair<Field, Any?>>
    get()= nestedSequenceSimple<Pair<Field, Any?>>(javaFieldValuesTree){
        var cls= it.first.type
        //Jika ternyata field bertipe Object, bisa jadi itu generic, maka assign `cls` dg kelas dari it.second scr langsung.
        if(cls == Object::class.java && it.second != null)
            cls= it.second!!.javaClass

        if(!cls.isCollection && !cls.isMap && !cls.isBaseType)
            it.second?.javaFieldValuesTree?.iterator()
        else null
    }
/**
 * Sama dg [javaNestedFieldValuesTree], namun tidak mengambil property
 * dari tipe data [Collection] dan [Map] karena menganggap bahwa yg penting dari tipe data tersebut
 * adalah isinya, bkn internal state-nya.
 */
@get:JvmName("javaNonExhaustiveNestedFieldValuesTree")
val Any.javaNonExhaustiveNestedFieldValuesTree: NestedSequence<Pair<Field, Any?>>
    get() {
//        prine("javaNonExhaustiveNestedFieldValuesTree this::class.java.fields= ${this::class.java.fields.joinToString()}")
        val parentMap= mutableMapOf<Int, MutableList<Any>>()
        val linearSeq= javaFieldValuesTree
        //1. Buat leveled sequence dg iterator yang melakukan pengecekan cyclic field value reference.
        val leveledSeq= object: LeveledNestedSequence<Pair<Field, Any?>> {
            override var currentLevel: Int= 0
            fun setCurrLevel(level: Int){ currentLevel= level }

            override fun iterator(): LeveledNestedIterator<*, Pair<Field, Any?>> =
                object: LeveledNestedIteratorSimpleImpl<Pair<Field, Any?>>(linearSeq.iterator()){
                    init { parentMap.clear() }
                    fun Any?.valueEmittedBefore(): Boolean {
                        if(this == null)
                            return false
                        var exists= false
//                        prine("implementedNestedPropertyValuesTree currLevel= $currentLevel this= $this")
                        for(i in 0 until currentLevel){
//                                    prine("implementedNestedPropertyValuesTree any it= $it nowInput= $this")
                            if(parentMap[i]!!.any { it == this }){
                                exists= true
                                break
                            }
                        }
                        return exists
                    }
                    override fun skip(now: LeveledValue<Pair<Field, Any?>>): Boolean = now.value.second.valueEmittedBefore()
                    override fun getOutputValueIterator(nowInput: Pair<Field, Any?>): Iterator<Pair<Field, Any?>>? {
                        val exists= nowInput.second.valueEmittedBefore()
//                        prine("implementedNestedPropertyValuesTree exists= $exists nowInput= $nowInput currLevel= $currentLevel")
                        if(!exists && nowInput.second != null && !nowInput::class.isBaseType)
                            (parentMap[currentLevel] ?: mutableListOf<Any>().also { parentMap[currentLevel]= it }) += nowInput.second!!
                        //pair: Pair<SiProperty1<Any, *>, Any?> ->
                        return if(exists) null else {
                            var cls= nowInput.first.type
                            //Jika ternyata field bertipe Object, bisa jadi itu generic, maka assign `cls` dg kelas dari it.second scr langsung.
                            if(cls == Object::class.java && nowInput.second != null)
                                cls= nowInput.second!!.javaClass

//                            prine("cls= $cls cls.isPrimitiveWrapper= ${cls.isPrimitiveWrapper} nowInput.second= ${nowInput.second}")

                            if(!cls.isCollection && !cls.isMap && !cls.isBaseType)
                                nowInput.second?.javaFieldValuesTree?.iterator()
                            else null
                        }
                    }

                    override fun onNext(currentNext: LeveledValue<Pair<Field, Any?>>) {
//                        prine("implementedNestedPropertyValuesTree onNext() currentNext= $currentNext")
                        super.onNext(currentNext)
                        setCurrLevel(currentLevel)
                    }
                }
        }
        //Bungkus [leveledSeq] menjadi sequence yang mengeluarkan hanya nilai.
        return leveledSeq.asValueSequence()
    }
/*
@get:JvmName("javaFieldValuesTreeMap")
val Any.javaFieldValuesTreeMap: Map<Field, Any?>
    get()= mutableMapOf<Field, Any?>().apply {
        javaFieldValuesTree.forEach { this[it.first]= it.second }
    }

@Unsafe("Dapat menyebabkan infinite loop saat terdapat cyclic field reference.")
@get:JvmName("unsafeJavaNestedFieldValuesTreeMap")
val Any.unsafeJavaNestedFieldValuesTreeMap: Map<Field, Any?>
    get()= mutableMapOf<Field, Any?>().apply {
        unsafeJavaNestedFieldValuesTree.forEach { this[it.first]= it.second }
    }
@get:JvmName("javaNestedFieldValuesTreeMap")
val Any.javaNestedFieldValuesTreeMap: Map<Field, Any?>
    get()= mutableMapOf<Field, Any?>().apply {
        javaNestedFieldValuesTree.forEach { this[it.first]= it.second }
    }
 */

/*
==========================
Named Map - Berguna untuk interoperability terhadap Flutter Dart, yaitu dengan menge-pass map String-Any?
==========================
 */
/**
 * Mengubah data `this.extension` dg tipe `Object` menjadi [Map] yang berisi semua atribut dg tipe `primitive`.
 * Jika tipe data field berupa `Object`, maka nilai field tersebut berupa [Map] yang berisi nilai nested field.
 */
@get:JvmName("javaNestedPrimitiveFieldValuesTreeNamedMap")
val Any.javaNestedPrimitiveFieldValuesTreeNamedMap: Map<String, Any?>
    get()= mutableMapOf<String, Any?>().apply {
//        val a: MutableMap
//        var isItrInNested= false
        var currLevel= 0
        val mapHierarchy= mutableListOf<MutableMap<String, Any?>>()
//        val allMaps= mutableMapOf<Int, MutableMap<String, Any?>>()
//        var parentMap: MutableMap<String, Any?>? = null //= mutableMapOf()
        var map: MutableMap<String, Any?> = this //= mutableMapOf()
        mapHierarchy += map

        var currField: Field?= null //Untuk menyimpan data ttg field sebelumnya untuk mendapatkan data mengenai field parent.

        this@javaNestedPrimitiveFieldValuesTreeNamedMap.javaNonExhaustiveNestedFieldValuesTree.withLevel()
            .forEach { (level, pair) ->
                val field= pair.first
                val value= pair.second
                val cls= field.type

                if(level != currLevel){
                    if(level > currLevel) { //Jika field skrg merupakan nested dari field sebelumnya.
                        val newMap= mutableMapOf<String, Any?>().also {
                            mapHierarchy += it
                        }
                        map["@${currField!!.name}"]= newMap
                        map= newMap
                    } else {
                        for(i in currLevel downTo level +1)
                            mapHierarchy.removeLast()
                        map= mapHierarchy.last()
                    }
                    currLevel= level
                }
                if(cls.isBaseType)
                    map[field.name]= value
                currField= field
            }
    }

@get:JvmName("javaPrimitiveFieldValuesTreeNamedMap")
val Any.javaPrimitiveFieldValuesTreeNamedMap: Map<String, Any?>
    get()= mutableMapOf<String, Any?>().apply {
        this@javaPrimitiveFieldValuesTreeNamedMap.javaFieldValuesTree.forEach {
            if(it.second == null || it.second!!.clazz.isPrimitive || it.second is String)
                this[it.first.name]= it.second
        }
    }
