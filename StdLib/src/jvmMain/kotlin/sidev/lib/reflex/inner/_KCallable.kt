@file:JvmName("KCallableKtJvm")

package sidev.lib.reflex.inner

import kotlin.reflect.KCallable
import kotlin.reflect.jvm.isAccessible as _isAccessible

actual var KCallable<*>.isAccessible: Boolean
    get()= _isAccessible
    set(v){ _isAccessible= v }