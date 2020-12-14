@file:JvmName("_JavaReflexFun")
package sidev.lib.reflex.jvm

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