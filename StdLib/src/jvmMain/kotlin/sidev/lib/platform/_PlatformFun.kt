@file:JvmName("_PlatformFunJvm")
package sidev.lib.platform

actual val platform: Platform
    @JvmName("platform") get()= Platform.JVM