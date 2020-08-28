package sidev.lib.reflex.type
/*
import sidev.lib.reflex.inner.isSubclassOf
import sidev.lib.reflex.inner.isSubtypeOf
import sidev.lib.reflex.inner.isSuperclassOf
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter


/**
 * Menentukan apakah `this.extension` [KTypeParameter] merupakan subtype dari [base]
 * berdasarkan [KTypeParameter.upperBounds]. Fungsi ini menggunakan upperBounds karena
 * tidak mungkin membandingkan menggunakan tipe sesungguhnya mengingat tidak terdapat type-projection.
 */
fun KTypeParameter.isUpperBoundSubTypeOf(base: KTypeParameter): Boolean{
    return when(val baseBound= base.upperBounds.first().classifier){
        is KClass<*> -> isUpperBoundSubTypeOf(baseBound)
        is KTypeParameter -> isUpperBoundSubTypeOf(baseBound)
        else -> false
    }
}
fun KTypeParameter.isUpperBoundSuperTypeOf(derived: KTypeParameter): Boolean{
    return when(val baseBound= derived.upperBounds.first().classifier){
        is KClass<*> -> isUpperBoundSuperTypeOf(baseBound)
        is KTypeParameter -> isUpperBoundSuperTypeOf(baseBound)
        else -> false
    }
}

fun KTypeParameter.isUpperBoundSubTypeOf(base: KClass<*>): Boolean{
    return when(val thisBound= upperBounds.first().classifier){
        is KClass<*> -> thisBound.isSubclassOf(base)
        is KTypeParameter -> thisBound.isUpperBoundSubTypeOf(base)
        else -> false
    }
}
fun KTypeParameter.isUpperBoundSuperTypeOf(derived: KClass<*>): Boolean{
    return when(val thisBound= upperBounds.first().classifier){
        is KClass<*> -> thisBound.isSuperclassOf(derived)
        is KTypeParameter -> thisBound.isUpperBoundSuperTypeOf(derived)
        else -> false
    }
}

fun KClass<*>.isUpperBoundSubTypeOf(base: KTypeParameter): Boolean{
    return when(val baseBound= base.upperBounds.first().classifier){
        is KClass<*> -> isSubclassOf(baseBound)
        is KTypeParameter -> isUpperBoundSubTypeOf(baseBound)
        else -> false
    }
}
fun KClass<*>.isUpperBoundSuperTypeOf(base: KTypeParameter): Boolean{
    return when(val baseBound= base.upperBounds.first().classifier){
        is KClass<*> -> isSuperclassOf(baseBound)
        is KTypeParameter -> isUpperBoundSuperTypeOf(baseBound)
        else -> false
    }
}

fun KClassifier.isUpperBoundSubTypeOf(base: KClassifier): Boolean{
    return when(this){
        is KClass<*> -> when(base){
            is KClass<*> -> isSubclassOf(base)
            is KTypeParameter -> isUpperBoundSubTypeOf(base)
            else -> false
        }
        is KTypeParameter -> when(base){
            is KClass<*> -> isUpperBoundSubTypeOf(base)
            is KTypeParameter -> isUpperBoundSubTypeOf(base)
            else -> false
        }
        else -> false
    }
}
fun KClassifier.isUpperBoundSuperTypeOf(derived: KClassifier): Boolean{
    return when(this){
        is KClass<*> -> when(derived){
            is KClass<*> -> isSuperclassOf(derived)
            is KTypeParameter -> isUpperBoundSuperTypeOf(derived)
            else -> false
        }
        is KTypeParameter -> when(derived){
            is KClass<*> -> isUpperBoundSuperTypeOf(derived)
            is KTypeParameter -> isUpperBoundSuperTypeOf(derived)
            else -> false
        }
        else -> false
    }
}

fun KType.isSameTypeAs(other: KType, includeNullability: Boolean= true): Boolean
        = classifier == other.classifier
        && arguments == other.arguments
        && (!includeNullability || isMarkedNullable == other.isMarkedNullable)

/**
 * Menentukan apakah `this.extension` [KType] merupakan subtype dari [base].
 * Definisi subtype pada fungsi ini berbeda sedikit dg [KType.isSubtypeOf] pada [kotlin.reflect.full],
 * yaitu:
 *  -> subtype jika `this.extension`.classifier merupakan subtype atau sama dg [base].classifier, dan
 *  -> `this.extension`.arguments merupakan subtype atau sama dg [base].arguments.
 */
fun KType.isSubTypeOf(base: KType): Boolean{
    val isClassifierSubtype= base.classifier != null && classifier?.isUpperBoundSubTypeOf(base.classifier!!) ?: false
    var isTypeArgSubtype= true

    val otherArgs= base.arguments

    for((i, typeArg) in arguments.withIndex()){
        val otherTypeArg= try{ otherArgs[i].type }
        catch (e: Exception){ break }
        isTypeArgSubtype= isTypeArgSubtype && (otherTypeArg != null && typeArg.type?.isSubtypeOf(otherTypeArg) ?: true)
    }
    return isClassifierSubtype && isTypeArgSubtype
}
fun KType.isSuperTypeOf(derived: KType): Boolean = derived.isSubTypeOf(this)

 */