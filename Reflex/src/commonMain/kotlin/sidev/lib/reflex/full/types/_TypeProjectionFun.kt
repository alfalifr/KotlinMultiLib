package sidev.lib.reflex.full.types

import sidev.lib.check.notNull
import sidev.lib.collection.findIndexed
import sidev.lib.collection.lazy_list.flattenToNested
import sidev.lib.console.prine
import sidev.lib.exception.UndefinedDeclarationExc
import sidev.lib.collection.iterator.NestedIterator
import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.NestedIteratorSimple
import sidev.lib.collection.iterator.NestedIteratorSimpleImpl
import sidev.lib.collection.iterator.iteratorSimple
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiTypeParameter
import sidev.lib.reflex.SiTypeProjection
import sidev.lib.reflex.SiVariance
import sidev.lib.reflex.core.ReflexCheck
import sidev.lib.reflex.core.createType
import sidev.lib.reflex.full.*
import sidev.lib.reflex.native_.si
import sidev.lib.collection.sequence.NestedSequence
import kotlin.jvm.JvmName
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
fun SiTypeParameter.getClassProjectionIn(owner: Any): SiTypeProjection {
    val ownerCls= owner::class.si
//    prine("SiTypeParameter.getClassProjectionIn() ownerCls= $ownerCls owner= $owner")
    if(ownerCls.isArray && this in ownerCls.typeParameters){ //Jika owner array dan `this` ada dalam list type-param nya.
        return if(ownerCls.isObjectArray) SiTypeProjection(
            variance,
            inferElementType(owner as Array<*>)
        ) //Jika itu array object, maka lakukan inferasi tipe elemen.
        else ownerCls.arrayTypeArgument //Jika array primitif, maka langsung ambil tipenya.
            ?: throw UndefinedDeclarationExc(undefinedDeclaration = owner::class, detailMsg = "Terdapat sebuah array primitif, namun tidak terdefinisi dalam bahasa Kotlin.")
    }
    for(prop in ownerCls.nestedDeclaredMemberPropertiesTree){
//        prine("SiTypeParameter.getClassProjectionIn() prop= $prop this= $this this == prop.returnType.classifier => ${this == prop.returnType.classifier}")
        if((prop.returnType.classifier as? SiClass<*>)?.isArray == true){ //Ada kasus spesial, yaitu array, di mana elemennya tidak ada pada properti.
            if(prop.returnType.arguments.find { it.type?.classifier == this } != null) //Cek apakah type-arg pada array sama dg type-param `this`.
                prop.forceGet(owner).notNull { array ->
                    (prop.returnType.classifier as SiClass<*>).typeParameters.first()
                        .getClassProjectionIn(array).also { if(it != SiTypeProjection.STAR) return it }
                }
        } else prop.forceGet(owner).notNull { valueProjection ->
            if(prop.returnType.classifier is SiTypeParameter
                && ReflexCheck.typeParamEquality(this, prop.returnType.classifier as SiTypeParameter, false)){
//                prine("owner= $owner prop= $prop valueProjection= $valueProjection class= ${valueProjection::class}")
                val typeParamProjectionList= ArrayList<SiTypeProjection>()
                for(typeParam in valueProjection::class.si.typeParameters){
//                    prine("SiTypeParameter.getClassProjectionIn() for typeParam= $typeParam valueProjection= $valueProjection")
                    typeParam.getClassProjectionIn(valueProjection)
                        .notNull { typeParamProjectionList += it }
                }
                //                            .isNull { typeParamProjectionList += KTypeProjection.STAR }
                val type= valueProjection::class.si.createType(typeParamProjectionList, prop.returnType.isMarkedNullable)
                return SiTypeProjection(variance, type)
            } else { //Jika type param nested, cari di returnType.arguments.
                prop.returnType.arguments.findIndexed { it.value.type?.classifier == this }.notNull {
                    val returnTypeParam= (prop.returnType.classifier as SiClass<*>).typeParameters[it.index] //Langsung di-cast jadi [SiClass] karena jelas yg punya typeParam hanya kelas, bkn typeParam lain.
                    return returnTypeParam.getClassProjectionIn(valueProjection)
                        .let{ SiTypeProjection(variance, it.type) }
                }
            }
        }

    }
    prine("""SiTypeParameter.getProjectionIn() -> Tidak bisa mendapatkan proyeksi dari SiTypeParameter: "$this", proyeksi akhir "SiTypeProjection.STAR".""")
    return SiTypeProjection.STAR
}

/** Mengambil hubungan nested antar [KTypeParameter] dalam satu [KClass]. */
fun SiTypeParameter.getTypeParameterLink(clazz: SiClass<*>): LinkedTypeParameter? {
    val targetTypeParams= clazz.typeParameters
    if(this !in targetTypeParams) return null

    val linkedTypeParamList= ArrayList<SiTypeParameter>()
    for(typeArg in nestedUpperBoundTypeArguments){
        val typeParamItr= typeArg.type?.classifier
        if(typeParamItr in targetTypeParams && typeParamItr !in linkedTypeParamList)
            linkedTypeParamList += typeParamItr as SiTypeParameter
    }
    return LinkedTypeParameter(this, linkedTypeParamList)
}

