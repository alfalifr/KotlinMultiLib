package sidev.lib.reflex.common.core

import sidev.lib.check.asNotNullTo
import sidev.lib.console.prine
import sidev.lib.platform.Platform
import sidev.lib.platform.platform
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.native.SiKClassifier
import sidev.lib.reflex.common.native.SiNativeWrapper
import sidev.lib.reflex.common.native.isDynamicEnabled
import sidev.lib.reflex.common.native.isTypeFinal
import sidev.lib.reflex.fullName
import kotlin.reflect.KClass
import kotlin.reflect.KTypeParameter


internal expect fun getNativeReflexDescription(nativeReflexUnit: Any): SiDescriptor.ElementType?


/**
 * Penamaan komponen refleksi di sini meniru penamaan pada Kotlin.
 */
object ReflexDescriptor {
    const val SI_TYPE_PATTERN= "package.Class<type>?"
    const val SI_CLASSIFIER_PATTERN= "[class|type parameter] package.Class"
    const val SI_CALLABLE_PATTERN= "[fun|val|var] $SI_CLASSIFIER_PATTERN.name: $SI_TYPE_PATTERN"
    const val SI_PARAMETER_PATTERN= "parameter #n name of $SI_CALLABLE_PATTERN"
    const val SI_CALLABLE_CONSTRUCTOR_NAME= "<init>"
    const val SI_CALLABLE_GETTER_NAME= "<get-prop>"
    const val SI_CALLABLE_SETTER_NAME= "<set-prop>"

    fun createDescriptor(
        owner: SiReflex, host: SiReflex?, nativeCounterpart: SiNativeWrapper?, modifier: Int
    ): SiDescriptor = object : SiDescriptorImpl() {
        override val innerName: String? = nativeCounterpart?.nativeInnerName
        override val owner: SiReflex = owner
//        override val innerName: String = getReflexInnerName(owner)
        override val type: SiDescriptor.ElementType = getReflexElementType(owner, nativeCounterpart?.implementation)
        override var native: Any? = nativeCounterpart?.implementation
        init{ this.host= host }
        /** Agar tidak berat saat komputasi string descriptor. */
        private lateinit var string: String //by mutab; { getDescriptorString(this) }
        override fun toString(): String {
            if(!isDescStrCalculated){
                string= getDescriptorString(this)
                isDescStrCalculated= true
            }
            return string
        }
        override var modifier: Int = modifier
    }

//    fun getClassDesc(clazz: SiClass<*>): String = "class ${clazz.qualifiedName}"

    fun SiTypeParameter.getDescStr(includeUpperBounds: Boolean= true): String{
        val upperBoundStr= if(includeUpperBounds){
            ": " +(if(upperBounds.size == 1) upperBounds.first().toString()
            else upperBounds.joinToString(" & "))
        } else ""

        val varianceStr= when(variance){
            SiVariance.IN -> "in "
            SiVariance.OUT -> "out "
            else -> ""
        }
        return "$varianceStr$name$upperBoundStr"
    }
    fun List<SiTypeParameter>.getSiTypeParamDescStr(includeUpperBounds: Boolean= true): String{
        return if(isNotEmpty()) {
            var str= "<"
            for(typeParam in this)
                str += "${typeParam.getDescStr(includeUpperBounds)}, "
            str= str.removeSuffix(", ")
            "$str>"
        } else ""
    }

    fun List<SiParameter>.getSiParamDescStr(): String{
        var str= "("
        if(!isDynamicEnabled){
            for(param in this){
                //TODO <20 Agustu 2020> => [optionalStr] msh " = ?" bkn value sesugguhnya karena tidak dapat mendapatkan defaultValuenya.
                val optionalStr= if(param.isOptional) " = ?" else ""
                val varargStr= if(param.isVararg) "*" else ""
                val typeStr= (if(param.isVararg) param.type.arguments.first().type else param.type).toString()
                if(param.kind == SiParameter.Kind.VALUE)
                    str += "$varargStr$typeStr$optionalStr, "
            }
        } else{
            for(param in this){
                val optionalStr= if(param.isOptional) " = ${param.defaultValue}" else ""
                if(param.kind == SiParameter.Kind.VALUE)
                    str += "${param.name}$optionalStr, "
            }
        }
        str= str.removeSuffix(", ")
        return "$str)"
    }

