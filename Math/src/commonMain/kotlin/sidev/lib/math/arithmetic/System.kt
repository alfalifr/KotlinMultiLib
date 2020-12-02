package sidev.lib.math.arithmetic

import sidev.lib.check.notNull
import sidev.lib.collection.toArrayOf
import sidev.lib.console.prine
import sidev.lib.reflex.clazz
import sidev.lib.reflex.getHashCode

/**
 * Interface yang berisi dari bbrp [SimpleEquation].
 */
interface System: Solvable {
    val equations: List<SimpleEquation>
    //3x + 2y = 5
    //2y = 5
    override fun solve(vararg varArg: Pair<String, Calculable>): List<SimpleEquation> =
        solveWithCalc(*varArg.toArrayOf { variableForHash(it.first) to it.second })

    override fun solveWithCalc(vararg varArg: Pair<Calculable, Calculable>): List<SimpleEquation> {
        val resEq= mutableListOf<SimpleEquation>()
        val usedVarArg= varArg.toMutableList()
        val isOperated= BooleanArray(equations.size)

        for((i, eq1) in equations.withIndex()){
            if(isOperated[i]) continue
            prine("System.solveWithCalc() i= $i eq1= $eq1 eq1.hasMultipleVarNames()= ${eq1.hasMultipleVarNames()}")
            if(!eq1.hasMultipleVarNames()){
                val res= eq1.solveSimple()

                prine("System.solveWithCalc() ATAS res= $res left.class= ${res?.left?.clazz}")
                if(res != null) {
                    if(res.left is Variable<*>){
                        usedVarArg += (res.left as Variable<*>).forHash() to res.right
                        resEq += res
                        isOperated[i]= true
                    } else {
                        usedVarArg += res.left to res.right
                    }
                }
                continue
            }

            for(u in i+1 until equations.size){
                val eq2= equations[u]
                val commonVarName= eq1.commonVarName(eq2)
                prine("System.solveWithCalc() commonVarName= $commonVarName")
                if(commonVarName != null){
                    val res= eq1.solveSimple(commonVarName, *usedVarArg.toTypedArray())
                    prine("System.solveWithCalc() res= $res")
                    if(res != null){
                        if(res.left is Variable<*>){
                            usedVarArg += (res.left as Variable<*>).forHash() to res.right
                            resEq += res
                            isOperated[i]= true
                        } else {
                            usedVarArg += res.left to res.right
                        }
                    }
                }
            }
//            isOperated[i]= true
        }

        equations.last().solveSimple(null, *usedVarArg.toTypedArray())
            .notNull { resEq += it }

        return resEq
    }
}

internal open class SystemImpl(override val equations: List<SimpleEquation> = mutableListOf()): System {
    override fun toString(): String = equations.toString()
    override fun hashCode(): Int = getHashCode(equations, false)
    override fun equals(other: Any?): Boolean = when(other){
        is System -> hashCode() == other.hashCode()
        else -> super.equals(other)
    }
}