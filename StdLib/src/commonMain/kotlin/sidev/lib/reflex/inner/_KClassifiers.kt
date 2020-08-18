package sidev.lib.reflex.inner

import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection

expect fun KClassifier.createType(
    arguments: List<KTypeProjection> = emptyList(),
    nullable: Boolean = false,
    annotations: List<Annotation> = emptyList()
): KType