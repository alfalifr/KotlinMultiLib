package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.SiCallableImpl
import sidev.lib.reflex.common.SiMutableProperty1Impl
import sidev.lib.reflex.common.SiProperty1Impl
import sidev.lib.reflex.common.native.*


internal expect val SiNativeWrapper.nativeInnerName: String?
internal expect val SiNativeWrapper.nativeFullName: String?
internal expect val SiNativeWrapper.nativeSimpleName: String?

private val SiNativeWrapper.qualifiedNativeName: String
    get()= nativeFullName ?: NativeReflexConst.TEMPLATE_NATIVE_NAME


internal fun createNativeWrapper(native: Any): SiNativeWrapper = object : SiNativeWrapper{
    override val implementation: Any = native
}


fun SiClassifier.createType(
    arguments: List<SiTypeProjection> = emptyList(),
    nullable: Boolean = false
): SiType = ReflexFactory.createType(
    if(descriptor.native != null) createNativeWrapper(descriptor.native!!) else null,
    this, arguments, nullable
)

val SiClass<*>.startProjectedType: SiType
    get()= createType(typeParameters.map { SiTypeProjection.STAR })

object ReflexFactory{
    fun createType(
        nativeCounterpart: SiNativeWrapper?,
        classifier: SiClassifier?,
        arguments: List<SiTypeProjection> = emptyList(),
        nullable: Boolean = false
    ): SiType {
        val typeParam= if(classifier is SiClass<*>) classifier.typeParameters
            else emptyList()
        if(arguments.size < typeParam.size)
            throw IllegalArgumentException("arguments.size: ${arguments.size} < typeParam.size: ${typeParam.size}.")
        return object : SiTypeImpl() {
            override val descriptor: SiDescriptor = createDescriptor(nativeCounterpart = nativeCounterpart)
            override val arguments: List<SiTypeProjection> = arguments
            override val classifier: SiClassifier? = classifier
            override val isMarkedNullable: Boolean = nullable
/*
        override fun toString(): String {
            val str= super<SiTypeImpl>.toString()

            return "$str : {ASLI: native= $nativeCounterpart, impl= ${nativeCounterpart?.implementation}; classifier: $classifier, native= ${classifier?.descriptor?.native?.implementation}}"
        }
 */
        }
    }

    fun createParameter(
        nativeCounterpart: SiNativeWrapper?, //Untuk mengakomodasi parameter setter dan getter.
        hostCallable: SiCallable<*>?,
        index: Int, isOptional: Boolean, type: SiType,
        name: String?= null,
        kind: SiParameter.Kind= SiParameter.Kind.VALUE
    ): SiParameter = object: SiParamterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(hostCallable, nativeCounterpart)
        override val index: Int = index
        override val name: String? = name ?: nativeCounterpart?.qualifiedNativeName
        override val isOptional: Boolean = isOptional
        override var type: SiType = type
        override val kind: SiParameter.Kind = kind
    }

    fun createTypeParameter(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?,
        upperBounds: List<SiType>, variance: SiVariance
    ): SiTypeParameter = object: SiTypeParameterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val upperBounds: List<SiType> = upperBounds
        override val variance: SiVariance = variance
    }

    fun <R> createCallable(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        callBlock: (args: Array<out Any?>) -> R
    ): SiCallable<R> = object : SiCallableImpl<R>(){
        override val callBlock: (args: Array<out Any?>) -> R = callBlock
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = returnType
        override val parameters: List<SiParameter> = parameters
        override val typeParameters: List<SiTypeParameter> =
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(this, nativeCounterpart.implementation, name)
    }

    fun <R> createFunction(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        callBlock: (args: Array<out Any?>) -> R
    ): SiFunction<R> {
        val callable= createCallable(
            nativeCounterpart, host, returnType, parameters, typeParameters, callBlock
        )
        return object : SiFunctionImpl<R>(), SiCallable<R> by callable{
            override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart) //Agar ownernya jadi SiFunction
            override val callBlock: (args: Array<out Any?>) -> R = callBlock
        }
    }

    fun <T, R> createProperty1(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        type: SiType= ReflexTemplate.typeAnyNullable
    ): SiProperty1<T, R> = object : SiProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = type
    }
    fun <T, R> createMutableProperty1(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        type: SiType= ReflexTemplate.typeAnyNullable
    ): SiMutableProperty1<T, R> = object : SiMutableProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = type
    }

    fun <T: Any> createClass(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex? = null,
        constructors: List<SiFunction<T>> = emptyList(),
        members: Collection<SiCallable<*>> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList()
    ): SiClass<T> = object : SiClassImpl<T>() {
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart)
        override val qualifiedName: String? = nativeCounterpart.qualifiedNativeName
        override val simpleName: String? = nativeCounterpart.nativeSimpleName //ReflexFactoryHelper.getSimpleName(nativeCounterpart, qualifiedName)
        override var members: Collection<SiCallable<*>> = members
        override var constructors: List<SiFunction<T>> = constructors
        override var typeParameters: List<SiTypeParameter> =
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(this, nativeCounterpart.implementation, qualifiedName)
        override var supertypes: List<SiType> = ReflexFactoryHelper.getSupertypes(this, nativeCounterpart.implementation, qualifiedName)
    }

    internal fun <T, R> createPropertyGetter1(
        property: SiProperty1<T, R>
    ): SiPropertyGetter1<T, R> = object : SiPropertyGetter1<T, R>(property){
        override val descriptor: SiDescriptor = createDescriptor(property)
    }
    internal fun <T, R> createPropertySetter1(
        property: SiProperty1<T, R>
    ): SiPropertySetter1<T, R> = object : SiPropertySetter1<T, R>(property){
        override val descriptor: SiDescriptor = createDescriptor(property)
    }
}