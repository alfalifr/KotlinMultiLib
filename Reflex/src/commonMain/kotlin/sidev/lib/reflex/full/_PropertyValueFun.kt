package sidev.lib.reflex.full
/*
import sidev.lib.reflex._ReflexConst.K_PROPERTY_ARRAY_SIZE_STRING
import sidev.lib.reflex.inner.declaredMemberProperties
import sidev.lib.reflex.inner.memberProperties
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.renamedName
import sidev.lib.collection.iterator.NestedIteratorSimple
import sidev.lib.collection.iterator.NestedIteratorSimpleImpl
 */
import sidev.lib.reflex.SiMutableProperty1

/*
/*
==========================
Properties Tree - Value
==========================
 */
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
 */

/*
/** Mengambil semua properti berserta nilainya dari `this.extension` termasuk yg `private`. */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
val Any.implementedPropertiesValueMap: Sequence<Pair<KProperty1<Any, *>, Any?>>
    get()= object : Sequence<Pair<KProperty1<Any, *>, Any?>>{
        override fun iterator(): Iterator<Pair<KProperty1<Any, *>, Any?>>
                = object: Iterator<Pair<KProperty1<Any, *>, Any?>>{
            private val declaredPropsItr=
                this@implementedPropertiesValueMap::class.declaredMemberProperties.iterator() as Iterator<KProperty1<Any, *>>

            override fun hasNext(): Boolean = declaredPropsItr.hasNext()

            override fun next(): Pair<KProperty1<Any, *>, Any?> {
                val prop= declaredPropsItr.next()
                val value= if(prop.toString() != K_PROPERTY_ARRAY_SIZE_STRING)
                    prop.getter.forcedCall(this@implementedPropertiesValueMap)
                else (this@implementedPropertiesValueMap as Array<*>).size
                return Pair(prop, value)
            }
        }
    }
/** Sama dengan [implementedPropertiesValueMap], namun hanya nilai tanpa mapping dg [KProperty]. */
val Any.implementedPropertiesValue: Sequence<Any?>
    get()= object : Sequence<Any?>{
        override fun iterator(): Iterator<Any?>
                = object: Iterator<Any?>{
            private val declaredPropsItr= this@implementedPropertiesValue::class.declaredMemberProperties.iterator()

            override fun hasNext(): Boolean = declaredPropsItr.hasNext()

            override fun next(): Any? {
                val prop= declaredPropsItr.next()
                return if(prop.toString() != K_PROPERTY_ARRAY_SIZE_STRING)
                    prop.getter.forcedCall(this@implementedPropertiesValue)
                else (this@implementedPropertiesValue as Array<*>).size
            }
        }
    }

/** Sama dengan [implementedPropertiesValueMap], beserta properti dari properti. */
val Any.nestedImplementedPropertiesValueMap: NestedSequence<Pair<KProperty1<Any, *>, Any?>>
    get()= object :
        NestedSequence<Pair<KProperty1<Any, *>, Any?>> {
        override fun iterator(): NestedIteratorSimple<Pair<KProperty1<Any, *>, Any?>>
                = object : NestedIteratorSimpleImpl<Pair<KProperty1<Any, *>, Any?>>(
            this@nestedImplementedPropertiesValueMap.implementedPropertiesValueMap.iterator()
        ){
            override val tag: String
                get() = "nestedImplementedPropertiesValueMap"

            override fun getOutputIterator(nowInput: Pair<KProperty1<Any, *>, Any?>): Iterator<Pair<KProperty1<Any, *>, Any?>>?
                    = nowInput.second?.implementedPropertiesValueMap?.iterator()
        }
    }

/*
/** Sama dengan [nestedDeclaredPropertiesTree], namun tidak termasuk abstract property. */
val Any.implementedPropertiesValueMapTree: NestedSequence<Pair<KProperty1<*, *>, Any?>>
    get()= object: NestedSequence<Pair<KProperty1<*, *>, Any?>>{
        override fun iterator(): NestedIterator<Any?, Pair<KProperty1<*, *>, Any?>>
            = object: NestedIteratorImpl<Any?, Pair<KProperty1<*, *>, Any?>>(
                this@implementedPropertiesValueMapTree::class.declaredPropertiesTree.iterator()
            ){
            override fun getOutputIterator(nowInput: Any?): Iterator<Pair<KProperty1<*, *>, Any?>>? {}

            override fun getInputIterator(nowOutput: Pair<KProperty1<*, *>, Any?>): Iterator<Any?>? {}
        }
    }
*/
///*
/** Sama dg [implementedPropertiesValueMap], beserta semua properti `private` dari superclass. */
val Any.implementedPropertiesValueMapTree: Sequence<Pair<KProperty1<Any, *>, Any?>>
    get()= object : Sequence<Pair<KProperty1<Any, *>, Any?>>{
        override fun iterator(): Iterator<Pair<KProperty1<Any, *>, Any?>>
                = object: Iterator<Pair<KProperty1<Any, *>, Any?>>{
            private val declaredPropsItr= this@implementedPropertiesValueMapTree::class.implementedMemberPropertiesTree.iterator()

            override fun hasNext(): Boolean = declaredPropsItr.hasNext()

            override fun next(): Pair<KProperty1<Any, *>, Any?> {
                val prop= declaredPropsItr.next()
                val value= if(prop.toString() != K_PROPERTY_ARRAY_SIZE_STRING)
                    prop.getter.forcedCall(this@implementedPropertiesValueMapTree)
                else (this@implementedPropertiesValueMapTree as Array<*>).size
                return Pair(prop, value)
            }
        }
    }
// */

///*
/** Sama dg [implementedPropertiesValueMapTree], beserta semua properti dari properti, termasuk yg `private`. */
val Any.nestedImplementedPropertiesValueMapTree: NestedSequence<Pair<KProperty1<Any, *>, Any?>>
    get()= object:
        NestedSequence<Pair<KProperty1<Any, *>, Any?>> {
        override fun iterator(): NestedIteratorSimple<Pair<KProperty1<Any, *>, Any?>>
                = object: NestedIteratorSimpleImpl<Pair<KProperty1<Any, *>, Any?>>(
            this@nestedImplementedPropertiesValueMapTree.implementedPropertiesValueMapTree.iterator()
        ){
            override val tag: String
                get() = "nestedImplementedPropertiesValueMapTree"

            override fun getOutputIterator(nowInput: Pair<KProperty1<Any, *>, Any?>): Iterator<Pair<KProperty1<Any, *>, Any?>>?
                    = nowInput.second?.implementedPropertiesValueMapTree?.iterator()
        }
    }
// */


/** Sama dg [implementedPropertiesValueMapTree], namun tidak mengambil property yg `private`. */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
val Any.implementedAccesiblePropertiesValueMapTree: Sequence<Pair<KProperty1<Any, *>, Any?>>
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