package sidev.lib

import sidev.lib.`val`.*
import sidev.lib.algo.*
import sidev.lib.collection.*
import sidev.lib.collection.array.*
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
import sidev.lib.structure.util.Filter
import sidev.lib.text.*
import kotlin.arrayOf
import kotlin.intArrayOf
import kotlin.math.exp
import kotlin.math.log
import kotlin.ranges.until
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Duration
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

    @Test
    fun roundTest(){
        val range= 0 .. Int.MAX_VALUE
        val i= Int.MIN_VALUE
        val u= -1
        prin(i in range)
        prin(i.roundClosest(range))
        prin(u in range)
        prin(u.roundClosest(range))

        prine((Int.MAX_VALUE + Int.MAX_VALUE).toLong())
        prine((Int.MAX_VALUE.toLong() + Int.MAX_VALUE.toLong()))
    }

    @Test
    fun getCommonTest(){
        val a= "aiai adalah fag ok yo"
        val b= "adalah haho"
        val c = "D:\\DataCloud\\OneDrive\\OneDrive - Institut Teknologi Sepuluh Nopember\\Kuliah\\SMT 5\\MLTI-C"
        val d = "D:\\DataCloud\\OneDrive\\OneDrive - Institut Teknologi Sepuluh Nopember\\Kuliah\\SMT 7\\OKH-A"
        prin(a.getCommon(b))
        prin(c.getCommon(d))
    }

    fun originalMatchString(long: String, sub: String): Int = long.indexOf(sub)
    @ExperimentalTime
    fun testMatsStr(func: (String, String) -> Int){

        val a= "aiai adalah fag ok yo"
        val b= "adalah"
        val c= "aohaioj vairg awefhwe8hf aye8fhwe fuw2whf ueh faefua hewihfaiwef ah wefhawie fjauewh fawefiajweiufhaiwefawiefih aawefyawhefawiehfauw fa fwau ehaiwhefuawefah"
        val d= "awie fjau"
///*
        val e= """
            aohaioj vairg awefhwe8hf aye8fhwe fuw2whf ueh faefua hewihfaiwef ah wefhawie fjauewh fawefiajweiufhaiwefawiefih aawefyawhefawiehfauw fa fwau ehaiwhefuawefah
            anfoiag aigjaio agijaoig anghuiae fiuhefnae ageugaw eg uaerhgifab wg awirbgbawrg fua wefhwef weuhfw
            a weuhfawehf wuehfuwe fuawheguaehyg au7gfywa7neg egh7egmg7uhe34ryt2748tf weygfwaeg waf yeug a
            awe iuhf aweh t87238rhw fiuawg efawifoaw4yt3 rf8w ey7f8wehf7we f7we 78fw7e fwhgw g
        """.trimIndent()
        val f= "au7gfywa7neg egh7egmg7uhe34ryt2748tf"
// */
        val g= """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.
            Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage,
             and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus 
             Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. 
             The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
             There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which 
             don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. 
             All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary 
             of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always 
             free from repetition, injected humour, or non-characteristic words etc.
        """.trimIndent()
        val h= "If you are going to use a passage of Lorem Ipsum,"
// */
        val i= """Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown
            printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining
            essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software
            like Aldus PageMaker including versions of Lorem Ipsum.
            In the rapidly growing era of Internet-of-Things (IoT), healthcare systems have enabled a sea of
            connections of physical sensors. Data analysis methods (e.g., k-means) are often used to process data
            collected from wireless sensor networks to provide treatment advices for physicians and patients.
            However, many methods pose a threat of privacy leakage during the process of data handling. To
            address privacy issues, we propose a mutual privacy-preserving k-means strategy (M-PPKS) based on
            homomorphic encryption that neither discloses the participant’s privacy nor leaks the cluster center’s
            private data. The proposed M-PPKS divides each iteration of a k-means algorithm into two stages:
            finding the nearest cluster center for each participant, followed by computing a new center for each
            cluster. In both phases, the cluster center is confidential to participants, and the private information
            of each participant is not accessible by an analyst. Besides, M-PPKS introduces a third-party cloud
            platform to reduce the communication complexity of homomorphic encryption. Extensive privacy
            analysis and performance evaluation results manifest that the proposed M-PPKS strategy can achieve
            high performance. In addition, it can obtain approximate clustering results efficiently while preserving
            mutual private data
            The advancement of Internet-of-Things (IoT) [1,2] can transform any object into network data through wireless
            sensor networks. This has derivated a broad spectrum of diversified applications, such as smart cities [3,4],
            smart homes [5,6], and smart
            grids [7,8]. Besides, IoT has been widely adopted to design and
            promote healthcare systems [9,10], due to its effective capability
            of integrating within infrastructure resources and provisioning
            crucial information to users. Healthcare systems can collect a
            multitude of data through wireless sensor networks (WSNs) to
            help propagate many e-health services, e.g., electronic health
            cards, remote patient monitoring and health portals. Fig. 1 shows
            a framework of typical healthcare IoT systems [11,12]. It consists
            of three layers: perception layer, network layer and application
            layer
            ensuring reasonable data privacy-preserving. Jha et al. [23] considered privacy preservation in the process of calculating cluster
            centers. They proposed two privacy protection schemes, where
            one is based on the oblivious polynomial evaluation and the
            other is based on homomorphic encryption. However, the scheme
            did not consider the privacy leakage issues when assigning a
            participant into the nearest cluster center. Bunn et al. [24] proposed a two-party k-means clustering protocol, which computes
            the clustering results without using the cluster center in each
            iteration. This protocol avoids the leakage of cluster center during
            the clustering process. Xing et al. [19] proposed a mutual privacy
            protection scheme to prevent the disclosure of privacy data and
            effectively resist collusion attacks.
            thm was proposed by Yang et al. [26],
            to enable the random choice of the initial cluster center. As
            well as privacy-preserving based on hster center. Bunn et al. [24] proposed a two-party k-means clustering protocol, which computes
            the clustering results without using the cluster center in each
            iteration. This protocol avoids the leakage of cluster center during
            the clustering process. Xing et al. [19] promomorphic encryption
            and differential privacy, a novel blockchain-based data privacy
            protection scheme [27] was proposed, taking advantage of the
            blockchain infrastructure to remove the single-po
            Differential privacy has attracted many interests in the privacy
            protection of k-means research. Blum et al. [25] introduced a
            differential privacy mechanism into the privacy-preserving algorithm for the first time. The proposed mechanism reduces the risk
            of privacy leakage by adding noise to the cluster center. However,
            the selection of initial center points directly affects the performance of k-means clustering. Therefore, an improved differential
            privacy (IDP) k-means algorithm was proposed by Yang et al. [26],
            to enable the random choice of the initial cluster center. As
            well as privacy-preserving based on homomorphic encryption
            and differential privacy, a novel blockchain-based data privacy
            protection scheme [27] was proposed, taking advantage of the
            blockchain infrastructure to remove the single-point-failure issue.
            All the above studies have drawbacks so that they cannot
            be directly deployed in a time-sensitive system. Therefore, recent research has started paying close attention to the privacypreserving clustering strategy with low time complexity. Yu
            et al. [28] proposed a novel privacy-preserving multi-party kmeans clustering scheme, which is the first to use parallel computing techniques to improve the clustering process. The time
            complexity of their scheme is much lower than previous studies.
            Miao et al. [29] proposed a lightweight privacy-preserving framework based on truth discovery, L-PPTD and L2-PPTD, which were
            implemented by two cloud platforms to achieve less communication cost.
            In summary, existing privacy-preserving k-means clustering
            schemes cannot protect the privacy of participants while simultaneously preserving the privacy of the cluster center. Moreover,
            most of the strategies cannot resist collusion attacks or have high
            time complexity. To solve these issues, in this paper, we propose
            a low time complexity strategy M-PPKS. The M-PPKS strategy is
        """.trimIndent()
        val j= "8] proposed a novel privacy-preserving multi-party kmeans clustering scheme, which is the first to use paralle"

        val k= """
k-means clustering. In data analysis, an analyst A groups participants’ information using the k-means algorithm. It is worth
noticing that participants do not want to share their private
data with the analyst, meanwhile the analyst is reluctant to let
participants know the cluster center. To meet above constraints,
we propose a M-PPKS strategy that can protect privacy between
participants and analysts. As shown in Fig. 2, each iteration of a
k-means algorithm consists of two stages: (1) finding the nearest
cluster center for a participant and (2) computing a new center
of each cluster. In the first stage, we ensure that the private data
of a participant and the information of a cluster center is not
obtained by each other. In the second stage, we introduce a thirdparty cloud platform to reduce the communication complexity.
The homomorphic encryption is adopted to prevent participants’
privacy from being accessed by the third-party cloud platform
and analyst A. For M-PPKS, we assume that:
,8]. Besides, IoT has been widely adopted to design and
            prhe distance from ai to cj and ai to another cluster center. If
Dij − Dij′ > 0, ai
is closer to the center cj
′ . Then, we compare the
distance from ai to cj
′ and ai to another cluster center. Repeating
this process (k − 1) times can figure out the nearest center of
ai
. By analyzing this process, we can find that ai knows nothing
about the private information of a cluster center and does not
know which cluster it belongs to. The analyst A only knows which
cluster ai belongs, and A does not obtain any private data of ai
.
Fig. 3 illustrates the process of assigning a participant to a cluster.
To verify whether the result of (Dij − Dij′) calculated by our
strategy is the same as that calculated by the original k-means
algorithm, we use a(i,j,j
′
)
instead of ai to determine the closest
cluster center. The same result can be obtained when we use
a(i,j,j
′
) and ai to calculate (Dij − Dij′), respectively. The detailed
verification process can be represented by Eq. (14) according to
Eqs. (10)–(13)
            ensuring reasonable data privacy-preserving. Jha et al. [23] considered privacy preservation in the process of calculating cluster
            centerIn this section, we analyze the effectiveness of M-PPKS in
protecting the mutual privacy between participants and analysts,
and the ability to resist collusion attacks. To better understand
the performance analysis process, the relevant assumptions are
given below.
We assume that: (1) participants cannot access the private
information of a cluster center; (2) they know nothing about
which cluster they belong to; (3) they do not know who is in
the same cluster; (4) they cannot access the private information
about other participants.
And then, we assume that: (1) analyst A knows the information of a cluster center, and the affiliation between clusters
and participants; (2) it cannot access the private information of
participants.
In addition, we also consider the following assumptions: (1)
the number of participants satisfy n > 3; (2) the third-party cloud
platform is semi-honest and will not collude with othersto remove the single-point-failure issue.
            All the In the stage of computing a new center of each cluster, we
adopt the homomorphic encryption and the slicing method to
protect participants’ private information from being disclosed to
the analyst or external attackers. When a collusion attack or
external attacks occur among the analyst and participants, our
scheme can achieve effective privacy preservation.
First, we perform a privacy analysis on the participant’s information stored by the third-party cloud platform. This platform
only knows the relationship between participants and clusters,
the plaintext ai
′
, the ciphertext E
(
ai
′
)
and the newly composed
data after slicing ρi
′
. Since the private key is only known by
the analyst A, third-party cloud platforms cannot decrypt the
ciphertext. ai cannot be calculated by the third-party cloud platform thanks to that the plaintext ai
′
contains a random number
generated by participants. Thus, even if ai
′
, E
(
ai
′
)
and ρi
′
are
known by the third-party cloud platform, it cannot learn anything
about the participants’ private information.
Then, we will conduct the privacy analysis on the side of
participants. In addition to their information and the generated
random number ρ, each participant knows the ciphertext of slices
distributed by other participants. Based on this, the sum of the
slices received from other participants and the partial slices saved
by itself can be calculated. However, each participant cannot learn
anything about the private information of other participants and
the cluster center to which the other participants belong.
Analyst A knows nothing about the participant’s private information except for the aggregated results of participants in the
same cluster. In short, the private information of each participant
will not be disclosed to the third-party cloud platform and the
analyst. Moreover, the private information about the cluster center will not be disclosed to the participants and the third-party
cloud platform.
In the following, we analyze the cost of computing a new
cluster center. In the second stage, we observe that encryption
and decryption operations are done by the third-party cloud platform and analyst respectively. Since the cost of encryption and
decryption operations is largely determined by the processing
capabilities of the third-party cloud platform and the analyst, we
only consider the communication complexity of participants. The
participants need to do the following calculations: generating a
random q-dimensional vector to calculate perturbed data, slicing
and allocating data. In Algorithm 2, line 2 takes O(n) time to
calculate perturbed data, line 6 takes O(n) time, line 7 takes
O(q ∗ n) time, and line 8 and line 9 need O(n) time. Similar
iterations require k times. Therefore, the sum of communication
cost for all participants is O(kqn), which has lower communication
complexity than that of the algorithm in [19].ving k-means clustering
            scheunknown parameters in Eq. (20), but there is only n ∗
k(k−1)
2
equations. Therefore, the analyst cannot calculate the private
information of a1, a2, . . . , an through existing knowledge. Now
we consider the worst-case scenario when n participants collude
with each other. In other words, participants know the value of
{
c1
′−c2
′
, c1
′−c3
′
, . . . , ck−1
′−ck
′
}
and the private information of
other participants. On one hand, even if n participants collude,
the information of {c1−c2, c1−c3, . . . , c2−c3, . . . , ck−1−ck} will
not be leaked. On the other hand, although a participant knows
the private information of other participants, participants cannot
detect the cluster center because it does not know which cluster
it belongs to and which participants are within the same cluster.
Thus, we can conclude that our scheme can resist the collusion
attacks initiated by the largest n participants without leaking any
information about the cluster center, and the analyst A cannot
know any private information about participants.
In the following, we analyze the cost of finding the nearest
cluster center for each participant. Due to the uncertainty of
the size of data sets and the cluster center, the communication
cost of our algorithm is also uncertain. Removing the above
influencing factors, a simple communication cost analysis for our
proposed algorithm is presented as follows. We suppose there are
n participants, therefore, each participant should make O(k
2 +
1) comparisons to calculate the closest cluster center. The total
communication cost is therefore O(nk2+n), which has higher cost
than that of the algorithm in [19].
PKS. The M-PPKS strategyparticipants. Therefore, before analyst A calculates a new cluster
center, the collusion of n − 1 participants with analyst A cannot
infer the private information of the remaining participants.
After analyst A calculates the cluster center, the best case is
that as many as n − 2 participants who collude with the analyst
cannot infer the remaining two participants, and the remaining
two participants must be in the same cluster. The worst-case
scenario is when as many as n
2 − 1 participants are colluding
with the analyst, the analyst cannot infer the information of the
remaining n
2 + 1 participants.
Scenario 2. Collusion among the participants. If there are
n − 1 participants colluding together, it still cannot infer any
information about the cluster center, because the n − 1 participants in the conspiracy do not know the cluster to which the
remaining participants belong. Thus, we claim that our scheme
can effectively protect the privacy of the data analyst.
At last, we make a comparison of collusion resistance between
our strategy, [19] and the classical k-means algorithm, as shown
in Table 3. From this table, we can notice the significant advantages of our strategy compared to the other two strategies in
terms of collusion attack resistance.The first dataset includes the locations of public utilities in
a city [35]. It records about 240 utilities’ latitude and longitude
data, users can figure out the public utility distribution from the
results of our proposed clustering algorithm. The second dataset
is about the survival of patients who had undergone surgery
for breast cancer [36]. This dataset is collected from 306 patient
instances. The clustering results can provide advice for the doctor
to treat breast cancer. The last dataset is a real smartphone
dataset for human activity recognition in ambient assisted living
that is an addition to the dataset of [37]. This dataset contains
3-axial linear acceleration and 3-axial angular velocity of six activities (standing, sitting, lying, walking, walking upstairs, walking
downstairs) that were collected from 30 participants within the
age group of 22-79 years old. Each person carries a smart-phone
with sensors to measure linear acceleration and angular velocity.
Table 4 shows the detailed information about the three dataseThe clustering results are shown in Fig. 8. The results show the
effect of age, patient’s year of operation, and the positive axillary
nodes’ number detected on the patient’s recovery. Analysts can
cluster datasets without obtaining patient private information,
and provide the clustering results to the doctor for effective
reference. Fig. 9 shows the comparison of the cluster centers
calculated by our proposed algorithm with the cluster centers
calculated by the classical k-means algorithm. We can conclude
the accuracy of the M-PPKS is almost the same as the k-means algorithm. The reason for the difference in clustering centers is that
the randomness of the initial clustering center selection causes
inconsistent clustering results. Table 7 shows the precision, recall
and F1-measure of the clustering results of our strategy compared
with the classical k-means clustering algorithm. The experimental
results are calculated following the same way used for Table 6.
We can see that our clustering strategy can obtain clustering
results that are almost similar to the classic k-means algorithm
without leaking private information.
The last dataset is a collection of smart-phone sensor data for
different human activities. Table 8 shows the comparison clustering results between M-PPKS and classical k-means algorithm.
The experimental results are calculated following the same way
used for Table 6. The results demonstrate that our algorithm does
not have a significant impact on the final clustering results while
protecting participants’ data privacy.
 In this paper, we have proposed a mutual strategy M-PPKS that
can protect the privacy information of participants from being
exposed to the analyst and the private information of a cluster
center from being disclosed to the participants. Moreover, considering the time-sensitivity nature of the healthcare IoT systems,
a third-party cloud platform has been introduced in M-PPKS to
reduce the overhead of participants. A comprehensive analysis of
the privacy protection capabilities under normal circumstances
and collusion attacks has been conducted, demonstrating that the
proposed M-PPKS can achieve effective privacy protection. Extensive simulation results under three different datasets have shown
that our scheme can achieve better accuracy. In future work,
we plan to adopt differential privacy to design a new privacy
protection k-means algorithm and further reduce communication
complexityHui Lin is a professor in the College of Mathematics and Informatics at the Fujian Normal University,
FuZhou, China. He received his Ph.D. degree in Computing System Architecture from College of Computer
Science of the Xidian University, China, in 2013. Now
he is a M.E. supervisor in College of Mathematics and
Informatics at Fujian Normal University, FuZhou, China.
His research interests include mobile cloud computing
systems, blockchain, and network security. He has published more than 50 papers in international journals
and conferences.Yulei Wu is a Senior Lecturer with the Department of
Computer Science, College of Engineering, Mathematics
and Physical Sciences, University of Exeter, United
Kingdom. He received the B.Sc. degree (1st Class Hons.)
in Computer Science and the Ph.D. degree in Computing and Mathematics from the University of Bradford,
United Kingdom, in 2006 and 2010, respectively. His
expertise is on networking and his main research interests include computer networks, networked systems,
internet of things, software defined networks and systems, network management, and network security and
privacy. Dr. Wu contributes to major conferences on networking and networked
systems as various roles, including the Steering Committee Chair, General
Chair and Program Chair. His research has been supported by Engineering and
Physical Sciences Research Council of United Kingdom, National Natural Science
Foundation of China, University’s Innovation Platform and industry. He is an
Editor of IEEE Transactions on Network and Service Management, Computer
Networks (Elsevier) and IEEE Access. He is a Senior Member of the IEEE, and a
Fellow of the HEA (Higher Education Academy).Min Peng is currently pursuing the master’s degree
from the School of Mathematics and Information, Fujian Normal University. He received a bachelor’s degree
in computer science from the Lanzhou University of
Technology in China in 2018. His research interests
include blockchain, deep learning and network securityis
        """.trimIndent()
        val l= """Kingdom, in 2006 and 2010, respectively. His
expertise is on networking and his main research interests include computer networks, networked systems,
internet of things, software defined networks and systems, network management, and network security and
privacy. Dr. Wu contributes to major conferences on networking and networked
systems as various roles, including the Steering Committee Chair, General
Chair and Program Chair. His research has been supported by Engineering and
Physical Sciences Research Council of United Kingdom, National Natural Science
Foundation of China, University’s Innovation Platform and industry. He is an
Editor of IEEE Transactions on Network and"""

        val m=
            """Tugas kelompok pertama berkaitan dengan eskplorasi data (Exploratory Data Analysis/EDA) dan praproses
data terhadap sebuah data mengenai prediksi kemungkinan seorang kastemer akan melakukan tindakan
loncatan (customer churn prediction) menggunkan python. Gunakan referensi dari berbagai sumber,
termasuk catatan yang diberikan dalam kuliah dan tutorial. Hasil eskplorasi dan praproses data yang ada
lakukan akan digunakan dalam tugas kelompok berikutnya (Klasifikasi).
Data yang terdiri dari 28.382 baris dan 22 kolom merupakan data sebuah Bank yang bermaksud untuk
mempertahankan para kastemernya untuk produk perbankan, yaitu saldo tabungan (saving acounts). Pihak
bank menginginkan untuk mengidentifikasi apakah seorang kastemer kira-kira akan melakukan churn
terhadap saldo tabungannya. Dalam hal ini, churn dapat dipandang sebagai tindakan yang tidak diharapkan
sehingga saldo tabungannya tersisa di bawah saldo minimum.
Dalam set data, anda diberikan informasi mengenai kastemer, sepeti usia, jenis kelamin, data demografi
beserta beberapa data transaksi perbankan yang dilakukan oleh kastemer. Setiap baris data
merepresentasikan seorang kastemer, di mana setiap kolom data berisikan atribut yang terkait dengan
demografi kastemer dan juga berbagai transaksi yang pernah dilakukan oleh kastemer. Unutk ini, terdapat
beberapa variabel/atribut dalam set data yang dapat dikelompokkan menjadi 3 kategori seperti berikut.
Demographic information about customers:
customer_id - Customer id
vintage - Vintage of the customer with the bank in number of days
age - Age of customer
gender - Gender of customer
dependents - Number of dependents
occupation - Occupation of the customer
city - City of customer (anonymised)
Bank Related Information for customers:
customer_nw_category - Net worth of customer (3:Low 2:Medium 1:High)
branch_code - Branch Code for customer account
days_since_last_transaction - No of Days Since Last Credit in Last 1 year
Transactional Information:
current_balance - Balance as of today
previous_month_end_balance - End of Month Balance of previous month
average_monthly_balance_prevQ - Average monthly balances (AMB) in Previous Quarter
average_monthly_balance_prevQ2 - Average monthly balances (AMB) in previous to previous quarter
percent_change_credits - Percent Change in Credits between last 2 quarters
current_month_credit - Total Credit Amount current month
previous_month_credit - Total Credit Amount previous month
current_month_debit - Total Debit Amount current month
previous_month_debit - Total Debit Amount previous month
current_month_balance - Average Balance of current month
previous_month_balance - Average Balance of previous month
churn - Average balance of customer falls below minimum balance in the next quarter (1/0)
            B. Tugas
1. Lakukan eksplorasi data dari berbagai perspektif untuk memahami karakteristik data agar anda
mempunyai persepsi yang baik terhadap data. Gambakan hasil eksplortasi dalam berbagai bentuk
grafik/chart yang menurut anda paling sesuai untuk menggambarkan karateristik data secara
komprehensip dan mudah dipahami (anda dapat mengggunakan library visualisasi data yang popuar
dalam python , yaitu Seaborn yang pernah dijelaskan dalam tutorial).
Analisis eksploratori yang harus dilakukan paling tidak meliputi analisis univariate (analisis dari
setiap atribut) dan analisis bivariate (analisis korelasi) antara setiap atribut independen dan atribut
dependen (atribut kelas, yaitu churn). Khusus untuk analisis bivatriate, paling tidak ada harus
melakukan analisis terkait dengan beberapa hipotesis berikut:
a. Apakah kastemer lama (vintage customer) cenderung untuk melakukan churn?
b. Apakah kastemer dengan rata-rata saldo tertinggi akan cenderung untuk melakukan churn?
c. Apakah kastemer yang mengalami penurunan saldo bulanan akan cenderung untuk
melakukan churn?
d. Apakah kastemer perempuan mempunyau kecenderangan yang rendah untuk melakukan
churn?
e. Apakah kastemer muda akan mempunyai kecenderungan melakukan churn?
f. Apakah kastemer yang dengan penghasilan kecil akan cenderung melakukan churn?
g. Apakah kastemer yang memiliki tanggungan keluarga cenderung untuk melakukan churn?
h. Apakah kastemer dengan rata-rata jumlah tanggungan keluarga kurang dari 4 cenderung
untuk melakukan churn?
i. Apakah kastemer yang melakukan transaksi terakhir lebih dari 6 bulan lalu mempunyai
kecenderungan untuk mempunyai kemungkinan churn yang lebih tinggi?
j. Apakah ada kemungkinkan kota dan cabang dari bank dengan jumlah nasabah yang kecil
akan cenderung melakukan churn
2. Lakukan praproses data yang menurut anda diperlukan sebelum dilakukan proses penggalian data
(dalam tugas berikutnya). Praproses antara lain meliputi pengecekan setiap tipe data atribut dan
juga penyesuaian tipe data (jika tipe data yang ditentukan oleh python secara otomatis dianggap
kurang tepat atau tidak dikenali oleh python yaitu tipe data object). Selan itu juga perlu dilakukan
praproses terkait dengan missing value dan data pencilan (oulier).
C. Laporan dan Batas Waktu
• Laporan ditulis pada kertas berukuran A4 dengan spasi tunggal. Laporan dalam format PDF
diserahkan per kelompok dan diunggah dalam menu “Assignment” pada aplikasi Teams paling
lambat pada tanggal 14 November 2020 pukul 16:00 WIB (hanya satu orang dari setiap kelompok
yang harus menyerahkan laporan).
• Penilaian akan didasarkan pada aspek: sistematika penulisan dan kelengkapan laporan (15%),
kejelasan uraian laporan termasuk tata tulis (15%), kejelasan metode dan uraian hasil eksplorasi
data (40%), dan kejelasan metode dan uaian praproses data (30%).
• Selain laporan tugas, setiap kelompok juga harus mengunggah script program python (saved file
dalam Jupiter Notebook) dalam format <nama-file>.ipynb.
• Isi laporan dan script program yang mengindikasikan adanya plagiarisme tidak akan dinilai.
Mobile Health (mHealth) applications are readily accessible to the average user of mobile devices, and despite the potential of mHealth applications to improve the availability, affordability
and effectiveness of delivering healthcare services, they handle sensitive medical data, and as
such, have also the potential to carry substantial risks to the security and privacy of their users.
Developers of applications are usually unknown, and users are unaware of how their data are
being managed and used. This is combined with the emergence of new threats due to the deficiency in mobile applications development or the design ambiguities of the current mobile operating systems. A number of mobile operating systems are available in the market, but the
Android platform has gained the topmost popularity. However, Android security model is short of
completely ensuring the privacy and security of users’ data, including the data of mHealth applications. Despite the security mechanisms provided by Android such as permissions and
sandboxing, mHealth applications are still plagued by serious privacy and security issues. These
security issues need to be addressed in order to improve the acceptance of mHealth applications
among users and the efficacy of mHealth applications in the healthcare systems. Thus, this paper
presents a conceptual framework to improve the security of medical data associated with Android
mHealth applications, as well as to protect the privacy of their users. Based on the literature
review that suggested the need for the intended security framework, three-distinct and successive
phases are presented, each of which is described in a separate section. First, discussed the design
process of the first phase to develop a security framework for mHealth apps to ensure the security
and privacy of sensitive medical data. The second phase is discussed who to achieve the implementation of a prototypic proof-of-concept version of the framework. Finally, the third phase
ending discussed the evaluation process in terms of effectiveness and efficiency for the proposed
framework
There are several types of medical apps, some are using external devices such as medical sensors, and some apps are using smartphone resources, such as the camera for the treatment of the patient. The use of mHealth apps among physicians and patients has
grown significantly since the introduction of mobile phones. Physicians can access patients’ data and medical knowledge at the point
of care, and they can also monitor patient health through mHealth apps. The sensitive nature of these apps’ purpose and consequence
of use – in relation to human health – impose several questions about their reliability, authority, and compliance to regulations. Aside
from the functional requirements, issues related to non-functional requirements have also to be addressed, such as the usability of the
apps by users from different age groups. In particular, it soon became clear that mHealth apps carry substantial risks to the security of
user’s sensitive medical data as well as their privacy (Gill et al., 2012). Developers of these apps are usually unknown, and users are
unaware of how their data are being managed and used. In mHealth, users can easily enhance the functionalities of their smartphones
by connecting them to external devices, such as medical devices, sensors and credit card readers. This introduces many new threats
along with the useful applications in various domains, including healthcare information systems and retail (Avancha et al., 2012;
Murthy and Kotz, 2014; Istepanian et al., 2006; Anokwa et al., 2012; Naveed et al., 2014). Android is an operating system based on
Linux for mobile devices. Android platform provides a rich application framework that allows developers to build innovative apps in
the Java language environment. Android is a multi-user system in which each app is considered an individual user, and is given a
unique user ID (UID). Every app runs in its own Linux process and uses a separate virtual machine to be isolated from other apps. In
this way, Android platform implements the principle of least privilege. That is, each app, by default, can only access those components that are required to do its own work. mHealth apps operate on mobile platforms, and because the Android operating system is
chosen as the target platform in this article, there is a need to investigate its structure as well as its security model in detail.
In order to protect user data, system resources (including the network) and apps themselves, Android platform provides the
following extra security features: security at the OS level through the Linux kernel’s secure inter-process communication (IPC),
application sandbox, application signing, and the Android permission model. Recently, researchers have been actively involved in the
study of mHealth apps, in particular their security and privacy. For example, Mitchell et al. (2013) investigated the security and
privacy challenges of mHealth apps; He et al. (2014) raised the security concerns of Android mHealth apps; and Plachkinova et al.
(2015) proposed a taxonomy of mHealth apps’ security and privacy concerns. Nevertheless, beyond the identification and investigation of the problem itself, there is no actual solution for the security and privacy of mHealth apps specifically, except one
policy framework (Mitchell et al., 2013). This framework provides some guidelines to secure mHealth apps; however, these policies
are not enough and even not implemented to secure mHealth apps. In addition, Android-provided security features are still insufficient to protect user data against few security attacks that are equally applicable to mHealth apps and their data, such as side
channel threats, privilege escalation attacks, sensors-based covert channels and DMB attacks (Naveed et al., 2014; He et al., 2014;
Davi et al., 2011; Al-Haiqi et al., 2014). mHealth apps are a new and revolutionary development in healthcare system, and a huge
number of people can access this new system at a very low cost.
Considering the great utility and impact of this phenomenal development, and the detrimental effect that security and privacy
issues might cause to its successful deployment, those issues in mobile health applications on Android platform need to be addressed
to improve mHealth apps’ effectiveness and alleviate any barriers to their rapid integration into the healthcare system. The main
theme of this article is the security and privacy of mHealth apps on Android smartphones. This theme involves two main research
components: the security of Android smartphones, and the incorporation of mHealth apps’ security within Android security model.
Then, figure out the weak points and consequently proposed a new methodology as a solution are discussed as demonstrated in our
study framework in Fig. 1. The remaining sections of this paper are organized as follows. Section 2 describes the mobile health
applications on android platform. Section 3 reports discussion of three distinct and successive phases. Section 4 concludes the
conclusion.
instance: memory management, device drivers, process scheduling, and a file system. The Android middleware layer lies on the top of
the Linux kernel. This layer contains three main components: the application framework, the native operating system libraries, and
the Android runtime environment. The application framework is written in Java and is a collection of services that define the
environment in which Android apps are run and managed. These services are offered to apps as Java classes. System applications such
as the system content providers and system services are also part of the application framework. These applications and services provide
the essential functionalities and services of the platform, such as System Settings, Clipboard, LocationManager, WifiManager, and the
AudioManager. In Android platform, system content providers are essential databases, while system services provide the required highlevel functions to control the device’s hardware and to get information about the platform state, such as location and network status.
Another part of the middleware is a set of native libraries, which provide functionalities such as graphics processing and multimedia
support. These libraries are written in the C/C++ programming language. The final part of Android middleware layer is the runtime
environment, which comprises the Dalvik Virtual Machine and core Java libraries. This layer is mostly written in C/C++ except
parts of the core libraries, and is customized for the specific needs and requirements of resource-constrained mobile devices. As
illustrated in Fig. 2, Android Application layer is located at the top of the Android software stack. This contains both the pre-installed
apps (i.e., native Android apps) and the third party apps developed by different (unofficial) app developers. Apps are written in Java,
but for performance reasons may include native code (C/C++), which is called through the Java Native Interface (JNI). Basically,
the Android OS is a multi-user system, in which each app has a unique user ID (UID). All files in an app will be assigned to that apps
UID and usually not accessible to other apps. Each app runs in its own Linux process with a unique UNIX user identity and isolated
from other apps, so that apps must explicitly share data and resources. In this way, Android platform implements the principle of least
privilege. Generally, Android app consists of certain components: Activity (User interface), Service (background process), Broadcast
Receiver (mailbox for broadcast messages), and Content Provider (SQL-like database) (Bugiel et al., 2011). The usual path to develop
Android apps is to use the special Software Development Kit (SDK) provided by Google, which include an extensive set of tools and
API libraries, using the Java programming language. This development option relies on the rich Java application framework in
Android, which enable developers to interact with various aspects of the system including hardware and software components, such
as sensors, wireless interfaces, telephony services, as well as multimedia and user interface elements. It is also possible to write native
apps in the C/C++ language through the special Native Development Kit (NDK) (Android, 2016). A finalized Android app is not
distributed in the traditional Java bytecode format within “.class” files or “.jar” archives. Rather, Android development kit includes a
tool that converts the Java compiled bytecode into custom bytecode in the form of “.dex” files to be executed by the Dalvik virtual
machine. Developers have also to sign their apps, though they can use self-signed certificates (Android, 2016). The official online
store and market for Android apps is Google Play (Google, 2016), where developers can distribute and sell their apps.
Android platform is a common target for academic research studies, including the present one. The main reason behind this
popularity is straightforward: Android is an open product; its source code is available from Google for interested parties, including
other software developers and hardware vendors. This has resulted in an interesting outcome. On the one hand, researchers can
dissect the operating system source code, studying relevant parts to their research and potentially modifying the code base accordingly. The possession of a real and mature operating system code at the disposal of researchers to experiment with and implement
new ideas proved very attractive and rewarding. Most of the research literature on mobile platforms is directed towards Android,
which results in yet more studies that address this platform. On the other hand, being released under an open license (subject to
compliance agreement), Android platform can be adopted by any hardware vendors, including mobile device manufacturers or
embedded-system developers. The target devices that employ Android are far more than those employing competing mobile platforms. For example, the market share of Android-based smartphones is almost 87.6% as of 2016 (Corporation, 2016). This means that
research studies on the Android platform have much broader potential impact than studies performed on other mobile platforms. Also
related to this point is the fact that more hardware devices from various vendors are available to experiment with. This enables
researchers to verify their results on a variety of designs and implementations. The aforementioned reasons justify for the selection of
Android platform as the main target in this as well as other studies. Nevertheless, it is worthy emphasizing that most of the results are
often applicable to other mobile platforms, with some modifications that depend on the nature of the study
One of the most infamous attacks on the Android platform is the privilege escalation attack. In this attack, an app that lacks enough
permissions can delegate the performance of a task that needs missing privileges to another app with the necessary permissions. This
attack has been analysed in many studies (Davi et al., 2011; Felt et al., 2011; Marforio et al., 2012). Another type of studied attacks
include the exploitation of various vulnerabilities, such as those at the level of system design (Lee et al., 2014) and the level of
managing package updates (Xing et al., 2014).
Examples of other miscellaneous attacks include the exploitation of external device mis-bonding (Naveed et al., 2014), the userinterface state inference attack (Chen et al., 2014), the denial-of-app attack (Arzt et al., 2014), and the attack on the WebView
component (Luo et al., 2011). Furthermore, Android forensics received a lot of attention as well (Hoog, 2011; Lessard and Kessler,
2010; Spreitzenbarth, 2011), examples include forensic methods of collection and acquisition (Simão et al., 2011; Vidas et al., 2011),
methods for analysing the file system (Quick and Alzaabi, 2011; Schmitt, 2011), and techniques to counteract the forensic methods
(Albano et al., 2011; Distefano et al., 2010; Karlsson and Glisson, 2014).
Malicious apps (malware) are the main vehicle to implement threats on the Android platform. Therefore, many research studies
can be classified under the category of malware analysis (Felt et al., 2011; Yan and Yin, 2012; Zhou and Jiang, 2012). Other studies
focused on malware detection (Enck et al., 2009; Aafer et al., 2013; Arp et al., 2014; Bläsing et al., 2010; Burguera et al., 2011; Gorla
et al., 2014; Grace et al., 2012; Sanz et al., 2013; Shabtai et al., 2012; Weichselbaum et al., 2014; Wu et al., 2012; Zhou et al., 2013c;
Zhou et al., 2012a). Few researchers even evaluated malware detectors (Maggi et al., 2013; Rastogi et al., 2013; Zheng et al., 2012).
The analysis of Android apps revealed new types of threats, such as apps repackaging (Crussell et al., 2012; Crussell et al., 2013;
Gibler et al., 2013; Hanna et al., 2012; Linares-Vásquez et al., 2014; Zhou et al., 2013a; Zhou et al., 2012b). Another example of new
threats is the problem of embedded third-party code (Sun and Tan, 2014), especially threats from advertisement libraries (Grace
et al., 2012; Pearce et al., 2012; Shekhar et al., 2012; Zhang et al., 2013).
Malware is not the only threat that apps can impose on users. Apps might leak users’ sensitive data unintentionally. This type of
apps is sometimes referred to as grayware. Techniques to protect privacy were addressed by many research works. One of the most
common techniques is the concept of information-flow tracking. In this technique, private data are tainted and then traced
throughout its flow in the app. The flow is logged and possibly blocked whenever a labelled object moves from a private domain to a
public domain (e.g., transferred out through the network). A famous implementation of this type of techniques is TaintDroid (Enck
et al., 2014). Other examples of dynamic taint tracking can be found in Gilbert et al. (2011), Hornyack et al. (2011), Mollus et al.
(2014), Zheng et al. (2012).
Another technique to detect leakage of private data is the static analysis of the source code of Android apps after decompilation
(Arzt et al., 2014; Chan et al., 2012; Chin et al., 2011; Fuchs et al., 2009; Grace et al., 2012; Yang and Yang, 2012). Additional
techniques include symbolic execution (Yang et al., 2013), instrumentation of bytecode (Bartel et al., 2012; Karami et al., 2013) and
repackaging apps (Berthome et al., 2012).
Aside from examining protection mechanisms and analysing available apps, significant portion of the research on Android security proposes various enhancements to its security model. Several studies proposed enhancements to extend the current platform in
the form of new frameworks. Those enhancements aimed to improve several aspects of the system’s security and the user’s privacy.
For example, Saint (Ongtang et al., 2012) proposes a framework to control grants of install-time permissions and the use of those
granted permission during runtime, all according to a policy dictated by the app developer. Other frameworks that enhance runtime
policy include Apex (Nauman et al., 2010); Aurasium (Xu et al., 2012) and AppGuard (Backes et al., 2014).
Another way to improve Android security is to provide context-aware privacy. Among the works that proposed the latter are (Bai
et al., 2010; Chakraborty et al., 2014; Conti et al., 2011). Other works extended the security model with fine-grained access control
(Bugiel et al., 2013; Russello et al., 2011; Zhou et al., 2011). One solution enables the users to reply to apps’ requests with empty or
unavailable resources, based on certain conditions (Beresford et al., 2011). Yet another way to improve the security model is by
implementing isolation between several security domains (Bugiel et al., 2011), by providing different security profiles
(Zhauniarovich et al., 2014), or by enabling differentiated user access control (Ni et al., 2009). SELinux-based mandatory access
control was also added to the Android architecture (Shabtai et al., 2010; Bugiel et al., 2012).
It might be worthy to note, however, that comprehensive surveys on that rich Android security literature are very limited and
many times outdated (Enck, 2011; Becher et al., 2011; La Polla et al., 2013). Table 1 summarizes existing security solutions on
Android.
The market of mHealth apps is nevertheless growing rapidly; hence, health data are also increasing. Privacy
and security of sensitive medical data could be significantly affected by this new trend of treatment of the patient. mHealth apps
handle sensitive medical data for patients and healthcare professionals, and those data are as sensitive as those handled by HIPAA
entities, but mHealth apps handle the data using lower assurance than HIPPA entities. There is a need to develop frameworks and
guidelines for mHealth apps to ensure the security and privacy of health data (He et al., 2014). Android mHealth apps use third party
servers and unsecured Internet communication which have raised the security and privacy concerns. He et al. (2014) revealed that
several mHealth apps send information over the Internet without encryption and put sensitive information into logs. Numerous apps
have component exposure threats, and some apps store unencrypted information on an external storage, e.g., SD Card, where a
malicious app can read them. Several mHealth apps use Bluetooth in order to collect data from health or medical sensors. In fact,
Bluetooth play a major role for communication in sensor-based health monitoring systems; mHealth apps collect numerous types of
health information from Bluetooth, such as electrocardiogram (ECG), heart rate, pulse oximetry, respiration, blood pressure, body
temperature, body weight, exercise activities and quality of sleep (He et al., 2014). mHealth apps store various type of information
without encryption, including but not limited to, mobile users’ name, date of birth, country, preferred language(s), culture preference
(s), insurance carrier identifier, personal app identifier, medications, medical conditions, physician(s), and pharmacies (Mitchell
et al., 2013). mHealth apps are different from other health information systems from various perspectives. First, mHealth apps collect
large amount of data from patient because mobile devices are always with the patient and can collect data for a long time. Second,
mHealth apps collect much broader range of data, which include not only physiological data, but also include the patient activities,
location, lifestyle, social interactions, diet details, eating habits and so on. Third, the nature of communication between the patient
and healthcare professionals is different (He et al., 2014). These aspects imposed new security and privacy threats to mHealth
information systems. There are several potential attack surfaces in Android system that a malicious party can use to gain unauthorized access to sensitive medical data in mHealth apps. A recent study (He et al., 2014) stated seven attack surfaces that need
protection: Third Party Services, Internet, Logging, Bluetooth, SD Card Storage, Side Channels and Exported Components. mHealth
apps in Google Play usually send sensitive medical data in plain text and store them on third party servers whose confidentiality rules
are not sufficient for this type of data (He et al., 2014). Table 2 lists the seven attack surfaces that a malicious party can use to access
sensitive medical data. Developers can view and collect debugging output of apps from Android logging system; therefore, a malicious app with READ_LOGS permission may access sensitive information from log messages. A malicious app with WRITE_EXTERNAL_STORAGE or READ_EXTERNAL_STORAGE permissions can write or read files from external storages, such as SD card (He et al.,
2014). Android app developers can declare a component as public or exported; if a component is declared as exported, then a
malicious app can send unwanted intents to the component. Furthermore, if a content provider is declared as exported or public, this
enables malicious apps to write or read the exported content provider without any permission (He et al., 2014). A malicious party can
also use side channels to get sensitive information from mHealth apps in the Android operating system (He et al., 2014).
He et al. (2014) identified another problem. Developers usually put sensitive information into HTTPS URLs for secure transmission of data. However, even though this information is not visible during transmission, it is still visible in other places, such as
server logs, mobile app logs, browser history and so on. It may be difficult to identify or control who is accessing the logs. Mitchell
et al. (2013) used forensic analysis and testing to prove that current mHealth apps lack adequate privacy and security controls. The
authors revealed that several apps store unencrypted personal information on the smartphone itself. The log files show plain text
instead of encrypted text from top-downloaded app. These issues are more important for mHealth apps that store personal health
information (PHI) or electronic health records (EHR) and exchange that data with health-related websites (Mitchell et al., 2013). Data
security, access control and confidentiality are the main security issues in mHealth apps that must be addressed in order to use these
apps in healthcare system (Mitchell et al., 2013; He et al., 2014; Adhikari et al., 2014). Security standards have not yet been fully
implemented by OS developers, app programmers, device manufacturers or the different levels of government agencies; therefore, it
is very easy for malicious parties to get access to user data collected by mHealth apps. OS manufacturers commonly hide information
relating to data security and data collection policies inside the tens of pages of legalese presented to end users and they usually click
“Agree” without reading it (Mitchell et al., 2013). McCarthy (2013) reported that most of the users’ data are poorly protected in
mHealth apps. The author also reported that in a technical analysis study of 43 health and fitness apps, only 60% of the paid apps and
74% of the free apps had a privacy policy, and it is accessible either on the developer’s website or in the app. However, only 48% of
the paid apps and 25% of the free apps informed users about the privacy policy. In addition, none of the free apps and just one of the
paid apps encrypted all the communications sent from the device to the developer. The data without encryption in mHealth apps pose
a serious threat to users’ data privacy. Adhikari et al. (2014) performed analysis on 20 most popular mHealth apps and highlighted
the various security concerns. In brief, the serious risks to users’ data are insufficient security measures, lack of users’ authentication,
sharing of information with third party, and lack of users’ knowledge about the app. Nowadays, users can easily enhance the
functionalities of their smartphones by connecting them to external devices, such as medical devices, sensors and credit card readers,
which allow them to use smartphones in various application domains such as healthcare information systems and retail stores.
However, this new development is not accompanied by a corresponding levels of protection; indeed, if an app has permission to use
communication channels like Near Field Communication (NFC) and Bluetooth, then it can easily access the devices that communicate
with the smartphone on these channels (Naveed et al., 2014). Android’s permissions and sandbox security model mainly protect local
resources, such as SD-card, GPS, etc. Each of these resources is protected through one or more permissions, and can only be accessed
by Android apps that have appropriate permissions to use particular resources. On the other side, no permissions are allocated to an
external device; however, android can only control channels that links smartphone to external device, such as NFC, Bluetooth, Audio
port, etc. The main problem here is that many external devices share the same channel in order to communicate with smartphone and
many apps also claim the permission to use that channel for different purposes. Consequently, it is very difficult to control unauthorized access on external devices in the presence of those insiders (unauthorized apps that has permission to use the device’s
communication channel). Naveed et al. (2014) revealed this new security issue for Android devices called as External Device MisBonding (DMB). A malicious app using DMB attack can surreptitiously collect patient’s data from an Android device or spoof a device
and inject fake data into the original device’s medical app. In data-stealing attack, a malicious app with Bluetooth permission can
surreptitiously download a patient’s sensitive data from external devices without being noticed, using side-channel information to
find the right moment for download. In data-injection attack, a malicious app with Bluetooth permission can collect the pairing
information of an authorized device and reset the link key, and it can pair with a malicious device to inject fake medical data into the
original device’s official app. In fact, Bluetooth secure communcation is designed for protecting device to device communication, not
to protect communcation between a device and an app (Naveed et al., 2014). External devices may not develop their own authentication systems because these are simple sensors and usually do not have much resources to ensure authentication. Some of the
most common threats to mHealth apps are defined in Kotz (2011). Those threats include: (1) identity threats: misuse of patient
identity information (PII); (2) Access threats: unauthorized access to personal health information (PHI) or personal health records
(PHR); and (3) Disclosure threats: unauthorized disclosure of PII or PHI.
Plachkinova et al. (2015) report the various types of threats that mHealth app are posing to users. A taxonomy of mHealth apps
with respect to security and privacy is proposed by Plachkinova et al. (2015), and it has three dimensions, as shown in Fig. 5. Fig. 6
depicts the proposed taxonomy based on the model in Fig. 5. Details can be found in Plachkinova et al. (2015).
To gain a first-hand experience with the security and privacy of mHealth apps, we conducted an assessment experiment on
mHealth apps. A sample of 100 mHealth apps has been selected, downloaded, and inspected to identify possible security and privacy
issues. Fig. 7 presents the results of this experiment, summarising the issues that have been found on the sample mHealth apps.
During the experiment, it was observed that a lot of apps were disclosing the user data (73 out of 100). Only 11 apps out of 100 were
accessing the external devices, and when checked against DMB attacks (for both data injection and data stealing DMB attacks), not a
single app was able to defend against DMB attacks. Furthermore, 53 apps were a source for a malicious app to perform privileges
escalation attacks. Only 7 apps were using the encryption to secure user data. Some apps were accessing user information that was
not necessary at all to fulfil their functionalities. Only, 45 percent of the apps were using authentication to secure user information.
Among these 100 apps, 73 were detected to be leaking the private information. Most of those apps request to access the location,
contacts, call logs, phone identity, camera, account information and Bluetooth. Among the 73 apps, 48 apps leak the location
information, 43 apps send the IMEI number, and 24 apps leak both IMEI number and location information. Although 32 apps are
accessing the contacts, most of them do not need contacts to perform their functions. Further, 21 apps have permission to access
Bluetooth, which can cause DMB attack.
This policy framework is a set of guidelines to follow for the developers and users. These recommendations were developed based
on the results of conducting a physical forensics analysis of several widely used mHealth applications. Another security and privacy
solution for mHealth apps was proposed by Adhikari et al. (2014). Again, the authors proposed a set of recommendations to consumers and mHealth app developers that can be found in Table 3. The proposed guidelines are based on a comparative analysis of the
20 most popular mHealth apps at the time of publication. The aim of the analysis was to identify risk and safe features that can help
consumers select safe mHealth apps and aid developers in building mHealth apps with appropriate security and privacy measures.
On a more technical level, a static taint-analysis framework was proposed by He (2014), which is shown in Fig. 8. Static taint
analysis works by analysing tainted data flows through Android apps and sending outputs to human analysts or to automated tools
which can make security decisions. Again, in this research, the author also proposed some recommendations, listed below:
A key component of the Android security model is the permission system that controls the access to sensitive device resources by
third-party apps. However, Android’s permission control mechanism has been proven ineffective to protect user’s privacy and resource from malicious apps (Rashidi and Fung, 2015). Further, it has been shown that the majority of smartphone apps attempt to
collect data that are not required for the main function of the app (Rashidi and Fung, 2015; Gunasekera, 2012). Reasons for the
drawbacks of the existing permission system include users inexperience with realizing irrelevant resource requests and their urge to
use the app even at the expense of compromising their privacy (Rashidi and Fung, 2015; Felt et al., 2011; Felt et al., 2012). Further,
vulnerabilities in Android kernel can be exploited to obtain access to resources or services that are by default protected from an app
or a user. This type of attack is called privilege escalation attack and it enables unauthorized apps to perform actions with more
privileges than they have been granted. This, in turn, leads to unauthorized access to user data and many sensitive information
leakages. It is also possible to exploit Android exported (i.e. public) components an obtain access to critical permissions, and hence, to
sensitive resources and information (Davi et al., 2011; Rashidi and Fung, 2015). There is also the threat of colluding apps, where
several apps are developed by the same developer, and therefore released under the same certificate. Users install theses apps, some
of which are granted sensitive permissions and others are granted normal ones. Afterwards, each of these apps gets access to the
combined pool of their permissions and resources because they are all assigned the same UID (Rashidi and Fung, 2015; Marforio
et al., 2011). Android platform lacks a configurable, runtime ICC control. This was a design decision to fulfil several purposes. The
first purpose is to prevent an app from accessing any open interfaces of another app, even if the former had obtained the required
permissions at its install time (Felt et al., 2011; Chin et al., 2011; Tan et al., 2015). The second objective is to prevent an app from
intercepting an intent broadcast, and possibly stopping its propagation afterward (Chin et al., 2011; Tan et al., 2015). By intercepting
system-event broadcasts, a malicious app is able to intercept important system events stealthily, which contain sensitive information,
such as an incoming call or SMS. A third purpose is to isolate apps and prevent them from communicating via ICC and other shared
channels (Bugiel et al., 2011; Tan et al., 2015; Bugiel et al., 2012). However, this lack of runtime inter-app access control can lead to
data leakage and confused deputy problems. The presently uncontrolled ICC among apps in Android can be exploited by colluding
apps. Moreover, an Android device has several identifiers that can be used as a unique device ID, such as IMEI, Android system ID, or
hardware serial number (Tan et al., 2015). As Android devices are prone to information leakage, if this device ID is also leaked,
external parties can track the user easily. Even outside the Android middleware, there exist potential security weaknesses that could
compromise the security of an Android device. In particular, potential security weaknesses or vulnerabilities can be located at the
Linux kernel and its native libraries (Loscocco and Smalley, 2001; Zhou et al., 2013b). Also, weaknesses and vulnerabilities can be
associated with device manufacturers’ customization and preinstalled apps (Grace et al., 2012; Tan et al., 2015; Wu et al., 2013; Zhou
Using smartphone apps in the delivery of healthcare is rapidly proliferating (Alanazi et al., 2015; Alanazi et al., 2010; Kalid et al.,
2018a,b; Salman and Zaidan, 2017; Zaidan et al., 2015b). mHealth apps have several potentials that drive this popularity, including
the ability to increase patient satisfaction, improve doctor efficiency, and reduce the cost of healthcare (Bishop, 2013). There is still
no regulatory protection for mHealth apps similar to that available for traditional health sectors, including PC-based electronic health
(Hussain et al., 2015; Hussain et al., 2016; Mat Kiah et al., 2014c; Mat Kiah et al., 2013; Abdulnabi et al., 2017; Zaidan et al., 2015a;
Zaidan et al., 2011). For example, the Health Insurance Portability and Accountability Act (HIPPA) is not yet widely applied to
mHealth apps (Plachkinova et al., 2015). Similarly, the Food and Drug Administration (FDA) intends to apply its regulatory oversight
to only those apps that turn smartphones into medical devices and whose functionality can pose risk to patients’ safety if not
functioning as intended, which is only a subset of all mHealth apps (Food and D. Administration, 2015). Several recent studies
showed that the lack of standardization, guidelines, security and privacy of user data are the main barriers to the widespread use of
mHealth apps (Mitchell et al., 2013; He et al., 2014; Plachkinova et al., 2015; Adhikari et al., 2014; Kharrazi et al., 2012). mHealth
apps face the usual security challenges of enforcing confidentiality, integrity, and availability via authentication, authorization, and
access control (Mitchell et al., 2013; He et al., 2014; Plachkinova et al., 2015; Adhikari et al., 2014; Dehling et al., 2015). Such
protection is necessary to facilitate the adoption of these apps by the healthcare system. Users of mHealth apps are also susceptible to
privacy threats, such as identity theft, disclosure threats, privilege escalation attacks and side channel threats, among others (He
et al., 2014; Plachkinova et al., 2015; Davi et al., 2011; Kotz, 2011). Leakage of information is a major challenge for mHealth apps
(Dehling et al., 2015), where these apps may leak information in numerous ways. For example, apps usually declare their components
as public (He et al., 2014), so malicious apps can easily access their information. Besides, apps usually store unencrypted data on
smartphone external storage (Mitchell et al., 2013; He et al., 2014; McCarthy, 2013), so any app that has the permission to access
external storage can easily access the user’s data. Usage of third party services and sharing of information with social networks or
other third parties are also raising threats to mHealth apps (He et al., 2014; Plachkinova et al., 2015; Adhikari et al., 2014; Dehling
et al., 2015). In addition, mHealth apps use external devices to enhance the functionality of the phone. These devices also impose
serious threats to users data, such as external-Device MisBonding (DMB) attacks that include data-stealing and data-injection attacks
(Naveed et al., 2014), since Android permission system does not provide permission-based protection for external devices and
sensors. Existing smartphone operating systems, particularly Android, are not sufficient to ensure privacy and security of users’ data,
particularly in the case of mHealth apps. One major issue in the security model of Android is that the permission mechanism is too
coarse-grained and the user might not be aware of the full implications when granting permissions to apps (Zhou et al., 2011). Based
intended framework, and were grouped in a single layer named as the Security Module Layer (SML). Crucial to the operation of this
layer is to have a low-level access as an entry point into the internals of the underpinning Android platform, in terms of reference
monitors and hooks onto the various levels of the kernel, middleware as well as the application level. To keep the design modular, the
task of interfacing the SML to the mobile operating system should be delegated to another layer of the framework called the System
Interface Layer (SIL). This layer should be adopted from a previous research work that was leveraged to provide the necessary
foundation for SML. According to this reasoning during the design process, the SML is required to do all the compulsory security
functions and SIL is required to work as an integration layer between SML and the Android OS in order to make SML functional. The
resulting overall design was named as MHealth Apps Security Framework (MASF). MASF mitigates various security and privacy
threats facing by mHealth apps, such as data leakage, privileges escalation attacks, DMB attacks, and misuse of granted permissions to
apps. Furthermore, to protect sensitive medical data and mHealth apps from different attacks, MASF provides several mechanisms
that includes fine-grained access control, context-aware access control, and it further provides data shadowing mechanism to protect
user private information by providing fake versions of the requested data when deemed necessary by the framework. MASF also
enables the users to define their own policies according to their requirements.
In this phase, MASF should be evaluated and analysed in terms of effectiveness and efficiency. Effectiveness of this framework
should be evaluated by demonstrating that the framework can successfully protect the security and privacy of mHealth apps and its
users. To check the effectiveness, a sample of mHealth apps should be tested against the various attacks. Furthermore, a number of
experiments should be conducted to evaluate functioning of MASF, such as to test effectiveness of MASF against different attacks, test
against malwares, impact of data shadowing, impact of permission restrictions, impact of disabling intents, and impact of enabling/
disabling system peripherals. Subsequently, false positive and usability test should also perform on Android customized with MASF.
On the other hand, the efficiency of MASF should be evaluated by examining the performance overhead on the underlying Android
system during the operation of the framework. To check the performance overhead, the following metrics should be measured: CPU
utilization, memory usage, and energy consumption. Several experiments should be conducted to evaluate the performance of MASF.
Due to the lack of publicly available implementations of similar frameworks, the results of MASF’s performance evaluation should be
compared to those obtained out of stock Android versions. The performance of mainstream Android copies is considered as a baseline
against which the impact of the proposed framework’s impact on the system should be measured. Therefore, the CPU time, memory
usage and energy consumption of Android apps on a stock version (without MASF) are considered as the reference of measurement
for the apps performance after installing MASF
platform in the study. Second, the crossing of the mHealth apps and Android security issues was discussed in detail, in terms of
specific threats to mHealth apps, an empirical assessment of current mHealth apps, and previous proposals to address the security and
privacy of mHealth apps. Furthermore, outlines the general methodology that was adopted to carry out this research study. The
conceptual framework was presented based on three main phases. Each phase corresponds to a major distinct step in conducting in
producing the anticipated output. Beyond the literature review which is necessary to identify the research problem and main objectives, lists the major steps of designing the target security framework, implementing the proof-of-concept prototype of the framework, and then proposed the evaluation process for the resulting prototype against a set of performance criteria in terms of
effectiveness and efficiency. The proposed framework will be simulated and implemented in the future to serve as a guide for the
security of mobile health applications on android platform.
"""
        val n=
"""re released under the same certificate. Users install theses apps, some
of which are granted sensitive permissions and others are granted normal ones. Afterwards, each of these apps gets access to the
combined pool of their permissions and resources because they are all assigned the same UID (Rashidi and Fung, 2015; Marforio
et al., 2011). Android platform lacks a configurable, runtime ICC control. This was a design decision to fulfil several purposes. The
first purpose is to prevent an app from accessing any open interfaces of another app, even if the former had obtained the required
permissions at its install time (Felt et al., 2011; Chin et al., 2011; Tan et al., 2015). The second objective is to prevent an app from
intercepting an intent broadcast, and possibly stopping its propagation afterward (Chin et al., 2011; Tan et al., 2015). By intercepting
system-event broadcasts, a malicious app is able to intercept important system events stealthily, which contain sensitive information,
such as an incoming call or SMS. A third purpose is to isolate apps and prevent them from communicating via ICC and other shared
channels (Bugiel et al., 2011; Tan et al., 2015; Bugiel et al., 2012). However, this lack of runtime inter-app access control can lead to
data leakage and confused deputy problems. The presently uncontrolled ICC among apps in Android can be exploited by colluding
apps. Moreover, an Android device has several identifiers that can be used as a unique device ID, such as IMEI, Android system ID, or
hardware serial number (Tan et al., 2015). As Android devices are prone to information leakage, if this device ID is also leaked,
external parties can track the user easily. Even outside the Android middleware, there exist potential security weaknesses that could
compromise the security of an Android device. In particular, potential security weaknesses or vulnerabilities can be located at the
Linux kernel and its native libraries (Loscocco and Smalley, 2001; Zhou et al., 2013b). Also, weaknesses and vulnerabilities can be
associated with device manufacturers’ customization and preinstalled apps (Grace et al., 2012; Tan et al., 2015; Wu et al., 2013; Zhou
Using smartphone apps in the delivery of healthcare is rapidly proliferating (Alanazi et al., 2015; Alanazi et al., 2010; Kalid et al.,
2018a,b; Salman and Zaidan, 2017; Zaidan et al., 2015b). mHealth a"""

        prin("a.len= ${a.length}")
        prin("b len= ${b.length}")
        prin("c len= ${c.length}")
        prin("d len= ${d.length}")
        prin("e len= ${e.length}")
        prin("f len= ${f.length}")
        prin("g len= ${g.length}")
        prin("h len= ${h.length}")
        prin("i len= ${i.length}")
        prin("j len= ${j.length}")
        prin("k len= ${k.length}")
        prin("l len= ${l.length}")
        prin("m len= ${m.length}")
        prin("n len= ${n.length}")

        fun List<Duration>.avg(): Duration = reduce { acc, d -> acc + d } / size
/*
        val ib1: Int
        val ib2: Int
        val ib3: Int
        val ib4: Int
        val ib5: Int
        val ib6: Int
 */
        val ib7: Int
        val tbL= mutableListOf<Duration>()
/*
        val tb1= measureTime { ib1= func(a, b) }.also { tbL += it }
        val tb2= measureTime { ib2= func(c, d) }.also { tbL += it }
        val tb3= measureTime { ib3= func(e, f) }.also { tbL += it }
        val tb4= measureTime { ib4= func(g, h) }.also { tbL += it }
        val tb5= measureTime { ib5= func(i, j) }.also { tbL += it }
        val tb6= measureTime { ib6= func(k, l) }.also { tbL += it }
 */
        val tb7= measureTime { ib7= func(m, n) }.also { tbL += it }
        val tbM= tbL.avg()

        prin("func= $func")
/*
        prin("ib1 = $ib1")
        prin("ib2 = $ib2")
        prin("ib3 = $ib3")
        prin("ib4 = $ib4")
        prin("ib5 = $ib5")
        prin("ib6 = $ib6")
 */
        prin("ib7 = $ib7")
/*
        prin("tb1 = $tb1")
        prin("tb2 = $tb2")
        prin("tb3 = $tb3")
        prin("tb4 = $tb4")
        prin("tb5 = $tb5")
        prin("tb6 = $tb6")
 */
        prin("tb7 = $tb7")
        prin("tbM= $tbM l= $tbL")
    }

    @ExperimentalTime
    @Test
    fun strMatchTest(){
        //testMatsStr(::boyerMoore1)
        //testMatsStr(::knuthMorrisPratt)
        //testMatsStr(::greedySearch)
        testMatsStr(::originalMatchString)
    }

    @Test
    fun notNullItrTest(){
        val list= listOf(1,2,4,null,5,6,null,8,9)
        prin("============= print awal ===============")
        list.forEach { prin(it) }
        prin("============= print akhir ===============")
        list.notNullIterator().forEach { prin(it) }
    }

    @Test
    fun collTest(){
        val arr1= arrayOf(1,2,3)
        val arr2= arrayOf(3,2,1)

        val arr3= arrayOf(arr1)
        val arr4= arrayOf(arr2)

        prin(arr1.contentEquals(arr2))

        val ls1= listOf(1,2,3)
        val ls2= listOf(3,2,1)

        prin(ls1 == ls2)
    }
