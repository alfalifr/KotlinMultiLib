@file:JvmName("_JavaReflexFunJvm")
package sidev.lib.reflex.jvm

import sidev.lib.exception.IllegalStateExc
import sidev.lib.reflex.SiVisibility
import java.lang.reflect.Constructor
import java.lang.reflect.Executable
import java.lang.reflect.Method
import java.lang.reflect.Modifier

//internal val Class<*>.si

internal fun getVisibility(javaModifiers: Int): SiVisibility = when{
    Modifier.isPublic(javaModifiers) -> SiVisibility.PUBLIC
    Modifier.isProtected(javaModifiers) -> SiVisibility.PROTECTED
    Modifier.isPrivate(javaModifiers) -> SiVisibility.PRIVATE
    else -> SiVisibility.PUBLIC
}

///*
/**
 * Mengmabil jml parameter dari suatu [Executable] ([Method] maupun [Constructor]).
 * Property ini berguna untuk mengakomodasi pada Java 7 ke bawah.
 * Property ini meng-ekstensi [Method] bkn [Executable] karena untuk kompatibilitas Java 7 ke bawah.
 */
val Method.parameterCount_: Int
    get()= parameterTypes.size

/**
 * Mengmabil jml parameter dari suatu [Executable] ([Method] maupun [Constructor]).
 * Property ini berguna untuk mengakomodasi pada Java 7 ke bawah.
 * Property ini meng-ekstensi [Constructor] bkn [Executable] karena untuk kompatibilitas Java 7 ke bawah.
 */
val Constructor<*>.parameterCount_: Int
    get()= parameterTypes.size

/**
 * Mengambil `Package` paling dasar dari `this.extension`,
 * yaitu package yang memiliki nama terpendek.
 */
val Class<*>.rootPackage: Package
    get(){
        val thisPkgName= packageName
        val foundPkgs= classLoader.definedPackages.filter { thisPkgName.startsWith(it.name) }
        if(foundPkgs.isEmpty())
            throw IllegalStateExc(
                stateOwner = this.kotlin,
                currentState = "foundPkgs.size == 0",
                expectedState = "foundPkgs.size > 0",
                detMsg = "Seharusnya `ClassLoader` dari `this` ($this) memuat package dari `this`, namun tidak. \nOleh karena itu terjadi kesalahan internal sistem."
            )
        if(foundPkgs.size == 1) return foundPkgs[0]
        return foundPkgs.reduce { acc, p -> if(acc.name.length <= p.name.length) acc else p }
    }


@get:JvmName("isPrimitiveWrapper")
val Class<*>.isPrimitiveWrapper: Boolean get()= //_JavaReflexFun_java.isPrimitiveWrapper(this)
///*
    when(this){
        Int::class.javaObjectType -> true
        Long::class.javaObjectType -> true
//        String::class.javaObjectType -> true
        Float::class.javaObjectType -> true
        Double::class.javaObjectType -> true
        Char::class.javaObjectType -> true
        Short::class.javaObjectType -> true
        Boolean::class.javaObjectType -> true
        Byte::class.javaObjectType -> true
        else -> false
/*
        Integer::class.javaObjectType -> true
        java.lang.Long::class.javaObjectType -> true
        java.lang.Float::class.javaObjectType -> true
        java.lang.Double::class.javaObjectType -> true
        Character::class.javaObjectType -> true
        java.lang.Short::class.javaObjectType -> true
        java.lang.Boolean::class.javaObjectType -> true
        java.lang.Byte::class.javaObjectType -> true
        else -> false
*/
    }

@get:JvmName("isPrimitiveLike")
val Class<*>.isPrimitiveLike: Boolean
    get()= isPrimitive || isPrimitiveWrapper