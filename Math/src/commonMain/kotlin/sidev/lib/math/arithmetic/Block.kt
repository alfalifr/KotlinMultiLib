package sidev.lib.math.arithmetic

//import sidev.lib.console.prine
import sidev.lib.console.prinw
import sidev.lib.exception.IllegalArgExc
import sidev.lib.exception.IllegalStateExc
import sidev.lib.number.isNotNegative
import sidev.lib.text.*
import kotlin.jvm.JvmStatic


/**
 * Kumpulan operasi matematika yang berada pada tempat yg sama yg dipisahkan oleh tanda ekuasi.
 * Satu blok dapat terdiri dari bbrp nested blok (yg dipisahkan dg tanda kurung).
 */
interface Block: Calculatable {
    companion object {
        /**
         * Mengubah [blockStr] menjadi [Block].
         * [blockStr] tidak boleh mengandung tanda persamaan / pertidak-samaan karena
         * [blockStr] dikhususkan untuk string yg merepresentasikan block pada salah satu sisi yang dipisahkan
         * oleh tanda persamaan/ pertidak-samaan.
         */
        @JvmStatic
        fun parse(blockStr: String): Block {
            val resBlock= blockOf()
            var block= resBlock
            var openingBracket= 0
//            var closingBracket= 0

            var currOpParent: Operation?= null
            var currOp: Operation?= null
            var currNum: Int?= null
            var currName: String?= null
            var currElement: Calculatable?= null

            var i= 0
            fun Block.addCurrOperation(){
/*
                prine("i= $i ch= ${ try{blockStr[i]} catch (e: Exception){ "<IndexOutOfBound>" } }")
                prine("this= $this")
                prine("currOp= $currOp")
                prine("currNum= $currNum")
                prine("currName= $currName")
                prine("currElement= $currElement")
// */

                if(currElement == null)
                    currElement= if(currName != null) variableOf(currName!!, currNum!!)
                    else constantOf(currNum!!)

                if(currOp != null)
                    addOperation(currElement!!, currOp!!, prioritizePrecedence = false)
                else
                    setFirstElement(currElement!!)

                currElement= null
                currName= null
                currNum= null
            }

            val lastIndex= blockStr.length -1
            val len= blockStr.length
            while(i < len){
                val ch= blockStr[i]
//                prine("ch itr ch= $ch i= $i")
                when {
                    ch == '(' -> {
                        openingBracket++
                        currOpParent= currOp
                        currOp= null
                        block= blockOf(operationLevel = 10, parentBlock = block)
                    }
                    ch == ')' -> {
                        if(--openingBracket < 0)
                            throw IllegalStateExc(stateOwner = this::class, currentState = "\")\" terlalu banyak")
                        try { block.addCurrOperation() }
                        catch (e: NullPointerException) {
                            if(blockStr.prevNonWhitespaceChar(i-1) != ')')
                                throw IllegalStateExc(detMsg = "Terjadi kesalahan dalam penulisan block.")
                        }

                        currOp= currOpParent
                        currElement= block
                        currOpParent= null
                        block= block.parentBlock!!
                        block.addCurrOperation()
                    }
                    ch.isMathOperator() -> {
                        try { block.addCurrOperation() }
                        catch (e: NullPointerException) { /*abaikan karena kesalahan terjadi karena fungsi addCurrOperation() udah dipanggil duluan pas ')'*/ }

                        currOp= Operation.from(ch)
                        if(block.operationLevel > currOp.level)
                            (block as BlockImpl).operationLevel= currOp.level
                    }
                    ch.isDigit() -> {
                        val u = if(i < lastIndex) blockStr.indexOfWhere(i+1) { !it.isDigit() }
                        else len

                        if(u.isNotNegative()){
                            currNum= blockStr.substring(i, u).toInt()
                            i= u-1
                        }
                    }
                    ch.isLetter() -> {
                        val u = if(i < lastIndex) blockStr.indexOfWhere(i+1) { !it.isLetter() }
                        else len

                        if(u.isNotNegative()){
                            currName= blockStr.substring(i, u)
                            i= u-1
                        }
                    }
                }
                i++
            }
            if(openingBracket > 0)
                throw IllegalStateExc(stateOwner = this::class, currentState = "\"(\" terlalu banyak")

//            prine(" ===== akhir ===== currElement= $currElement currNum= $currNum currName= $currName")
            if(currNum != null)
                block.addCurrOperation()

            return resBlock
        }
    }
    val operationLevel: Int

