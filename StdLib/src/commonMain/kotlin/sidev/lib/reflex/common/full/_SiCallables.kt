package sidev.lib.reflex.common.full

import sidev.lib.check.notNull
import sidev.lib.check.notNullTo
import sidev.lib.reflex.common.SiCallable
import sidev.lib.reflex.common.native.getIsAccessible
import sidev.lib.reflex.common.native.setIsAccessible
import kotlin.reflect.KCallable


var SiCallable<*>.isAccessible: Boolean
    get()= descriptor.native.notNullTo { getIsAccessible(it) } ?: false
    set(v){ descriptor.native.notNull { setIsAccessible(it, v) } }