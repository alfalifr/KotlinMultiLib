package sidev.lib.`val`

enum class Assignment {
    /**
     * Inisialisasi / nilai awal.
     */
    INIT,

    /**
     * Meng-assign nilai baru ke variabel.
     */
    ASSIGN,

    /**
     * Meng-assign nilai yg sama ke variabel.
     */
    RE_ASSIGN,

    /**
     * Menghilangkan nilai dari suatu variabel dan status kembali ke [INIT].
     */
    REMOVE,

    /**
     * Kebalikan dari [INIT], yaitu menghilangkan nilai dari suatu variabel dan sekaligus menghilangkan variabel.
     */
    UN_INIT
}