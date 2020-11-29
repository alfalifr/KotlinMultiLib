package sidev.lib.structure.data

/**
 * Interface yang dapat mengatur ulang internal state (field) nya.
 */
interface Resettable {
    /**
     * Mengatur ulang semua field yang ada.
     */
    fun reset()
}