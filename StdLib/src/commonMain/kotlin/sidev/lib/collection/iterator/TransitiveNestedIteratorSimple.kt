package sidev.lib.collection.iterator

/**
 * [NestedIterator] yang menyertakan [I1] sebagai argumen pada fungsi [getOutputIterator]
 * dan [O1] sebagai argumen pada fungsi [getInputIterator].
 */
interface TransitiveNestedIteratorSimple<I1, I2>: TransitiveNestedIterator<I1, I2, I1, I2>, NestedIteratorSimple<I2>{
    override fun getInputIterator(nowOutput: I2): Iterator<I2>? = getOutputIterator(nowOutput, getOutputArg(nowOutput))
    override fun getInputIterator(nowOutput: I2, outputArg: I1): Iterator<I2>? = getOutputIterator(nowOutput, getOutputArg(nowOutput))

    override fun getOutputIterator(nowInput: I2): Iterator<I2>? = getOutputIterator(nowInput, getOutputArg(nowInput))

    override fun getInputArg(nowInput: I2): I1 = getOutputArg(nowInput)
}