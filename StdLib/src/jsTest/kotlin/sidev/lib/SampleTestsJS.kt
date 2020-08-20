package sidev.lib

import sidev.lib.collection.sequence.withLevel
import sidev.lib.console.log
import sidev.lib.console.prin
import sidev.lib.console.prine
import sidev.lib.platform.globalRef
import sidev.lib.platform.setGlobalObject
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.full.*
import sidev.lib.reflex.common.native.si
import sidev.lib.reflex.js.*
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

        println("============= AC.declaredMemberPropertiesTree =============")
        for((i, prop) in AC::class.si.declaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop")
        }
        println("============= AC.nestedDeclaredMemberPropertiesTree =============")
        for((i, prop) in AC::class.si.nestedDeclaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop type= ${prop.returnType}")
        }

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

        prin("\n============= ac.implementedNestedPropertyValuesTree =============\n")
        for((i, prop) in ac.implementedNestedPropertyValuesTree.withIndex()){
            println("i= $i prop= $prop isPrimitive= ${prop.returnType.isPrimitive}")
        }

        prin("\n============= AC::class.si.nestedDeclaredMemberPropertiesTree =============\n")
        for((i, prop) in AC::class.si.nestedDeclaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop isPrimitive= ${prop.returnType.isPrimitive}")
        }
    }
}