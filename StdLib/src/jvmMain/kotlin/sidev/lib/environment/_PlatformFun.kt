@file:JvmName("_PlatformFunJvm")
package sidev.lib.environment

actual val platform: Platform
    @JvmName("platform") get()= Platform.JVM