@file:JvmName("KClassifiersKtJvm")

package sidev.lib.reflex.inner

import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType as _createType

actual fun KClassifier.createType(
    arguments: List<KTypeProjection>,
    nullable: Boolean,
    annotations: List<Annotation>
): KType = _createType(arguments, nullable, annotations)