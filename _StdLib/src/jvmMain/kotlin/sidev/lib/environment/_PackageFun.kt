@file:JvmName("_PackageFunJvm")
package sidev.lib.environment

import sidev.lib.console.prinw
import kotlin.reflect.KClass
import java.lang.Package as JvmPkg

actual val KClass<*>.`package`: Package
    get()= createPackage(java.`package`)

val Class<*>.siPackage: Package
    get()= createPackage(`package`)

fun createPackage(jvmPkg: JvmPkg): Package = jvmPkg.run {
    prinw("Beberapa informasi terkait paket $this tidak tersedia karena diekstrak dari `java.lang.Package`.")
    createPackage(
        name,
        specificationVersion ?: implementationVersion,
        title = specificationTitle ?: implementationTitle,
        vendor = specificationVendor ?: implementationVendor
    )
}