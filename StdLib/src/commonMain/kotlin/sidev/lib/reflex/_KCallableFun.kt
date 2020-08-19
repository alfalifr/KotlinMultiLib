package sidev.lib.reflex
/*
import sidev.lib.reflex.inner.isAccessible
import sidev.lib.reflex.type.isAssignableFrom
import sidev.lib.exception.TypeExc
import sidev.lib.structure.data.type.TypedValue
import kotlin.reflect.*

/*
==========================
KCallable - Call - Set - Get
==========================
 */

/**
 * Cara aman untuk memanggil [KCallable.callBy].
 *
 * Fungsi ini melakukan pengecekan terhadap tipe argumen, nullabilitas, dan opsionalitasnya
 * sehingga meminimalisir terjadi error saat pemanggilan [KCallable].
 */
fun <R> KCallable<R>.callBySafely(args: Map<KParameter, Any?>): R{
    val newArgs= HashMap<KParameter, Any?>()
    for(param in parameters){
        var value= args[param]
        if(value == null){
            if(param.isOptional) continue
            if(!param.type.isMarkedNullable)
                throw IllegalArgumentException("Nilai argumen param: \"$param\" tidak boleh null")
        } else if(param.type.classifier != value::class){
            if(param.isOptional) continue
            if(param.type.isMarkedNullable) value= null
            else throw IllegalArgumentException("Tipe param: \"$param\" adalah ${param.type.classifier}, namun argumen bertipe ${value::class}")
        }
        newArgs[param]= value
    }
    return callBy(newArgs)
}


/** @return [O] jika operasi get berhasil, null jika refleksi dilarang. */
fun <I, O> KProperty1<I, O>.forcedGet(receiver: I): O?{
    return try{
        val oldIsAccesible= isAccessible
        isAccessible= true
        val value= get(receiver)
        isAccessible= oldIsAccesible
        value
    } catch (e: Exception){
        //Jika Kotlin melarang melakukan call melalui refleksi
        // --- atau ---
        //Jika terjadi error secara internal pada refleksi Java.
        // Biasanya terjadi pada operasi call melibatkan `lateinit var`
        null
    }
}
/** @return [R] jika operasi call berhasil, null jika refleksi dilarang. */
fun <R> KCallable<R>.forcedCall(vararg args: Any?): R?{
    return try{
        val oldIsAccesible= isAccessible
        isAccessible= true
        val value= call(*args) //get(receiver)
        isAccessible= oldIsAccesible
        value
    } catch (e: Exception){
        //Jika Kotlin melarang melakukan call melalui refleksi
        // --- atau ---
        //Jika terjadi error secara internal pada refleksi Java.
        // Biasanya terjadi pada operasi call melibatkan `lateinit var`
        null
    }
/*
    catch (e: IllegalCallableAccessException){ //Jika Kotlin melarang melakukan call melalui refleksi
        null
    } catch (e: InvocationTargetException){
        //Jika terjadi error secara internal pada refleksi Java.
        // Biasanya terjadi pada operasi call melibatkan `lateinit var`
        null
    }
 */
}
/** @return [R] jika operasi call berhasil, null jika refleksi dilarang. */
fun <R> KCallable<R>.forcedCallBy(args: Map<KParameter, Any?>): R?{
    return try{
        val oldIsAccesible= isAccessible
        isAccessible= true
        val value= callBy(args) //get(receiver)
        isAccessible= oldIsAccesible
        value
    } catch (e: Exception){
        //Jika Kotlin melarang melakukan call melalui refleksi
        // --- atau ---
        //Jika terjadi error secara internal pada refleksi Java.
        // Biasanya terjadi pada operasi call melibatkan `lateinit var`
        null
    }
}



/** @return -> `true` jika operasi set berhasil,
 *   -> `false` jika refleksi dilarang,
 *   -> @throws [TypeExc] jika [value] yg dipass tidak sesuai tipe `this.extension`. */
fun <I, O> KMutableProperty1<I, O>.forcedSet(receiver: I, value: O, alsoInferValueType: Boolean= true): Boolean{
    return try{
/*
value?.clazz?.isSubClassOf(returnType.classifier as KClass<*>) == true
            || (value == null && returnType.isMarkedNullable)

        val isAssignable= if(alsoInferValueType) returnType.isAssignableFrom(value.inferredType)
            else value?.clazz?.isSubClassOf(returnType.classifier as KClass<*>) == true
                    || (value == null && returnType.isMarkedNullable)
 */
        if(returnType.isAssignableFrom(value, alsoInferValueType)){
            val oldIsAccesible= isAccessible
            isAccessible= true
            set(receiver, value)
            isAccessible= oldIsAccesible
            true
        } else {
            throw TypeExc(
                expectedType = returnType.classifier as KClass<*>, actualType = value?.clazz,
                msg = "Tidak dapat meng-assign value dg tipe tersebut ke properti: $this."
            )
        }
    }  catch (e: Exception){
        //Jika Kotlin melarang melakukan call melalui refleksi
        // --- atau ---
        //Jika terjadi error secara internal pada refleksi Java.
        // Biasanya terjadi pada operasi call melibatkan `lateinit var`
        false
    }
}

/** @return -> `true` jika operasi set berhasil,
 *   -> `false` jika refleksi dilarang,
 *   -> @throws [TypeExc] jika [value] yg dipass tidak sesuai tipe `this.extension`. */
fun <I, O> KMutableProperty1<I, O>.forcedSetTyped(receiver: I, typedValue: TypedValue<O>): Boolean{
    return try{
        if(returnType.isAssignableFrom(typedValue.type)){
            val oldIsAccesible= isAccessible
            isAccessible= true
            set(receiver, typedValue.value)
            isAccessible= oldIsAccesible
            true
        } else {
            throw TypeExc(
                expectedType = returnType.classifier as KClass<*>, actualType = typedValue.value?.clazz,
                msg = "Tidak dapat meng-assign value dg tipe tersebut ke properti: $this."
            )
        }
    } catch (e: Exception){
        //Jika Kotlin melarang melakukan call melalui refleksi
        // --- atau ---
        //Jika terjadi error secara internal pada refleksi Java.
        // Biasanya terjadi pada operasi call melibatkan `lateinit var`
        false
    }
}


 */