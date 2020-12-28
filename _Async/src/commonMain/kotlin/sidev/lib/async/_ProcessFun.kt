package sidev.lib.async

fun <I, P, R> callback(
    onStart: (I) -> Unit,
    onProgress: (P) -> Unit,
    tag: String?= null,
    onCancel: (Int) -> Unit,
    onResult: (R) -> Unit,
): ProcessCallback<I, P, R> = ProcessCallbackImpl(onResult, tag, onProgress, onCancel, onStart)

fun <I, R> callback(
    onStart: (I) -> Unit = {},
    onCancel: (Int) -> Unit = {},
    tag: String?= null,
    onResult: (R) -> Unit,
): ProcessCallback<I, Nothing, R> = ProcessCallbackImpl(onResult, tag, {}, onCancel, onStart)


fun <I, P, R> process(
    tag: String?= null,
    block: (I) -> R
): Process<I, P, R> = ProcessImpl(block, tag)

fun <I, P, R> trackableProcess(
    tag: String?= null,
    block: (I) -> R
): TrackableProcess<I, P, R> = TrackableProcessImpl(block, tag)
