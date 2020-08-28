package sidev.lib.reflex.native

import sidev.lib.reflex.SiVisibility
import java.lang.reflect.Modifier

//internal val Class<*>.si

internal fun getVisibility(javaModifiers: Int): SiVisibility = when{
    Modifier.isPublic(javaModifiers) -> SiVisibility.PUBLIC
    Modifier.isProtected(javaModifiers) -> SiVisibility.PROTECTED
    Modifier.isPrivate(javaModifiers) -> SiVisibility.PRIVATE
    else -> SiVisibility.PUBLIC
}