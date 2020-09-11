package sidev.lib.reflex.type
/*
import sidev.lib.check.notNull
import sidev.lib.collection.iterator.newIteratorSimple
import sidev.lib.collection.lazy_list.flattenToNested
import sidev.lib.console.prine
import sidev.lib.reflex.*
import sidev.lib.reflex.inner.createType
import sidev.lib.exception.UndefinedDeclarationExc
import sidev.lib.collection.iterator.NestedIterator
import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.NestedIteratorSimple
import sidev.lib.collection.iterator.NestedIteratorSimpleImpl
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.structure.data.type.LinkedTypeParameter
import kotlin.reflect.*


/*
==========================
KType - KTypeParameter - KTypeProjection
==========================
 */
/**
 * Mengambil [KTypeProjection] dari `this.extension` [KTypeParameter] dg [KType]
 * dari kelas sesungguhnya yg disimpulkan dari nilai properti yg dimiliki oleh [owner].
 */
fun KTypeParameter.getClassProjectionIn(owner: Any): KTypeProjection {
    if(owner::class.isArray && this in owner::class.typeParameters){ //Jika owner array dan `this` ada dalam list type-param nya.
        return if(owner::class.isObjectArray) KTypeProjection(variance, inferElementType(owner as Array<*>)) //Jika itu array object, maka lakukan inferasi tipe elemen.
        else owner::class.arrayTypeArgument //Jika array primitif, maka langsung ambil tipenya.
            ?: throw UndefinedDeclarationExc(undefinedDeclaration = owner::class, detailMsg = "Terdapat sebuah array primitif, namun tidak terdefinisi dalam bahasa Kotlin.")
    }
    for(prop in owner::class.nestedDeclaredMemberPropertiesTree){
        if((prop.returnType.classifier as? KClass<*>)?.isArray == true){ //Ada kasus spesial, yaitu array, di mana elemennya tidak ada pada properti.
            if(prop.returnType.arguments.find { it.type?.classifier == this } != null) //Cek apakah type-arg pada array sama dg type-param `this`.
                prop.forcedGet(owner).notNull { array ->
                    (prop.returnType.classifier as KClass<*>).typeParameters.first()
                        .getClassProjectionIn(array).also { if(it != KTypeProjection.STAR) return it }
                }
        } else if(this == prop.returnType.classifier)
            prop.forcedGet(owner).notNull { valueProjection ->
//                    prine("owner= $owner prop= $prop valueProjection= $valueProjection class= ${valueProjection::class}")
                val typeParamProjectionList= ArrayList<KTypeProjection>()
//                for(ownerTypeParam in owner::class.typeParameters)
                for(typeParam in valueProjection::class.typeParameters)
                    typeParam.getClassProjectionIn(valueProjection)
                        .notNull { typeParamProjectionList += it }
//                            .isNull { typeParamProjectionList += KTypeProjection.STAR }
                val type= valueProjection::class.createType(typeParamProjectionList, prop.returnType.isMarkedNullable)
                return KTypeProjection(variance, type)
            }
    }
    prine("""KTypeParameter.getProjectionIn() -> Tidak bisa mendapatkan proyeksi dari KTypeParameter: "$this", proyeksi akhir "KTypeProjection.STAR".""")
    return KTypeProjection.STAR
}

/** Mengambil hubungan nested antar [KTypeParameter] dalam satu [KClass]. */
fun KTypeParameter.getTypeParameterLink(clazz: KClass<*>): LinkedTypeParameter? {
    val targetTypeParams= clazz.typeParameters
    if(this !in targetTypeParams) return null

    val linkedTypeParamList= ArrayList<KTypeParameter>()
    for(typeArg in nestedUpperBoundTypeArguments){
        val typeParamItr= typeArg.type?.classifier
        if(typeParamItr in targetTypeParams && typeParamItr !in linkedTypeParamList)
            linkedTypeParamList += typeParamItr as KTypeParameter
    }
    return LinkedTypeParameter(this, linkedTypeParamList)
}

