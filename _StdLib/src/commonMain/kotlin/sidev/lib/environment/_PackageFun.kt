package sidev.lib.environment

import sidev.lib.console.prinw
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.findAnnotation
import sidev.lib.reflex.getHashCode
import sidev.lib.reflex.kotlin
import kotlin.reflect.KClass

expect val KClass<*>.`package`: Package
val SiClass<*>.`package`: Package
    get()= try{ kotlin.`package` }
        catch (e: Throwable){
            findAnnotation<sidev.lib.annotation.SiPackage>().run {
                if(this != null)
                    createPackage(name, title = title, desc = desc, vendor = vendor, author = author)
                else {
                    prinw("Tidak terdapat informasi terkait paket pada kelas ${this@`package`}, return emptyPackage()")
                    emptyPackage()
                }
            }
        }

fun emptyPackage(): Package = createPackage("")

fun createPackage(
    name: String, versionName: String?= null, versionNumber: Long= -1,
    title: String?= null, desc: String?= null,
    vendor: String?= null, author: String?= null
): Package = object : Package {
    override val name: String= name
    override val versionName: String?= versionName
    override val versionNames: Array<String> = versionName?.split(".")?.toTypedArray() ?: emptyArray()
    override val versionNumber: Long= versionNumber
    override val title: String?= title
    override val description: String?= desc
    override val vendor: String?= vendor
    override val author: String?= author

    override fun equals(other: Any?): Boolean = if(other is Package){
        name == other.name && versionName == other.versionName && versionNumber == other.versionNumber
                && vendor == other.vendor && author == other.author
    } else hashCode() == other.hashCode()

    override fun hashCode(): Int = getHashCode(name, versionName, versionNumber, vendor, author)
    override fun toString(): String = "Package $name version: $versionName number: $versionNumber"
}