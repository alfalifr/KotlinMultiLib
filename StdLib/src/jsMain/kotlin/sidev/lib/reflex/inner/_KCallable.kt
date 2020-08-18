package sidev.lib.reflex.inner

import kotlin.reflect.KCallable

//TODO <11 Agus 2020> => Untuk smtr, apapun yg ada di js dapat dipanggil, karena blum ada reflection.
actual var KCallable<*>.isAccessible: Boolean
    get()= true
    set(v){}