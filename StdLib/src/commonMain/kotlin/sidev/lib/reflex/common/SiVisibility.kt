package sidev.lib.reflex.common

//Agar dapat dijalankan di Js.
enum class SiVisibility: SiReflex by SiReflexImpl() {
    PUBLIC,

    PROTECTED,

    INTERNAL,

    PRIVATE
}