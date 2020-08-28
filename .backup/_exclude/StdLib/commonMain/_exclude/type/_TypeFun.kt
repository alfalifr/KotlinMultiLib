package sidev.lib.reflex.type
/*
import sidev.lib.console.prine
import sidev.lib.reflex.classesTree
import sidev.lib.reflex.inner.isSubtypeOf
import sidev.lib.reflex.inner.isSupertypeOf
import sidev.lib.reflex.isObjectArray
import sidev.lib.reflex.isPrimitiveArray
import sidev.lib.reflex.typesTree
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KVariance


/**
 * Menentukan apakah `this.extension` [KType] dapat diassign dengan nilai yg memiliki tipe [other].
 * Fungsi ini juga memperhitungkan type-arg variance.
 */
fun KType.isAssignableFrom(other: KType): Boolean{
/*
    prine("isAssignableFrom classifier= $classifier")
    prine("isAssignableFrom other.classifier= ${other.classifier}")
    prine("isAssignableFrom arguments= $arguments")
    prine("isAssignableFrom other.arguments= ${other.arguments}")
// */
    return when{
        (classifier as? KClass<*>)?.isPrimitiveArray == true -> {
            when {
                (other.classifier as? KClass<*>)?.isPrimitiveArray == true -> classifier == other.classifier
                (other.classifier as? KClass<*>)?.isObjectArray == true ->
                    (classifier as KClass<*>).arrayTypeArgument?.type == other.arguments.first().type //Karena array INVARIANT.
                else -> false
            }
        }
        (classifier as? KClass<*>)?.isObjectArray == true -> {
            when {
                (other.classifier as? KClass<*>)?.isObjectArray == true ->
                    classifier == other.classifier //Karena setiap array dg type-arg berbeda akan menghasilkan false.
                (other.classifier as? KClass<*>)?.isPrimitiveArray == true ->
                    arguments.first().type == (other.classifier as KClass<*>).arrayTypeArgument?.type //Karena array INVARIANT.
                else -> false
            }
        }
        else -> {
            val commonType= other.typesTree.find { it.classifier == this.classifier } ?: return false
            //Menentukan apakah `this.extension`.classifier ada pada supertype dari `other`.
            // Jika ada, maka ambil commonType-nya.

            when(classifier){
                is KClass<*> -> { //Jika classifier adalah KClass<*>, maka tentukan apakah variance pada type-param udah sesuai dg commonType.arguments.
                    var isTypeArgCompatible= true
                    for((i, typeParam) in (classifier as KClass<*>).typeParameters.withIndex()){
                        isTypeArgCompatible= isTypeArgCompatible && when(typeParam.variance){
                            KVariance.INVARIANT -> arguments[i].type == commonType.arguments[i].type
                            KVariance.OUT -> commonType.arguments[i].type != null && arguments[i].type?.isSupertypeOf(commonType.arguments[i].type!!) ?: false
                            KVariance.IN -> commonType.arguments[i].type != null && arguments[i].type?.isSubtypeOf(commonType.arguments[i].type!!) ?: false
                        }
                    }
                    isTypeArgCompatible
                }
                else -> true //Jika classifier KTypeParameter atau yg lainnya, maka return true karena pengecekan sudah terjadi pada proses pengambilan commonType.
            }
        }
    }
}

fun KType.isAssignableFrom(value: Any?, deepInspect: Boolean= true): Boolean
        = when{
    deepInspect -> isAssignableFrom(value.inferredType)
    value != null -> classifier in value::class.classesTree
    else -> isMarkedNullable
}

 */