package sidev.lib.reflex.core

import sidev.lib.reflex.native_.SiKClassifier
import sidev.lib.reflex.SiType
import sidev.lib.reflex.si
import kotlin.reflect.KClassifier

object ReflexTemplate{
    val classifierAny: SiKClassifier = (Any::class as KClassifier).si
    val classifierUnit: SiKClassifier = (Unit::class as KClassifier).si

    val typeAnyNullable: SiType = ReflexFactory.createType(classifierAny, classifierAny, nullable = true)
    val typeDynamic: SiType = typeAnyNullable
    val typeAny: SiType = ReflexFactory.createType(classifierAny, classifierAny)
    val typeUnitNullable: SiType = ReflexFactory.createType(classifierUnit, classifierUnit, nullable = true)
    val typeUnit: SiType = ReflexFactory.createType(classifierUnit, classifierUnit)
}