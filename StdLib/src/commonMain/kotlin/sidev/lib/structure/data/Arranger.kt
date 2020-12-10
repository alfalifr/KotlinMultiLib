package sidev.lib.structure.data

import sidev.lib.structure.util.Comparison
import sidev.lib.structure.util.Filter

/**
 * Interface yang dapat melakukan fungsi `sort`, `filter`, dan `search`.
 */
interface Arranger<T> {
    fun sort(comparator: Comparator<T>)
    fun filter(filter: Filter<T>)
    fun search(filter: Comparison<T>)
}