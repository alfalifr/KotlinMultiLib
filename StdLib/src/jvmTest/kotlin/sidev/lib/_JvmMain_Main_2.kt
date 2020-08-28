package sidev.lib
/*
import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.sequence.withLevel
import sidev.lib.console.prin
import sidev.lib.reflex.comp.SiClass
import sidev.lib.reflex.comp.SiProperty1
import sidev.lib.reflex.full.*
import sidev.lib.reflex.comp.native.si
import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.universal.structure.collection.sequence.NestedSequence
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

fun main(){
    prin("AE::class ${AE::class} AE::class.typeParameters= ${AE::class.typeParameters}")
    prin("AE::class.si= ${AE::class.si} AE::class.si.typeParameters= ${AE::class.si.typeParameters}")
    val enumTypeParam= Enum::class.si.typeParameters.first()
    prin("Enum::class.si.typeParameters.first()= ${enumTypeParam} upperBound= ${enumTypeParam.upperBounds}")
    prin("Enum::class.si= ${Enum::class.si}")
    prin("Enum::class.typeParameters.first().upperBounds= ${Enum::class.typeParameters.first().upperBounds}")
    prin("AC::class.si= ${AC::class.si}")
    prin("AC::class= ${AC::class}")
    val ab_absField= AC<BlaBla2>::ab_abs.javaField
    prin("ab_absField= $ab_absField")

    prin("\n=============== AC::class.si.members ====================\n")
    for((i, member) in AC::class.si.members.withIndex()){
        prin("i= $i member= $member type= ${member.returnType} native= ${member.descriptor.native}") //typeNative= ${member.returnType.descriptor.native} typeClass= ${member.returnType.classifier}
        for((u, param) in member.parameters.withIndex())
            prin("--u= $u param= $param")
        for((o, typeParam) in member.typeParameters.withIndex())
            prin("--o= $o typeParam= $typeParam")
    }
    prin("\n=============== AC::class.si.constructors ====================\n")
    for((i, constr) in AC::class.si.constructors.withIndex()){
        prin("i= $i constr= $constr host= ${constr.descriptor.host}")
        for((u, param) in constr.parameters.withIndex())
            prin("--u= $u param= $param host= ${param.descriptor.host} isProp= ${param.isPropertyLike(
                AC::class.si.declaredMemberProperties.find { it.name == param.name }!!
            )}")
    }

    prin("AC::class.si.leastRequiredParamConstructor= ${AC::class.si.leastRequiredParamConstructor}")
    prin("ArrayList::class.si.leastRequiredParamConstructor= ${ArrayList::class.si.leastRequiredParamConstructor}")
//    java.util.Arrays.ArrayList::class.si.leastRequiredParamConstructor

    class A(val a: Int)

    prin("A::class.java= ${A::class.java}")
    prin("\n=============== A::class.si.constructors ====================\n")
    for((i, constr) in A::class.si.constructors.withIndex()){
        prin("i= $i constr= $constr host= ${constr.descriptor.host}")
        for((u, param) in constr.parameters.withIndex())
            prin("--u= $u param= $param host= ${param.descriptor.host} isProp= ${param.isPropertyLike(
                A::class.si.declaredMemberProperties.find { it.name == param.name }!!
            )}")
    }

    val poin= Poin(y=14)
    val poinPropSeq= poin.implementedPropertyValuesTree//.filter { it.first.hasBackingField }
    val poinPropSeq2= object : NestedSequence<Pair<SiProperty1<Poin, *>, Any?>>{
        override fun iterator(): NestedIterator<SiClass<*>, Pair<SiProperty1<Poin, *>, Any?>> = object : NestedIteratorImpl<SiClass<*>, Pair<SiProperty1<Poin, *>, Any?>>(Poin::class.si.classesTree.iterator()){
            override fun getOutputIterator(nowInput: SiClass<*>): Iterator<Pair<SiProperty1<Poin, *>, Any?>>? {
                return nowInput.declaredMemberProperties
                    .filter { !it.isAbstract }
                    .map {
                        val vals= (it as SiProperty1<Poin, *>).forceGet(poin)
                        Pair(it, vals)
                    }.iterator()
            }

            override fun getInputIterator(nowOutput: Pair<SiProperty1<Poin, *>, Any?>): Iterator<SiClass<*>>? = null
        }
    }
    val declaredProps= poin::class.si.declaredMemberProperties

    prin("\n=============== poin.poinPropSeq ====================\n")
    for((i, prop) in poinPropSeq.withIndex()){
        prin("i= $i prop= $prop")
    }
    prin("\n=============== poin.poinPropSeq 2 ====================\n")
    for((i, prop) in poinPropSeq.withIndex()){
        prin("i= $i prop= $prop")
    }
    prin("\n=============== poin.poinPropSeq 3 ====================\n")
    for((i, prop) in poinPropSeq.withIndex()){
        prin("i= $i prop= $prop")
    }

    prin("\n=============== poin.poinPropSeq2 ====================\n")
    for((i, prop) in poinPropSeq2.withIndex()){
        prin("i= $i prop= $prop")
    }
    prin("\n=============== poin.poinPropSeq2 2 ====================\n")
    for((i, prop) in poinPropSeq2.withIndex()){
        prin("i= $i prop= $prop")
    }

    prin("\n=============== Poin.declaredProps ====================\n")
    for((i, prop) in declaredProps.withIndex()){
        prin("i= $i prop= $prop")
    }
    prin("\n=============== Poin.declaredProps 2 ====================\n")
    for((i, prop) in declaredProps.withIndex()){
        prin("i= $i prop= $prop")
    }

    prin("\n=============== Poin::class.si.leastRequiredParamConstructor ====================\n")
    val leastConstr= Poin::class.si.leastRequiredParamConstructor
    val paramSize= leastConstr.parameters.size

    prin("leastConstr= $leastConstr paramSize= $paramSize")
    prin("\n=============== Poin::class.si.constructors ====================\n")
    for((i, constr) in Poin::class.si.constructors.withIndex()){
        prin("i= $i constr= $constr host= ${constr.descriptor.host}")
        for((u, param) in constr.parameters.withIndex()){
            prin("--u= $u param= $param")
        }
    }

    prin("AC::class.si.constructors= ${AC::class.si.constructors}")

    prin("\n=============== AC::class.si.declaredMemberProperties ====================\n")
    for((i, member) in AC::class.si.declaredMemberProperties.withIndex()){
        prin("i= $i member= $member")
    }

    prin("\n=============== AC::class.si.declaredMemberPropertiesTree ====================\n")
    for((i, prop) in AC::class.si.declaredMemberPropertiesTree.withIndex()){
        prin("i= $i prop= $prop hasField= ${prop.backingField != null} nativeField= ${(prop.descriptor.native as KProperty<*>).javaField}")
    }
    prin("\n=============== AC::class.si.nestedDeclaredMemberPropertiesTree ====================\n")
    for((i, member) in AC::class.si.nestedDeclaredMemberPropertiesTree.withIndex()){
        prin("i= $i member= $member")
    }

    prin("\n=============== AC::class.si.classesTree ====================\n")
    for((i, member) in AC::class.si.classesTree.withIndex()){
        prin("i= $i member= $member")
    }


    prin("AC::class.si.supertypes= ${AC::class.si.supertypes}")

    val ac= AC<BlaBla2>(Poin(16, 199))

    prin("\n============= ac.implementedFieldValuesTree =============\n")
    for((i, field) in ac.fieldValuesTree.withIndex()){
//        prin("i= $i field= $field valType= ${(field.second as Object).`class`} valCls= ${field.second!!::class} val= ${field.second}")
        prin("i= $i field= $field")
    }

    ac.poin.x= 27
    val ac2= ac.clone()

    prin("\n============= ac.poin.implementedPropertyValuesTree =============\n")
    for((i, prop) in ac.poin.implementedPropertyValuesTree.withIndex()){
        prin("i= $i prop= $prop")
    }

    prin("\n============= Clone =============\n")
    prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
    prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
    prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
    prin("ac.poinConstr.x= ${ac.poinConstr.x} ac2.poinConstr.x= ${ac2.poinConstr.x}")
    prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
    prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
    prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
    ac.poinConstr.x= 30
    ac.poin.x= 20
    ac.dDariAA.d= 19
    ac.acStr1= "bbb1"
    ac2.acStr2= "bbb2"
    prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
    prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
    prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
    prin("ac.poinConstr.x= ${ac.poinConstr.x} ac2.poinConstr.x= ${ac2.poinConstr.x}")
    prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
    prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
    prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")

    prin("\n=============== ac.implementedPropertyValuesTree ====================\n")
    for((i, prop) in ac.implementedPropertyValuesTree.withIndex()){
        prin("i= $i prop= $prop")
    }

    prin("\n=============== ac.implementedNestedPropertyValuesTree ====================\n")
    for((i, prop) in ac.implementedNestedPropertyValuesTree.withLevel().withIndex()){
        prin("i= $i prop= $prop")
    }
}

 */