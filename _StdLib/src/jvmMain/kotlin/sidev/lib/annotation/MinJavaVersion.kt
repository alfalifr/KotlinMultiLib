package sidev.lib.annotation

import sidev.lib._config_.JavaVersion

/**
 * Elemen yg di-anotasi dg ini menandakan bahwa butuh minimum versi Java.
 */
annotation class MinJavaVersion(val version: JavaVersion)