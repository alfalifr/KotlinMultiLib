@file:JvmName("_EnumReflexFunJvm")
package sidev.lib.reflex.full

import sidev.lib.`val`.SuppressLiteral
import kotlin.jvm.JvmName

@Suppress(SuppressLiteral.UNCHECKED_CAST)
@get:JvmName("enumValues")
actual val <E: Enum<E>> E.enumValues: Array<E>
    get() = this::class.java.getMethod("values").invoke(this) as Array<E>