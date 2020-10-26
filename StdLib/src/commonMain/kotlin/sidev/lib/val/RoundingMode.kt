package sidev.lib.`val`

enum class RoundingMode {
    /**
     * Pembulatan normal, yaitu 5 sbg batas (inclusive), untuk membulatkan ke atas.
     */
    HALF_UP,

    /**
     * Pembulatan normal, yaitu 5 sbg batas (exclusive), untuk membulatkan ke atas.
     */
    HALF_DOWN,

    /**
     * Pembulatan mendekati infinity positif.
     */
    CEIL,

    /**
     * Pembulatan menjauhi 0.
     */
    UP,

    /**
     * Pembulatan mendekati infinity negatif.
     */
    FLOOR,

    /**
     * Pembulatan mendekati 0.
     */
    DOWN,
}