@file:JvmName("KClassesKtJvm")

package sidev.lib.reflex.inner

import kotlin.reflect.*
import kotlin.reflect.full.primaryConstructor as _primaryConstructor
import kotlin.reflect.full.companionObject as _companionObject
import kotlin.reflect.full.declaredMembers as _declaredMembers
import kotlin.reflect.full.staticFunctions as _staticFunctions
import kotlin.reflect.full.memberFunctions as _memberFunctions
import kotlin.reflect.full.memberExtensionFunctions as _memberExtensionFunctions
import kotlin.reflect.full.declaredFunctions as _declaredFunctions
import kotlin.reflect.full.declaredMemberFunctions as _declaredMemberFunctions
import kotlin.reflect.full.declaredMemberExtensionFunctions as _declaredMemberExtensionFunctions
import kotlin.reflect.full.staticProperties as _staticProperties
import kotlin.reflect.full.memberProperties as _memberProperties
import kotlin.reflect.full.memberExtensionProperties as _memberExtensionProperties
import kotlin.reflect.full.declaredMemberProperties as _declaredMemberProperties
import kotlin.reflect.full.declaredMemberExtensionProperties as _declaredMemberExtensionProperties
import kotlin.reflect.full.allSupertypes as _allSupertypes
import kotlin.reflect.full.allSuperclasses as _allSuperclasses
import kotlin.reflect.full.isSubclassOf as _isSubclassOf
import kotlin.reflect.full.cast as _cast


actual val <T : Any> KClass<T>.primaryConstructor: KFunction<T>? get()= _primaryConstructor

actual val KClass<*>.companionObject: KClass<*>? get()= _companionObject

actual val KClass<*>.declaredMembers: Collection<KCallable<*>> get()= _declaredMembers

actual val KClass<*>.staticFunctions: Collection<KFunction<*>> get()= _staticFunctions

actual val KClass<*>.memberFunctions: Collection<KFunction<*>> get()= _memberFunctions

actual val KClass<*>.memberExtensionFunctions: Collection<KFunction<*>> get()= _memberExtensionFunctions

actual val KClass<*>.declaredFunctions: Collection<KFunction<*>> get()= _declaredFunctions

actual val KClass<*>.declaredMemberFunctions: Collection<KFunction<*>> get()= _declaredMemberFunctions

actual val KClass<*>.declaredMemberExtensionFunctions: Collection<KFunction<*>> get()= _declaredMemberExtensionFunctions

actual val KClass<*>.staticProperties: Collection<KProperty0<*>> get()= _staticProperties

actual val <T : Any> KClass<T>.memberProperties: Collection<KProperty1<T, *>> get()= _memberProperties

actual val <T : Any> KClass<T>.memberExtensionProperties: Collection<KProperty2<T, *, *>> get()= _memberExtensionProperties

actual val <T : Any> KClass<T>.declaredMemberProperties: Collection<KProperty1<T, *>> get()= _declaredMemberProperties

actual val <T : Any> KClass<T>.declaredMemberExtensionProperties: Collection<KProperty2<T, *, *>> get()= _declaredMemberExtensionProperties

actual val KClass<*>.allSupertypes: Collection<KType> get()= _allSupertypes

actual val KClass<*>.allSuperclasses: Collection<KClass<*>> get()= _allSuperclasses

actual fun KClass<*>.isSubclassOf(base: KClass<*>): Boolean = _isSubclassOf(base)

actual fun <T : Any> KClass<T>.cast(value: Any?): T = _cast(value)
