package sidev.lib

actual class Sample {
    actual fun checkMe() = 7
}

actual object Platform {
    actual val name: String = "Native"
}

class A
fun aaa(){
    A::class.qualifiedName
}