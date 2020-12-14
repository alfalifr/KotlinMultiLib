package sidev.lib.environment

import sidev.lib.exception.UnavailableOperationExc
import sidev.lib.reflex.js.JsClass_
import sidev.lib.reflex.js.kotlin
import kotlin.reflect.KClass

actual val KClass<*>.`package`: Package
    get()= throw UnavailableOperationExc(
        Package::class, "val KClass<*>.`package`",
        "Gunakan val SiClass<*>.`package`."
    )

val JsClass<*>.`package`: Package
    get()= kotlin.`package`

val JsClass_<*>.`package`: Package
    get()= kotlin.`package`