package sidev.lib.reflex.core

import sidev.lib.platform.Platform
import sidev.lib.platform.platform
import sidev.lib.platform.setGlobalObject
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.lazy_list.asCached
import sidev.lib.console.prine
import sidev.lib.reflex.*
import sidev.lib.reflex.SiClassImpl
import sidev.lib.reflex.SiParameterImplConst
import sidev.lib.reflex.native_.*
import sidev.lib.reflex.native_.getConstrCallBlock
import sidev.lib.reflex.native_.getFuncDefaultCallBlock
import sidev.lib.reflex.native_.getModifiers
import sidev.lib.reflex.native_.getNativeConstructors

/**
 * Objek yg bertugas untuk me-reload data refleksi dari sebuah instance.
 *
 * Gagasan mekanisme reload komponen reflex:
 *   1. Ambil komponen refleksi native scr langsung menggunakan fungsi pada [_BasicNativeReflex.kt].
 *   2. Bungkus komponen native dg menggunakan fungsi pada [NativeReflexFactory].
 *   3. Bungkus lagi menjadi komponen reflex common dg mengggunakan fungsi pada [ReflexFactory].
 *   4. Pasangkan komponen reflex common dan sesuaiakan [SiDescriptor.host]-nya.
 */
object ReflexLoader{
/*
    internal object Manager{
        val loadedSiClass: MutableMap<String, SiClass<*>> by lazy{
            mutableMapOf<String, SiClass<*>>()
        }
    }
 */

/*
    fun loadSiNativeParams(nativeFunc: Any): Sequence<SiNativeParameter> = nativeFunc.getParameters().mapIndexed { i, param ->
        NativeReflexFactory.createParameter(param, i, getParamIsOptional(param), getParamType(param))
    }
    fun <T> toSiNativeFunc(siNativeFuncHost: SiNativeClassifier, nativeFunc: Any): SiNativeFunction<T>{
        val paramList= loadSiNativeParams(nativeFunc).toList()
        return NativeReflexFactory.createFunction<T>(nativeFunc, paramList, getFuncCallBlock(siNativeFuncHost.implementation, nativeFunc))
            .apply { parameters.forEach { it.mutableHost= this } }
            .apply { mutableHost= siNativeFuncHost }
    }

    fun <T> toSiNativeProp(nativeProp: Any): SiNativeProperty<T>
            = NativeReflexFactory.createProperty(nativeProp, getBlock = getPropValueBlock(nativeProp))

    fun <T> toSiNativeMutableProp(nativeMutableProp: Any): SiNativeMutableProperty<T>
            = NativeReflexFactory.createMutableProperty(nativeMutableProp, getPropValueBlock(nativeMutableProp), setPropValueBlock(nativeMutableProp))

    fun loadSiNativeFuncs(siNativeClass: SiNativeClassifier): Sequence<SiNativeFunction<*>>
            = siNativeClass.getNativeFunctions().map { toSiNativeFunc<Any?>(siNativeClass, it).apply { mutableHost= siNativeClass } }

    fun <T> loadSiNativeConstructors(siNativeClass: SiNativeClassifier): Sequence<SiNativeFunction<T>>
        = siNativeClass.getNativeConstructors().map { toSiNativeFunc<T>(siNativeClass, it).apply { mutableHost= siNativeClass }}
 */

    fun <T> loadSiConstructors(nativeClass: Any): Sequence<SiFunction<T>> =
        getNativeConstructors(nativeClass).map { nativeConstr ->
            ReflexFactory.createFunctionLazyly(
                createNativeWrapper(nativeConstr), null, //getReturnType(nativeConstr),
                //callBlock = getConstrCallBlock<T>(nativeClass, nativeConstr), //loadSiParam(nativeConstr).toList()
                //defaultCallBlock = getFuncDefaultCallBlock(nativeClass, nativeConstr),
                modifier = getModifiers(nativeConstr)
            ) //.also { func -> func.parameters.forEach { it.mutableHost= func } }
        }
                /*
            .toList().apply { forEach { func -> func.parameters.forEach { it.mutableHost= func } } }
            .asSequence()
                 */
/*
            .apply {
                forEach { func ->
                    func.parameters.forEach {
                        prine("after constr param = ${it.name} host==null => ${it.descriptor.host == null} it.mutableHost == null => ${it.mutableHost == null} host= ${(it.descriptor.host as? SiCallable<*>)?.name} mutabHost= ${(it.mutableHost as? SiCallable<*>)?.name} descriptor==null => ${it.descriptor == null}")
                    }
                }
            }
 */

    fun loadSiFunction(nativeClass: Any): Sequence<SiFunction<*>> =
        getNativeFunctions(nativeClass).map { nativeFunc ->
            ReflexFactory.createFunctionLazyly<Any?>(
                createNativeWrapper(nativeFunc), null, //getReturnType(nativeFunc),
                //callBlock = getFuncCallBlock<Any?>(nativeClass, nativeFunc), //loadSiParam(nativeFunc).toList()
                //defaultCallBlock = getFuncDefaultCallBlock(nativeClass, nativeFunc),
                modifier = getModifiers(nativeFunc)
            )
        }

