@file:JvmName("_ExcFunJvm")
package sidev.lib.exception

actual val Exception.isUninitializedExc: Boolean
    get()= this is UninitializedPropertyAccessException