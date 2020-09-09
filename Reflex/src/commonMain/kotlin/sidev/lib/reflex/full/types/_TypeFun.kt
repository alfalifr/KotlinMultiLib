package sidev.lib.reflex.full.types

import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiVariance
import sidev.lib.reflex.full.classesTree
import sidev.lib.reflex.full.isObjectArray
import sidev.lib.reflex.full.isPrimitiveArray
import sidev.lib.reflex.full.typesTree
import sidev.lib.reflex.native_.si
import kotlin.reflect.KType

//TODO <28 Agustus 2020> => Pengecekan typeParam pada Js msh blum bisa karena Kotlin/Js msh blum bisa refleksi.
/**
 * Menentukan apakah `this.extension` [KType] dapat diassign dengan nilai yg memiliki tipe [other].
 * Fungsi ini juga memperhitungkan type-arg variance.
 */
fun SiType.isAssignableFrom(other: SiType): Boolean{
/*
    prine("isAssignableFrom classifier= $classifier")
    prine("isAssignableFrom other.classifier= ${other.classifier}")
    prine("isAssignableFrom arguments= $arguments")
    prine("isAssignableFrom other.arguments= ${other.arguments}")
// */
    return when{
        (classifier as? SiClass<*>)?.isPrimitiveArray == true -> {
            when {
                (other.classifier as? SiClass<*>)?.isPrimitiveArray == true -> classifier == other.classifier
                (other.classifier as? SiClass<*>)?.isObjectArray == true ->
                    (classifier as SiClass<*>).arrayTypeArgument?.type == other.arguments.first().type //Karena array INVARIANT.
                else -> false
            }
        }
        (classifier as? SiClass<*>)?.isObjectArray == true -> {
            when {
                (other.classifier as? SiClass<*>)?.isObjectArray == true ->
                    classifier == other.classifier //Karena setiap array dg type-arg berbeda akan menghasilkan false.
                (other.classifier as? SiClass<*>)?.isPrimitiveArray == true ->
                    arguments.first().type == (other.classifier as SiClass<*>).arrayTypeArgument?.type //Karena array INVARIANT.
                else -> false
            }
        }
        else -> {
            val commonType= other.typesTree.find { it.classifier == this.classifier } ?: return false
            //Menentukan apakah `this.extension`.classifier ada pada supertype dari `other`.
            // Jika ada, maka ambil commonType-nya.

            when(classifier){
                is SiClass<*> -> { //Jika classifier adalah KClass<*>, maka tentukan apakah variance pada type-param udah sesuai dg commonType.arguments.
                    var isTypeArgCompatible= true
                    for((i, typeParam) in (classifier as SiClass<*>).typeParameters.withIndex()){
                        isTypeArgCompatible= isTypeArgCompatible && when(typeParam.variance){
                            SiVariance.INVARIANT -> arguments[i].type == commonType.arguments[i].type
                            SiVariance.OUT -> commonType.arguments[i].type != null && arguments[i].type?.isSuperTypeOf(commonType.arguments[i].type!!) ?: false
                            SiVariance.IN -> commonType.arguments[i].type != null && arguments[i].type?.isSubTypeOf(commonType.arguments[i].type!!) ?: false
                        }
                    }
                    isTypeArgCompatible
                }
                else -> true //Jika classifier KTypeParameter atau yg lainnya, maka return true karena pengecekan sudah terjadi pada proses pengambilan commonType.
            }
        }
    }
}

fun SiType.isAssignableFrom(value: Any?, deepInspect: Boolean= true): Boolean
        = when{
    deepInspect -> isAssignableFrom(value.inferredType)
    value != null -> classifier in value::class.si.classesTree
    else -> isMarkedNullable
}
