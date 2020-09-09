@file:JvmName("_ExcFunJvm")
package sidev.lib.exception

actual val Exception.isUninitializedExc: Boolean
    @JvmName("isUninitializedExc") get()= this is UninitializedPropertyAccessException