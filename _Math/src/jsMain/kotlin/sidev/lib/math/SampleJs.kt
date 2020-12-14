package sidev.lib.math

import sidev.lib.collection.duplicatUnion

actual class Sample {
    actual fun checkMe() = 12
}

actual object Platform {
    actual val name: String = "JS"
}

fun ada(){
    val list1= listOf(1,2,34)
    val list2= listOf(1,2,34)
    list1 duplicatUnion list2
}