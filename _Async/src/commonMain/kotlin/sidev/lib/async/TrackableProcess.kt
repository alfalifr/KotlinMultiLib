package sidev.lib.async

import sidev.lib.console.prine
import sidev.lib.exception.IllegalStateExc

interface TrackableProcess<I, P, R>: Process<I, P, R> {
    fun addCallback(callback: ProcessCallback<I, P, R>): Boolean
    fun addCallback(
        onStart: (I) -> Unit = {},
        onProgress: (P) -> Unit = {},
        tag: String?= null,
        onCancel: (Int) -> Unit = {},
        onResult: (R) -> Unit,
    ): Boolean = addCallback(callback(onStart, onProgress, tag, onCancel, onResult))

    fun addOnProgress(
        tag: String?= "<onProgress>",
        onProgress: (P) -> Unit
    ): Boolean = addCallback(callback({}, onProgress, tag, {}, {}))

    fun addOnCancel(
        tag: String?= "<onCancel>",
        onCancel: (Int) -> Unit
    ): Boolean = addCallback(callback({}, {}, tag, onCancel, {}))

    fun addOnStart(
        tag: String?= "<onStart>",
        onStart: (I) -> Unit
    ): Boolean = addCallback(callback(onStart, {}, tag, {}, {}))

    fun addOnResult(
        tag: String?= "<onResult>",
        onResult: (R) -> Unit
    ): Boolean = addCallback(callback({}, {}, tag, {}, onResult))
}


internal open class TrackableProcessImpl<I, P, R>(
    block: (I) -> R,
    override val tag: String?= null
): ProcessImpl<I, P, R>(block, tag), TrackableProcess<I, P, R> {
    override fun addCallback(callback: ProcessCallback<I, P, R>): Boolean {
        return if(status != Process.Status.RUNNING){
            callbacks += callback
            true
        } else {
            prine("Tidak dapat menambahkan `callback` ($callback) saat `this` `Process` msh berjalan.")
            false
        }
    }
}