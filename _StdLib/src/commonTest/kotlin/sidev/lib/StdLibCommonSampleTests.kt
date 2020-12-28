package sidev.lib

import sidev.lib.`val`.*
import sidev.lib.algo.*
import sidev.lib.collection.*
import sidev.lib.collection.array.copy
import sidev.lib.collection.array.forEach
import sidev.lib.collection.common.*
import sidev.lib.collection.lazy_list.CachedSequence
import sidev.lib.collection.lazy_list.LazyHashMap
import sidev.lib.collection.lazy_list.rangeTo
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.date.Date
import sidev.lib.number.*
import sidev.lib.progression.*
import sidev.lib.reflex.getHashCode
import sidev.lib.util.Locale
import sidev.lib.collection.array.get
import sidev.lib.structure.util.Filter
import sidev.lib.text.Charset
import sidev.lib.text.removePrefixWhitespace
import sidev.lib.text.removeSuffixWhitespace
import sidev.lib.text.removeSurroundingWhitespace
import kotlin.math.exp
import kotlin.math.log
import kotlin.ranges.until
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class StdLibCommonSampleTests {

    @Test
    fun cobTest(){
        Filter<Int> { true }
    }

    @Test
    fun nestedSeqTest(){
/*
        prin("========= Halo bro ===========")
        skippingNestedSequenceSimple<KClass<*>>(ArrayList::class){
            it.supertypes.asSequence().filter { it.classifier is KClass<*> }.map { it.classifier }.iterator() as Iterator<KClass<*>>
        }.also {
            prin("hasNext()= ${it.iterator().hasNext()}")
        }.forEachIndexed { index, kClass ->
            prin("i= $index cls= $kClass")
        }

        var i= 0
        prin("========= Halo bro - 2 ===========")
        nestedIteratorSimple<KClass<*>>(ArrayList::class){
            it.supertypes.asSequence().filter {
//                prin("nestedIteratorSimple it= $it it is KClass<*>= ${it is KClass<*>} it::class= ${it::class}")
                it.classifier is KClass<*>
            }.map{ it.classifier }.iterator() as Iterator<KClass<*>>
        }.also {
            prin("hasNext()= ${it.iterator().hasNext()}")
        }.forEach { kClass ->
            prin("i= ${i++} cls= $kClass")
        }

        i= 0
        prin("========= Halo bro - 3 ===========")
        val itr: LeveledNestedIterator_2<*, KClass<*>> = leveledNestedIteratorSimple<KClass<*>>(ArrayList::class){
            it.supertypes.asSequence().filter {
//                prin("nestedIteratorSimple it= $it it is KClass<*>= ${it is KClass<*>} it::class= ${it::class}")
                it.classifier is KClass<*>
            }.map{ it.classifier }.iterator() as Iterator<KClass<*>>
        }.also {
            prin("hasNext()= ${it.iterator().hasNext()}")
        }
        itr.forEach { kClass ->
            prin("i= ${i++} level= ${itr.currentLevel} cls= $kClass")
        }

        i= 0
        prin("========= Halo bro - 4 ===========")
        val itr2= leveledNestedIterator<KType, String>(ArrayList::class.supertypes.iterator(), {
            if(i < 2) it::class.supertypes.iterator() else null
        }){
            iteratorSimple(it.toString())
        }//.withLevel()
        itr2.forEach { kClass ->
            prin("i= ${i++} level= ${itr2.currentLevel} cls= $kClass")
        }

        prin("========= ArrayList::class.supertypes ===========")
        ArrayList::class.supertypes.forEachIndexed{ i, e ->
            prin("i= $i cls= $e")
        }
// */
    }

    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }


