package sidev.data

import sidev.lib.exception.IllegalArgExc
import java.io.InputStream
import java.lang.NullPointerException

object Source {
///    operator fun get(fileName: String): File = //this::class.java.getResourceAsStream("src/main/kotlin/sidev/data/quran/src/$fileName") File("src/main/kotlin/sidev/data/quran/src/$fileName")
    operator fun get(
        fileName: String,
        loader: ClassLoader = this.javaClass.classLoader ?: ClassLoader.getSystemClassLoader()
    ): InputStream = try {
        loader.getResourceAsStream("/$fileName")!!
    } catch (e: NullPointerException) {
        throw IllegalArgExc(
            paramExcepted = arrayOf("fileName"),
            detailMsg = "Resource `fileName` ($fileName) tidak tersedia di direktori `res`."
        )
    }
    operator fun get(
        fileName: String,
        pkgInstance: Any
    ): InputStream = get(fileName, pkgInstance.javaClass.classLoader)
}