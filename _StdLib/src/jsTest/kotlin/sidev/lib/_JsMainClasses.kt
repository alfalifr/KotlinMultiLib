package sidev.lib
/*
//import sidev.lib.annotation.AnnotatedFunctionClass
//import sidev.lib.annotation.Rename
import sidev.lib.console.prin


class ClsGen<T, R>(val isi: T, var list: List<R>)

internal annotation class Ano
internal annotation class Anotasi<T: Number, O>(val a: Int= 1) //: Ano()

internal fun anot(){
    val aa: Annotation
}


@Anotasi<Int, String>
internal interface A
internal interface Y
internal interface X: Y{
    val x: String
}
internal open class B

internal class C: Z, B(){
    override val a: Boolean
        get() = true
}

internal data class AAD(val a: Int= 100, val b: Boolean, val bClss: B= B(), val dClss: D?)



internal interface Z: A{
    val a: Boolean
}
internal open class F{
    val f= 0
}
internal open class E{
    val e= true
    val fDiE= F()
}
internal open class D{
    var d= 1
    val dStr= ""
    val eDiD= E()
    val fDiD= F()
}

internal open class AA____
internal open class AA___: AA____()
internal open class AA__: AA___(){
    val aa__= 1
}
internal open class AA_: AA__(){
    var aa_= 123
}
internal sealed class AA: AA_(){
    private val aa= ""
    val dDariAA= D()
}
internal sealed class AB: AA(){
    protected val ab= "ok"
    private val ab_2= "ok"
    open var ab_3= 6
    abstract var ab_abs: Int
    abstract fun ada()
}

internal annotation class FunAnot(val a: Int= 101)

@Anotasi<Int, Char>
internal open class AC(val poinConstr: Poin= Poin(y= 14)): Z, Y, X, AB() { //, AnnotatedFunctionClass {
    constructor(): this(Poin(1, 3))
    val ac= "ppop"
    private var acPriv= "aaa"
    private var acPriv_2= "aaa1"
    private var privUInt= 100
    override val a: Boolean = true
    override val x: String
        get() = "as"
    var poin= Poin(1, 2)
    var array= arrayOf(1, 2, 3)
    lateinit var poinLate: Poin //= Poin(1, 2)
    override var ab_abs: Int= 10
    override var ab_3: Int = 80
    val aLazy: String by lazy { "lazy" }

    var acStr1= "aaa1"
    var acStr2= "aaa2"
    var acStr3: String
        set(v){ acStr1= v }
        get()= acStr2

    var dynamicVar: dynamic= 109

    @Anotasi<Int, Any> @FunAnot(10)
    fun someFun(x: Int) = prin("\n\n ==== Halo dari AC.someFun() x= $x ==== \n\n")
    @Anotasi<Int, Any> @FunAnot(2)
    fun someOtherFun(x: Int, az: Int= 1) = prin("\n\n ==== Halo dari AC.someOtherFun() x= $x az= $az ==== \n\n")
    override fun ada() {}
}

class Poin(var x: Int= 13, var y: Int, /*@property:Rename("az") TODO temporary*/ var z: Int= 10){
    /*@Rename("aa_aa_diPoin") TODO temporary */ private var aa_diPoin= AA_()
}



open class Food
open class FastFood: Food()
open class Burger: FastFood()

open class Producer<out T: Food>{
    open fun produce(): T? = null
}
open class Consumer<in T: Food>{
    open fun consume(ins: T) {}
}

open class FoodStore: Producer<Food>()
open class FastFoodStore: Producer<FastFood>()
open class BurgerStore: Producer<Burger>()

open class Everyone: Consumer<Food>()
open class ModernPipel: Consumer<FastFood>()
open class American: Consumer<Burger>()


internal enum class En(val a: Int, val pos: Int){
    A(2, 10),
    B(2, 3),
    C(3, 5);
    val b= 12
}


 */