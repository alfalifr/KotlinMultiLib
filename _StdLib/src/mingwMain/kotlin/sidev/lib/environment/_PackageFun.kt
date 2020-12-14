package sidev.lib.environment

import sidev.lib.exception.UnavailableOperationExc
import kotlin.reflect.KClass

actual val KClass<*>.`package`: Package
    get()= throw UnavailableOperationExc(
        Package::class, "val KClass<*>.`package`",
        "Gunakan val SiClass<*>.`package`."
    )