package sidev.lib.check


/**
 * Bentuk native dari [trya]. [catcha] memiliki satu parameter berupa [Any] karena pada Js, Kotlin Throwable bisa
 * saja bkn merupakan tipe error dari bawaan Js.
 */
actual fun <T> nativeTrya(
    ignoreError: Boolean,
    catcha: (nativeThrowable: Any) -> T?,
    trya: () -> T
): T? { //trya(ignoreError, catcha, trya)
    var res: T?= null
    js("try{ res = trya() } catch(e) { if(!ignoreError) throw e; res = catcha(e) }")
    return res
}


/**
 * Bentuk native dari [trya]. [catcha] memiliki satu parameter berupa [Any] karena pada Js, Kotlin Throwable bisa
 * saja bkn merupakan tipe error dari bawaan Js.
 */
fun nativeTrya2(
    ignoreError: Boolean,
    catcha: (nativeThrowable: Any) -> Any?,
    trya: () -> Any
): Any?  { //trya(ignoreError, catcha, trya)
    var res: Any?= null
    js("try{ res = trya() } catch(e) { if(!ignoreError) throw e; res = catcha(e) }")
    return res
}