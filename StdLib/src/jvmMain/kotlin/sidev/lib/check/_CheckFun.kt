@file:JvmName("_CheckFunJvm")

package sidev.lib.check


/**
 * Bentuk native dari [trya]. [catcha] memiliki satu parameter berupa [Any] karena pada Js, Kotlin Throwable bisa
 * saja bkn merupakan tipe error dari bawaan Js.
 */
actual fun <T> nativeTrya(
    ignoreError: Boolean,
    catcha: (nativeThrowable: Any) -> T?,
    trya: () -> T
): T? = trya(ignoreError, catcha, trya)
