package sidev.lib

import sidev.lib.check.nativeTrya2
import sidev.lib.collection.sequence.toTypedArray
import sidev.lib.collection.sequence.withLevel
import sidev.lib.console.log
import sidev.lib.console.prin
import sidev.lib.number.*
import sidev.lib.platform.setGlobalObject
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.core.createType
import sidev.lib.reflex.common.full.*
import sidev.lib.reflex.common.full.types.*
import sidev.lib.reflex.common.native.jsClass
import sidev.lib.reflex.common.native.si
import sidev.lib.reflex.common.native.siClass
import sidev.lib.reflex.js.*
import sidev.lib.type.Null
import sidev.lib.`val`.RoundingMode
import kotlin.math.exp
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsJS {
    @Test
    fun testHello() {
        assertTrue("JS" in hello())
    }

    @Test
    fun reflex(){
        println("Test halo")

        val jsPrimNumberConstr= eval(JsPrimitiveType.NUMBER.jsConstructorName).unsafeCast<Any>().jsClass
        prin("jsPrimNumberConstr= $jsPrimNumberConstr")
        log(jsPrimNumberConstr.valueOf())
        js("function J(b){this.b= b; this.a= 10; console.log('halo J b= ' +this.b +' a= ' +this.a)}")
        val funJCls= eval("J").unsafeCast<Any>().siClass
        val boolCls= true::class.si
        val strCls= "aku maka"::class.si
        val numCls= 1.3::class.si
        val otherCls= A::class.si
        prin("boolCls.isPrimitive= ${boolCls.isPrimitive}")
        prin("strCls.isPrimitive= ${strCls.isPrimitive}")
        prin("numCls.isPrimitive= ${numCls.isPrimitive}")
        prin("otherCls.isPrimitive= ${otherCls.isPrimitive}")
        prin("funJCls= $funJCls")
        log(funJCls)
        prin("funJCls.isPrimitive= ${funJCls.isPrimitive}")

        prin("\n=============== funJCls member =============\n")
        for((i, member) in funJCls.members.withIndex()){
            prin("i= $i member= $member")
        }

        val instJ= funJCls.constructors.first().descriptor.native.unsafeCast<JsClass_<*>>().new("'aku b'")
        prin("instJ= $instJ")
        log(instJ)

        println("============= AC super =============")
        for((i, supert) in AC::class.si.classesTree.withIndex()){
            println("i= $i super= $supert ")
        }

        setGlobalObject("getFunction", getFunctionOnGlobal)
/*
//        eval("window")
        for((i, mem) in (globalRef as Any).properties.withIndex()){
            println("i= $i mem= $mem")
        }
 */

//        putGlobalObject("getFunction")
//        println("getFunctionOnGlobal= $getFunctionOnGlobal")
//        getFunctionOnGlobal("D")
        val putGlobalObjectFunc= ::setGlobalObject
        println("putGlobalObjectFunc= $putGlobalObjectFunc")
        println("AC::class= ${AC::class}")
        log(AC::class)
        println("AC::class.si= ${AC::class.si}")
        for((i, member) in AC::class.si.members.withIndex()){
            println("i= $i member= $member innerName= ${member.descriptor.innerName} native= ${member.descriptor.native}")
            for((u, param) in member.parameters.withIndex())
                println("---u= $u param= $param default= ${param.defaultValue}")
        }
        println("AC::class.js= ${AC::class.js}")
        log(AC::class.js)
        val members= AC::class.si.members.toList()
        val func= members.last()
        val params= func.parameters
        val param= params.lastOrNull()

        println("func= $func")
        println("param= $param name= ${param?.name}")

        val someOtherFun= members[members.lastIndex -1]
        println("someOtherFun= ${someOtherFun.descriptor.native!!}")
        log(someOtherFun.descriptor.native!!)

        val aLazy= members[8]
        val pointConstr= members[9]

        val isMutableProp1= aLazy.descriptor.native is JsMutableProperty<*,*>
        val isMutableProp2= pointConstr.descriptor.native is JsMutableProperty<*,*>

        val isProp1= aLazy.descriptor.native is JsProperty<*,*>
        val isProp2= pointConstr.descriptor.native is JsProperty<*, *>

        println("isMutableProp1= $isMutableProp1 isMutableProp2= $isMutableProp2")
        println("isProp1= $isProp1 isProp2= $isProp2")
        println("aLazy= $aLazy")
        println("pointConstr= $pointConstr")

        for((i, sup) in AC::class.si.supertypes.withIndex()){
            println("i= $i super= $sup")
        }
        println("======super kedua========")
        for((i, sup) in AC::class.si.supertypes.withIndex()){
            println("i= $i super= $sup")
        }
        val Ab= AC::class.si.supertypes.first()
        println("Ab= $Ab classifier= ${Ab.classifier} native= ${Ab.descriptor.native}")
        println("classifier.native= ${Ab.classifier?.descriptor?.native}")

        println("============= AB member =============")
        for((i, member) in (Ab.classifier as SiClass<*>).members.withIndex()){
            println("i= $i member= $member native= ${member.descriptor.native}")
        }
/*
        println("============= Poin members =============")
        for((i, member) in Poin::class.si.members.withIndex()){
            println("i= $i members= $member ")
        }
 */
        val poin= Poin(y=10)
        val poinRef= eval("Poin")
        println("poinRef= $poinRef")
//        js("class;a")
        println("============= AC declaredMemberPropertiesTree =============")
        for((i, prop) in AC::class.si.declaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop type= ${prop.returnType}")
        }

        println("============= Poin members =============")
        for((i, member) in Poin::class.si.members.withIndex()){
            println("i= $i members= $member ")
        }

        println("============= AC declaredMemberPropertiesTree II =============")
        for((i, prop) in AC::class.si.declaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop type= ${prop.returnType}")
        }

        println("============= D members =============")
        for((i, member) in D::class.si.members.withIndex()){
            println("i= $i members= $member ")
        }

        println("AC::class.js.prototype= ${AC::class.js.prototype}")
        log(AC::class.js.prototype)

        println("============= AC js.properties =============")
        for((i, prop) in AC::class.js.properties.withIndex()){
            println("i= $i prop= $prop")
        }

        println("============= AC js.prototype.properties =============")
        for((i, prop) in AC::class.js.prototype.properties.withIndex()){
            println("i= $i prop= $prop")
        }

        val ac= AC(Poin(y = 199))
        val ac2= ac.clone()
//        js("class;a")

        prin("\n============= Clone =============\n")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.ab_abs= ${ac.ab_abs} ac2.ab_abs= ${ac2.ab_abs}")
        prin("ac.dDariAA.d= ${ac.dDariAA.d} ac2.dDariAA.d= ${ac2.dDariAA.d} ")
//    ac.poin.x= 10
        ac.dDariAA.d= 19
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

        println("============= AC.declaredMemberPropertiesTree =============")
        for((i, prop) in AC::class.si.declaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop")
        }
        println("============= AC.nestedDeclaredMemberPropertiesTree =============")
        for((i, prop) in AC::class.si.nestedDeclaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop type= ${prop.returnType}")
        }

/*
        prin("\n============= ac.implementedNestedPropertyValuesTree =============\n")
        for((i, prop) in ac.implementedNestedPropertyValuesTree.withIndex()){
            println("i= $i prop= $prop isPrimitive= ${prop.returnType.isPrimitive}")
        }
 */

        prin("\n============= AC::class.si.nestedDeclaredMemberPropertiesTree =============\n")
        for((i, prop) in AC::class.si.nestedDeclaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop isPrimitive= ${prop.returnType.isPrimitive}")
        }
    }

    @Test
    fun paramTest(){
        val firstParam= Poin::class.si.constructors.first().parameters.first()
        prin("\n=================\n")
        for((i, prop) in Poin::class.si.declaredMemberProperties.withIndex()){
            prin("i= $i prop= $prop isInConstr= ${firstParam.isPropertyLike(prop)}")
        }
    }

    @Test
    fun cloneTest(){
        val ac= AC(Poin(y = 199))
        val ac2= ac.clone()
//        js("class;a")

        prin("\n============= Clone =============\n")
        prin("ac.acStr3= ${ac.acStr3} ac2.acStr3= ${ac2.acStr3}")
        prin("ac.acStr1= ${ac.acStr1} ac2.acStr1= ${ac2.acStr1}")
        prin("ac.acStr2= ${ac.acStr2} ac2.acStr2= ${ac2.acStr2}")
        prin("ac.poin.x= ${ac.poin.x} ac2.poin.x= ${ac2.poin.x}")
        prin("ac.poinConstr.x= ${ac.poinConstr.x} ac2.poinConstr.x= ${ac2.poinConstr.x}")
        prin("ac.poinConstr.y= ${ac.poinConstr.y} ac2.poinConstr.y= ${ac2.poinConstr.y}")
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
        prin("ac.poinConstr.x= ${ac.poinConstr.x} ac2.poinConstr.x= ${ac2.poinConstr.x}")
        prin("ac.poinConstr.y= ${ac.poinConstr.y} ac2.poinConstr.y= ${ac2.poinConstr.y}")
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

        val ac3= AC()
        val ac4= AC()
        val list1= listOf(ac3, ac4)
        val list2= list1.clone()


        prin("ac3::class.si= ${ac3::class.si} ac3::class.js= ${ac3::class.js}")

        prin("list2::class.si= ${list2::class.si}")
        prin("list2::class.si.isCopySafe= ${list2::class.si.isCopySafe}")
        prin("list2::class.si.isCollection= ${list2::class.si.isCollection}")
        prin("\"afsa\"::class.si.isCopySafe= ${"afsa"::class.si.isCopySafe}")

        prin("\n============= List Clone ===============\n")
        list1[0].poin.x= 16
        list1[1].poin.y= 32
        list1[0].poinConstr.y= 36
        list1[1].poinConstr.x= 76
        prin("list1[0].poinConstr.y= ${list1[0].poinConstr.y} list1[1].poinConstr.y= ${list1[1].poinConstr.y}")
        prin("list1[0].poinConstr.x= ${list1[0].poinConstr.x} list1[1].poinConstr.x= ${list1[1].poinConstr.x}")
        prin("list1[0].poin.x= ${list1[0].poin.x} list1[1].poin.x= ${list1[1].poin.x}")
        prin("list1[0].poin.y= ${list1[0].poin.y} list1[1].poin.y= ${list1[1].poin.y}")

        1f.pow(1)
    }

    @Test
    fun cob(){
        val array= arrayOf(1, 3, 2, 4)
        val array1= arrayOf(1, 4)
        val array2= arrayOf("aad", 4, null)

        prin("Enum::class.si= ${Enum::class.si}")
        prin("Enum::class= ${Enum::class}")

        prin("array::class= ${array::class}")
        prin("array::class == array1::class= ${array::class == array1::class}")
        prin("array::class == Array<*>::class= ${array::class == ArrayList::class}")

        prin("array::class.si.isArray= ${array::class.si.isArray}")
        prin("array2::class.si.isArray= ${array2::class.si.isArray}")
        prin("\"afaf\"::class.si.isArray= ${"afaf"::class.si.isArray}")
        prin("array::class.si= ${array::class.si}")
        prin("array2::class.si= ${array2::class.si}")
        prin("array::class.si.descriptor.native= ${array::class.si.descriptor.native}")
//        prin("array::class.si.descriptor.native= ${(array::class.si.descriptor.native as JsClassImpl_<*>).fun}")
    }

    @Test
    fun reflexTypeTest(){
        prin("Null::class= ${Null::class}")
//        prin("SiReflexConst.nullType= ${SiReflexConst.nullType}")

        prin("ArrayList::class.si= ${ArrayList::class.si}")
        ArrayList::class.si.classesTree

        prin("\n========== ArrayList::class.si.classesTree ===========\n")
        for((i, cls) in ArrayList::class.si.classesTree.withLevel().sortedBy { it.level }.withIndex()){
            prin("i= $i cls= $cls")
        }

        val a= ::nativeTrya2
        val b= eval("nativeTrya2")
        prin("a= $a b= $b")
        val singletonList= listOf("bla")
        val array5= arrayOf("bli")
        log(singletonList)
        log(array5)
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

        val is2SubtypeOf4= inferreType1.isSubTypeOf(inferreType3)

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

        prin("clsGen= $clsGen hash= ${clsGen.hashCode()} list= ${clsGen.list} first= ${clsGen.list.firstOrNull()?.firstOrNull()} clsInferredType= $clsInferredType")
        prin("clsGen2= $clsGen2 hash= ${clsGen2.hashCode()} list= ${clsGen2.list} first= ${clsGen2.list.firstOrNull()?.firstOrNull()}")
// */

        val listSingle= listOf(9) //java.util.Collections.singletonList(9)
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
    fun typeAssignableTest(){
//        prin("Null::class= ${Null::class}")
//        prin("Null::class.si= ${Null::class.si}")
//        prin("Null::class= ${Null::class}")

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
//        prin("Enum::class.si.typeParameters.first().upperBounds ${Enum::class.si.typeParameters.first().upperBounds}")
//        prin("Enum::class.si.typeParameters.first() ${Enum::class.si.typeParameters.first()}")
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
    fun inheritenceTest(){
        prin("true.isBoolean= ${true.isBoolean}")
        log(Any::class.js.toString())
        log(Any::class.js.prototype.properties.toTypedArray())
        log(Any::class.js.prototype.prototype)
        prin("=== Any::class.js::class.js =====")
        log(Any::class.js::class.js::class.js.toString())
        log(Any::class.js.prototype.prototype.isFunction)
        log(Any::class.js.prototype.prototype.isObject)
//        log(Any::class.js.prototype.prototype.properties.toTypedArray())
/*
        log(AC::class.js.prototype.prototype.prototype.prototype.prototype.prototype.prototype.properties.toList().toTypedArray())
        log(AC::class.js.prototype.prototype.prototype.prototype.prototype.prototype.prototype.prototype.isFunction)
        log(AC::class.js.prototype.prototype.prototype.prototype.prototype.prototype.prototype.prototype.prototype)
        log(AC::class.js.prototype.prototype.prototype.prototype.prototype.prototype.prototype.prototype.prototype.prototype)
        log(AC::class.js.prototype.prototype.prototype.prototype.isFunction)
 */
        prin("AC::class.js.asDynamic().__proto__ ${AC::class.js.asDynamic().__proto__}")
        prin("AC::class.js.asDynamic().__proto__.__proto__ ${AC::class.js.asDynamic().__proto__.__proto__}")

        prin("\n============== AC::class.si.classesTree ================\n")
        for((i, cls) in AC::class.si.classesTree.withIndex()){
            prin("i= $i cls= $cls")
        }

        prin("=== AC::class.js ====")
        log(AC::class.js)
    }


    @Test
    fun mathTest(){
        val int= 2.3 as Number
        val int2= (2 as Number / int)
        prin("int= $int2")

//        prin(ipow(2, 3))
//        prin(ipow(2, -2))
        prin("2.pow(-2)= ${2 pow -2}")
        prin("125 root 3= ${125 root 3}")
        prin("81 root 4= ${81 root 4}")
        prin("81 root 2= ${81 root 2}")
        prin("81.sqrt()= ${81.sqrt()}")
        prin("exp(2.0)= ${exp(2.0)}")
//        prin(ipow(3, 4))
//        prin(ipow(5, 5))
        kotlin.math.log(2f, 3f)
        prin("10 log 1000= ${10 log 1000}")
        prin("3 log 81= ${3 log 81}")

        //TODO <25 Agustus 2020> => operasi digit pada platform Js blum berjalan sesuai.
        // Terutama fungsi [getNumberAtDigit()] saat param `digitPlace` sama dg minus (-).
        prin("223.1352= ${223.1352}")
        prin("223.1352.round()= ${223.1352.round()}")
        prin("223.1352.round(-1)= ${223.1352.round(-1)}")
        prin("223.1352.round(-2)= ${223.1352.round(-2)}")

        prin("\n================== Round =====================\n")
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
        prin("223.1352[1]= ${223.1352[1]}")
        prin("221343.1352[3]= ${221343.1352[3]}")
        prin("221343.4352[-2]= ${221343.4352[-2]}")
        prin("43.43[-1]= ${43.43[-1]}")
        //TODO <25 Agustus 2020> => Ada keanehan saat di-run. Hasilnya `2.2134343520000002E8`, jadi 2,....
        // Kemungkinan bug di Kotlin/Js.
        prin("221343.4352 * 1000= ${221343.4352 * 1000}")
    }
}