    override val nInput: Int
        get() = elements.fold(0) { acc, calculatable -> acc + calculatable.nInput }

    /**
     *
     */
    val parentBlock: Block?

    /**
     * Kumpulan element [Calculatable] yang terhubung dengan [operations] dg `level` yang sama.
     */
    val elements: List<Calculatable>

    /**
     * Jumlah [elements] yang berupa [Variable].
     */
    val varCount: Int
        get() {
            var i= 0
            for(e in elements){
                if(e is Variable<*> || e.nInput == 1)
                    i++
            }
            return i
        }

    /**
     * Jumlah [elements] yang berupa [Constant].
     */
    val constCount: Int
        get() {
            var i= 0
            for(e in elements){
                if(e is Constant<*> || e.nInput == 0)
                    i++
            }
            return i
        }

    /**
     * Jumlah [elements] selain [Variable] dan [Constant].
     */
    val otherElementCount: Int
        get() {
            var i= 0
            for(e in elements){
                if(e.nInput > 2)
                    i++
            }
            return i
        }

    /**
     * Kumpulan [Operation] yang digunakan untuk mengolah [elements].
     * Jml [operations] harus n-1 jml [elements].
     * [operations] memiliki [Operation.level] yang sama untuk 1 `SubBlock`, yaitu [operationLevel].
     */
    val operations: List<Operation>

    fun setFirstElement(element: Calculatable)

    /**
     * return `Block` yang menjadi akar, yaitu `Block` yg punya [operationLevel] lebih rendah.
     */
    fun addOperation(
        element: Calculatable, operation: Operation,
        elementIndex: Int = elements.size, prioritizePrecedence: Boolean= true
    )

    private fun Map<String, Number>.getVarNum(varr: Variable<*>): Number = this[varr.name]
        ?: throw IllegalArgExc(paramExcepted = arrayOf("calculate() `namedNums`"), detailMsg = "Tidak terdapat nilai untuk variabel \"${varr.name}\" dg coeficient \"${varr.coeficient}\".")


    override operator fun invoke(vararg namedNums: Pair<String, Number>): Number = calculate(*namedNums)
    override fun calculate(vararg namedNums: Pair<String, Number>): Number {
        val numMap= mapOf(*namedNums)
        val elItr= elements.iterator()
        val opItr= operations.iterator()

        var res= elItr.next().let { calc ->
            val res= when(calc) {
                is Constant<*> -> calc()
                is Variable<*> -> calc(numMap.getVarNum(calc))
                else -> calc(*namedNums)
//                else -> calc(*namedNums.toArrayOf { it.second })
            }
            res
        }
//        prine("Block calculate first= $res owner= $this")

        while(elItr.hasNext()){
            val el= elItr.next()
            val op= opItr.next()
            res= op(res, when(el) {
                is Constant<*> -> el()
                is Variable<*> -> el(numMap.getVarNum(el))
                else -> el(*namedNums)
//                else -> el(*namedNums.toArrayOf { it.second })
            })
//            prine("Block calculate e= $el op= $op owner= $this")
//            prine("Block calculate res= $res owner= $this")
        }
        return res

    }

