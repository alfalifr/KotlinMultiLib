package sidev.lib.reflex.native

//import sidev.lib.exception.ReflexComponentExc

/*
internal expect val SiNative.nativeFullName: String?
internal expect val SiNative.nativeSimpleName: String?
private val SiNative.qualifiedNativeName: String
    get()= nativeFullName ?: NativeReflexConst.TEMPLATE_NATIVE_NAME



//internal expect val <T> SiNativeCallable<T>.callBlock: (args: Array<out Any?>) -> T


object NativeReflexFactory{
    private fun checkReflexUnitType(nativeReflexUnit: Any, requestedType: KClass<out SiNative> = SiNative::class){
//        prine("NativeReflexFactory checkReflexUnitType() nativeReflexUnit= $nativeReflexUnit requestedType= $requestedType")
        var errorMsg= "nativeReflexUnit bkn merupakan "
        if(!when(requestedType){
                SiNativeClassifier::class -> { errorMsg += "classifier"; nativeReflexUnit.isClassifier }
                SiNativeFunction::class -> { errorMsg += "function"; nativeReflexUnit.isFunction }
                SiNativeMutableProperty::class -> { errorMsg += "mutable property"; nativeReflexUnit.isMutableProperty }
                SiNativeProperty::class -> { errorMsg += "property"; nativeReflexUnit.isProperty }
                SiNativeCallable::class -> { errorMsg += "callable"; nativeReflexUnit.isCallable }
                SiNativeParameter::class -> {
                    errorMsg += "parameter"
                    nativeReflexUnit.isParameter || nativeReflexUnit is String //Untuk mengakomodasi setter-getter native.
                }
                else -> {
                    errorMsg += "nativeReflexUnit"
                    nativeReflexUnit.isReflexUnit || nativeReflexUnit is String //Untuk mengakomodasi setter-getter native.
                }
        }){
            throw ReflexComponentExc(NativeReflexFactory::class, nativeReflexUnit::class, errorMsg)
        }
    }
    /**
     * Membungkus [nativeReflexUnit] menjadi representasi komponen reflex yg dapat
     * diproses dalam library ini, yaitu komponen [SiNative].
     *
     * Komponen [SiNative] yg dihasilkan tidak memiliki [SiDescriptor.host]
     * karena merupakan komponen dasar untuk hanya sekadar membungkus.
     *
     * Jika ingin mendapatkan [SiDescriptor.host], maka gunakan implementasi [SiReflex]
     * yg bukan merupakan [SiNative].
     */
    fun createNativeReflex(nativeReflexUnit: Any): SiNative{
        checkReflexUnitType(nativeReflexUnit)

        return object : SiNativeImpl() {
            override val implementation: Any = nativeReflexUnit
            override val name: String = qualifiedNativeName
            override val descriptor: SiDescriptor = createDescriptor() //Tidak punya native karena `this` udah native.
/*
                {
                prine("createNativeReflex() SiNativeImpl() descriptor is created. name= $name native= $implementation")
                val desc= createDescriptor()
                prine("createNativeReflex() SiNativeImpl() descriptor has done created. desc= $desc")
                desc
            }() //Tidak punya native karena `this` udah native.
 */
        }
    }

    fun createClassifier(nativeClass: Any): SiNativeClassifier{
        checkReflexUnitType(nativeClass, SiNativeClassifier::class)
        return object : SiNativeClassifierImpl(), SiNative by createNativeReflex(nativeClass) {}
    }

    fun <T> createCallable(
        nativeCallable: Any,
        parameters: List<SiNativeParameter>,
        callBlock: (args: Array<out Any?>) -> T
    ): SiNativeCallable<T>{
        checkReflexUnitType(nativeCallable, SiNativeCallable::class)
        return object : SiNativeCallableImpl<T>(callBlock), SiNative by createNativeReflex(nativeCallable) {
            override val parameters: List<SiNativeParameter> = parameters
        }
    }

    fun <T> createFunction(
        nativeFunction: Any,
        parameters: List<SiNativeParameter>,
        callBlock: (args: Array<out Any?>) -> T
    ): SiNativeFunction<T> {
        checkReflexUnitType(nativeFunction, SiNativeFunction::class)
        return object : SiNativeFunctionImpl<T>(callBlock), SiNativeCallable<T> by createCallable(
            nativeFunction,
            parameters,
            callBlock
        ) {}
    }

    fun <T> createProperty(
        nativeProperty: Any,
        receiverParamList: List<SiNativeParameter> = listOf(SiNativeParameterImplConst.receiver0),
        getBlock: (receivers: Array<out Any>) -> T
    ): SiNativeProperty<T> {
        checkReflexUnitType(nativeProperty, SiNativeProperty::class)
        return object: SiNativePropertyImpl<T>(getBlock), SiNativeCallable<T> by createCallable(
            nativeProperty, receiverParamList, getBlock as (Array<out Any?>) -> T
        ) {}
    }

    fun <T> createMutableProperty(
        nativeMutableProperty: Any,
        getBlock: (receivers: Array<out Any>) -> T,
        setBlock: (receivers: Array<out Any>, value: T) -> Unit,
        receiverParamList: List<SiNativeParameter> = mutableListOf(SiNativeParameterImplConst.receiver0)
    ): SiNativeMutableProperty<T> {
        checkReflexUnitType(nativeMutableProperty, SiNativeMutableProperty::class)
        return object: SiNativeMutablePropertyImpl<T>(getBlock, setBlock), SiNativeProperty<T> by createProperty(
            nativeMutableProperty, receiverParamList, getBlock
        ) {
            //Agar paramter tidak ikut dg delegate sehingga value yg dipass dapat di-inspeksi.
            override val parameters: List<SiNativeParameter> = receiverParamList + listOf(SiNativeParameterImplConst.setterValue1)
        }
    }

    /**
     * [nativeParameter] dapat berupa hanya string keterangan
     * untuk mengakomodasi parameter dari setter dan getter.
     */
    fun createParameter(
        nativeParameter: Any,
        index: Int, isOptional: Boolean, type: SiType
    ): SiNativeParameter{
        checkReflexUnitType(nativeParameter, SiNativeParameter::class)
        return object : SiNativeParamterImpl(), SiNative by createNativeReflex(nativeParameter) {
            override val index: Int = index
            override val isOptional: Boolean = isOptional
            override val type: SiType = type
        }
    }

}

 */
