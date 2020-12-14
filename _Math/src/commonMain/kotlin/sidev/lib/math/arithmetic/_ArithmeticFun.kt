package sidev.lib.math.arithmetic

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.check.isNull
import sidev.lib.check.notNull
import sidev.lib.collection.iterator.iteratorSimple
import sidev.lib.collection.sequence.NestedSequence
import sidev.lib.collection.sequence.emptyNestedSequence
import sidev.lib.collection.sequence.nestedSequence
import sidev.lib.console.prine

fun <T: Number> variableOf(name: String, coeficient: T): Variable<T> = VariableImpl(name, coeficient)
fun <T: Number> constantOf(number: T): Constant<T> = ConstantImpl(number)

fun variableForHash(name: String): VariableForHash = VariableForHashMap(name)
fun variableForHash(origin: Variable<*>): VariableForHash = when(origin){
    is VariableForHash -> origin
    else -> VariableForHashMap(origin)
}

fun Variable<*>.forHash(): VariableForHash = when(this){
    is VariableForHash -> this
    else -> VariableForHashMap(this)
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <T> Map<out Variable<*>, T>.get(`var`: Variable<*>): T? = (this as Map<Variable<*>, T>)[`var`.forHash()]
operator fun <T> MutableMap<Calculable, T>.set(`var`: Variable<*>, value: T) {
    put(`var`.forHash(), value)
}

fun blockOf(firstElement: Calculable = NullCalculable, /*operationLevel: Int = 1,*/ parentBlock: Block? = null): Block =
    BlockImpl(firstElement, parentBlock)

fun blockOf(blockStr: String): Block = Block.parse(blockStr)

fun Calculable.numberComponent(factorizeBlockFirst: Boolean = true): Number? = when(this){
    is Constant<*> -> number
    is Variable<*> -> coeficient
/*
    is Block -> {
        (if(factorizeBlockFirst) Solver.factorize(this).also {
            if(it !is Block) return it.numberComponent(false)
        } else this).run { this as Block
            elements.find { it is Constant<*> }?.numberComponent(false)
                ?: 1
        }
    }
 */
    else -> null
}
fun Calculable.nameComponent(): String? = when(this){
    is Variable<*> -> name
/*
    is Block -> {
        (if(factorizeBlockFirst) Solver.factorize(this).also {
            if(it !is Block) return it.numberComponent(false)
        } else this).run { this as Block
            elements.find { it is Constant<*> }?.numberComponent(false)
                ?: 1
        }
    }
 */
    else -> null
}

val Calculable.varNames: NestedSequence<String> get()= when(this){
    is Variable<*> -> nestedSequence(this) { iteratorSimple(it.name) }
    is Block -> {
        prine("Calculable.varNames this= $this elements= $elements class= ${this::class}")
        nestedSequence<Sequence<Calculable>, String>(elements.asSequence()) { list ->
            prine("Calculable.varNames list= $list")
            list.flatMap { it.varNames }.iterator()
        }
    }
    else -> emptyNestedSequence()
}

val Equation.varNames: Sequence<String> get()= blocks.asSequence().flatMap { it.varNames }


val Calculable.varNameCounts: Map<String, Int> get()= when(this){
    is Variable<*> -> mapOf(name to 1)
    is Block -> {
        val resCounts= mutableMapOf<String, Int>()
        elements.forEach {  e ->
            e.varNameCounts.forEach { entry ->
                resCounts[entry.key]
                    .notNull { resCounts[entry.key]= it + 1 }
                    .isNull { resCounts[entry.key]= 1 }
            }
        }
        resCounts
    }
    else -> emptyMap()
}

fun Block.hasMultipleVarNames(): Boolean {
    val itr= (this as Calculable).varNames.iterator()
    val firstName= itr.next()
    if(!itr.hasNext())
        return false
    while(itr.hasNext()){
        if(firstName != itr.next())
            return true
    }
    return false
}

fun SimpleEquation.hasMultipleVarNames(): Boolean {
    if(left is Block && (left as Block).hasMultipleVarNames())
        return true
    if(right is Block && (right as Block).hasMultipleVarNames())
        return true

    //Sampai sini, `left` dan `right` hanya memiliki varName sebanyak 0 atau 1.
    val leftName= left.varNames.firstOrNull().also { if(it == null) return false }
    val rightName= right.varNames.firstOrNull().also { if(it == null) return false }

    //Jika `leftName` != `rightName`, artinya `this` `SimpleEquation` punya multipleVarNames.
    return leftName != rightName
}

fun Calculable.hasVarName(varName: String): Boolean = when(this){
    is Variable<*> -> name == varName
    is Block -> hasVarName(varName)
    else -> false
}

fun Equation.hasVarName(varName: String): Boolean = when(this){
    is SimpleEquation -> hasVarName(varName)
    else -> {
        blocks.forEach { if(it.hasVarName(varName)) return@hasVarName true }
        false
    }
}
fun SimpleEquation.hasVarName(varName: String): Boolean = left.hasVarName(varName) || right.hasVarName(varName)

fun Calculable.hasSameVarNameWith(vararg calculables: Calculable, varName: String? = null): Boolean {
    if(varName != null) {
        if(!hasVarName(varName))
            return false

        for(calc in calculables){
            if(!calc.hasVarName(varName))
                return false
        }
        return true
    } else {
        return commonVarName(*calculables) != null
/*
        when(this){
            is Variable<*> -> {
                for(calc in calculables){
                    if(!calc.hasVarName(name))
                        return false
                }
                return true
            } //return !hasSameVarNameWith(*calculables, varName = name)
            is Block -> {
                for(name in (this as Calculable).varNames){
                    var bool= true
                    for(calc in calculables){
                        bool= bool && calc.hasVarName(name)
                        if(!bool)
                            break
                    }
                    if(bool)
                        return true
                }
                return false
            }
            else -> return false
        }
 */
    }
}
fun Equation.hasSameVarNameWith(vararg equations: Equation, varName: String? = null): Boolean {
    if(varName != null) {
        if(!hasVarName(varName))
            return false

        for(eq in equations){
            if(!eq.hasVarName(varName))
                return false
        }
        return true
    } else {
        return commonVarName(*equations) != null
    }
/*
        for(name in varNames){
            var bool= true
            for(eq in equations){
                bool= bool && eq.hasVarName(name)
                if(!bool)
                    break
            }
            if(bool)
                return true
        }
        return false
    }
 */
}

fun Calculable.commonVarName(vararg calculables: Calculable): String? {
    when(this){
        is Variable<*> -> {
            val name= name
            for(calc in calculables){
                if(!calc.hasVarName(name))
                    return null
            }
            return name
        } //return !hasSameVarNameWith(*calculables, varName = name)
        is Block -> {
            for(name in (this as Calculable).varNames){
                var bool= true
                for(calc in calculables){
                    bool= bool && calc.hasVarName(name)
                    if(!bool)
                        break
                }
                if(bool)
                    return name
            }
            return null
        }
        else -> return null
    }
}
fun Equation.commonVarName(vararg equations: Equation): String? {
    for(name in varNames){
        var bool= true
        for(eq in equations){
            bool= bool && eq.hasVarName(name)
            if(!bool)
                break
        }
        if(bool)
            return name
    }
    return null
}


fun simpleEquationOf(
    leftCalc: Calculable, rightCalc: Calculable,
    sign: Equation.Sign = Equation.Sign.EQUAL
): SimpleEquation =
    SimpleEquationImpl(leftCalc, rightCalc, sign)

fun simpleEquationOf(equationStr: String): SimpleEquation = SimpleEquation.parse(equationStr)

fun systemEquationOf(
    vararg simpleEquations: SimpleEquation
): System =
    SystemImpl(simpleEquations.toList())



fun Char.isMathOperator(): Boolean = when(this) {
    '+' -> true
    '-' -> true
    '*' -> true
    '/' -> true
    '%' -> true
    '^' -> true
    '~' -> true
    else -> false
}

fun String.isMathEquitySign(): Boolean = when(this){
    "=" -> true
    ">" -> true
    "<" -> true
    ">=" -> true
    "<=" -> true
    else -> false
}

fun String.isMathSign() = first().isMathOperator() || isMathEquitySign() || when(this) {
    "(" -> true
    ")" -> true
    else -> false
}