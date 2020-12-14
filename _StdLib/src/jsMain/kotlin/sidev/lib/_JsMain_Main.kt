package sidev.lib

import sidev.lib.reflex.js.properties

fun main(){
    var a: dynamic= 10
    a= ""

    a::class

    (a as Any).properties
}