///*
    @ExperimentalStdlibApi
    @ExperimentalTime
    @Test
    fun collTest_2(){
        val arr1= intArrayOf(1,2,3)
        val arr2= intArrayOf(3,2,1)

        val arr3= arrayOf(arr1)
        val arr4= arrayOf(arr2)
        val arr5= arrayOf(arr4)

        prin("arr4 == Array::class= ${arr4 == Array::class}")

        prin((arr1.asList() as List<Any>).containsAll(arr2.asList()))

        val t1= measureTime { prin(arr1.contentEquals(arr2, checkOrder = false)) }
        prin(arr3.contentEquals(arr4, true, false))
        prin(arr3.contentEquals(arr4, false, true))
        prin("==== t1= $t1 ===")

    prin("getHashCode(arr3, calculateOrder = false)= ${getHashCode(arr3, calculateOrder = false, ignoreLevel = false)}")
    prin("getHashCode(arr4, calculateOrder = false)= ${getHashCode(arr4, calculateOrder = false, ignoreLevel = false)}")
    prin("getHashCode(arr5, calculateOrder = false)= ${getHashCode(arr5, calculateOrder = false, ignoreLevel = false)}")

        prin(arr3.contentEquals(arr4, false, false))
        prin(arr3.contentEquals(arr5, false, false))

        val t2= measureTime { prin(arr1.ktContentEquals(arr2)) }
        prin("t2= $t2")


        val ls1= listOf(1,2,3)
        val ls2= listOf(3,2,1)

        prin(ls1 == ls2)

        prin("+==========")
        val arr6= arrayOf(1,2,3).asList()
        val arr7= arrayOf(3L,2L,1L).asList()

        val t3= measureTime { prin(arr6.shallowContentEquals(arr7, checkOrder = false) { it.toInt() })  } //, { e1, e2 -> e1.compareTo(e2) == 0 }

        prin("t3= $t3")

        val arr10= arrayOf(1,2,3).asList()
        val arr11= arrayOf(1L,2L,3L).asList()

        val t4= measureTime { prin(arr10.shallowContentEquals(arr11) { it.toInt() })  } //, { e1, e2 -> e1.compareTo(e2) == 0 }

        prin("t4= $t4")


        val arr8= intArrayOf(1,2,3)
        val arr9= intArrayOf(3,2,2)

        prin("===== arr8 & arr9 ======")
        prin(arr8.contentEquals(arr9, false))

        //val arrI= intArrayOf(1,3,4)
        //val arrO= arrI as Array<Any>

        //prin(arrO)
    }

    @Test
    fun delegateFun(){
        val delg= Delegate()
        prin(delg.a)
        prin(delg.b)
        delg.b= "oy"
        prin(delg.b)
        //prin(delg.c)
        delg.c= "ahuy"
        prin(delg.c)
        prin(delg.c)
        delg.c= "ahoyu"
        prin(delg.c)
        prin(delg.c)
        prin(delg.c)
        delg.c= "ahoyu_asa"
        prin(delg.c)
    }
// */
}