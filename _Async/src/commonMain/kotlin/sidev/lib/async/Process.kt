package sidev.lib.async

import sidev.lib.console.prine
import sidev.lib.exception.IllegalStateExc
import sidev.lib.structure.prop.TagProp

interface Process<I, P, R>: TagProp<String> {
    enum class Status {
        NOT_STARTED,
        RUNNING,
        FINISHED,
        CANCELED
    }
    val status: Status
    fun postResult(result: R)
    fun postProgress(progress: P)
    fun start(input: I) //Harusnya gak perlu pake callback karena ini dilakukan scr async.
    fun cancel(code: Int = 0) //Ini juga gak perlu callback
}


internal open class ProcessImpl<I, P, R>(
    private val block: (I) -> R,
    override val tag: String?= null
): Process<I, P, R> {
    protected val callbacks: MutableList<ProcessCallback<I, P, R>> = mutableListOf()
    final override var status: Process.Status= Process.Status.NOT_STARTED
        private set

    override fun postResult(result: R) {
        status= Process.Status.FINISHED
        callbacks.forEach { it.onResult(result) }
    }

    override fun postProgress(progress: P) {
        callbacks.forEach { it.onProgress(progress) }
    }

    override fun start(input: I) {
        if(status == Process.Status.RUNNING)
            throw IllegalStateExc(
                stateOwner = this::class,
                currentState = "status == Process.Status.RUNNING",
                expectedState = "status != Process.Status.RUNNING",
                detMsg = ""
            )
        status= Process.Status.RUNNING
        postResult(block(input))
    }

    override fun cancel(code: Int) {
        status= Process.Status.CANCELED
        callbacks.forEach { it.onCancel(code) }
    }
    override fun toString(): String = "${super.toString()}@${tag ?: ""}"
}