package sidev.lib.reflex.full

import sidev.lib.collection.sequence.NestedSequence
import kotlin.reflect.KClass


expect val KClass<*>.isPrimitive: Boolean
expect val KClass<*>.isArray: Boolean
expect val KClass<*>.isCopySafe: Boolean
expect val KClass<*>.isCollection: Boolean

expect val KClass<*>.classesTree: NestedSequence<KClass<*>>