    fun loadSiParam(nativeFunc: Any): Sequence<SiParameter> =
        getNativeParameters(nativeFunc).mapIndexed { i, nativeParam ->
            var modifier= 0
            if(getParamIsOptional(nativeParam))
                modifier= modifier or SiModifier.OPTIONAL.id
            if(getParamIsVararg(nativeParam))
                modifier= modifier or SiModifier.VARARG.id

            ReflexFactory.createParameterLazyly(
                createNativeWrapper(nativeParam), null, i, //getParamType(nativeParam),
                kind = getParamKind(nativeParam), defaultValue = getParamDefaultValue(nativeParam),
                modifier = modifier
            )
        }

    fun <T> loadSiImmutableProperty(nativeClass: Any): Sequence<SiProperty1<T, Any?>> =
        (getNativeProperties(nativeClass) - getNativeMutableProperties(nativeClass))
            .map { nativeProp ->
            ReflexFactory.createProperty1Lazyly<T, Any?>(
                createNativeWrapper(nativeProp), null, //getReturnType(nativeProp),
                getModifiers(nativeProp)
            )
        }

    fun <T> loadSiProperty(nativeClass: Any): Sequence<SiProperty1<T, Any?>> =
        getNativeProperties(nativeClass).map { nativeProp ->
            ReflexFactory.createProperty1Lazyly<T, Any?>(
                createNativeWrapper(nativeProp), null, //getReturnType(nativeProp),
                getModifiers(nativeProp)
            )
        }

    fun <T> loadSiMutableProperty(nativeClass: Any): Sequence<SiMutableProperty1<T, Any?>> =
        getNativeMutableProperties(nativeClass).map { nativeMutableProp ->
            ReflexFactory.createMutableProperty1Lazyly<T, Any?>(
                createNativeWrapper(nativeMutableProp), null, //getReturnType(nativeMutableProp),
                getModifiers(nativeMutableProp)
            )
        }


    fun <T: Any> loadSiConstructors(siClass: SiClass<T>): Sequence<SiFunction<T>> {
        return loadSiConstructors<T>(siClass.descriptor.native!!)
            .map { it.mutableHost= siClass; it }
    }
    fun <T: Any> loadSiMember(siClass: SiClass<T>): Sequence<SiCallable<*>> {
        val nativeClass= siClass.descriptor.native!!
        val siProps= loadSiImmutableProperty<T>(nativeClass)
        val siMutableProps= loadSiMutableProperty<T>(nativeClass)
        val siFunctions= loadSiFunction(nativeClass)
        return (siProps + siMutableProps + siFunctions).map { callable ->
            callable.mutableHost= siClass
/*
            callable.parameters.forEach {
                if(it.name != SiParameterImplConst.receiver0.name)
                    it.mutableHost= callable
            }
 */
            callable
        }
    }


/*
    fun <T: Any> loadClass(native: T): SiClass<T>{
        return loadClass<T>(
            NativeReflexFactory.createClassifier(native.getClass())
                .also {
                    if(it.name in Manager.loadedSiClass.keys)
                        return Manager.loadedSiClass[it.name] as SiClass<T>
                }
        )
    }
 */
    fun <T: Any> loadClass(native: T): SiClass<T> {
        val nativeClass= getNativeClass(native)
        val wrapper= createNativeWrapper(nativeClass)

        if(ReflexLoaderManager.checkCachedClass(nativeClass))
            return ReflexLoaderManager.loadCachedClass(nativeClass)

//        prine("loadClass() native= $native nativeClass= $nativeClass")

        val siClass= ReflexFactory.createClass<T>(wrapper, modifier = getModifiers(nativeClass))
                as SiClassImpl

        if(platform == Platform.JS){
            if(siClass.descriptor.native != null)
                @Suppress(SuppressLiteral.DEPRECATION)
                setGlobalObject(siClass.qualifiedName!!, siClass.descriptor.native!!)
        }
/*
        val siProps= loadSiImmutableProperty<T>(nativeClass)
//            .apply { forEach { it.mutableHost= siClass } }

        val siMutableProps= loadSiMutableProperty<T>(nativeClass)
//            .apply { forEach { it.mutableHost= siClass } }

        val siFunctions= loadSiFunction(nativeClass)
//            .apply { forEach { it.mutableHost= siClass }  }

        val siConstructors= loadSiConstructors<T>(nativeClass).toList()
            .apply { forEach { it.mutableHost= siClass } }

        siClass.constructors= siConstructors
        siClass.members= (siProps + siMutableProps + siFunctions).toList()
            .apply {
                forEach { callable ->
                    callable.mutableHost= siClass
                    callable.parameters.forEach {
                        if(it.name != SiParameterImplConst.receiver0.name)
                            it.mutableHost= callable
                    }
                }
            }
 */

        return siClass.also {
            ReflexLoaderManager.saveLoadedClass(it)
        }
    }
}