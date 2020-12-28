package sidev.lib.text

import kotlin.jvm.JvmOverloads


expect fun Char.isDigit(): Boolean
expect fun Char.isLetter(): Boolean
//expect fun Char.isWhiteSpace(): Boolean
fun Char.isLetterOrDigit(): Boolean = isLetter() || isDigit()
fun Char.isSpecialChar(): Boolean = "[^A-Za-z0-9 ]".toRegex().find(toString()) != null


fun String.removeWhitespace(): String = replace("\\s".toRegex(), "")
@JvmOverloads
fun String.removeSurroundingWhitespace(prefixEnd: Int= -1, suffixStart: Int= -1): String {
    return if(prefixEnd <= -1 && suffixStart <= -1){
        var start= 0
        var end= length
        i@ for((i, char1) in withIndex()){
            if(char1.isWhitespace()){
                for(u in i+1 until length){
                    val char2= this[u]
                    if(!char2.isWhitespace()){
                        start= u
                        break@i
                    }
                }
            }
        }
        i@ for(i in lastIndex downTo 0){
            val char1= this[i]
            if(char1.isWhitespace()){
                for(u in i-1 downTo 0){
                    val char2= this[u]
                    if(!char2.isWhitespace()){
                        end= u+1
                        break@i
                    }
                }
            }
        }
        substring(start, end)
    } else {
        removePrefixWhitespace(prefixEnd).removeSuffixWhitespace(suffixStart)
    }
}
@JvmOverloads
fun String.removePrefixWhitespace(prefixEnd: Int= -1): String {
    return if(prefixEnd <= -1){
        var start= 0
        i@ for((i, char1) in withIndex()){
            if(char1.isWhitespace()){
                for(u in i+1 until length){
                    val char2= this[u]
                    if(!char2.isWhitespace()){
                        start= u
                        break@i
                    }
                }
            }
        }
        substring(start)
    } else {
        val prefix= substring(0, prefixEnd).removeWhitespace()
        val suffix= substring(prefixEnd)
        "$prefix$suffix"
    }
}
@JvmOverloads
fun String.removeSuffixWhitespace(suffixStart: Int= -1): String {
    return if(suffixStart <= -1){
        var end= length
        i@ for(i in lastIndex downTo 0){
            val char1= this[i]
            if(char1.isWhitespace()){
                for(u in i-1 downTo 0){
                    val char2= this[u]
                    if(!char2.isWhitespace()){
                        end= u+1
                        break@i
                    }
                }
            }
        }
        substring(0, end)
    } else {
        val prefix= substring(0, suffixStart)
        val suffix= substring(suffixStart).removeWhitespace()
        "$prefix$suffix"
    }
}