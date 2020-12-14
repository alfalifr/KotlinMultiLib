package sidev.lib._config_

enum class JavaVersion {
    JAVA_1_1, JAVA_1_2, JAVA_1_3, JAVA_1_4,
    JAVA_1_5, JAVA_1_6, JAVA_1_7, JAVA_1_8,
    JAVA_9, JAVA_10, JAVA_11, JAVA_12;

    companion object{
        /**
         * Versi Java yg pertama menggunakan penamaan tanpa embel-embel.
         * Contoh: Sebelum Java 9, penamaannya 1.x
         */
        const val MIN_JAVA_VERSION_WITH_EXPLICIT_VERSION_NAME= 9
    }

    /**
     * Versi keluaran, contoh versi 1.8 majorVersion-nya sama dg 8.
     */
    val majorVersion: Int
        get()= ordinal +1

    override fun toString(): String =
        if(majorVersion < MIN_JAVA_VERSION_WITH_EXPLICIT_VERSION_NAME) "1.$majorVersion"
        else majorVersion.toString()
}