    /*
    private fun Array<*>.getVarNum(varr: Variable<*>): Number = when(val fir= first()){
        is Pair<*, *> -> try {
            (this as Array<Pair<String, Number>>).find { it.first == varr.name }!!.second
        } catch (e: NullPointerException) {
            throw IllegalArgExc(paramExcepted = arrayOf("calculate() `namedNums`"), detailMsg = "Tidak terdapat nilai untuk variabel \"${varr.name}\" dg coeficient \"${varr.coeficient}\".")
        }
        is Number -> fir
        else -> throw IllegalStateExc(stateOwner = this::class, currentState = "Array<*>", expectedState = "Array<Pair<String, Number>> || Array<Number>")
    }
 */

/*
    @Deprecated("Gunakan calculate() dg Pair<String, Number>", level = DeprecationLevel.ERROR,
        replaceWith = ReplaceWith("super.invoke(*nums)")
    )
    override fun invoke(vararg nums: Number): Number = super.invoke(*nums)

    @Deprecated("Gunakan calculate() dg Pair<String, Number>", level = DeprecationLevel.ERROR)
    override fun calculate(vararg nums: Number): Number {
        val varCount= varCount
        if(nums.size < varCount)
            throw IllegalArgExc(paramExcepted = arrayOf("numForVars"), detailMsg = "Param `numForVars`.size (${nums.size}) < varCount ($varCount)")

        val elItr= elements.iterator()
        val opItr= operations.iterator()
        val decreasingNumList= nums.toMutableList()

        var res= elItr.next().let { calc ->
            val res= when(calc) {
                is Constant<*> -> calc()
                is Variable<*> -> calc(decreasingNumList.removeFirst())
                else -> calc(*decreasingNumList.toTypedArray()).also {
                    for(i in 0 until calc.nInput)
                        decreasingNumList.removeFirst()
                }
            }
            res
        }

        prine("Block calculate first= $res owner= $this")

        while(elItr.hasNext()){
            val el= elItr.next()
            val op= opItr.next()
            res= op(res, when(el) {
                is Constant<*> -> el()
                is Variable<*> -> el(decreasingNumList.removeFirst())
                else -> el(*decreasingNumList.toTypedArray()).also {
                    for(i in 0 until el.nInput)
                        decreasingNumList.removeFirst()
                }
            })

            prine("Block calculate e= $el op= $op owner= $this")
            prine("Block calculate res= $res owner= $this")
        }
        return res
    }
 */
}

internal class BlockImpl(
    firstElement: Calculatable = NullCalculatable,
    override var operationLevel: Int = 1,
    override val parentBlock: Block? = null
): Block{
    override val elements: List<Calculatable> = mutableListOf()
    override val operations: List<Operation> = mutableListOf()

    init {
        (elements as MutableList) += firstElement
    }

    override fun setFirstElement(element: Calculatable) {
        (elements as MutableList)[0]= element
    }

    //
    //1+2+3-4
    //1+2+(3*5)-4
    //1+2+(3*5/7)-4
    //1+2+(3*5)-(10/7)-4
    override fun addOperation(
        element: Calculatable, operation: Operation,
        elementIndex: Int, prioritizePrecedence: Boolean
    ) {
        if(elements.first() == NullCalculatable){
            prinw("Block.addOperation() elemen awal masih `NullCalculatable`, `operation` diabaikan.")
            setFirstElement(element)
            return
        }

        if(prioritizePrecedence){
            if(operation.level == operationLevel) {
                (elements as MutableList).add(elementIndex, element)
                (operations as MutableList).add(
                    if(elementIndex > 1) elementIndex -1
                    else 0
                    , operation
                )
            } else if(operation.level > operationLevel){
                val firstElement= elements[elementIndex-1]
//                    try{  } catch (e: IndexOutOfBoundsException) { elements.last() }

                val newBlock= BlockImpl(firstElement, operation.level, this)
                newBlock.addOperation(element, operation)
                (elements as MutableList)[elementIndex -1]= newBlock
            } else {
                val tailElements= try{ elements.subList(elementIndex, elements.lastIndex) }
                    catch (e: Exception) { listOf(elements.last()) }
                val tailOps= try{ operations.subList(elementIndex-1, operations.lastIndex) }
                    catch (e: Exception) { listOf(operations.last()) }

                val tailBlock= BlockImpl(element, operationLevel, parentBlock)
                tailElements.forEachIndexed { index, calculatable ->
                    tailBlock.addOperation(calculatable, tailOps[index])
                    (elements as MutableList).removeLast()
                    (operations as MutableList).removeLast()
                }
                val indexOfThisBlock= parentBlock!!.elements.indexOf(this)
                parentBlock.addOperation(tailBlock, operation, indexOfThisBlock +1)
            }
        } else {
            (elements as MutableList).add(elementIndex, element)
            (operations as MutableList).add(
                if(elementIndex > 1) elementIndex -1
                else 0
                , operation
            )
        }
    }

    override fun toString(): String {
//        var bracketChecpointIndex= 0 //Untuk menandai index start dari "("

        var res= ""
        val elItr= elements.iterator()
        val opItr= operations.iterator()

        var e= elItr.next()
        var op: Operation

        res += if(e !is Block) e.toString() else "($e)"
        var i= 0
        while(elItr.hasNext()){
            e= elItr.next()
            op= opItr.next()

            val eStr= if(e !is Block) e.toString() else "($e)"
            if(op.level > operationLevel && i > 0){
                res = "($res"
                res += ") $op $eStr"
            } else {
                res += " $op $eStr"
            }
            i++
        }
        res += ""
        return res
    }
}