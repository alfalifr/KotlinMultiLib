package sidev.lib

//import com.sun.org.apache.xalan.internal.lib.ExsltMath.power
import sidev.lib.`val`.Order
import sidev.lib.console.prin
/*
import sidev.lib.reflex.core.createType
import sidev.lib.reflex.full.*
import sidev.lib.reflex.full.types.*
import sidev.lib.reflex.comp.native.si
 */
import sidev.lib.algo.insertionSort
import sidev.lib.algo.mergeSort
import sidev.lib.algo.quickSort
import sidev.lib.algo.selectionSort
import sidev.lib.collection.copyGrowTimely
import sidev.lib.collection.fastSort
import sidev.lib.collection.iterator.iteratorSimple
import sidev.lib.collection.sequence.nestedSequenceSimple
import sidev.lib.collection.toTypedArray
import kotlin.collections.ArrayList
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import java.util.Arrays.sort as jsort


class SampleTestsJVM {
    @Test
    fun testHello() {
        assertTrue("JVM" in hello())
    }

    @Test
    fun cobTest(){
        listOf(1)::class.java.declaredFields.forEach(::println)
//        nestedSequence()
    }

    @Test
    fun jvmNestedItrTest(){
        val seq = nestedSequenceSimple(ArrayList::class.java as Class<*>){
            iteratorSimple(it.superclass)
        }
        for(cls in seq){
            prin("cls = $cls")
        }
    }

    @Test
    fun arrayTest(){
        val arr= sidev.lib.collection.array.arrayOf(2, 1, 3, 4)
        prin(arr::class.java)
        arr[0]= 1
        prin(arr[0].inc())
        prin(arrayOf(1,3,4)::class.java)
    }
/*
    @Test
    fun reflexTypeTest(){
        val singletonList= listOf("bla")
        val array5= arrayOf("bli")
        prin("singletonList::class.si.isCollection= ${singletonList::class.si.isCollection}")
        prin("array5::class.si.isCollection= ${array5::class.si.isCollection}")

        val array1= arrayOf(1,3,4,5,6)
        val intArray1= intArrayOf(1,3,4,5,6)
        val array2= arrayOf(1,3,4,5,6, 's', "ada")
        val array3= arrayOf(1,3,4,5,6, 2.3, 4f, 2L)
        val array4= arrayOf(1,3,4,5,6, 's', "ada", null)

        val commonClass1= getCommonClass(*array1)
        val commonClass2= getCommonClass(*array2)
        val commonClass3= getCommonClass(*array3)

        prin("commonClass1= $commonClass1")
        prin("commonClass2= $commonClass2")
        prin("commonClass3= $commonClass3")

        val inferreType1= array1.inferredType
        val inferreType2= array2.inferredType
        val inferreType3= array3.inferredType
        val inferreType4= array4.inferredType

        val is2SubtypeOf4= inferreType2.isSubTypeOf(inferreType4)

        prin("inferreType1= $inferreType1")
        prin("inferreType2= $inferreType2")
        prin("inferreType3= $inferreType3")
        prin("inferreType4= $inferreType4")
        prin("is2SubtypeOf4= $is2SubtypeOf4")
///*
        val clsGen= ClsGen(10.9.asNumber(), listOf(arrayOf("sf")))
        val clsGen2= clsGen.clone()
        clsGen.list= listOf(arrayOf("pa"))
        val clsInferredType= clsGen.inferredType

        prin("clsGen= $clsGen hash= ${clsGen.hashCode()} list= ${clsGen.list} clsInferredType= $clsInferredType")
        prin("clsGen2= $clsGen2 hash= ${clsGen2.hashCode()} list= ${clsGen2.list}")
// */

        val listSingle= java.util.Collections.singletonList(9)
        prin("\n=============== listSingle.implementedPropertyValuesTree ===============\n")
        for((i, prop) in listSingle.implementedPropertyValuesTree.withIndex()){
            prin("i= $i prop= $prop returnType.class= ${prop.first.returnType.classifier}")
        }

