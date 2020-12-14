package sidev.lib.unavailable

import sidev.lib.console.prine
import sidev.lib.exception.UnavailableOperationExc

fun throwUnavailableOp(msg: String, accessedElement: Any): Nothing
        = throw UnavailableOperationExc(accessedElement = accessedElement, detailMsg = msg)