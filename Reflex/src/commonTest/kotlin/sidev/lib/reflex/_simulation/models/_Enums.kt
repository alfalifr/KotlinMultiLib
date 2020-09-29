package sidev.lib.reflex._simulation.models


enum class BoundStatus{
    Pending, Proses, Sukses, Semua
}

enum class BoundKind{
    INBOUND, OUTBOUND, CROSSDOCKING
}

enum class PackagingEnum{
    PLASTIK, BUBBLE_WRAP, KERDUS
}

enum class SendMethod{
    REGULAR, INSTANT, JOS
}

enum class CourierType{
    PERSONAL, THIRD_PARTY
}