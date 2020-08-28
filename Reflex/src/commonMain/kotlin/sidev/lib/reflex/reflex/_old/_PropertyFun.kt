package sidev.lib.reflex._old
/*
import sidev.lib.reflex.inner.declaredMemberProperties
import sidev.lib.reflex.inner.declaredMembers
import sidev.lib.reflex.inner.memberProperties
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.NestedIteratorSimple
import sidev.lib.collection.iterator.NestedIteratorSimpleImpl
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1


/*
==========================
Properties Tree
==========================
 */
/**
 * Properti yg mengembalikan [NestedSequence] yg berisi semua function dan property yg
 * terdapat di this [KClass] dan supertypes.
 */
val KClass<*>.declaredMembersTree: NestedSequence<KCallable<*>>
    get()= object : NestedSequence<KCallable<*>> {
        override fun iterator(): NestedIterator<KClass<*>, KCallable<*>>
                = object: NestedIteratorImpl<KClass<*>, KCallable<*>>(classesTree.iterator()){
            override val tag: String
                get() = "declaredMembersTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KCallable<*>>?
                    = nowInput.declaredMembers.iterator()
            override fun getInputIterator(nowOutput: KCallable<*>): Iterator<KClass<*>>? = null
        }
    }
/**
 * Properti yg mengembalikan [NestedSequence] yg berisi semua function dan property yg
 * terdapat di this [KClass] dan supertypes. Sequence juga berisi [KCallable] dari [KCallable]
 * jika returnType berupa object.
 */
val KClass<*>.nestedDeclaredMembersTree: NestedSequence<KCallable<*>>
    get()= object : NestedSequence<KCallable<*>> {
        override fun iterator(): NestedIterator<KClass<*>, KCallable<*>>
                = object: NestedIteratorImpl<KClass<*>, KCallable<*>>(classesTree.iterator()){
            override val tag: String
                get() = "declaredMembersTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KCallable<*>>?
                    = nowInput.declaredMembers.iterator()
            override fun getInputIterator(nowOutput: KCallable<*>): Iterator<KClass<*>>?
                    = (nowOutput.returnType.classifier as? KClass<*>)?.classesTree?.iterator()
        }
    }
/**
 * Properti yg mengembalikan [NestedSequence] yg berisi semua function dan property yg
 * terdapat di this [KClass]. Sequence juga berisi [KCallable] dari [KCallable]
 * jika returnType berupa object.
 */
val KClass<*>.nestedMembers: NestedSequence<KCallable<*>>
    get()= object : NestedSequence<KCallable<*>> {
        override fun iterator(): NestedIteratorSimple<KCallable<*>>
                = object: NestedIteratorSimpleImpl<KCallable<*>>(members.iterator()){
            override val tag: String
                get() = "nestedMembers"

            override fun getOutputIterator(nowInput: KCallable<*>): Iterator<KCallable<*>>?
                    = (nowInput.returnType.classifier as? KClass<*>)?.members?.iterator()
        }
    }

/** Sama dg [declaredMembersTree], namun tidak termasuk yg abstrak. */
val KClass<*>.implementedMembersTree: NestedSequence<KCallable<*>>
    get()= object : NestedSequence<KCallable<*>> {
        override fun iterator(): NestedIterator<KClass<*>, KCallable<*>>
                = object: NestedIteratorImpl<KClass<*>, KCallable<*>>(classesTree.iterator()){
            override val tag: String
                get() = "implementedMembersTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KCallable<*>>?
                    = nowInput.declaredMembers.iterator()
            override fun getInputIterator(nowOutput: KCallable<*>): Iterator<KClass<*>>? = null
            override fun skip(now: KCallable<*>): Boolean = now.isAbstract
        }
    }
/** Sama dg [nestedDeclaredMembersTree], namun tidak termasuk yg abstrak. */
val KClass<*>.nestedImplementedMembersTree: NestedSequence<KCallable<*>>
    get()= object : NestedSequence<KCallable<*>> {
        override fun iterator(): NestedIterator<KClass<*>, KCallable<*>>
                = object: NestedIteratorImpl<KClass<*>, KCallable<*>>(classesTree.iterator()){
            override val tag: String
                get() = "nestedImplementedMembersTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KCallable<*>>?
                    = nowInput.declaredMembers.iterator()
            override fun getInputIterator(nowOutput: KCallable<*>): Iterator<KClass<*>>?
                    = (nowOutput.returnType.classifier as? KClass<*>)?.classesTree?.iterator()
            override fun skip(now: KCallable<*>): Boolean = now.isAbstract
        }
    }

