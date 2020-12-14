package sidev.lib.reflex.full

import sidev.lib.reflex.js.JsReflex


actual val Any.isNativeReflexUnit: Boolean
    get()= this is JsReflex

//TODO <23 Agustus 2020> => Pengecekan pada Js hanya dilakukan pada pengecekan tipe kelass, bkn adanya method getValue() / setValue().
internal actual val Any.isNativeDelegate: Boolean get()= false