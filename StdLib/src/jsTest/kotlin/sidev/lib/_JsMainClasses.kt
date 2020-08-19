package sidev.lib

//import sidev.lib.annotation.AnnotatedFunctionClass
//import sidev.lib.annotation.Rename
import sidev.lib.console.prin


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
    val d= 1
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
internal open class AC(val poinConstr: Poin): Z, Y, X, AB() { //, AnnotatedFunctionClass {
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

    @Anotasi<Int, Any> @FunAnot(10)
    fun someFun(x: Int) = prin("\n\n ==== Halo dari AC.someFun() x= $x ==== \n\n")
    @Anotasi<Int, Any> @FunAnot(2)
    fun someOtherFun(x: Int, az: Int= 1) = prin("\n\n ==== Halo dari AC.someOtherFun() x= $x az= $az ==== \n\n")
    override fun ada() {}
}

internal class Poin(var x: Int= 13, var y: Int, /*@property:Rename("az") TODO temporary*/ var z: Int= 10){
    /*@Rename("aa_aa_diPoin") TODO temporary */ var aa_diPoin= AA_()
}
