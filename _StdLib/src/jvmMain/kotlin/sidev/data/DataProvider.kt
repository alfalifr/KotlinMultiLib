package sidev.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sidev.lib.collection.ReadOnlyList
import sidev.lib.collection.asReadOnly
import sidev.lib.exception.IllegalArgExc
import sidev.lib.reflex.jvm.rootPackage
import sidev.lib.structure.data.value.indexes
import sidev.lib.structure.data.value.IndexedValue
import java.io.InputStream
import java.util.*


/**
 * Interface umum bagi objek yang menyediakan record dari file.
 */
interface DataProvider: Iterable<List<String>> {
    val headerList: ReadOnlyList<String>

    /**
     * Nama file yang menjadi sumber data dari `this`.
     * Property ini juga termasuk ekstensi.
     */
    val fileName: String

    /**
     * Range index yang berisi record, tidak termasuk header.
     */
    val recordRange: IntRange

    //    fun cacheToList()
    fun clearCache()

    operator fun get(header: String, rowIndex: Int): String {
        val index= headerList.indexOf(header.toLowerCase())
        if(index !in headerList.indices)
            throw IllegalArgExc(paramExcepted = arrayOf("header"), detailMsg = "Param `header` ($header) memiliki `index` ($index) di luar `headerList.indices` (${headerList.indices})")
        val row= this[rowIndex]
        return row[index]
    }
    operator fun get(index: Int): ReadOnlyList<String>
///*
    fun getAsync(
        header: String, rowIndex: Int,
        onProgress: ((IndexedValue<ReadOnlyList<String>>) -> Unit)?= null,
        onResult: (String) -> Unit
    ): Job = GlobalScope.launch {
        val str= get(header, rowIndex)
        onResult(str)
    }
    fun getAsync(
        index: Int,
        onProgress: ((IndexedValue<ReadOnlyList<String>>) -> Unit)?= null,
        onResult: (ReadOnlyList<String>) -> Unit
    ): Job = GlobalScope.launch {
        val str= get(index)
        onResult(str)
    }
// */
}

internal abstract class DataProviderImpl: DataProvider {
    //    abstract val valStr: String
    abstract val headerStr: String
    abstract override val fileName: String
    abstract val loaderOwner: Any

    private lateinit var scanner: Scanner
    private var currPointer= 0
    private var list: List<ReadOnlyList<String>>?= null
    override val headerList: ReadOnlyList<String> by lazy { headerStr.split(";").map { it.replace("\"", "") }.asReadOnly(false) }
    /*
        override fun cacheToList(){
            if(list != null) return
            list= valStr.split("\n").map { it.split(";").map { it.replace("\"", "") } }
        }
     */
    protected abstract fun inputStream(): InputStream
    private fun initScanner() {
//        val fileName= if(fileName.endsWith(".csv")) fileName else "$fileName.csv"
        val stream= try{ Source[fileName, loaderOwner] }
            catch (e: IllegalArgExc){ inputStream() } //File("_src/$fileName")
//        prine(file.absolutePath)
//        prine(file.exists())
//        val file= File(File("src/$fileName").absolutePath)
        scanner= Scanner(stream, Charsets.UTF_8.name())
    }
    private fun initCache(){
        if(list != null) return
        list= mutableListOf()
        initScanner()
        currPointer= 0
    }
    final override fun clearCache(){
        (list as MutableList).clear()
        list= null
    }

    private fun checkRecordRange(index: Int) {
        if(index !in recordRange)
            throw IllegalArgExc(
                paramExcepted = arrayOf("index"),
                detailMsg = "Param `index` ($index) tidak berada di antara nilai $recordRange"
            )
    }

    private fun splitLineToColumn(line: String): ReadOnlyList<String> =
        line.split(";").map { it.replace("\"", "") }.asReadOnly(false)

    final override operator fun get(header: String, rowIndex: Int): String {
        checkRecordRange(rowIndex)
        return super.get(header, rowIndex)
    }
/*
    {
        val index= headerList.indexOf(header.toLowerCase())
        if(index !in headerList.indices)
            throw IllegalArgExc(paramExcepted = arrayOf("header"), detailMsg = "Param `header` ($header) memiliki `index` ($index) di luar `headerList.indices` (${headerList.indices})")
        val row= this[rowIndex]
        return row[index]
    }
 */
    final override operator fun get(index: Int): ReadOnlyList<String> {
        checkRecordRange(index)
        initCache()
        val mutList= list as MutableList
//        prine("currPointer <= index => ${currPointer <= index} scanner.hasNextLine() => ${scanner.hasNextLine()} scanner.hasNext() => ${scanner.hasNext()}")
        while(currPointer <= index && scanner.hasNextLine()) {
            mutList += splitLineToColumn(scanner.nextLine())
            currPointer++
        }
        if(currPointer <= index)
            throw IllegalArgExc(paramExcepted = arrayOf("index"), detailMsg = "Param `index` ($index) melebihi panjang data ($currPointer)")
        return mutList[index]
    }

    final override fun getAsync(
        header: String,
        rowIndex: Int,
        onProgress: ((IndexedValue<ReadOnlyList<String>>) -> Unit)?,
        onResult: (String) -> Unit
    ): Job = getAsync(rowIndex, onProgress) {
        checkRecordRange(rowIndex)
        val index= headerList.indexOf(header.toLowerCase())
        if(index !in headerList.indices)
            throw IllegalArgExc(paramExcepted = arrayOf("header"), detailMsg = "Param `header` ($header) memiliki `index` ($index) di luar `headerList.indices` (${headerList.indices})")
//        val row= this[rowIndex]
        onResult(it[index])
    }
    final override fun getAsync(
        index: Int,
        onProgress: ((IndexedValue<ReadOnlyList<String>>) -> Unit)?,
        onResult: (ReadOnlyList<String>) -> Unit
    ): Job {
        checkRecordRange(index)
        initCache()
        val mutList= list as MutableList
//        prine("currPointer <= index => ${currPointer <= index} scanner.hasNextLine() => ${scanner.hasNextLine()} scanner.hasNext() => ${scanner.hasNext()}")
        return GlobalScope.launch {
            while(currPointer <= index && scanner.hasNextLine()) {
                mutList += splitLineToColumn(scanner.nextLine()).also {
                    onProgress?.invoke(currPointer indexes it)
                }
                currPointer++
            }
            if(currPointer <= index)
                throw IllegalArgExc(paramExcepted = arrayOf("index"), detailMsg = "Param `index` ($index) melebihi panjang data ($currPointer)")
            onResult(mutList[index])
        }
//        return mutList[index]
    }

    override fun iterator(): Iterator<List<String>> = object: Iterator<List<String>> {
        val mutList: MutableList<ReadOnlyList<String>> by lazy {
            initCache()
            list as MutableList
        }
        var i= 0

        override fun hasNext(): Boolean = i < mutList.size || scanner.hasNextLine()
        override fun next(): List<String> {
            val next= if(i < mutList.size) mutList[i]
            else splitLineToColumn(scanner.nextLine()).also {
                mutList += it
                currPointer++
            }
            i++
            return next
        }
    }

    override fun toString(): String = "DataProviderOf `${loaderOwner.javaClass.rootPackage.name}/$fileName`"
}