/** Sequence semua nested [KTypeProjection] dari `upperBounds`. `this.extension` upperBounds juga disertakan. */
val KTypeParameter.nestedUpperBoundTypeArguments: NestedSequence<KTypeProjection>
    get()= object : NestedSequence<KTypeProjection> {
        private val initUpperBounds= this@nestedUpperBoundTypeArguments.upperBounds
        override fun iterator(): NestedIterator<KType, KTypeProjection>
                = object : NestedIteratorImpl<KType, KTypeProjection>(initUpperBounds.iterator()){
            private var initOutputEmitionLimit= initUpperBounds.size
            private var emissionNumber= 0
            override fun getOutputIterator(nowInput: KType): Iterator<KTypeProjection>?
                    = if(emissionNumber++ < initOutputEmitionLimit) {
                newIteratorSimple(KTypeProjection(this@nestedUpperBoundTypeArguments.variance, nowInput))
            } else nowInput.arguments.iterator()
            override fun getInputIterator(nowOutput: KTypeProjection): Iterator<KType>?
                    = if(nowOutput.type != null) newIteratorSimple(nowOutput.type!!) else null
        }
    }

/**
 * Sequence semua nested [KTypeProjection] yg ada di dalam `this.extension`.
 * Properti ini mirip dg [nestedUpperBoundTypeArguments], namun yg dimaksud [KTypeProjection] di sini
 * adalah proyeksi yg sesungguhnya, bkn merupakan yg dideklarasikan pada sebuah kelas (upperBounds).
 */
val KTypeProjection.nestedProjections: NestedSequence<KTypeProjection>
    get()= object : NestedSequence<KTypeProjection> {
        override fun iterator(): NestedIteratorSimple<KTypeProjection>
                = object : NestedIteratorSimpleImpl<KTypeProjection>(this@nestedProjections){
            override fun getOutputIterator(nowInput: KTypeProjection): Iterator<KTypeProjection>?
                    = nowInput.type?.arguments?.iterator()
        }
    }
/**
 * Sama dg [KTypeProjection.nestedProjections]. `this.extension` juga diikutkan namun sbg [simpleTypeProjection].
 */
val KType.nestedProjections: NestedSequence<KTypeProjection>
    get()= object : NestedSequence<KTypeProjection> {
        override fun iterator(): NestedIteratorSimple<KTypeProjection>
                = object : NestedIteratorSimpleImpl<KTypeProjection>(this@nestedProjections.simpleTypeProjection){
            override fun getOutputIterator(nowInput: KTypeProjection): Iterator<KTypeProjection>?
                    = nowInput.type?.arguments?.iterator()
        }
    }
/**
 * Sama dg [KTypeProjection.nestedProjections]. `this.extension` juga diikutkan namun sbg [simpleTypeProjection].
 */
val KType.nestedProjectionsTree: NestedSequence<KTypeProjection>
    get()= object : NestedSequence<KTypeProjection> {
        override fun iterator(): NestedIteratorSimple<KTypeProjection>
                = object : NestedIteratorSimpleImpl<KTypeProjection>(this@nestedProjectionsTree.typesTree.map { it.simpleTypeProjection }.iterator()){
            override fun getOutputIterator(nowInput: KTypeProjection): Iterator<KTypeProjection>?
                    = nowInput.type?.arguments?.iterator()
        }
    }
val KType.simpleTypeProjection: KTypeProjection
    get()= KTypeProjection(KVariance.INVARIANT, this)


val KClass<*>.nestedTypeParameters: NestedSequence<KTypeParameter>
    get()= object : NestedSequence<KTypeParameter> {
        override fun iterator(): NestedIteratorSimple<KTypeParameter>
                = object : NestedIteratorSimpleImpl<KTypeParameter>(this@nestedTypeParameters.typeParameters){
            override fun getOutputIterator(nowInput: KTypeParameter): Iterator<KTypeParameter>? {
                val upperBoundSeq= nowInput.upperBounds.asSequence().map { it.classifier }
                val typeParamSeq= upperBoundSeq.filter { it is KTypeParameter } as Sequence<KTypeParameter>
                val classSeq= upperBoundSeq.filter { it is KClass<*> } as Sequence<KClass<*>>
                val typeSeqFromClass= classSeq.map { it.typeParameters.asSequence() }.flattenToNested()
                return (typeParamSeq + typeSeqFromClass).iterator()
            }
        }
    }


 */