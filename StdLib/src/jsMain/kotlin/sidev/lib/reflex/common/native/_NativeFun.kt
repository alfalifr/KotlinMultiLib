package sidev.lib.reflex.common.native

import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.core.ReflexLoader
import sidev.lib.universal.`val`.SuppressLiteral
import kotlin.reflect.KClass


/**
 * Mengambil instance [SiClass] yg merepresentasikan `this.extension`.
 * property ini tidak dilakukan pada level [KClass] karena untuk mendapatkan
 * fungsi constructor dari kelas [KClass] akan susah jika fungsi Js didefinisikan
 * lewat fungsi [js()][js].
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
val <T: Any> T.siClass: SiClass<T>
    get() = ReflexLoader.loadClass(this)