package sidev.lib.reflex.inner
/*
/**
 * Fungsi yg sama dg [kotlin.reflect.full.KClasses] untuk tujuan cross-platform.
 */
import kotlin.reflect.*

//@file:JvmName("KClasses")
//@file:Suppress("HAS_NO_ACTUAL")

/**
 * Returns the primary constructor of this class, or `null` if this class has no primary constructor.
 * See the [Kotlin language documentation](http://kotlinlang.org/docs/reference/classes.html#constructors)
 * for more information.
 */
@SinceKotlin("1.1")
expect val <T : Any> KClass<T>.primaryConstructor: KFunction<T>?


/**
 * Returns a [KClass] instance representing the companion object of a given class,
 * or `null` if the class doesn't have a companion object.
 */
@SinceKotlin("1.1")
expect val KClass<*>.companionObject: KClass<*>?

/**
 * Returns an instance of the companion object of a given class,
 * or `null` if the class doesn't have a companion object.
 */
@SinceKotlin("1.1")
val KClass<*>.companionObjectInstance: Any?
    get() = companionObject?.objectInstance


/**
 * Returns all functions and properties declared in this class.
 * Does not include members declared in supertypes.
 */
@SinceKotlin("1.1")
expect val KClass<*>.declaredMembers: Collection<KCallable<*>>

/**
 * Returns all functions declared in this class, including all non-static methods declared in the class
 * and the superclasses, as well as static methods declared in the class.
 */
@SinceKotlin("1.1")
val KClass<*>.functions: Collection<KFunction<*>>
    get() = members.filterIsInstance<KFunction<*>>()

/**
 * Returns static functions declared in this class.
 */
@SinceKotlin("1.1")
expect val KClass<*>.staticFunctions: Collection<KFunction<*>>

/**
 * Returns non-extension non-static functions declared in this class and all of its superclasses.
 */
@SinceKotlin("1.1")
expect val KClass<*>.memberFunctions: Collection<KFunction<*>>

/**
 * Returns extension functions declared in this class and all of its superclasses.
 */
@SinceKotlin("1.1")
expect val KClass<*>.memberExtensionFunctions: Collection<KFunction<*>>

/**
 * Returns all functions declared in this class.
 * If this is a Java class, it includes all non-static methods (both extensions and non-extensions)
 * declared in the class and the superclasses, as well as static methods declared in the class.
 */
@SinceKotlin("1.1")
expect val KClass<*>.declaredFunctions: Collection<KFunction<*>>

/**
 * Returns non-extension non-static functions declared in this class.
 */
@SinceKotlin("1.1")
expect val KClass<*>.declaredMemberFunctions: Collection<KFunction<*>>

/**
 * Returns extension functions declared in this class.
 */
@SinceKotlin("1.1")
expect val KClass<*>.declaredMemberExtensionFunctions: Collection<KFunction<*>>

/**
 * Returns static properties declared in this class.
 * Only properties representing static fields of Java classes are considered static.
 */
@SinceKotlin("1.1")
expect val KClass<*>.staticProperties: Collection<KProperty0<*>>

/**
 * Returns non-extension properties declared in this class and all of its superclasses.
 */
@SinceKotlin("1.1")
expect val <T : Any> KClass<T>.memberProperties: Collection<KProperty1<T, *>>

/**
 * Returns extension properties declared in this class and all of its superclasses.
 */
@SinceKotlin("1.1")
expect val <T : Any> KClass<T>.memberExtensionProperties: Collection<KProperty2<T, *, *>>

/**
 * Returns non-extension properties declared in this class.
 */
@SinceKotlin("1.1")
expect val <T : Any> KClass<T>.declaredMemberProperties: Collection<KProperty1<T, *>>

/**
 * Returns extension properties declared in this class.
 */
@SinceKotlin("1.1")
expect val <T : Any> KClass<T>.declaredMemberExtensionProperties: Collection<KProperty2<T, *, *>>

/*
private val KCallableImpl<*>.isExtension: Boolean
    get() = descriptor.extensionReceiverParameter != null

private val KCallableImpl<*>.isNotExtension: Boolean
    get() = !isExtension
 */

/**
 * Immediate superclasses of this class, in the order they are listed in the source code.
 * Includes superclasses and superinterfaces of the class, but does not include the class itself.
 */
@SinceKotlin("1.1")
val KClass<*>.superclasses: List<KClass<*>>
    get() = supertypes.mapNotNull { it.classifier as? KClass<*> }

/**
 * All supertypes of this class, including indirect ones, in no particular order.
 * There is not more than one type in the returned collection that has any given classifier.
 */
@SinceKotlin("1.1")
expect val KClass<*>.allSupertypes: Collection<KType>

/**
 * All superclasses of this class, including indirect ones, in no particular order.
 * Includes superclasses and superinterfaces of the class, but does not include the class itself.
 * The returned collection does not contain more than one instance of any given class.
 */
@SinceKotlin("1.1")
expect val KClass<*>.allSuperclasses: Collection<KClass<*>>

/**
 * Returns `true` if `this` class is the same or is a (possibly indirect) subclass of [base], `false` otherwise.
 */
@SinceKotlin("1.1")
expect fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean

/**
 * Returns `true` if `this` class is the same or is a (possibly indirect) superclass of [derived], `false` otherwise.
 */
@SinceKotlin("1.1")
fun KClass<*>.isSuperclassOf(derived: KClass<*>): Boolean =
    derived.isSubclassOf(this)


/**
 * Casts the given [value] to the class represented by this [KClass] object.
 * Throws an exception if the value is `null` or if it is not an instance of this class.
 *
 * @see [KClass.isInstance]
 * @see [KClass.safeCast]
 */
@SinceKotlin("1.1")
expect fun <T : Any> KClass<T>.cast(value: Any?): T

/**
 * Casts the given [value] to the class represented by this [KClass] object.
 * Returns `null` if the value is `null` or if it is not an instance of this class.
 *
 * @see [KClass.isInstance]
 * @see [KClass.cast]
 */
@SinceKotlin("1.1")
fun <T : Any> KClass<T>.safeCast(value: Any?): T? {
    return if (isInstance(value)) value as T else null
}


/**
 * Creates a new instance of the class, calling a constructor which either has no parameters or all parameters of which are optional
 * (see [KParameter.isOptional]). If there are no or many such constructors, an exception is thrown.
 */
@SinceKotlin("1.1")
fun <T : Any> KClass<T>.createInstance(): T {
    // TODO: throw a meaningful exception
    val noArgsConstructor = constructors.singleOrNull { it.parameters.all(KParameter::isOptional) }
        ?: throw IllegalArgumentException("Class should have a single no-arg constructor: $this")

    return noArgsConstructor.callBy(emptyMap())
}

 */