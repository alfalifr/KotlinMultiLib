package sidev.lib.`val`

enum class Order {
    /**
     * Urutan yang dimulai dari terkecil hingga terbesar.
     */
    ASC,

    /**
     * Urutan yang dimulai dari terbesar hingga terkecil.
     */
    DESC;

    val reverse: Order get() = if(this == ASC) DESC else ASC
}