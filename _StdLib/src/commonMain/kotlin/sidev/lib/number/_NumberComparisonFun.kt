package sidev.lib.number

/*
========== Universal Comparison ==============
Digunakan untuk komparasi antar tipe data. Tidak direkomendasikan digunakan di luar library ini.
==============================================
*/
fun <T: Comparable<*>> univLessThan(i1: T, i2: T): Boolean = i1 < i2
//        = try{ i1 < i2 } catch (e: ClassCastException){ (i1 as Comparable<*>) < (i2 as Comparable<*>) }
fun <T: Comparable<*>> univLessThanEqual(i1: T, i2: T): Boolean = i1 <= i2
//        = try{ i1 <= i2 } catch (e: ClassCastException){ (i1 as Comparable<*>) <= (i2 as Comparable<*>) }
fun <T: Comparable<*>> univMoreThan(i1: T, i2: T): Boolean = i1 > i2
//        = try{ i1 > i2 } catch (e: ClassCastException){ (i1 as Comparable<*>) > (i2 as Comparable<*>) }
fun <T: Comparable<*>> univMoreThanEqual(i1: T, i2: T): Boolean = i1 >= i2
//        = try{ i1 >= i2 } catch (e: ClassCastException){ (i1 as Comparable<*>) >= (i2 as Comparable<*>) }

fun <T: Comparable<T>> univAsc(i1: T, i2: T): Boolean = univLessThan(i1,  i2)
fun <T: Comparable<T>> univDesc(i1: T, i2: T): Boolean = univMoreThan(i1,  i2)


//================ Uni Comparison ===================

fun <T: Comparable<T>> lessThan_():(T, T) -> Boolean= { i1, i2 -> i1 < i2 }
fun <T: Comparable<T>> lessThanEqual_():(T, T) -> Boolean= { i1, i2 -> i1 <= i2 }
fun <T: Comparable<T>> moreThan_():(T, T) -> Boolean= { i1, i2 -> i1 > i2 }
fun <T: Comparable<T>> moreThanEqual_():(T, T) -> Boolean= { i1, i2 -> i1 >= i2 }

//fun <T: Comparable<*>> lessThan(i1: T, i2: T): Boolean = i1 < i2
fun <T: Comparable<T>> lessThan(i1: T, i2: T): Boolean = i1 < i2
fun <T: Comparable<T>> lessThanEqual(i1: T, i2: T): Boolean = i1 <= i2
fun <T: Comparable<T>> moreThan(i1: T, i2: T): Boolean = i1 > i2
fun <T: Comparable<T>> moreThanEqual(i1: T, i2: T): Boolean = i1 >= i2

fun <T: Comparable<T>> asc(i1: T, i2: T): Boolean = lessThan(i1,  i2)
fun <T: Comparable<T>> desc(i1: T, i2: T): Boolean = moreThan(i1,  i2)

