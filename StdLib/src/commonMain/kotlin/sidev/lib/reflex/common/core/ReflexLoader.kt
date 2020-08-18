package sidev.lib.reflex.common.core

import sidev.lib.console.prine
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.native.*
import sidev.lib.reflex.common.native.getNativeFunctions

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
            ReflexFactory.createFunction(
                createNativeWrapper(nativeConstr), null, getReturnType(nativeConstr),
                loadSiParam(nativeConstr).toList(), callBlock = getFuncCallBlock<T>(nativeClass, nativeConstr)
            )
        } //.apply { forEach { func -> func.parameters.forEach { it.mutableHost= func } } }

    fun loadSiFunction(nativeClass: Any): Sequence<SiFunction<*>> =
        getNativeFunctions(nativeClass).map { nativeFunc ->
            ReflexFactory.createFunction(
                createNativeWrapper(nativeFunc), null, getReturnType(nativeFunc),
                loadSiParam(nativeFunc).toList(), callBlock = getFuncCallBlock<Any?>(nativeClass, nativeFunc)
            )
        }

    fun loadSiParam(nativeFunc: Any): Sequence<SiParameter> =
        getNativeParameters(nativeFunc).mapIndexed { i, nativeParam ->
            prine("loadSiParam()= param= $nativeParam")
            ReflexFactory.createParameter(
                createNativeWrapper(nativeParam), null, i,
                getParamIsOptional(nativeParam), getParamType(nativeParam),
                kind = getParamKind(nativeParam)
            )
        }

    fun <T> loadSiImmutableProperty(nativeClass: Any): Sequence<SiProperty1<T, Any?>> =
        (getNativeProperties(nativeClass) - getNativeMutableProperties(nativeClass))
            .map { nativeProp ->
            ReflexFactory.createProperty1<T, Any?>(
                createNativeWrapper(nativeProp), null, getReturnType(nativeProp)
            )
        }

    fun <T> loadSiMutableProperty(nativeClass: Any): Sequence<SiMutableProperty1<T, Any?>> =
        getNativeMutableProperties(nativeClass).map { nativeMutableProp ->
            ReflexFactory.createMutableProperty1<T, Any?>(
                createNativeWrapper(nativeMutableProp), null, getReturnType(nativeMutableProp)
            )
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
    fun <T: Any> loadClass(native: T): SiClass<T>{
        val nativeClass= getNativeClass(native)
        val wrapper= createNativeWrapper(nativeClass)

        if(ReflexLoaderManager.checkCachedClass(nativeClass))
            return ReflexLoaderManager.loadCachedClass(nativeClass)
//        prine("siNativeClass $siNativeClass class= ${siNativeClass::class}")
        val siClass= ReflexFactory.createClass<T>(wrapper) as SiClassImpl

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
        return siClass.also {
            ReflexLoaderManager.saveLoadedClass(it)
        }
    }
}