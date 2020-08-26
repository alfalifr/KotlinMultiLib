package sidev.lib.reflex.common.core

import sidev.lib.console.prine
import sidev.lib.platform.Platform
import sidev.lib.platform.platform
import sidev.lib.property.reevaluateLazy
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
        nullable: Boolean = false,
        modifier: Int= 0
    ): SiType {
        val typeParam= if(classifier is SiClass<*>) classifier.typeParameters
            else emptyList()
        if(arguments.size < typeParam.size)
            throw IllegalArgumentException("arguments.size: ${arguments.size} < typeParam.size: ${typeParam.size}.")
        return _createType(nativeCounterpart, classifier, arguments, nullable, modifier)
    }

    /**
     * Sama dg [createType] namun tidak melakukan pengecekan jml type argument untuk kepentingan refleksi internal.
     */
    internal fun _createType(
        nativeCounterpart: SiNativeWrapper?,
        classifier: SiClassifier?,
        arguments: List<SiTypeProjection> = emptyList(),
        nullable: Boolean = false,
        modifier: Int= 0
    ): SiType {
        return object : SiTypeImpl() {
            override val descriptor: SiDescriptor = createDescriptor(nativeCounterpart = nativeCounterpart, modifier = modifier)
            override var arguments: List<SiTypeProjection> = arguments
            override val classifier: SiClassifier? = classifier
            override val isMarkedNullable: Boolean = nullable
        }
    }


    fun createParameter(
        nativeCounterpart: SiNativeWrapper?, //Untuk mengakomodasi parameter setter dan getter.
        hostCallable: SiCallable<*>?,
        index: Int, type: SiType,
        name: String?= null,
        kind: SiParameter.Kind= SiParameter.Kind.VALUE,
        defaultValue: Any?= null,
        modifier: Int= 0
    ): SiParameter = object: SiParamterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(hostCallable, nativeCounterpart, modifier)
        override val index: Int = index
        override val name: String? = name ?: nativeCounterpart?.qualifiedNativeName
        override val isOptional: Boolean = SiModifier.isOptional(this)
        override val isVararg: Boolean = SiModifier.isVararg(this)
        override var type: SiType = type
        override val kind: SiParameter.Kind = kind
        override val defaultValue: Any? = defaultValue
    }

    internal fun createParameterLazyly(
        nativeCounterpart: SiNativeWrapper?, //Untuk mengakomodasi parameter setter dan getter.
        hostCallable: SiCallable<*>?,
        index: Int, //type: SiType,
        name: String?= null,
        kind: SiParameter.Kind= SiParameter.Kind.VALUE,
        defaultValue: Any?= null,
        modifier: Int= 0
    ): SiParameter = object: SiParamterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(hostCallable, nativeCounterpart, modifier)
        override val index: Int = index
        override val name: String? = name ?: nativeCounterpart?.qualifiedNativeName
        override val isOptional: Boolean = SiModifier.isOptional(this)
        override val isVararg: Boolean = SiModifier.isVararg(this)
        override val type: SiType by reevaluateLazy { eval ->
            val type= if(nativeCounterpart != null) getReturnType(nativeCounterpart.implementation)
                else ReflexTemplate.typeDynamic.also { eval.value= true; return@reevaluateLazy it }
            eval.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val kind: SiParameter.Kind = kind
        override val defaultValue: Any? = defaultValue
    }


    fun createTypeParameter(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?,
        upperBounds: List<SiType>, variance: SiVariance,
        modifier: Int= 0
    ): SiTypeParameter = object: SiTypeParameterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override var upperBounds: List<SiType> = upperBounds
        override val variance: SiVariance = variance
    }

    fun <R> createCallable(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0,
        defaultCallBlock: ((args: Array<out Any?>) -> R)?= null,
        callBlock: (args: Array<out Any?>) -> R
    ): SiCallable<R> = object : SiCallableImpl<R>(){
        override val callBlock: (args: Array<out Any?>) -> R = callBlock
        override val defaultCallBlock: ((args: Array<out Any?>) -> R)? = defaultCallBlock
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = returnType
        override val parameters: List<SiParameter> = parameters
        override val typeParameters: List<SiTypeParameter> by lazy{
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(descriptor.host as? SiClass<*>, this, nativeCounterpart.implementation)
        }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }

    internal fun <R> createCallableLazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