/**
 * Properti yg mengembalikan [NestedSequence] yg berisi semua properti yg
 * terdapat di `this.extension` [KClass]. Sequence juga termasuk properti dari properti jika tipenya
 * berupa object.
 */
val KClass<*>.nestedMemberProperties: NestedSequence<KProperty1<*, *>>
    get()= object :
        NestedSequence<KProperty1<*, *>> {
        override fun iterator(): NestedIteratorSimple<KProperty1<*, *>>
                = object: NestedIteratorSimpleImpl<KProperty1<*, *>>(memberProperties){
            override fun getOutputIterator(nowInput: KProperty1<*, *>): Iterator<KProperty1<*, *>>?
                    = (nowInput.returnType.classifier as? KClass<*>)?.memberProperties?.iterator()
        }
    }
/**
 * Properti yg mengembalikan [NestedSequence] yg berisi semua properti yg
 * terdapat di this [KClass] dan supertypes.
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
val KClass<*>.declaredMemberPropertiesTree: NestedSequence<KProperty1<Any, *>>
    get()= object :
        NestedSequence<KProperty1<Any, *>> {
        override fun iterator(): NestedIterator<KClass<*>, KProperty1<Any, *>>
                = object: NestedIteratorImpl<KClass<*>, KProperty1<Any, *>>(classesTree.iterator()){
            override val tag: String
                get() = "declaredPropertiesTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KProperty1<Any, *>>?
                    = nowInput.declaredMemberProperties.iterator() as Iterator<KProperty1<Any, *>>
            override fun getInputIterator(nowOutput: KProperty1<Any, *>): Iterator<KClass<*>>? = null
        }
    }


/** Sama dengan [declaredMemberPropertiesTree], ditambah isi dari properti jika properti merupakan object. */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
val KClass<*>.nestedDeclaredMemberPropertiesTree: NestedSequence<KProperty1<Any, *>>
    get()= object :
        NestedSequence<KProperty1<Any, *>> {
        override fun iterator(): NestedIterator<KClass<*>, KProperty1<Any, *>>
                = object: NestedIteratorImpl<KClass<*>, KProperty1<Any, *>>(classesTree.iterator()){
            override val tag: String
                get() = "nestedDeclaredPropertiesTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KProperty1<Any, *>>?
                    = nowInput.declaredMemberProperties.iterator() as Iterator<KProperty1<Any, *>>
            override fun getInputIterator(nowOutput: KProperty1<Any, *>): Iterator<KClass<*>>?
                    = (nowOutput.returnType.classifier as? KClass<*>)?.classesTree?.iterator()
        }
    }

/** Sama dengan [declaredMemberPropertiesTree], namun tidak termasuk abstract property. */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
val KClass<*>.implementedMemberPropertiesTree: NestedSequence<KProperty1<Any, *>>
    get()= object :
        NestedSequence<KProperty1<Any, *>> {
        override fun iterator(): NestedIterator<KClass<*>, KProperty1<Any, *>>
                = object: NestedIteratorImpl<KClass<*>, KProperty1<Any, *>>(classesTree.iterator()){
            override val tag: String
                get() = "declaredPropertiesTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KProperty1<Any, *>>?
                    = nowInput.declaredMemberProperties.iterator() as Iterator<KProperty1<Any, *>>
            override fun getInputIterator(nowOutput: KProperty1<Any, *>): Iterator<KClass<*>>? = null
            override fun skip(now: KProperty1<Any, *>): Boolean = now.isAbstract
        }
    }

/** Sama dengan [nestedDeclaredMemberPropertiesTree], namun tidak termasuk abstract property. */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
val KClass<*>.nestedImplementedMemberPropertiesTree: NestedSequence<KProperty1<Any, *>>
    get()= object :
        NestedSequence<KProperty1<Any, *>> {
        override fun iterator(): NestedIterator<KClass<*>, KProperty1<Any, *>>
                = object: NestedIteratorImpl<KClass<*>, KProperty1<Any, *>>(classesTree.iterator()){
            override val tag: String
                get() = "nestedDeclaredPropertiesTree"

            override fun getOutputIterator(nowInput: KClass<*>): Iterator<KProperty1<Any, *>>?
                    = nowInput.declaredMemberProperties.iterator() as Iterator<KProperty1<Any, *>>

            override fun getInputIterator(nowOutput: KProperty1<Any, *>): Iterator<KClass<*>>?
                    = (nowOutput.returnType.classifier as? KClass<*>)?.classesTree?.iterator()

            override fun skip(now: KProperty1<Any, *>): Boolean = now.isAbstract
        }
    }

 */