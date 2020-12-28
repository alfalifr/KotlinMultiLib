package sidev.lib.async

import sidev.lib.structure.prop.TagProp

interface ProcessCallback<I, P, R>: TagProp<String> {
    fun onProgress(progress: P)
    fun onResult(result: R)
    fun onStart(input: I)
    fun onCancel(code: Int)
}

internal open class ProcessCallbackImpl<I, P, R>(
    private val onResult: (R) -> Unit = {},
    override val tag: String? = null,
    private val onProgress: (P) -> Unit = {},
    private val onCancel: (Int) -> Unit = {},
    private val onStart: (I) -> Unit
): ProcessCallback<I, P, R> {
    override fun onProgress(progress: P) = onProgress.invoke(progress)
    override fun onResult(result: R) = onResult.invoke(result)
    override fun onStart(input: I) = onStart.invoke(input)
    override fun onCancel(code: Int) = onCancel.invoke(code)
    override fun toString(): String = "${super.toString()}@${tag ?: ""}"
}