//        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0,
        defaultCallBlock: ((args: Array<out Any?>) -> R)?= null,
        callBlock: (args: Array<out Any?>) -> R
    ): SiCallable<R> = object : SiCallableImpl<R>(){
        override val callBlock: (args: Array<out Any?>) -> R = callBlock
        override val defaultCallBlock: ((args: Array<out Any?>) -> R)? = defaultCallBlock
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType by reevaluateLazy {
            val type= getReturnType(nativeCounterpart.implementation)
            it.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val parameters: List<SiParameter> = parameters
        override val typeParameters: List<SiTypeParameter> by lazy{
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(descriptor.host as? SiClass<*>, this, nativeCounterpart.implementation)
        }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    fun <R> createFunction(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0,
        defaultCallBlock: ((args: Array<out Any?>) -> R)?= null,
        callBlock: (args: Array<out Any?>) -> R
    ): SiFunction<R> {
        val callable= createCallable(
            nativeCounterpart, host, returnType, parameters, typeParameters, modifier, defaultCallBlock, callBlock
        )
        return object : SiFunctionImpl<R>(), SiCallable<R> by callable{
            override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier) //Agar ownernya jadi SiFunction
            override val callBlock: (args: Array<out Any?>) -> R = callBlock
            override val defaultCallBlock: ((args: Array<out Any?>) -> R)? = defaultCallBlock
        }
    }

    internal fun <R> createFunctionLazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
//        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0,
        defaultCallBlock: ((args: Array<out Any?>) -> R)?= null,
        callBlock: (args: Array<out Any?>) -> R
    ): SiFunction<R> {
        val callable= createCallableLazyly(
            nativeCounterpart, host, parameters, typeParameters, modifier, defaultCallBlock, callBlock
        )
        return object : SiFunctionImpl<R>(), SiCallable<R> by callable{
            override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier) //Agar ownernya jadi SiFunction
            override val callBlock: (args: Array<out Any?>) -> R = callBlock
            override val defaultCallBlock: ((args: Array<out Any?>) -> R)? = defaultCallBlock
        }
    }


    fun <T, R> createProperty1(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiProperty1<T, R> = object : SiProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = type
        override val backingField: SiField<T, R>? by lazy{ _createFieldFromProperty<T, R>(this) }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }

    internal fun <T, R> createProperty1Lazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
