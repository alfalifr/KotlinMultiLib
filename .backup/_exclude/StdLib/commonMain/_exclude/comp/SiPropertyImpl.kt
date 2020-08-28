package sidev.lib.reflex.comp

import sidev.lib.reflex.core.ReflexFactory
import sidev.lib.reflex.core.ReflexTemplate
import sidev.lib.reflex.comp.native.getPropGetValueBlock
import sidev.lib.reflex.comp.native.getPropSetValueBlock


//internal expect fun <T, R> getPropertyValue1(receiver: T): R
//internal expect fun <T, R> setPropertyValue1(receiver: T, value: R)


internal abstract class SiPropertyGetter1<T, out R>(override val property: SiProperty<R>)
    : SiCallableImpl<R>(), SiProperty1.Getter<T, R> {
    override val callBlock: (args: Array<out Any?>) -> R by lazy{
        getPropGetValueBlock<R>(property.descriptor.native!!) as (Array<out Any?>) -> R
    }
    override val defaultCallBlock: ((args: Array<out Any?>) -> R)? = null
    override val name: String by lazy { "<get-${property.name}>" } //Agar [property] di-init dulu dan gak leak.
    override val returnType: SiType get()= property.returnType
    override val parameters: List<SiParameter> by lazy{ //listOf(SiParameterImplConst.receiver0)
        val template= SiParameterImplConst.receiver0
        listOf(ReflexFactory.createParameter(
            null, this, template.index,
            template.type, template.name, modifier = template.descriptor.modifier
        ))
    }
    override val typeParameters: List<SiTypeParameter> = emptyList()
}
internal abstract class SiPropertySetter1<T, R>(override val property: SiProperty<R>)
    : SiCallableImpl<Unit>(), SiMutableProperty1.Setter<T, R> {
    override val callBlock: (args: Array<out Any?>) -> Unit by lazy {
        { args: Array<out Any?> ->
            getPropSetValueBlock<R>(property.descriptor.native!!)(args as Array<out Any>, args[1] as R)
        }
    }
    override val defaultCallBlock: ((args: Array<out Any?>) -> Unit)? = null
    override val name: String by lazy { "<set-${property.name}>" } //Agar [property] di-init dulu dan gak leak.
    override val returnType: SiType = ReflexTemplate.typeUnit
    override val parameters: List<SiParameter> by lazy { //listOf(SiParameterImplConst.setterValue1)
        val template= SiParameterImplConst.setterValue1
        listOf(ReflexFactory.createParameter(
            null, this, template.index,
            returnType, template.name, modifier = template.descriptor.modifier
        ))
    }
    override val typeParameters: List<SiTypeParameter> = emptyList()
}



internal abstract class SiProperty1Impl<T, out R>
    : SiCallableImpl<R>(), SiProperty1<T, R> {
    override val callBlock: (args: Array<out Any?>) -> R = { null as R } //Karena callBlocknya ikut SiProperty1, pake getter.
    override val defaultCallBlock: ((args: Array<out Any?>) -> R)? = null
    override val getter: SiProperty1.Getter<T, R> by lazy {
        ReflexFactory.createPropertyGetter1(this)
    }
    /** Property gak punya param, aksesornya yg punya. */
    override val parameters: List<SiParameter> by lazy { getter.parameters }
    override val typeParameters: List<SiTypeParameter> = emptyList()
/*
    override val hasBackingField: Boolean by lazy {
        ReflexFactoryHelper.hasBackingField(this, descriptor.native!!)
    }
 */
    override fun get(receiver: T): R {
//        prine("get()= receiver= $receiver prop= $this")
        return getter.call(receiver)
    }
    /** Call dari property sama dg call getter-nya. */
    override fun call(vararg args: Any?): R = super<SiProperty1>.call(*args) //get(args.first() as T)
}

internal abstract class SiMutableProperty1Impl<T, R>
    : SiProperty1Impl<T, R>(), SiMutableProperty1<T, R> {
    override val setter: SiMutableProperty1.Setter<T, R> by lazy { ReflexFactory.createPropertySetter1(this) }
    override fun set(receiver: T, value: R) = setter.call(receiver, value)
}