/** Sequence semua nested [KTypeProjection] dari `upperBounds`. `this.extension` upperBounds juga disertakan. */
val SiTypeParameter.nestedUpperBoundTypeArguments: NestedSequence<SiTypeProjection>
    get()= object : NestedSequence<SiTypeProjection> {
        private val initUpperBounds= this@nestedUpperBoundTypeArguments.upperBounds
        override fun iterator(): NestedIterator<SiType, SiTypeProjection>
                = object : NestedIteratorImpl<SiType, SiTypeProjection>(initUpperBounds.iterator()){
            private var initOutputEmitionLimit= initUpperBounds.size
            private var emissionNumber= 0
            override fun getOutputIterator(nowInput: SiType): Iterator<SiTypeProjection>?
                    = if(emissionNumber++ < initOutputEmitionLimit) {
                iteratorSimple(
                    SiTypeProjection(
                        this@nestedUpperBoundTypeArguments.variance,
                        nowInput
                    )
                )
            } else nowInput.arguments.iterator()
            override fun getInputIterator(nowOutput: SiTypeProjection): Iterator<SiType>?
                    = if(nowOutput.type != null) iteratorSimple(nowOutput.type!!) else null
        }
    }

/**
 * Sequence semua nested [KTypeProjection] yg ada di dalam `this.extension`.
 * Properti ini mirip dg [nestedUpperBoundTypeArguments], namun yg dimaksud [KTypeProjection] di sini
 * adalah proyeksi yg sesungguhnya, bkn merupakan yg dideklarasikan pada sebuah kelas (upperBounds).
 */
val SiTypeProjection.nestedProjections: NestedSequence<SiTypeProjection>
    get()= object : NestedSequence<SiTypeProjection> {
        override fun iterator(): NestedIteratorSimple<SiTypeProjection>
                = object : NestedIteratorSimpleImpl<SiTypeProjection>(this@nestedProjections){
            override fun getOutputIterator(nowInput: SiTypeProjection): Iterator<SiTypeProjection>?
                    = nowInput.type?.arguments?.iterator()
        }
    }
/**
 * Sama dg [KTypeProjection.nestedProjections]. `this.extension` juga diikutkan namun sbg [simpleTypeProjection].
 */
@get:JvmName("nestedProjections")
val SiType.nestedProjections: NestedSequence<SiTypeProjection>
    get()= object : NestedSequence<SiTypeProjection> {
        override fun iterator(): NestedIteratorSimple<SiTypeProjection>
                = object : NestedIteratorSimpleImpl<SiTypeProjection>(this@nestedProjections.simpleTypeProjection){
            override fun getOutputIterator(nowInput: SiTypeProjection): Iterator<SiTypeProjection>?
                    = nowInput.type?.arguments?.iterator()
        }
    }
/**
 * Sama dg [KTypeProjection.nestedProjections]. `this.extension` juga diikutkan namun sbg [simpleTypeProjection].
 */
@get:JvmName("nestedProjectionsTree")
val SiType.nestedProjectionsTree: NestedSequence<SiTypeProjection>
    get()= object : NestedSequence<SiTypeProjection> {
        override fun iterator(): NestedIteratorSimple<SiTypeProjection>
                = object : NestedIteratorSimpleImpl<SiTypeProjection>(this@nestedProjectionsTree.typesTree.map { it.simpleTypeProjection }.iterator()){
            override fun getOutputIterator(nowInput: SiTypeProjection): Iterator<SiTypeProjection>?
                    = nowInput.type?.arguments?.iterator()
        }
    }

@get:JvmName("simpleTypeProjection")
val SiType.simpleTypeProjection: SiTypeProjection
    get()= SiTypeProjection(SiVariance.INVARIANT, this)


@get:JvmName("nestedTypeParameters")
val SiClass<*>.nestedTypeParameters: NestedSequence<SiTypeParameter>
    get()= object : NestedSequence<SiTypeParameter> {
        override fun iterator(): NestedIteratorSimple<SiTypeParameter>
                = object : NestedIteratorSimpleImpl<SiTypeParameter>(this@nestedTypeParameters.typeParameters){
            override fun getOutputIterator(nowInput: SiTypeParameter): Iterator<SiTypeParameter>? {
                val upperBoundSeq= nowInput.upperBounds.asSequence().map { it.classifier }
                val typeParamSeq= upperBoundSeq.filter { it is SiTypeParameter } as Sequence<SiTypeParameter>
                val classSeq= upperBoundSeq.filter { it is SiClass<*> } as Sequence<SiClass<*>>
                val typeSeqFromClass= classSeq.map { it.typeParameters.asSequence() }.flattenToNested()
                return (typeParamSeq + typeSeqFromClass).iterator()
            }
        }
    }


