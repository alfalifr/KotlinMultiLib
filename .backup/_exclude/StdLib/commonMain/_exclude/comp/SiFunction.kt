package sidev.lib.reflex.comp


interface SiFunction<out R>: SiCallable<R>

internal abstract class SiFunctionImpl<out R>: SiCallableImpl<R>(), SiFunction<R>