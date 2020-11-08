package sidev.lib.collection.iterator

/**
 * [NestedIterator] yang menyertakan [I1] sebagai argumen pada fungsi [getOutputIterator]
 * dan [O1] sebagai argumen pada fungsi [getInputIterator].
 */
interface TransitiveNestedIterator<I1, I2, O1, O2>: NestedIterator<I2, O2> {
    fun getOutputIterator(nowInput: I2, inputArg: I1): Iterator<O2>?
    fun getInputIterator(nowOutput: O2, outputArg: O1): Iterator<I2>?

    fun getInputArg(nowInput: I2): I1
    fun getOutputArg(nowOutput: O2): O1

    override fun getOutputIterator(nowInput: I2): Iterator<O2>? = getOutputIterator(nowInput, getInputArg(nowInput))
    override fun getInputIterator(nowOutput: O2): Iterator<I2>? = getInputIterator(nowOutput, getOutputArg(nowOutput))
}

/*
/**
 * [NestedIterator] yang menyertakan [I1] sebagai argumen pada fungsi [getOutputIterator]
 * dan [O1] sebagai argumen pada fungsi [getInputIterator].
 */
interface TransitiveNestedIterator<I1, I2, O1, O2>: NestedIterator<I2, O2> {
    fun getOutputIterator(nowInput: I2, inputArg: I1): Iterator<O2>?
    fun getInputIterator(nowOutput: O2, outputArg: O1): Iterator<I2>?

    fun getInputArg(nowInput: I2): I1
    fun getOutputArg(nowOutput: O2): O1

    override fun getOutputIterator(nowInput: I2): Iterator<O2>? = getOutputIterator(nowInput, getInputArg(nowInput))
    override fun getInputIterator(nowOutput: O2): Iterator<I2>? = getInputIterator(nowOutput, getOutputArg(nowOutput))
}

 */