        prin("\n=============== Number::class.si.classesTree ===============\n")
        for((i, cls) in Number::class.si.classesTree.withIndex()){
            prin("i= $i cls= $cls")
        }
    }

    @Test
    fun cloneTest(){
        val ac= AC<BlaBla2>(Poin(y = 199))
        val ac2= ac.clone()
//        js("class;a")

        prin("\n============= Clone =============\n")
        prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
        prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
        prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
        prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
        ac.poin.x= 12
        ac.dDariAA.d= 19
        ac.acStr1= "bbb1"
        ac2.acStr2= "bbb2"
        prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
        prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
        prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
        prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
        prin("\n============= ac.implementedPropertyValuesTree =============\n")

        for((i, prop) in ac.implementedPropertyValuesTree.withIndex()){
/*
            if(prop.first.name == "aLazy"){
                prine("prop.first.name == \"aLazy\"")
                val lazyDelName= jsName(prop.second!!)
                prin("lazyDelName= $lazyDelName")
                log(prop.second!!)
                log(prop.second.asDynamic().initializer_0.toString())
            }
 */
            println("i= $i prop= $prop")
        }

        val ac3= AC<BlaBla2>()
        val ac4= AC<BlaBla2>()
        val list1= listOf(ac3, ac4)
        val list2= list1.clone()


        prin("list2::class.si= ${list2::class.si}")
        prin("list2::class.si.isCopySafe= ${list2::class.si.isCopySafe}")
        prin("list2::class.si.isCollection= ${list2::class.si.isCollection}")
        prin("\"afsa\"::class.si.isCopySafe= ${"afsa"::class.si.isCopySafe}")

        prin("\n============= List Clone ===============\n")
        list1[0].poin.x= 16
        list1[1].poin.y= 32
        prin("list1[0].poin.x= ${list1[0].poin.x} list1[1].poin.x= ${list1[1].poin.x}")
        prin("list1[0].poin.y= ${list1[0].poin.y} list1[1].poin.y= ${list1[1].poin.y}")
    }

    @Test
    fun typeAssignableTest(){
        val everyone= Everyone()
        val american= American()

        val foodStore= FoodStore()
        val burgerStore= BurgerStore()

        val amer2: Consumer<Burger> = everyone
        val foodStr2: Producer<Food> = burgerStore
//        val burgStr2: Producer<Burger> = foodStore

        val consBurger= Consumer::class.si.createType(listOf(Burger::class.si.createType().simpleTypeProjection))
        val prodFood= Producer::class.si.createType(listOf(Food::class.si.createType().simpleTypeProjection))
        val prodBurger= Producer::class.si.createType(listOf(Burger::class.si.createType().simpleTypeProjection))

        val everyType= everyone::class.si.createType()
        val burgStrType= burgerStore::class.si.createType()
        val foodStrType= foodStore::class.si.createType()

        val isConsAssignable= consBurger.isAssignableFrom(everyType)
        val isProdAssignable= prodFood.isAssignableFrom(burgStrType)
        val isProdAssignable2= prodBurger.isAssignableFrom(foodStrType)

        prin("consBurger= $consBurger prodFood= $prodFood")
        prin("everyType= $everyType burgStrType= $burgStrType")
        prin("isConsAssignable= $isConsAssignable isProdAssignable= $isProdAssignable isProdAssignable2= $isProdAssignable2")
    }

    @Test
    fun enumTest(){
        prin("Enum::class.si= ${Enum::class.si}")
        prin("Comparable::class.si= ${Comparable::class.si}")
        prin("Enum::class.si.typeParameters.first().upperBounds ${Enum::class.si.typeParameters.first().upperBounds}")
        prin("Enum::class.si.typeParameters.first() ${Enum::class.si.typeParameters.first()}")
        prin("En::class.si= ${En::class.si}")
        prin("En::class.si.classesTree= ${En::class.si.classesTree}")
        prin("En.A::class.si= ${En.A::class.si}")

        prin("\n============= En::class.si.classesTree =============\n")
        for((i, cls) in En::class.si.classesTree.withIndex()){
            prin("i= $i cls= $cls")
        }

        prin("\n============= String::class.si.classesTree =============\n")
        for((i, cls) in String::class.si.classesTree.withIndex()){
            prin("i= $i cls= $cls")
        }

        prin("En::class.si.isCopySafe= ${En::class.si.isCopySafe}")
        prin("En.A::class.si.isCopySafe= ${En.A::class.si.isCopySafe}")
        prin("\"aaf\"::class.si.isCopySafe= ${"aaf"::class.si.isCopySafe}")
    }

    @Test
    fun mathTest(){
        val int= 2.3 as Number
        val int2= (2 as Number / int)
        prin("int= $int2")

        prin(ipow(2, 3))
        prin(ipow(2, -2))
        prin("2.pow(-2)= ${2 pow -2}")
        prin("125 root 3= ${125 root 3}")
        prin("81 root 4= ${81 root 4}")
        prin("81 root 2= ${81 root 2}")
        prin("81.sqrt()= ${81.sqrt()}")
        prin("exp(2.0)= ${exp(2.0)}")
        prin(ipow(3, 4))
        prin(ipow(5, 5))
        log(2f, 3f)
        prin("10 log 1000= ${10 log 1000}")
        prin("3 log 81= ${3 log 81}")

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
        array.forEach {
            prin("array foreeach it= $it")
        }
    }
 */
    @ExperimentalTime
    @Test
    fun jvmSortTest(){
        val arr= arrayOf(1,2,4,1,5,1,5,12,42,23,11,10,15,14)
        val arrk = arr.copyOf()
        val arrj = arr.copyOf()
        val arr1: Array<Int> = arr.copyOf()
        val arr2: Array<Int> = arr.copyOf()
        val arr3: Array<Int> = arr.copyOf()
        val arr4: Array<Int> = arr.copyOf()

        val tk= measureTime { arrk.sort() }
        val tj= measureTime { jsort(arrj) }
        val t1= measureTime { arr1.selectionSort() }
        val t2= measureTime { arr2.insertionSort() }
        val t3= measureTime { arr3.mergeSort() }
        val t4= measureTime { arr4.quickSort() }

        prin("\n\n ========== size=14 ============ \n\n ")
        prin("arr= ${arr.joinToString()}")
        prin("arrk= ${arrk.joinToString()} \n tk= $tk")
        prin("arrj= ${arrj.joinToString()} \n tj= $tj")
        prin("arr1= ${arr1.joinToString()} \n t1= $t1")
        prin("arr2= ${arr2.joinToString()} \n t2= $t2")
        prin("arr3= ${arr3.joinToString()} \n t3= $t3")
        prin("arr4= ${arr4.joinToString()} \n t4= $t4")

        val arr_= arr.copyGrowTimely(20).toTypedArray()
        val arr_k= arr_.copyOf()
        val arr_j= arr_.copyOf()
        val arr5: Array<Int> = arr_.copyOf()
        val arr6: Array<Int> = arr_.copyOf()
        val arr7: Array<Int> = arr_.copyOf()
        val arr8: Array<Int> = arr_.copyOf()

        val t_k= measureTime { arr_k.sort() }
        val t_j= measureTime { jsort(arr_j) }
        val t5= measureTime { arr5.selectionSort() }
        val t6= measureTime { arr6.insertionSort() }
        val t7= measureTime { arr7.mergeSort() }
        val t8= measureTime { arr8.quickSort() }

        prin("\n\n ========== size=280 ============ \n\n ")
        prin("arr_= ${arr_.joinToString()}")
        prin("arr_K= ${arr_k.joinToString()} \n t_k= $t_k")
        prin("arr_j= ${arr_j.joinToString()} \n t_j= $t_j")
        prin("arr5= ${arr5.joinToString()} \n t5= $t5")
        prin("arr6= ${arr6.joinToString()} \n t6= $t6")
        prin("arr7= ${arr7.joinToString()} \n t7= $t7")
        prin("arr8= ${arr8.joinToString()} \n t8= $t8")

        val arr__= arr.copyGrowTimely(100).toTypedArray()
        val arr__k= arr__.copyOf()
        val arr__j= arr__.copyOf()
        val arr9: Array<Int> = arr__.copyOf()
        val arr10: Array<Int> = arr__.copyOf()
        val arr11: Array<Int> = arr__.copyOf()
        val arr12: Array<Int> = arr__.copyOf()

        val t__k= measureTime { arr__k.sort() }
        val t__j= measureTime { jsort(arr__j) }
        val t9= measureTime { arr9.selectionSort() }
        val t10= measureTime { arr10.insertionSort() }
        val t11= measureTime { arr11.mergeSort() }
        val t12= measureTime { arr12.quickSort() }

        prin("\n\n ========== size=1400 ============ \n\n ")
        prin("arr__= ${arr__.joinToString()}")
        prin("arr__k= ${arr__k.joinToString()} \n t__k= $t__k")
        prin("arr__j= ${arr__j.joinToString()} \n t__j= $t__j")
        prin("arr9= ${arr9.joinToString()} \n t9= $t9")
        prin("arr10= ${arr10.joinToString()} \n t10= $t10")
        prin("arr11= ${arr11.joinToString()} \n t11= $t11")
        prin("arr12= ${arr12.joinToString()} \n t12= $t12")

        val arr___= arr.copyGrowTimely(2000).toTypedArray()
        val arr___k= arr___.copyOf()
        val arr___j= arr___.copyOf()
        val arr13: Array<Int> = arr___.copyOf()
        val arr14: Array<Int> = arr___.copyOf()
        val arr15: Array<Int> = arr___.copyOf()
        val arr16: Array<Int> = arr___.copyOf()

        val t___k= measureTime { arr___k.sort() }
        val t___j= measureTime { jsort(arr___j) }
        val t13= measureTime { arr13.selectionSort() }
        val t14= measureTime { arr14.insertionSort() }
        val t15= measureTime { arr15.mergeSort() }
        val t16= measureTime { arr16.quickSort() }

        prin("\n\n ========== size=28000 ============ \n\n ")
        prin("arr___= ${arr___.joinToString()}")
        prin("arr___k= ${arr___k.joinToString()} \n t___k= $t___k")
        prin("arr___j= ${arr___j.joinToString()} \n t___j= $t___j")
        prin("arr13= ${arr13.joinToString()} \n t13= $t13")
        prin("arr14= ${arr14.joinToString()} \n t14= $t14")
        prin("arr15= ${arr15.joinToString()} \n t15= $t15")
        prin("arr16= ${arr16.joinToString()} \n t16= $t16")

//        Collections.reverseOrder<>()
    }
    @ExperimentalTime
    @Test
    fun jvmSortDescTest(){
        val arr= arrayOf(1,2,4,1,5,1,5,12,42,23,11,10,15,14)
        val arrk = arr.copyOf()
        val arrj = arr.copyOf()
        val arr1: Array<Int> = arr.copyOf()
        val arr2: Array<Int> = arr.copyOf()
        val arr3: Array<Int> = arr.copyOf()
        val arr4: Array<Int> = arr.copyOf()

        val tk= measureTime { arrk.sortDescending() }
        val tj= measureTime { arrj.fastSort(order = Order.DESC) }
        val t1= measureTime { arr1.selectionSort(order = Order.DESC) }
        val t2= measureTime { arr2.insertionSort(order = Order.DESC) }
        val t3= measureTime { arr3.mergeSort(order = Order.DESC) }
        val t4= measureTime { arr4.quickSort(order = Order.DESC) }

        prin("\n\n ========== size=14 ============ \n\n ")
        prin("arr= ${arr.joinToString()}")
        prin("arrk= ${arrk.joinToString()} \n tk= $tk")
        prin("arrj= ${arrj.joinToString()} \n tj= $tj")
        prin("arr1= ${arr1.joinToString()} \n t1= $t1")
        prin("arr2= ${arr2.joinToString()} \n t2= $t2")
        prin("arr3= ${arr3.joinToString()} \n t3= $t3")
        prin("arr4= ${arr4.joinToString()} \n t4= $t4")

        val arr_= arr.copyGrowTimely(20).toTypedArray()
        val arr_k= arr_.copyOf()
        val arr_j= arr_.copyOf()
        val arr5: Array<Int> = arr_.copyOf()
        val arr6: Array<Int> = arr_.copyOf()
        val arr7: Array<Int> = arr_.copyOf()
        val arr8: Array<Int> = arr_.copyOf()

        val t_k= measureTime { arr_k.sortDescending() }
        val t_j= measureTime { arr_j.fastSort(order = Order.DESC) }
        val t5= measureTime { arr5.selectionSort(order = Order.DESC) }
        val t6= measureTime { arr6.insertionSort(order = Order.DESC) }
        val t7= measureTime { arr7.mergeSort(order = Order.DESC) }
        val t8= measureTime { arr8.quickSort(order = Order.DESC) }

        prin("\n\n ========== size=280 ============ \n\n ")
        prin("arr_= ${arr_.joinToString()}")
        prin("arr_K= ${arr_k.joinToString()} \n t_k= $t_k")
        prin("arr_j= ${arr_j.joinToString()} \n t_j= $t_j")
        prin("arr5= ${arr5.joinToString()} \n t5= $t5")
        prin("arr6= ${arr6.joinToString()} \n t6= $t6")
        prin("arr7= ${arr7.joinToString()} \n t7= $t7")
        prin("arr8= ${arr8.joinToString()} \n t8= $t8")

        val arr__= arr.copyGrowTimely(100).toTypedArray()
        val arr__k= arr__.copyOf()
        val arr__j= arr__.copyOf()
        val arr9: Array<Int> = arr__.copyOf()
        val arr10: Array<Int> = arr__.copyOf()
        val arr11: Array<Int> = arr__.copyOf()
        val arr12: Array<Int> = arr__.copyOf()

        val t__k= measureTime { arr__k.sortDescending() }
        val t__j= measureTime { arr__j.fastSort(order = Order.DESC) }
        val t9= measureTime { arr9.selectionSort(order = Order.DESC) }
        val t10= measureTime { arr10.insertionSort(order = Order.DESC) }
        val t11= measureTime { arr11.mergeSort(order = Order.DESC) }
        val t12= measureTime { arr12.quickSort(order = Order.DESC) }

        prin("\n\n ========== size=1400 ============ \n\n ")
        prin("arr__= ${arr__.joinToString()}")
        prin("arr__k= ${arr__k.joinToString()} \n t__k= $t__k")
        prin("arr__j= ${arr__j.joinToString()} \n t__j= $t__j")
        prin("arr9= ${arr9.joinToString()} \n t9= $t9")
        prin("arr10= ${arr10.joinToString()} \n t10= $t10")
        prin("arr11= ${arr11.joinToString()} \n t11= $t11")
        prin("arr12= ${arr12.joinToString()} \n t12= $t12")

        val arr___= arr.copyGrowTimely(2000).toTypedArray()
        val arr___k= arr___.copyOf()
        val arr___j= arr___.copyOf()
        val arr13: Array<Int> = arr___.copyOf()
        val arr14: Array<Int> = arr___.copyOf()
        val arr15: Array<Int> = arr___.copyOf()
        val arr16: Array<Int> = arr___.copyOf()

        val t___k= measureTime { arr___k.sortDescending() }
        val t___j= measureTime { arr___j.fastSort(order = Order.DESC) }
        val t13= measureTime { arr13.selectionSort(order = Order.DESC) }
        val t14= measureTime { arr14.insertionSort(order = Order.DESC) }
        val t15= measureTime { arr15.mergeSort(order = Order.DESC) }
        val t16= measureTime { arr16.quickSort(order = Order.DESC) }

        prin("\n\n ========== size=28000 ============ \n\n ")
        prin("arr___= ${arr___.joinToString()}")
        prin("arr___k= ${arr___k.joinToString()} \n t___k= $t___k")
        prin("arr___j= ${arr___j.joinToString()} \n t___j= $t___j")
        prin("arr13= ${arr13.joinToString()} \n t13= $t13")
        prin("arr14= ${arr14.joinToString()} \n t14= $t14")
        prin("arr15= ${arr15.joinToString()} \n t15= $t15")
        prin("arr16= ${arr16.joinToString()} \n t16= $t16")

//        Arrays.copyOf()
//        Collections.reverseOrder<>()
    }
}