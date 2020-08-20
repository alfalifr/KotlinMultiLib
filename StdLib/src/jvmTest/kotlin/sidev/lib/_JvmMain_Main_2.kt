package sidev.lib

import sidev.lib.collection.sequence.withLevel
import sidev.lib.console.prin
import sidev.lib.reflex.common.full.*
import sidev.lib.reflex.common.native.si

fun main(){
    prin("AC::class.si= ${AC::class.si}")
    prin("AC::class= ${AC::class}")
    prin("\n=============== AC::class.si.members ====================\n")
    for((i, member) in AC::class.si.members.withIndex()){
        prin("i= $i member= $member type= ${member.returnType} native= ${member.descriptor.native}") //typeNative= ${member.returnType.descriptor.native} typeClass= ${member.returnType.classifier}
        for((u, param) in member.parameters.withIndex())
            prin("--u= $u param= $param")
        for((o, typeParam) in member.typeParameters.withIndex())
            prin("--o= $o typeParam= $typeParam")
    }

    prin("\n=============== AC::class.si.declaredMemberProperties ====================\n")
    for((i, member) in AC::class.si.declaredMemberProperties.withIndex()){
        prin("i= $i member= $member")
    }

    prin("\n=============== AC::class.si.declaredMemberPropertiesTree ====================\n")
    for((i, member) in AC::class.si.declaredMemberPropertiesTree.withIndex()){
        prin("i= $i member= $member")
    }
    prin("\n=============== AC::class.si.nestedDeclaredMemberPropertiesTree ====================\n")
    for((i, member) in AC::class.si.nestedDeclaredMemberPropertiesTree.withIndex()){
        prin("i= $i member= $member")
    }

    prin("\n=============== AC::class.si.classesTree ====================\n")
    for((i, member) in AC::class.si.classesTree.withIndex()){
        prin("i= $i member= $member")
    }

    prin("AC::class.si.constructors= ${AC::class.si.constructors}")

    prin("AC::class.si.supertypes= ${AC::class.si.supertypes}")

    val ac= AC<BlaBla2>()

    prin("\n=============== ac.implementedPropertyValuesTree ====================\n")
    for((i, prop) in ac.implementedPropertyValuesTree.withIndex()){
        prin("i= $i prop= $prop")
    }

    prin("\n=============== ac.implementedNestedPropertyValuesTree ====================\n")
    for((i, prop) in ac.implementedNestedPropertyValuesTree.withLevel().withIndex()){
        prin("i= $i prop= $prop")
    }
}