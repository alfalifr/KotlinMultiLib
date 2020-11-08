package sidev.lib.environment

import sidev.lib.reflex.SiReflex

/**
 * Interface yang berisi informasi terkait paket yang terdapat pada project.
 */
interface Package: SiReflex {
    /**
     * Nama dari suatu paket yang dipisahkan dengan titik `.`.
     * Contoh: sidev.lib.environment
     */
    val name: String

    /**
     * Nama versi untuk paket yang terdiri dari major, minor, micro, dll. yang dipisahkan dengan titik `.`.
     * `null` jika tidak dispesifikasikan.
     * Contoh: 0.0.1x, terdapat 3 elemen nama versi, yaitu major= 0, minor= 0, micro= 1x.
     */
    val versionName: String?

    /**
     * Array komponen yang membentuk [versionName], yaitu dengan memisahkan tiap titik `.` dan meletakannya pada [Array].
     * [Array] yang dikembalikan akan kosong jika [versionName] == `null`.
     */
    val versionNames: Array<String>
        get()= versionName?.split(".")?.toTypedArray() ?: emptyArray()

    /**
     * Urutan sejak versi ditetapkan. Berupa angka bulat karena menunjukan urutan.
     * [versionNumber] memiliki nilai negatif jika [versionName] == `null`
     * Contoh: Terdapat versi 0.0.1xx dan 0.0.1x. Versi 0.0.1x memiliki [versionNumber] 2.
     */
    val versionNumber: Long

    /**
     * Judul yang diberikan untuk paket ini pada versi tertentu. `null` jika tidak memiliki judul.
     */
    val title: String?

    /**
     * Deskripsi yang diberikan untuk paket ini pada versi tertentu. `null` jika tidak memiliki deskripsi.
     */
    val description: String?

    /**
     * Nama vendor yang memiliki hak atas paket ini. `null` jika tidak dispesifikasikan.
     */
    val vendor: String?

    /**
     * Nama penanggung jawab terhadap apa yang ada di paket ini.
     */
    val author: String?
}