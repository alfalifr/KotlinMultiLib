package sidev.data

import sidev.lib.structure.data.value.Var
import java.io.InputStream

fun dataProvider(
    headerStr: String,
    fileName: String,
    recordRange: IntRange,
    loaderOwner: Any,
    inputStreamGenerator: () -> InputStream
): DataProvider = object : DataProviderImpl(){
    override val recordRange: IntRange = recordRange
    override val headerStr: String = headerStr
    override val fileName: String = fileName
    override val loaderOwner: Any = loaderOwner
    override fun inputStream(): InputStream = inputStreamGenerator()
}

fun dataProvider(
    headerStr: String,
    fileName: String,
    recordRange: IntRange,
    loaderOwner: Var<Any>,
    inputStreamGenerator: () -> InputStream
): DataProvider = object : DataProviderImpl(){
    override val recordRange: IntRange = recordRange
    override val headerStr: String = headerStr
    override val fileName: String = fileName
    override val loaderOwner: Any
        get()= loaderOwner.value
    override fun inputStream(): InputStream = inputStreamGenerator()
}