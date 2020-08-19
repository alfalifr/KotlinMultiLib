package sidev.lib

import sidev.lib.console.log
import sidev.lib.console.prin
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.common.full.classesTree
import sidev.lib.reflex.common.full.declaredMemberPropertiesTree
import sidev.lib.reflex.common.native.si
import sidev.lib.reflex.js.JsMutableProperty
import sidev.lib.reflex.js.JsProperty
import sidev.lib.reflex.js.properties
import sidev.lib.reflex.js.prototype
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
        println("AC::class= ${AC::class}")
        log(AC::class)
        println("AC::class.si= ${AC::class.si}")
        for((i, member) in AC::class.si.members.withIndex()){
            println("i= $i member= $member innerName= ${member.descriptor.innerName} native= ${member.descriptor.native}")
            for((u, param) in member.parameters.withIndex())
                println("---u= $u param= $param")
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

        println("============= AC super =============")
        for((i, supert) in AC::class.si.classesTree.withIndex()){
            println("i= $i super= $supert ")
        }
        println("============= AB declaredMemberPropertiesTree =============")
        for((i, prop) in AC::class.si.declaredMemberPropertiesTree.withIndex()){
            println("i= $i prop= $prop")
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
    }
}