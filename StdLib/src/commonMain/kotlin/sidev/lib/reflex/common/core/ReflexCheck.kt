package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.SiTypeParameter

object ReflexCheck {
    fun typeParamEquality(unit1: SiTypeParameter, unit2: SiTypeParameter, includeUpperBound: Boolean= true): Boolean =
        if(includeUpperBound) unit1 == unit2 else unit1.name == unit2.name
}