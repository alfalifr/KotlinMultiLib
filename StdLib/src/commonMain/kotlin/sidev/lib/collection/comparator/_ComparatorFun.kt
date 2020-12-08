package sidev.lib.collection.comparator

fun <T: Comparable<T>> naturalComparator(): Comparator<T> = NaturalOrderComparator as Comparator<T>
fun <T: Comparable<T>> reverseComparator(): Comparator<T> = ReversedOrderComparator as Comparator<T>

fun <T> numberNaturalComparator(): Comparator<T> where T: Comparable<T>, T: Number = NumberNaturalOrderComparator as Comparator<T>
fun <T> numberReverseComparator(): Comparator<T> where T: Comparable<T>, T: Number = NumberReversedOrderComparator as Comparator<T>