    fun List<SiTypeProjection>.getSiTypeProjectionDescStr(): String{
        return if(isNotEmpty()) {
            var str= "<"
            for(arg in this){
                val varianceStr= when(arg.variance){
                    SiVariance.IN -> "in "
                    SiVariance.OUT -> "out "
                    else -> ""
                }
                val argTypeStr= arg.type ?: "*"
                str += "$varianceStr$argTypeStr, "
            }
            str= str.removeSuffix(", ")
            "$str>"
        } else ""
    }
/*
    fun getNativeDescriptorString(siNativeDesc: SiDescriptor): String {
        var str= siNativeDesc.description ?: ""
        str += when(val owner= siNativeDesc.owner){
            is SiNativeClassifier -> " ${owner.name}"
            is SiNativeCallable<*> -> {
                val hostString= if(owner.name == SI_CALLABLE_CONSTRUCTOR_NAME) ""
                else siNativeDesc.host.asNotNullTo { clazz: SiClass<*> -> clazz.qualifiedName +"." } ?: ""

                val paramStr= if(owner !is SiFunction<*>) ""
                else owner.parameters.getSiParamDescStr()

                " $hostString${owner.name}$paramStr"
            }
        }
    }
 */
    fun getDescriptorString(desc: SiDescriptor): String {
        var str= desc.type.description
        str += when(val owner= desc.owner){
            is SiKClassifier -> " ${owner.nativeFullName}"
            is SiClass<*> -> " ${owner.qualifiedName}${owner.typeParameters.getSiTypeParamDescStr(false)}"
            is SiCallable<*> -> {
                val hostString= if(owner.name == SI_CALLABLE_CONSTRUCTOR_NAME) ""
                else desc.host.asNotNullTo { clazz: SiClass<*> ->
                    val clsTypeParamStr= clazz.typeParameters.getSiTypeParamDescStr(false)
                    "${clazz.qualifiedName}$clsTypeParamStr."
                } ?: ""

                var typeParamString= owner.typeParameters.getSiTypeParamDescStr(false)
                if(typeParamString.isNotBlank())
                    typeParamString += " "

                val paramStr=
                    if(owner is SiFunction<*>
                        || owner is SiMutableProperty.Setter<*>
                        || owner is SiProperty.Getter<*> )
                        owner.parameters.getSiParamDescStr()
                else ""

                val returnTypeStr= if(!isDynamicEnabled) ": ${owner.returnType}" else ""

                val nameStr= if(isDynamicEnabled && hostString.removeSuffix(".") == owner.name)
                    SI_CALLABLE_CONSTRUCTOR_NAME
                else owner.name

                " $typeParamString$hostString$nameStr$paramStr$returnTypeStr"
            }
            is SiParameter -> {
                val optionalStr= if(owner.isOptional) "?" else ""
                val paramStr= when(owner.kind){
                    SiParameter.Kind.VALUE -> {
                        val varargStr= if(owner.isVararg) "*" else ""
                        val paramTypeStr= if(!isDynamicEnabled){
                            ": " +(if(!owner.isVararg) owner.type else owner.type.arguments.first().type).toString()
                        } else ""
                        "$varargStr${owner.name}$optionalStr$paramTypeStr"
                    }
                    SiParameter.Kind.INSTANCE -> "instance parameter"
                    SiParameter.Kind.RECEIVER -> "receiver$optionalStr: ${owner.type}"
                }
                " #${owner.index} $paramStr -- of -- ${desc.host}"
            }
            is SiTypeParameter -> " ${owner.getDescStr()}"
            is SiType -> {
                //TODO ada bbrp tipe di platfor Js yg name-nya jadi "KClass".
                prine("getDescriptor() SiType native= ${owner.descriptor.native}")
                val str= when(val classifier= owner.classifier){
                    is SiKClassifier -> {
                        val str= classifier.nativeFullName
                        val typeArgStr= owner.arguments.getSiTypeProjectionDescStr()
                        "$str$typeArgStr"
                    }
                    is SiClass<*> -> {
                        val str= classifier.qualifiedName
                        val typeArgStr= owner.arguments.getSiTypeProjectionDescStr()
                        "$str$typeArgStr"
                    }
                    is SiTypeParameter -> classifier.name
                    is SiKClassifier -> when(val native= classifier.implementation){
                        is KClass<*> -> {
                            var str= native.fullName //qualifiedName
                            val typeArgStr= owner.arguments.getSiTypeProjectionDescStr()
                            "$str$typeArgStr"
                        }
                        is KTypeParameter -> native.name
                        else -> SiReflexConst.TEMPLATE_TYPE_NAME
                    }
                    else -> SiReflexConst.TEMPLATE_TYPE_NAME
                }
                val nullableStr= if(owner.isMarkedNullable) "?" else ""
                val additionStr=
                    if(platform == Platform.JS && !isTypeFinal(owner.descriptor.native!!))
                        " " +SiReflexConst.NOTE_CLASSIFIER_NOT_READY
                    else ""
                "$str$nullableStr$additionStr"
            }
            else -> " ${SiReflexConst.TEMPLATE_REFLEX_UNIT_NAME}"
        }
        return str
    }
//    fun getClassDesc(clazz: SiClass<*>): String = "class ${clazz.qualifiedName}"

}

fun SiReflex.createDescriptor(
    host: SiReflex? = null, nativeCounterpart: SiNativeWrapper? = null, modifier: Int= 0
): SiDescriptor = ReflexDescriptor.createDescriptor(this, host, nativeCounterpart, modifier)

internal var SiReflex.mutableHost: SiReflex?
    get() = descriptor.host
    set(v){ (descriptor as SiDescriptorImpl).host= v }

internal fun getReflexElementType(reflexUnit: SiReflex, nativeCounterpart: Any?): SiDescriptor.ElementType = when(reflexUnit){
    is SiClass<*> -> SiDescriptor.ElementType.CLASS
    is SiFunction<*> -> SiDescriptor.ElementType.FUNCTION
    is SiMutableProperty<*> -> SiDescriptor.ElementType.MUTABLE_PROPERTY
    is SiProperty<*> -> SiDescriptor.ElementType.PROPERTY
    is SiCallable<*> -> SiDescriptor.ElementType.FUNCTION
    is SiParameter -> SiDescriptor.ElementType.PARAMETER
    is SiTypeParameter -> SiDescriptor.ElementType.TYPE_PARAMETER
    is SiType -> SiDescriptor.ElementType.TYPE
    else -> (if(nativeCounterpart != null) getNativeReflexDescription(nativeCounterpart) else null)
        ?: SiDescriptor.ElementType.ANY
}

//internal expect fun getReflexInnerName(reflexUnit: SiReflex): String