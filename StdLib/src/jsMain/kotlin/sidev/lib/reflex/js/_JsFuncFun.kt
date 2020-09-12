package sidev.lib.reflex.js

import sidev.lib.console.prine
import sidev.lib.reflex.js.kotlin.KotlinJsConst

/**
 * Mengambil fungsi yg didklarasikan pada konstruktor dari [any]
 * atau [any] itu sendiri jika merupakan fungsi.
 * Fungsi yg diambil hanya yg publik.
 */
fun getDeclaredFunction(any: Any): List<JsCallable<*>>{
    val any= try{ jsPureFunction(any) } catch (e: Throwable){ any } as Any
            //Agar dapat mengekstrak nilai sesungguhnya
            // jika [obj] merupakan wrapper seperti JsCallable.
    if(any.isUndefined)
        throw IllegalArgumentException("obj: undefined.") //Agar lebih kontekstual.

    return any.prototype.properties.filter { it.second?.isFunction == true }
        .map { prop ->
            var funName= jsName(jsPureFunction(prop.second!!))
//            prine("getDeclaredFun() prop= $prop funName= $funName")
            var func= prop.second!!
            if(funName.isBlank())
                JsReflexConst.FUNCTION_PATTERN.toRegex().findAll(func.toString())
                    .forEach { res ->
                        val vals= res.groupValues
                        funName= prop.first

                        //Jika terdapat bbrp fungsi dg nama inner yg diberi Kotlin.
                        KotlinJsConst.FUNCTION_INNER_NAME_PATTERN.toRegex().findAll(prop.first).forEach {
                            funName= it.groupValues.last()
                        }

                        val paramStr= vals[2]
                        val blockStr= vals[3]
//                        val newNamedFunStr= createNativeFunStr(funName, paramStr, blockStr) //"function $funName($paramStr) { try{ $blockStr } catch(e){ if(!(e instanceof ReferenceError)) throw e; else console.log('Terjadi ReferenceError dalam $funName: ' +e) } }"
/*
                        val newFun=
                            //TODO <24 Agustus 2020> => Entah knp kok `eval("newFun = $newNamedFunStr")` gak bisa di-catch error-nya
                            try{ eval("newFun = $newNamedFunStr") }
                            catch (e: Throwable){ eval("temp = ${funReturningFun(funName, newNamedFunStr)}") }
 */
                        val newFun = try {
                            val newNamedFunStr= createNativeFunStr(funName, paramStr, blockStr, false) //"function $funName($paramStr) { try{ $blockStr } catch(e){ if(!(e instanceof ReferenceError)) throw e; else console.log('Terjadi ReferenceError dalam $funName: ' +e) } }"
                            val temp= null
                            eval("temp = $newNamedFunStr")
                        } catch (e: Throwable){
                            val isMemberFunc= isFunMember(blockStr)
                            createFunWrapper(funName, paramStr, func, isMemberFunc)
                        }

                        func= newFun

                        //TODO <24 Agustus 2020> => setProperty(any.prototype, prop.first, func) dapat merubah fungsi yg semula gak terjadi ReferenceError jadi ada.
                        //  Mungkin karena deklarasi fungsi di luar package.
                        //TODO <25 Agustus 2020> => Udah bisa, solusinya yaitu buat fungsi wrapper dg isinya adalah fungsi yg lama.
                        //  Hapus segera todo
                        setProperty(any.prototype, prop.first, func)
                    }
            object : JsCallableImpl<Any?>(func){
                override val innerName: String = prop.first
            }
        }.toList()
}

/**
 * Membungkus fungsi dg nama [returnedFunName] ke dalam sebuah fungsi pembungkus bernama [funName].
 * Fungsi pembungkus ini berguna untuk melakukan lazy komputasi terhadap fungsi yg blum tersedia saat pemanggilan.
 *
 * Fungsi pembungkus yg dihasilkan memiliki satu parameter yg merupakan wrapper untuk menampung
 * nilai boolean yg menunjukan bahwa fungsi [funName] merupakan fungsi pembungkus,
 * sehingga `caller` dapat mengetahui dan melakukan pemanggilan lagi terhadap nilai yg dikembalikan
 * oleh fungsi [funName].
 */
fun funReturningFun(funName: String, returnedFunName: String): dynamic{
    val isFunTypeWrapperName= JsReflexConst.PARAMETER_CHECK_FUN_IS_WRAPPER
    var a_temp: dynamic
    return eval(""" 
|a_temp = function $funName($isFunTypeWrapperName){
|   if($isFunTypeWrapperName !== void 0){
|   $isFunTypeWrapperName.$isFunTypeWrapperName = true
|}
|   return $returnedFunName 
|}""".trimMargin())
}

fun createNativeFunStr(name: String, paramStr: String, blockStr: String, withRefErrorSafety: Boolean= true): String{
    val reformedBlockStr= if(withRefErrorSafety) """
|try{ $blockStr } 
|catch(e){ 
|   if(!(e instanceof ReferenceError)) throw e; 
|   else console.error('Terjadi ReferenceError dalam $name: ' +e) 
|}""".trimMargin()
    else blockStr

    return """function $name($paramStr) { $reformedBlockStr }""" //console.info(`func $name dipanggil`);
}

/**
 * Membungkus fungsi [func] dg fungsi baru dg nama [name].
 * Fungsi pembungkus ini berguna untuk fungsi yg didalamnya memiliki deklarasi internal
 * sehingga tidak bisa dipanggil di luar fungsi asli [func].
 */
fun createFunWrapper(name: String, paramStr: String, func: dynamic, isMemberFunc: Boolean= true): dynamic{
    val funcCallStr= if(isMemberFunc) "func.call(this${ if(paramStr.isNotBlank()) ", $paramStr" else "" })"
        else "func($paramStr)"
    val temp= null
//    prine("createFunWrapper() paramStr= $paramStr funcCallStr= $funcCallStr func= $func isimember= $isMemberFunc")
    return eval("""
        temp = function $name($paramStr){
            return $funcCallStr
        }
    """.trimIndent())
    /*
            try{ return $funcCallStr }
            catch(e){
                console.warn("Tidak dapat memanggil fungsi asli, return func= " +func +"\n  e= " +e)
                return func
            }
     */
}

fun isFunMember(blockStr: String): Boolean = blockStr.contains("this.[\\S]".toRegex())
        || blockStr.contains("\\(this.*\\)".toRegex())
        || blockStr.contains(".call\\(this.*\\)".toRegex())

//val Any.constructor: Any