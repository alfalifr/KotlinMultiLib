package sidev.lib.reflex.common.full.types

import sidev.lib.console.prine
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.SiClassifier
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.SiTypeParameter
import sidev.lib.reflex.common.full.isSubclassOf
import sidev.lib.reflex.common.full.isSuperclassOf


/**
 * Menentukan apakah `this.extension` [SiTypeParameter] merupakan subtype dari [base]
 * berdasarkan [SiTypeParameter.upperBounds]. Fungsi ini menggunakan upperBounds karena
 * tidak mungkin membandingkan menggunakan tipe sesungguhnya mengingat tidak terdapat type-projection.
 */
fun SiTypeParameter.isUpperBoundSubTypeOf(base: SiTypeParameter): Boolean{
    return when(val baseBound= base.upperBounds.first().classifier){
        is SiClass<*> -> isUpperBoundSubTypeOf(baseBound)
        is SiTypeParameter -> isUpperBoundSubTypeOf(baseBound)
        else -> false
    }
}
fun SiTypeParameter.isUpperBoundSuperTypeOf(derived: SiTypeParameter): Boolean{
    return when(val baseBound= derived.upperBounds.first().classifier){
        is SiClass<*> -> isUpperBoundSuperTypeOf(baseBound)
        is SiTypeParameter -> isUpperBoundSuperTypeOf(baseBound)
        else -> false
    }
}

fun SiTypeParameter.isUpperBoundSubTypeOf(base: SiClass<*>): Boolean{
    return when(val thisBound= upperBounds.first().classifier){
        is SiClass<*> -> thisBound.isSubclassOf(base)
        is SiTypeParameter -> thisBound.isUpperBoundSubTypeOf(base)
        else -> false
    }
}
fun SiTypeParameter.isUpperBoundSuperTypeOf(derived: SiClass<*>): Boolean{
    return when(val thisBound= upperBounds.first().classifier){
        is SiClass<*> -> thisBound.isSuperclassOf(derived)
        is SiTypeParameter -> thisBound.isUpperBoundSuperTypeOf(derived)
        else -> false
    }
}

fun SiClass<*>.isUpperBoundSubTypeOf(base: SiTypeParameter): Boolean{
    return when(val baseBound= base.upperBounds.first().classifier){
        is SiClass<*> -> isSubclassOf(baseBound)
        is SiTypeParameter -> isUpperBoundSubTypeOf(baseBound)
        else -> false
    }
}
fun SiClass<*>.isUpperBoundSuperTypeOf(base: SiTypeParameter): Boolean{
    return when(val baseBound= base.upperBounds.first().classifier){
        is SiClass<*> -> isSuperclassOf(baseBound)
        is SiTypeParameter -> isUpperBoundSuperTypeOf(baseBound)
        else -> false
    }
}

fun SiClassifier.isUpperBoundSubTypeOf(base: SiClassifier): Boolean{
    return when(this){
        is SiClass<*> -> when(base){
            is SiClass<*> -> isSubclassOf(base)
            is SiTypeParameter -> isUpperBoundSubTypeOf(base)
            else -> false
        }
        is SiTypeParameter -> when(base){
            is SiClass<*> -> isUpperBoundSubTypeOf(base)
            is SiTypeParameter -> isUpperBoundSubTypeOf(base)
            else -> false
        }
        else -> false
    }
}
fun SiClassifier.isUpperBoundSuperTypeOf(derived: SiClassifier): Boolean{
    return when(this){
        is SiClass<*> -> when(derived){
            is SiClass<*> -> isSuperclassOf(derived)
            is SiTypeParameter -> isUpperBoundSuperTypeOf(derived)
            else -> false
        }
        is SiTypeParameter -> when(derived){
            is SiClass<*> -> isUpperBoundSuperTypeOf(derived)
            is SiTypeParameter -> isUpperBoundSuperTypeOf(derived)
            else -> false
        }
        else -> false
    }
}

fun SiType.isSameTypeAs(other: SiType, includeNullability: Boolean= true): Boolean
        = classifier == other.classifier
        && arguments == other.arguments
        && (!includeNullability || isMarkedNullable == other.isMarkedNullable)

/**
 * Menentukan apakah `this.extension` [SiType] merupakan subtype dari [base].
 * Definisi subtype pada fungsi ini berbeda sedikit dg [SiType.isSubtypeOf] pada [kotlin.reflect.full],
 * yaitu:
 *  -> subtype jika `this.extension`.classifier merupakan subtype atau sama dg [base].classifier, dan
 *  -> `this.extension`.arguments merupakan subtype atau sama dg [base].arguments.
 */
fun SiType.isSubTypeOf(base: SiType): Boolean{
    if((base.isMarkedNullable || !isMarkedNullable).not())
        return false
    val isClassifierSubtype= base.classifier != null && classifier?.isUpperBoundSubTypeOf(base.classifier!!) ?: { prine("SiType.isSubTypeOf masuk ke null"); false }()
    var isTypeArgSubtype= true

    val otherArgs= base.arguments

    for((i, typeArg) in arguments.withIndex()){
        val otherTypeArg= try{ otherArgs[i].type }
        catch (e: Exception){ break }
        isTypeArgSubtype= isTypeArgSubtype && (otherTypeArg != null && typeArg.type?.isSubTypeOf(otherTypeArg) ?: true)
    }
//    prine("SiType.isSubTypeOf this= $this base= $base isClassifierSubtype= $isClassifierSubtype isTypeArgSubtype= $isTypeArgSubtype")
    return isClassifierSubtype && isTypeArgSubtype
}
fun SiType.isSuperTypeOf(derived: SiType): Boolean = derived.isSubTypeOf(this)