/*
    @Test
    fun stdReflexTest(){
        prin("AC::class.si= ${AC::class.si}")
        for((i, member) in AC::class.si.members.withIndex()){
            prin("i= $i member= $member")
        }
    }
 */


    @Test
    fun mathTest(){
        val int= 2.3 as Number
        val int2= (2 as Number / int)
        prin("int= $int2")

        prin(ipow(2, 3))
        prin(ipow(2, -2))
        prin("2.pow(-2)= ${2 powCast -2}")
        prin("125 root 3= ${125 rootCast 3}")
        prin("81 root 4= ${81 rootCast 4}")
        prin("81 root 2= ${81 rootCast 2}")
        prin("81.sqrt()= ${81.sqrtCast()}")
        prin("exp(2.0)= ${exp(2.0)}")
        prin(ipow(3, 4))
        prin(ipow(5, 5))
        log(2f, 3f)
        prin("10 log 1000= ${10 logCast 1000}")
        prin("3 log 81= ${3 logCast 81}")

        prin("223.1352= ${223.1352}")
        prin("223.1352.round()= ${223.1352.round()}")
        prin("-223.1352.round()= ${(-223.1352).round()}")
        prin("223.1352.round(-1)= ${223.1352.round(-1)}")
        prin("-223.1352.round(-1)= ${(-223.1352).round(-1)}")
        prin("223.1352.round(-2)= ${223.1352.round(-2)}")

        prin("-223.1352.round(-2, RoundingMode.UP)= ${(-223.1352).round(-2, RoundingMode.UP)}")
        prin("223.1352.round(-2, RoundingMode.UP)= ${(223.1352).round(-2, RoundingMode.UP)}")
        prin("-223.1352.round(-2, RoundingMode.CEIL)= ${(-223.1352).round(-2, RoundingMode.CEIL)}")
        prin("223.1352.round(-2, RoundingMode.CEIL)= ${(223.1352).round(-2, RoundingMode.CEIL)}")
        prin("-223.1352.round(-2, RoundingMode.DOWN)= ${(-223.1352).round(-2, RoundingMode.DOWN)}")
        prin("223.1352.round(-2, RoundingMode.DOWN)= ${(223.1352).round(-2, RoundingMode.DOWN)}")
        prin("-223.1352.round(-2, RoundingMode.FLOOR)= ${(-223.1352).round(-2, RoundingMode.FLOOR)}")
        prin("223.1352.round(-2, RoundingMode.FLOOR)= ${(223.1352).round(-2, RoundingMode.FLOOR)}")
        prin("223.1352.round(-2, RoundingMode.HALF_UP)= ${(223.1352).round(-2, RoundingMode.HALF_UP)}")
        prin("223.1352.round(-2, RoundingMode.HALF_DOWN)= ${(223.1352).round(-2, RoundingMode.HALF_DOWN)}")
        prin("223.1352[-2]= ${223.1352[-2]}")
    }


    fun ipow(base: Int, exp: Int): Int {
        prine("base= $base exp= $exp")
        if (exp == 0) return 1
        val temp = ipow(base, (exp / 2))
        return if (exp % 2 == 0) temp * temp else base * temp * temp
    }

    @Test
    fun commonListTest(){
        val arrayWrap= arrayWrapperOf(1,3,5,6.778,"hkj")
        val array by arrayWrap

        prin("\n================ array.forEach ================\n")
        array.forEach {
            prin("array foreeach it= $it")
        }

        val commonList = commonMutableListOf<String, Int>()
        commonList.put("haha", 1)
//        commonList["haha" to 1]
        commonList["hihe"] = 2
//        commonList.set("hoha", 3)
        commonList["hoha"]

        prin("\n================ commonList.forEachCommonEntry ================\n")
        commonList.forEachCommonEntry { prin("commonList entry= $it") }

        val commonIndexedList = commonIndexedMutableListOf<String>()
        commonIndexedList += "as"
        commonIndexedList += "asa"
        commonIndexedList += "asap"
        commonIndexedList[1] = "asf"
//        commonIndexedList[4] = "asafr"

        prin("\n================ commonIndexedList.forEachCommonEntry ================\n")
        commonIndexedList.forEachCommonEntry { prin("commonIndexedList entry= $it") }
    }

    @Test
    fun lazyListTest(){
        println("\n============= BATAS CachedSequence ==============\n")
        val strSeq= sequenceOf("Aku", "Mau", "Makan")
        val strSeq2= sequenceOf("Kamu" , "Dan", "Dia")
        val lazySeq= CachedSequence<String>()
        lazySeq += "Halo"
        lazySeq += "Bro"

        prin("lazySeq.size= ${lazySeq.size}")
        //lazySeq.addValues(strSeq)
        lazySeq .. strSeq .. strSeq2
///*
        val containsAku= "Aku" in lazySeq //.contains("Aku")
        val containsKamu= "Kamu" in lazySeq //.contains("Kamu")
        val containsDiaJelek= "Dia Jelek" in lazySeq //.contains("Kamu")
        val indexMau= lazySeq.indexOf("Mau")
        val indexKamu= lazySeq.indexOf("Kamu")
        val ke4= lazySeq[4]
        prin("lazySeq.size= ${lazySeq.size}")
        prin("indexMau= $indexMau ke4= $ke4 containsAku= $containsAku containsKamu= $containsKamu containsDiaJelek= $containsDiaJelek indexKamu= $indexKamu")
// * /

        println("\n============= BATAS CachedSequence.iterator() ==============\n")
        for((i, data) in lazySeq.withIndex()){
            prin("i= $i data= $data")
        }

        println("\n============= BATAS LazyHashMap ==============\n")

        val pairSeq= sequenceOf("Aku" to 3, "Dia" to 10, "Kamu" to 1)
        val pairSeq2= sequenceOf("Makan" to 2, "Belajar" to 5, "Tidur" to 0)
        val lazyMap= LazyHashMap<String, Int>()
        lazyMap["Mau"]= 7
        lazyMap["Iya"]= 6
        lazyMap .. pairSeq .. pairSeq2

        prin("lazyMap= $lazyMap")
//        lazyMap.addIterator(pairSeq.iterator())

        println("\n============= BATAS LazyHashMap.iterator() ==============\n")
        for((i, data) in lazyMap.withIndex()){
            prin("i= $i data= $data")
        }

        val valueMau= lazyMap["Mau"]
        val containsKamuMap= lazyMap.containsKey("Kamu")
        val valueIya= lazyMap["Iya"]
        val valueKamu= lazyMap["Kamu"]
        val valueTidur= lazyMap["Tidur"]
        val contains5= lazyMap.containsValue(5)
        val contains0= lazyMap.containsValue(0)
        val contains4= lazyMap.containsValue(4)

        prin("valueMau= $valueMau valueIya= $valueIya valueKamu= $valueKamu contains5= $contains5 contains0= $contains0 contains4= $contains4 valueTidur= $valueTidur containsKamuMap= $containsKamuMap")
    }

    @Test
    fun dateTest(){
        val date= Date()
        val locale= Locale("id-ID") //"ar-SA"
        prin("Date().time= ${date.time}")
        prin("Date().toFormatString()= ${date.toFormatString(locale = locale)}")
///*
        prin("locale.country= ${locale.country}")
        prin("locale.language= ${locale.language}")
        prin("locale.displayName= ${locale.displayName}")
        prin("locale.script= ${locale.script}")
        prin("locale.qualifiedId= ${locale.qualifiedId}")
// */
    }

    @Test
    fun queueTest(){
        val stack= stackOf(1,2,3,4,5)
        val stack2= stackOf(6,7,8,9)
        stack.forEach { prin(it) }
        stack2.forEach { stack.push(it) }
        stack.forEach { prine(it) }
        for(i in 0 until stack.size){
            prin(stack[i])
        }

        var i= 0
        var itr= stack.listIterator()
        while(itr.hasNext()){
            if(i == 3)
                prine("prev= ${itr.previous()}")
            prine(itr.next())
            i++
        }

        i= 0
        itr= mutableListOf(1,2,3,4,5).listIterator()
        while(itr.hasNext()){
            if(i == 3)
                prin("prev= ${itr.previous()}")
            prin(itr.next())
            i++
        }

        val list= listOf(1,2,3,4,5)
        prine("list.peek()= ${list.peek()}")
        prine("list.peek()= ${list.peek()}")

        val mutList= mutableListOf(1,2,3,4,5)
        prine("mutList.pop()= ${mutList.pop()}")
        prine("mutList.pop()= ${mutList.pop()}")
        prine("mutList.pop()= ${mutList.pop()}")
        mutList.push(6)
        prine("mutList.pop()= ${mutList.pop()}")
        prine("mutList.peek()= ${mutList.peek()}")
        prine("mutList.peek()= ${mutList.peek()}")

        prin(listOf(1,2,3,4,5).subList(1,3))

        val array= sidev.lib.collection.array.arrayOf(1, 2, 3, 4, 5)
        val arr2= array[1..3]
        val mutList2= mutList[1 .. 1]


        prin("mutList= $mutList mutList2= $mutList2")
        prin("array= ${array.joinToString()} arr2= ${arr2.joinToString()}")
    }

    @Test
    fun progressionTest(){
        prin("halo")

        1.progressTo(2).forEach {
            prin(it)
        }

        (2.3f percentileTo 5.0f).forEach {
            prine(it)
        }

        (1 quartileTo 3).forEach {
            prin(it)
        }

        (1.3 centileTo 3).forEach {
            prine(it)
        }

        (1.3 quartileTo 3).forEach {
            prin(it)
        }

        (4 downTo 3).forEach {
            prine(it)
        }

        (1.3 downUntil -3).forEach {
            prin(it)
        }
//        1f as Int
        (1 .. 10)[1..3].forEach {
            prine(it)
        }

        (0.04.progressTo(100, step = 10, operationMode = NumberOperationMode.MULTIPLICATIONAL)).forEach {
            prin(it)
        }

        (2.progressTo(300, step = 2, operationMode = NumberOperationMode.EXPONENTIAL)).forEach {
            prine(it)
        }

        (2.3.progressTo(100, step = 2, operationMode = NumberOperationMode.MULTIPLICATIONAL)).forEach {
            prin(it)
        }

        (2f.progressTo(100.5, step = 20.3)).forEach {
            prine(it)
        }
/*
        (16.0.progressTo(0.5, step = 0.5, operationMode = NumberOperationMode.EXPONENTIAL)).forEachIndexed { index, d ->
            if(index >= 20) return@forEachIndexed
            prine(d)
        }
 */

        var powRes= 16
        powRes= powRes powCast 0.5

        prin("powRes= $powRes")
        prin("16 pow 0.5= ${16 powCast 0.5}")

        prine((1 .. 10)[1])
        prin("size= ${(1 .. 10).size}")
        prin("range= ${(1 .. 10).range}")

        prin("'a' progressTo 'c'= ${'a' progressTo 'c'}")
        prin("'f' progressTo 'a'= ${'f' progressTo 'a'}")

        val charProg= 'f'.progressTo('z', 3)
        prin("charProg= $charProg")
        prin("'z'.toInt()= ${'z'.toInt()}")
        for(c in charProg){
            prin("c= $c int= ${c.toInt()}")
        }

        val charProg2= 'g' progressTo 'a'
        prin("charProg2= $charProg2")
        prin("'e' in charProg2= ${'e' in charProg2}")
    }

    @Test
    fun randomTest(){
        val list= ArrayList<List<*>>()
        for(i in 0 until 14)
            prin(listOf(1,2,"F",3).shuffled().also { list += it }) //.random()

        val uniqueList= mutableMapOf<List<*>, Int>()
        list.forEach {
            uniqueList[it]= (uniqueList[it]?.plus(1) ?: 1)
        }

        val len= list.size
        val percents= mutableListOf<Float>()
        uniqueList.forEach { (ls, count) ->
            val percent= count / len.toFloat()
            prin("list= $ls len= $len count= $count percent= $percent")
        }
    }

    @Test
    fun decimalNumberTest(){
        prin("123.123 * 1000= ${123.123 * 1000}")
        prin("123.123f.getDigitBehindDecimal() = ${123.123f.getDigitBehindDecimal()}")
    }

    @Test
    fun hashCodeTest(){
        class A
        val h1= getHashCode(listOf(1,2,3), calculateOrder = false)
        val h2= getHashCode(listOf(3,1,2), calculateOrder = false)

        prin("h1= $h1")
        prin("h2= $h2")

        val a= A()
        val b= A()
        val c= A()

        prin("a.hashCode()= ${a.hashCode()}")
        prin("b.hashCode()= ${b.hashCode()}")
        prin("c.hashCode()= ${c.hashCode()}")

        prin("a.hashCode()= ${a.hashCode()}")
        prin("b.hashCode()= ${b.hashCode()}")
        prin("c.hashCode()= ${c.hashCode()}")

        val h3= getHashCode(listOf(a,b,c).also { prin("list hashCode= ${it.hashCode()}") }, calculateOrder = false)
        val h4= getHashCode(listOf(b,c,a), calculateOrder = false)

        prin("h3= $h3")
        prin("h4= $h4")

        val x= 1624113833
        val y= 789160464
        val z= 436643214

        prin("x+y+z= ${x+y+z}")
        prin("z+y+x= ${z+y+x}")
    }

    @Test
    fun forEachTes(){
        listOf(1,2,3,4,5,6,7,8,9).forEachIndexed(reversed = true) { i, e ->
//            if(e > 7) return@forEachIndexed
            prin(e)
        }
    }

    @Test
    fun searcTest(){
        val ls= listOf(1,3,3,7,8,12,21)
        ls.sorted()
    }

    @ExperimentalTime
    @Test
    fun sortTest(){
        val arr= sidev.lib.collection.array.arrayOf(1, 2, 4, 1, 5, 1, 5, 12, 42, 23, 11, 10, 15, 14)
        val arrk = arr.copyOf()
        val arr1: Array<Int> = arr.copyOf()
        val arr2: Array<Int> = arr.copyOf()
        val arr3: Array<Int> = arr.copyOf()
        val arr4: Array<Int> = arr.copyOf()

        val tk= measureTime { arrk.sort() }
        val t1= measureTime { arr1.selectionSort() }
        val t2= measureTime { arr2.insertionSort() }
        val t3= measureTime { arr3.mergeSort() }
        val t4= measureTime { arr4.quickSort() }

        prin("\n\n ========== size=14 ============ \n\n ")
        prin("arr= ${arr.joinToString()}")
        prin("arrk= ${arrk.joinToString()} \n tk= $tk")
        prin("arr1= ${arr1.joinToString()} \n t1= $t1")
        prin("arr2= ${arr2.joinToString()} \n t2= $t2")
        prin("arr3= ${arr3.joinToString()} \n t3= $t3")
        prin("arr4= ${arr4.joinToString()} \n t4= $t4")

        val arr_= arr.copyGrowTimely(20).toTypedArray()
        val arr_k= arr_.copyOf()
        val arr5: Array<Int> = arr_.copyOf()
        val arr6: Array<Int> = arr_.copyOf()
        val arr7: Array<Int> = arr_.copyOf()
        val arr8: Array<Int> = arr_.copyOf()

        val t_k= measureTime { arr_k.sort() }
        val t5= measureTime { arr5.selectionSort() }
        val t6= measureTime { arr6.insertionSort() }
        val t7= measureTime { arr7.mergeSort() }
        val t8= measureTime { arr8.quickSort() }

        prin("\n\n ========== size=280 ============ \n\n ")
        prin("arr_= ${arr_.joinToString()}")
        prin("arr_K= ${arr_k.joinToString()} \n t_k= $t_k")
        prin("arr5= ${arr5.joinToString()} \n t5= $t5")
        prin("arr6= ${arr6.joinToString()} \n t6= $t6")
        prin("arr7= ${arr7.joinToString()} \n t7= $t7")
        prin("arr8= ${arr8.joinToString()} \n t8= $t8")

        val arr__= arr.copyGrowTimely(100).toTypedArray()
        val arr__k= arr__.copyOf()
        val arr9: Array<Int> = arr__.copyOf()
        val arr10: Array<Int> = arr__.copyOf()
        val arr11: Array<Int> = arr__.copyOf()
        val arr12: Array<Int> = arr__.copyOf()

        val t__k= measureTime { arr__k.sort() }
        val t9= measureTime { arr9.selectionSort() }
        val t10= measureTime { arr10.insertionSort() }
        val t11= measureTime { arr11.mergeSort() }
        val t12= measureTime { arr12.quickSort() }

        prin("\n\n ========== size=1400 ============ \n\n ")
        prin("arr__= ${arr__.joinToString()}")
        prin("arr__k= ${arr__k.joinToString()} \n t__k= $t__k")
        prin("arr9= ${arr9.joinToString()} \n t9= $t9")
        prin("arr10= ${arr10.joinToString()} \n t10= $t10")
        prin("arr11= ${arr11.joinToString()} \n t11= $t11")
        prin("arr12= ${arr12.joinToString()} \n t12= $t12")

        val arr___= arr.copyGrowTimely(2000).toTypedArray()
        val arr___k= arr___.copyOf()
        val arr13: Array<Int> = arr___.copyOf()
        val arr14: Array<Int> = arr___.copyOf()
        val arr15: Array<Int> = arr___.copyOf()
        val arr16: Array<Int> = arr___.copyOf()

        val t___k= measureTime { arr___k.sort() }
        val t13= measureTime { arr13.selectionSort() }
        val t14= measureTime { arr14.insertionSort() }
        val t15= measureTime { arr15.mergeSort() }
        val t16= measureTime { arr16.quickSort() }

        prin("\n\n ========== size=28000 ============ \n\n ")
        prin("arr___= ${arr___.joinToString()}")
        prin("arr___k= ${arr___k.joinToString()} \n t___k= $t___k")
        prin("arr13= ${arr13.joinToString()} \n t13= $t13")
        prin("arr14= ${arr14.joinToString()} \n t14= $t14")
        prin("arr15= ${arr15.joinToString()} \n t15= $t15")
        prin("arr16= ${arr16.joinToString()} \n t16= $t16")
    }

    @Test
    fun toArrayTest(){
        val ls= listOf(1,2,3,4,1,3)
        val arr1= ls.toArray()
        val arr2= ls.toTypedArray(3)

        prin("arr1= '${arr1.joinToString()}'")
        prin("arr2= '${arr2.joinToString()}'")

        val arr= arrayOf(1, 2, 3, 1, 4, 5)
        val arr3= arr.copy(3)
        val arr4= arr.copy(3, reversed = true)

        prin("arr= '${arr.joinToString()}'")
        prin("arr3= '${arr3.joinToString()}'")
        prin("arr4= '${arr4.joinToString()}'")
    }

    @Test
    fun sortNSearchTest(){
        val arr= arrayOf(1,2,41,4,3,6,12,5,7,8,14)
        val arr1= arr.copyOf()
        arr1.fastSort()
        val ind0= arr.fastSearch(5, true)
        val ind1= arr1.fastSearch(5, true)
        val ind2= arr1.fastSearch(9, true)
        val ind3= arr1.fastSearch(9)

        prin("arr= ${arr.joinToString()}")
        prin("arr1= ${arr1.joinToString()}")
        prin("ind1= $ind1")
        prin("ind2= $ind2")
        prin("ind3= $ind3")
        prin("ind0= $ind0")
    }

    @Test
    fun commonListSortTest(){
        arrayOf(1,31,31,13,4).toMutableList().sortDescending()
        val ls= mutableListOf(1,31,41,21,42,1)
//        ls.sort()

        val list= commonIndexedMutableListOf(1,2,4.1,13,2,1,4,15,3,1.2,3.2,12.5)
        val list1= list.copy()
        val list2= list.copy()
        val list3= list.copy()

        prin("list3= $list3")
        list3 += 13
/*
        val list2= list.copy()
        val list3= list.copy()
        val list4= list.copy()
 */
//        reverseOrder<>()
//        list.sort(withNumberSafety = true)
//        list.sortDescending(withNumberSafety = true)
        list1.selectionSort()
        list2.selectionSort(order = Order.DESC)
        list3.fastSort(withNumberSafety = true)

        val ind2= list2.binarySearch(1.2, order = Order.DESC, withNumberSafety = true)
        val ind3= list3.binarySearch(1.2, withNumberSafety = true)
//        list3.sort()

        prin("list= $list")
        prin("list1= $list1")
        prin("list2= $list2")
        prin("list3= $list3")

        prin("ind2= $ind2")
        prin("ind3= $ind3")
    }

    @Test
    fun gapTest(){
        val ls= listOf(2,1,20,1,4,8,5)
        val dups= ls.countDuplication()
        val gaps= ls.gaps()
        prin("gaps= $gaps")
        prin("dups= $dups")
    }

    @Test
    fun consoleTest(){
        val charset= Charset.UTF_8
        val byteArray= charset.encode("بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيم")
        val str= "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيم" //charset.decode(byteArray).concatToString()
        prin(str, charset = Charset.UTF_16)
        prin(str, StringLiteral.ANSI_YELLOW, charset = Charset.UTF_8)
        prin(str, charset = Charset.US_ASCII)
        prin(str, charset = Charset.ISO_8859_1)
        prin(str)
    }

    @Test
    fun roListTest(){
        val mutList= mutableListOf(1,2,4)
        val list= mutList.toList()
        (list as MutableList).add(10)
        prin(list)

        val roList= ReadOnlyList(mutList, false)//.asReadOnly()
//        (roList as MutableList<*>)//.add(10)
        prin(roList)
        mutList.add(100)
        prin(roList)

        val roList2= mutList.asReadOnly()
//        (roList as MutableList<*>)//.add(10)
        prin(roList2)
        mutList.add(1002)
        prin(roList2)
        prin(mutList)
    }

    @Test
    fun roMapTest(){
        val mutMap= mutableMapOf(
            "a" to 1, "b" to 3
        )
        val map= mutMap.toMap()
        (map as MutableMap)["c"]= 7
        prin(map)
        prin(mutMap)

        val roMap= mutMap.asReadOnly()
//        (roMap as MutableMap<String, Int>)["c"]= 7
        prin(roMap)
        listOf(1,2).random()

        mutMap["d"]= 100
        prin(roMap)
        prin(mutMap)
    }

    @Test
    fun numFactorTest(){
        val prog= 1.progressTo(-14, operationMode = NumberOperationMode.MULTIPLICATIONAL, startExclusiveness = Exclusiveness.EXCLUSIVE)
        prin("prog= $prog")
        prin("4 in prog= ${4 in prog}")
        prin("6 in prog= ${6 in prog}")
        prin("8 in prog= ${8 in prog}")
        prin("-8 in prog= ${-8 in prog}")
        prog.forEach { prin(it) }

        val prog1= 10.0.progressTo(0.1, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        prin("prog1= $prog1")
        prin("2.5 in prog1= ${2.5 in prog1}")
        prin("1.25 in prog1= ${1.25 in prog1}")
        prin("1.0 in prog1= ${1.0 in prog1}")
        prog1.forEach { prin(it) }

        val prog2= 10.progressTo(-1, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        prin("prog2= $prog2")
        prog2.forEach { prin(it) }

        val prog3= 2.progressTo(500, operationMode = NumberOperationMode.EXPONENTIAL)
        prin("prog3= $prog3")
        prin("16 in prog3= ${16 in prog3}")
        prin("8 in prog3= ${8 in prog3}")
        prog3.forEach { prin(it) }

        val prog4= 'z'.progressTo('f', 3, startExclusiveness = Exclusiveness.EXCLUSIVE)
        prin("prog4= $prog4")
        prin("'g' in prog4= ${'g' in prog4}")
        prin("'e' in prog4= ${'e' in prog4}")
        prin("'n' in prog4= ${'n' in prog4}")
        prin("'z' in prog4= ${'z' in prog4}")
        prog4.forEach { prin(it) }

        val prog5= 11 progressTo 7
        prin("prog5= $prog5")
        prin("9 in prog5= ${9 in prog5}")
        prin("6 in prog5= ${6 in prog5}")
        prog5.forEach { prin(it) }

        val prog6= 11 progressTo 20
        prin("prog6= $prog6")
        prog6.forEach { prin(it) }

        val prog7= 256.progressTo(1, operationMode = NumberOperationMode.EXPONENTIAL)
        prin("prog7= $prog7")
        prin("4 in prog7= ${4 in prog7}")
        prin("16 in prog7= ${16 in prog7}")
        prin("8 in prog7= ${8 in prog7}")
        prog7.forEach { prin(it) }

        val prog_= 1 progressTo 10
        val prog_1= 17 progressTo 7
        val prog_2= 8 progressTo 3
        val prog_3= 10.progressTo(3, startExclusiveness = Exclusiveness.EXCLUSIVE)

        prin("prog_ intersects prog_1= ${prog_ intersects prog_1}")
        prin("prog_1 in prog_= ${prog_1 in prog_}")
        prin("prog_ in prog_1= ${prog_ in prog_1}")
        prin("prog_ intersects prog_2= ${prog_ intersects prog_2}")
        prin("prog_2.intersects(prog_)= ${prog_2.intersects(prog_)}")
        prin("prog_2 in prog_= ${prog_2 in prog_}")
        prin("prog_ in prog_2= ${prog_ in prog_2}")
        prin("prog_ intersects prog_3= ${prog_ intersects prog_3}")
        prin("prog_3 intersects prog_= ${prog_3 intersects prog_}")
        prin("prog_3 in prog_= ${prog_3 in prog_}")
        prin("prog_ in prog_3= ${prog_ in prog_3}")

        val progr_= IntProgression.fromClosedRange(1, 10, 1)
        val progr_1= IntProgression.fromClosedRange(17, 7, -1)
        val progr_2= IntProgression.fromClosedRange(8, 3, -1)

        prin("progr_ intersects progr_1 = ${progr_ intersects progr_1}")
        prin("progr_1 in progr_ = ${progr_1 in progr_}")

        prin("progr_ intersects progr_2 = ${progr_ intersects progr_2}")
        prin("progr_2 in progr_ = ${progr_2 in progr_}")

        val prog8= 3.progressTo(10, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        prin("prog= $prog8")
        prin("6 in prog8= ${6 in prog8}")
        prin("-6 in prog8= ${-6 in prog8}")
        prin("3 in prog8= ${3 in prog8}")
        prin("-3 in prog8= ${-3 in prog8}")
        prog8.forEach { prin(it) }

        //2 log 20 = 4
        //2 log 3 = 1
        //2 log 17 = 4
        //2 log 6 = 2

        //2 log 9 = 3

        //1
        //
        //2 log 14 = 3
        //2 log 3 = 1
        //2 log 11 = 3
        //2 log 3 = 1

        //2 log 60 = 5
        //5 / 3 = 1
        //2 log 42 = 5
        //5 / 3 = 1

        //2 log 30 = 4
        //2 log 6 = 2

        //2 log 30 = 4
        //2 log 30 = 4

        //3 log 30 = 3
        //3 log 15 = 2
        //      2
        //3 log 17 =

        //3 log 35 = 3
        //3 log 8 = 1
        //3 log 4 = 1

        //3 log 12 = 2

        //8 + 5 = 13
        //3 log 13 = 2

        //13 + 3 = 16
        //4 log 16 = 2

        //20 + 2 = 22
        //3 log 22 = 2 + 1

        //22 + 2 = 22
        //5 log 22 = 1 + 1

        //22
        //6 log 22 = 1 + 1

        //22
        //4 log 22 = 2 + 1

        //10 + 4 = 14
        //2 log 14 = 3 + 1

        //8 + 5 = 13
        //2 log 13 = 3 + 1

        //8 + 5 = 13
        //3 log 13 = 2

        //20 + 2 = 22
        //5 log 22 = 1 + 1

        //17 + 2 = 19
        //5 log 19 = 1 +1

        //13 + 3 = 16
        //6 log 16 = 1 + 1

        //6 + 7 = 13
        //5 log 13 = 1 + 1

        //11 + 7 = 18
        //5 log 18 = 1 + 1

        //18
        //2 log 18 = 4 + 1
        val prog9= 7.progressTo(80, 2, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        prin("prog9= $prog9")
        prog9.forEach { prin(it) }
    }

    @Test
    fun progSizeTest(){
        //1 + 7 = 8
        //2 log 8 = 3
        //7 log 1 = 0
        //2 log 7 = 2
        //2 log 1 = 0
        //
        //
        //4 + 7 = 11
        //2 log 7 = 2
        //2 log 4 = 2
        //4 log 2 = 0
        //2 log 5 = 1
        //2 log 3 = 1

        //11 + 7 = 18
        //2 log 18 = 4 + 1
        //
        //30 / 7 = 4
        //4
        //2 log 30 = 4
        val prog1= 7.progressTo(8, 2, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls1= prog1.toList()
        val size1= prog1.size
        val size1_l= ls1.size

        prin("prog1= $prog1 size1= $size1 size1_l= $size1_l ls1= $ls1 same= ${size1 == size1_l}")

        val prog2= 9.progressTo(132, 4, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls2= prog2.toList()
        val size2= prog2.size
        val size2_l= ls2.size

        prin("prog2.canFit(31,4)= ${prog2.canFit(31,4)}")
        prin("prog2.canFit(1,124)= ${prog2.canFit(1,124)}")
        prin("prog2.canFit(1,125)= ${prog2.canFit(1,125)}")
        prin("prog2= $prog2 size2= $size2 size2_l= $size2_l ls2= $ls2 same= ${size2 == size2_l}")

        val prog3= 6.progressTo(-132, -11, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls3= prog3.toList()
        val size3= prog3.size
        val size3_l= ls3.size

        prin("prog3.canFit(31,4)= ${prog3.canFit(31,4)}")
        prin("prog3.canFit(1,127)= ${prog3.canFit(1,127)}")
        prin("prog3.canFit(1,128)= ${prog3.canFit(1,128)}")
        prin("prog3= $prog3 size3= $size3 size3_l= $size3_l ls3= $ls3 same= ${size3 == size3_l}")

        val prog4= 6.progressTo(6911237, 51, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls4= prog4.toList()
        val size4= prog4.size
        val size4_l= ls4.size

        prin("prog4= $prog4 size4= $size4 size4_l= $size4_l ls4= $ls4 same= ${size4 == size4_l}")

        val prog5= 9.progressTo(14131, 17, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls5= prog5.toList()
        val size5= prog5.size
        val size5_l= ls5.size

        prin("prog5= $prog5 size5= $size5 size5_l= $size5_l ls5= $ls5 same= ${size5 == size5_l}")

        val prog6= 7.progressTo(45414, 2, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls6= prog6.toList()
        val size6= prog6.size
        val size6_l= ls6.size

        prin("prog6= $prog6 size6= $size6 size6_l= $size6_l ls6= $ls6 same= ${size6 == size6_l}")

        val prog7= 42.progressTo(-345414, -5, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls7= prog7.toList()
        val size7= prog7.size
        val size7_l= ls7.size

        prin("prog7= $prog7 size7= $size7 size7_l= $size7_l ls7= $ls7 same= ${size7 == size7_l}")

        val prog8= 127.0.progressTo(3454143.0, 5.0, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls8= prog8.toList()
        val size8= prog8.size
        val size8_l= ls8.size

        prin("prog8= $prog8 size8= $size8 size8_l= $size8_l ls8= $ls8 same= ${size8 == size8_l}")

        val prog9= 127.progressTo(0.1, -5, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls9= prog9.toList()
        val size9= prog9.size
        val size9_l= ls9.size

        prin("5 in prog9= ${5 in prog9}")
        prin("-5 in prog9= ${-5 in prog9}")
        prin("-1 in prog9= ${-1 in prog9}")
        prin("1 in prog9= ${1 in prog9}")
        prin("prog9= $prog9 size9= $size9 size9_l= $size9_l ls9= $ls9 same= ${size9 == size9_l}")

        val prog10= 127.0.progressTo(0.01, 1.0/5, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls10= prog10.toList()
        val size10= prog10.size
        val size10_l= ls10.size


        prin("1.016 in prog10= ${1.016 in prog10}")
        prin("prog10= $prog10 size10= $size10 size10_l= $size10_l ls10= $ls10 same= ${size10 == size10_l}")

        val prog11= 127.0.progressTo(0.01, 5, operationMode = NumberOperationMode.MULTIPLICATIONAL)
        val ls11= prog11.toList()
        val size11= prog11.size
        val size11_l= ls11.size

        prin("1.016 in prog11= ${1.016 in prog11}")
        prin("prog11= $prog11 size11= $size11 size11_l= $size11_l ls11= $ls11 same= ${size11 == size11_l}")

        val prog12= 12.progressTo(-17, 4)
        val ls12= prog12.toList()
        val size12= prog12.size
        val size12_l= ls12.size

        prin("prog12.canFit(7,4)= ${prog12.canFit(7,4)}")
        prin("prog12.canFit(1,29)= ${prog12.canFit(1,29)}")
        prin("prog12.canFit(1,30)= ${prog12.canFit(1,30)}")
        prin("prog12.canFit(1,31)= ${prog12.canFit(1,31)}")
        prin("prog12.canFit(3,10)= ${prog12.canFit(3,10)}")
        prin("-8 in prog12= ${-8 in prog12}")
        prin("8 in prog12= ${8 in prog12}")
        prin("16 in prog12= ${16 in prog12}")
        prin("-16 in prog12= ${-16 in prog12}")
        prin("-15 in prog12= ${-15 in prog12}")
        prin("prog12 containsInRange -15= ${prog12 containsInRange -15}")
        prin("prog12= $prog12 size12= $size12 size12_l= $size12_l ls12= $ls12 same= ${size12 == size12_l}")

        val prog13= 12.progressTo(17, -4)
        val ls13= prog13.toList()
        val size13= prog13.size
        val size13_l= ls13.size

        prin("-8 in prog13= ${-8 in prog13}")
        prin("prog13 containsInRange -8= ${prog13 containsInRange -8}")
        prin("16 in prog13= ${16 in prog13}")
        prin("prog13.canFit(2,2)= ${prog13.canFit(2,2)}")
        prin("prog13.canFit(2,3)= ${prog13.canFit(2,3)}")
        prin("prog13.canFit(3,2)= ${prog13.canFit(3,2)}")
        prin("prog13.canFit(2,4)= ${prog13.canFit(2,4)}")
        prin("prog13.canFit(1,6)= ${prog13.canFit(1,6)}")
        prin("prog13.canFit(1,7)= ${prog13.canFit(1,7)}")
        prin("prog13= $prog13 size13= $size13 size13_l= $size13_l ls13= $ls13 same= ${size13 == size13_l}")
    }

    @Test
    fun removeWhitespaceTest(){
        val ori= "  agafa afh a 5    "
        val prefRem= ori.removePrefixWhitespace()
        val suffRem= ori.removeSuffixWhitespace()
        val surrRem= ori.removeSurroundingWhitespace()

        prin("ori= '$ori'")
        prin("prefRem= '$prefRem'")
        prin("suffRem= '$suffRem'")
        prin("surrRem= '$surrRem'")
    }

    @Test
    fun numBitShiftTest(){
        val i= 21
        val u= 56
        prin(i shl 4)
        prin(i shr 4)
        prin(u shr 5)
    }
}