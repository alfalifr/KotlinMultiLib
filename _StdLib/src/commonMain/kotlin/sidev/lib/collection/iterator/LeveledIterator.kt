package sidev.lib.collection.iterator

import sidev.lib.structure.data.value.LeveledValue

/** [Iterator] yg disertai dg level hirarki untuk setiap next. */
interface LeveledIterator<T>: Iterator<LeveledValue<T>>{
    /** Level dari nilai [next] yang baru saja di-emit. */
    val currentLevel: Int
}