package sidev.lib.reflex.js

import sidev.lib.console.log
import sidev.lib.console.prine
import sidev.lib.reflex.SiReflex
import sidev.lib.reflex.core.SiReflexConst
import kotlin.js.json


internal fun SiReflex.putSiMetadata(key: String, data: Any): Boolean{
    return try{
        val func= jsPureFunction(this)
        var meta = func[SiReflexConst.SI_METADATA_KEY]
        if(meta == undefined){
            meta= json()
            func[SiReflexConst.SI_METADATA_KEY]= meta
        }
        meta[key]= data
        true
    } catch (e: Throwable){ false }
}

internal fun <T> getSiMetadata(element: Any, key: String): T?{
    return try{
        val meta = jsPureFunction(element)[SiReflexConst.SI_METADATA_KEY]
        if(meta != undefined)
            meta[key] as? T
        else null
    } catch (e: Throwable){ null }
}