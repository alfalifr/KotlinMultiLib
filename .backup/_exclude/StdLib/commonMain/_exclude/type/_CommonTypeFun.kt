package sidev.lib.reflex.type
/*
import sidev.lib.check.contentEquals
import sidev.lib.collection.lazy_list.flattenToNested
import sidev.lib.collection.leveledIterator
import sidev.lib.collection.sequence.withLevel
import sidev.lib.collection.string
import sidev.lib.collection.toArrayOf
import sidev.lib.console.prine
import sidev.lib.reflex.classesTree
import sidev.lib.reflex.inner.createType
import sidev.lib.universal.`val`.SuppressLiteral
import sidev.lib.type.Null
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import sidev.lib.collection.intersect


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun getCommonClass(vararg classes: KClass<*>): KClass<*> {
    if(classes.isEmpty())
        throw NoSuchElementException("""Tidak bisa mendapatkan common-class dari list "classes" kosong.""")
    val usedClasses= classes.asSequence().filter { it != Null::class }.toList() //Kelas Null tidak dihitung karena hanya sbg representasi pada operasi [getCommonType].
    val superClassList= usedClasses.first().classesTree.withLevel().toMutableList().distinct()
        .sortedBy { it.level }.map { it.value } as MutableCollection<KClass<*>>

    for(i in 1 until usedClasses.size)
        superClassList intersect usedClasses[i].classesTree.toMutableList()
    return superClassList.first()
}
fun getCommonClass(vararg any: Any): KClass<*> = getCommonClass(*any.toArrayOf { it::class })

fun getCommonType(vararg types: KType): KType {
//    prine("==types= ${types.string}")
    if(types.isEmpty())
        throw NoSuchElementException("""Tidak bisa mendapatkan common-type dari list "types" kosong.""")

    val isMarkedNullable= types.find { it.isMarkedNullable }?.isMarkedNullable ?: false

    val usedTypes= types.asSequence().filter { it.classifier != Null::class }.toList()
    val classesArray= types.mapNotNull { it.classifier as? KClass<*> }.toTypedArray()
    val commonClass= getCommonClass(*classesArray)

    val commonTypeArgs: MutableList<KTypeProjection> = mutableListOf()
    if(commonClass.typeParameters.isNotEmpty()){ //Agar tidak terjadi komputasi mahal yg melibatkan [KTypeProjection.nestedProjectionsTree].
        val foundTypeArgs: MutableList<List<KTypeProjection>> = mutableListOf()
        for(typeArg in usedTypes.map { it.nestedProjectionsTree }.asSequence().flattenToNested()){
//        prine("typeArg= $typeArg getCommonType() -> typeArg.type?.classifier= ${typeArg.type?.classifier} commonClass= $commonClass")
            if(typeArg.type?.classifier == commonClass){
                foundTypeArgs += typeArg.type!!.arguments
//            break
            }
        }

//    prine("==== foundTypeArgs= $foundTypeArgs")
        //Jika foundTypeArgs == types, maka typeArg disimpulkan menjadi supertype dari commonClass.
        // Contoh kasus ini adalah Int, String, dan Double di mana common-class adalah Comparable<T> di mana T merupakan cyclic type-param.
        // Jika scr program, foundTypeArgs == [Int, String, Double] di mana jika dipanggil fungsi [getCommonType] ini lagi,
        // maka akan terjadi infinite loop.
        if(foundTypeArgs.flatten().mapNotNull{ it.type }.contentEquals(usedTypes))
            commonTypeArgs += commonClass.supertypes[0].simpleTypeProjection
        else{
            for(typeArgs in foundTypeArgs.leveledIterator){
                if(typeArgs.isEmpty()) continue
                commonTypeArgs += getCommonType(*typeArgs.toArrayOf { it.type }).simpleTypeProjection
            }
        }
    }
    return commonClass.createType(commonTypeArgs, isMarkedNullable)
}
fun getCommonType(vararg any: Any?): KType = getCommonType(*any.toArrayOf { it.inferredType.type })


 */