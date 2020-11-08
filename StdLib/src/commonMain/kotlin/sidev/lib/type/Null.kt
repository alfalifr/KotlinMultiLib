package sidev.lib.type

import sidev.lib.environment.Platform
import sidev.lib.environment.platform
import sidev.lib.environment.putInternalObjectOnGlobal
import sidev.lib.reflex.core.createType
import sidev.lib.reflex.si

/**
 * Tipe data yg digunakan untuk menunjukan tipe null.
 * Tipe ini digunakan pada proses [Any.inferredType] untuk menunjukan inferredType dari null.
 */
object Null{
    init{
//        prine("init Null")
        //TODO <23 Agustus 2020> => Tiba-tiba `this::class.si` menyebabkan `this Null` jadi unreachable dari kode.
        if(platform == Platform.JS)
            putInternalObjectOnGlobal(this)
    }
    val clazz by lazy { this::class.si }
    val type by lazy { clazz.createType(nullable = true) }
}