//        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiProperty1<T, R> = object : SiProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType by reevaluateLazy {
            val type= getReturnType(nativeCounterpart.implementation)
            it.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val backingField: SiField<T, R>? by lazy{ _createFieldFromProperty<T, R>(this) }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    fun <T, R> createMutableProperty1(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiMutableProperty1<T, R> = object : SiMutableProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = type
        override val backingField: SiMutableField<T, R>? by lazy{ _createFieldFromProperty<T, R>(this) }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    internal fun <T, R> createMutableProperty1Lazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer?= null,
//        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiMutableProperty1<T, R> = object : SiMutableProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType by reevaluateLazy {
            val type= getReturnType(nativeCounterpart.implementation)
            it.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val backingField: SiMutableField<T, R>? by lazy{ _createFieldFromProperty<T, R>(this) }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }

    internal fun <T, R> createPropertyGetter1(
        property: SiProperty1<T, R>, visibility: SiVisibility = SiVisibility.PUBLIC,
        modifier: Int= 0
    ): SiPropertyGetter1<T, R> = object : SiPropertyGetter1<T, R>(property){
        override val descriptor: SiDescriptor = createDescriptor(property, modifier = modifier)
        override val visibility: SiVisibility = visibility
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }
    internal fun <T, R> createPropertySetter1(
        property: SiProperty1<T, R>, visibility: SiVisibility = SiVisibility.PUBLIC,
        modifier: Int= 0
    ): SiPropertySetter1<T, R> = object : SiPropertySetter1<T, R>(property){
        override val descriptor: SiDescriptor = createDescriptor(property, modifier = modifier)
        override val visibility: SiVisibility = visibility
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    fun <T: Any> createClass(
        nativeCounterpart: SiNativeWrapper,
        host: SiDescriptorContainer? = null,
        constructors: List<SiFunction<T>> = emptyList(),
        members: Collection<SiCallable<*>> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0
    ): SiClass<T> = object : SiClassImpl<T>() {
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val qualifiedName: String? = nativeCounterpart.nativeFullName //nativeCounterpart.qualifiedNativeName
        override val simpleName: String? = nativeCounterpart.nativeSimpleName //ReflexFactoryHelper.getSimpleName(nativeCounterpart, qualifiedName)
        override var members: Collection<SiCallable<*>> = members
        override var constructors: List<SiFunction<T>> = constructors
        override val typeParameters: List<SiTypeParameter> by lazy {
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(this, nativeCounterpart.implementation)
        }
        override val supertypes: List<SiType> by lazy{
            ReflexFactoryHelper.getSupertypes(this, nativeCounterpart.implementation)
        }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }
///*
    fun <R, T>createField(
    nativeCounterpart: SiNativeWrapper?,
    host: SiDescriptorContainer?= null,
    name: String,
    type: SiType,
    modifier: Int= 0
    ): SiField<R, T> {
        val getBlock= if(nativeCounterpart != null) getPropGetValueBlock<T>(nativeCounterpart.implementation) //(arrayOf(receiver as Any))
        else {
            val propGetter= (host as? SiProperty<T>)?.getter
            { receivers: Array<out Any> -> propGetter?.call(receivers.first())!! }
        }
        return object : SiFieldImpl<R, T>(
            { receiver: R -> getBlock(arrayOf<Any>(receiver!!)) }
        ){
            override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
            override val name: String = name
            override val type: SiType = type
        }
    }

    fun <R, T>createMutableField(
        nativeCounterpart: SiNativeWrapper?,
        host: SiDescriptorContainer?= null,
        name: String,
        type: SiType,
        modifier: Int= 0
    ): SiMutableField<R, T> {
        val getBlock= if(nativeCounterpart != null) getPropGetValueBlock(nativeCounterpart.implementation) //(arrayOf(receiver as Any))
        else {
            prine("createField() getBlock else")
            val propGetter= (host as? SiProperty<T>)?.getter
            { receivers: Array<out Any> -> propGetter?.call(receivers.first())!! }
        }
        val setBlock= if(nativeCounterpart != null) getPropSetValueBlock(nativeCounterpart.implementation) //(arrayOf(receiver as Any))
        else {
            val propSetter= (host as? SiMutableProperty<T>)?.setter
            { receivers: Array<out Any>, value: T -> propSetter?.call(receivers.first(), value)!! }
        }
        return object : SiMutableFieldImpl<R, T>(
            { receiver: R -> getBlock(arrayOf<Any>(receiver!!)) },
            { receiver: R, value: T -> setBlock(arrayOf<Any>(receiver!!), value) }
        ){
            override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
            override val name: String = name
            override val type: SiType = type
        }
    }

    /**
     * Untuk kepentingan internal, semua field adalah mutable field, namun bbrp tidak diekspos fungsi set-nya.
     * Pada dasarnya, semua field itu mutable. Namun API Reflex sangat menyarankan immutability sehingga
     * bbrp akses `set` dibatasi.
     */
    internal fun <R, T>_createField(
        nativeCounterpart: SiNativeWrapper?,
        host: SiDescriptorContainer?= null,
        name: String,
        type: SiType,
        modifier: Int= 0
    ): SiMutableField<R, T> = createMutableField(nativeCounterpart, host, name, type, modifier)

    /**
     * Untuk kepentingan internal, semua field adalah mutable field, namun bbrp tidak diekspos fungsi set-nya.
     * Pada dasarnya, semua field itu mutable. Namun API Reflex sangat menyarankan immutability sehingga
     * bbrp akses `set` dibatasi.
     */
    internal fun <R, T> _createFieldFromProperty(property: SiProperty<T>): SiMutableField<R, T>?{
        return property.descriptor.native?.let { getNativeField(it) }?.let { createNativeWrapper(it) }
            ?.let { nativeField ->
                _createField(nativeField, property, property.name, property.returnType, getModifiers(nativeField.implementation))
            }
    }
// */
}